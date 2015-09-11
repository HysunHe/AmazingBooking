/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with AsiaInspection. This 
 * information shall not be distributed or copied without written 
 * permission from the AsiaInspection.
 *
 ***************************************************************************/

package com.ai.hackathon.amazingbooking;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;

import org.apache.commons.lang.StringUtils;

import com.ai.consts.MailConstant;
import com.ai.hackathon.amazingbooking.bean.MailContent;
import com.ai.hackathon.amazingbooking.bean.OrderBean;
import com.ai.hackathon.amazingbooking.consts.Consts;
import com.ai.hackathon.amazingbooking.dao.OrderDao;
import com.ai.hackathon.amazingbooking.parser.MailParser;
import com.ai.hackathon.amazingbooking.parser.POP3MailParser;
import com.ai.hackathon.amazingbooking.utils.OrderUtils;
import com.ai.hackathon.amazingbooking.utils.ServiceCallUtil;
import com.ai.userservice.common.bean.ServiceCallResult;
import com.ai.userservice.common.util.HttpUtil;

/***************************************************************************
 * <PRE>
 *  Project Name    : AmazingBooking
 * 
 *  Package Name    : com.ai.hackathon.amazingbooking
 * 
 *  File Name       : Main.java
 * 
 *  Creation Date   : 2015年9月10日
 * 
 *  Author          : Hysun He
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class Main {
	/**
	 * Load configuration file pop3-imap.properties.
	 */
	private static Properties loadConfiguration() {
		Properties props = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = MailParser.class.getClassLoader()
					.getResourceAsStream("pop3-imap.properties");
			props.load(inputStream);
			return props;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					inputStream = null;
				}
			}
		}
	}

	/**
	 * @param props
	 * @throws Exception
	 */
	public static List<MailContent> listenAndParse(Properties props)
			throws Exception {
		Session session = Session.getDefaultInstance(props);
		session.setDebug(false);
		Store store = session.getStore("imap");
		store.connect(props.getProperty("mail.server"),
				props.getProperty("mail.user"),
				props.getProperty("mail.password"));

		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message messages[] = inbox.search(new FlagTerm(new Flags(
				Flags.Flag.SEEN), false));
		System.out.println("* Got unread messages: " + messages.length);

		final List<MailContent> mails = new ArrayList<>();
		for (int i = 0; i < messages.length; i++) {
			MimeMessage mimeMessage = (MimeMessage) messages[i];
			String subject = mimeMessage.getSubject();
			if (subject == null
					|| !subject.toUpperCase().contains(Consts.SUBJECT_KEYWORD)) {
				continue;
			}
			MailParser mailParser = new POP3MailParser(mimeMessage, props);
			MailContent mailObject = mailParser.parse();
			if (mailObject != null) {
				mails.add(mailObject);
				System.out.println("* Got mail: " + mailObject);
			}
			MimeMessage copy = new MimeMessage(mimeMessage);
			System.out.println("* Mark the mail as read: " + copy.getSubject());
		}
		inbox.close(false);
		store.close();

		return mails;
	}

	/**
	 * @param origMail
	 * @param orderNo
	 */
	private static void sendSuccessMail(Properties props, MailContent origMail,
			String orderNo) {
		final String service = props.getProperty("mail.service.url")
				+ "/mailx/send-html";

		// TODO: Use mail template to eliminate hard coded info.
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MailConstant.API.TO, origMail.getSenderMail().split("\\,"));
		params.put(MailConstant.API.SENDER_NAME, "AmazingBooking System");
		params.put(MailConstant.API.SENDER_MAIL, "noreply@asiainspection.com");

		if (StringUtils.isNotEmpty(origMail.getCc())) {
			params.put(MailConstant.API.CC, origMail.getCc().split("\\,"));
		}

		params.put(MailConstant.API.DESCRIPTION,
				"Our system successfully placed the order " + orderNo
						+ " for you.");
		params.put(MailConstant.API.SUBJECT, "Your new order " + orderNo);
		params.put(MailConstant.API.TEXT_BODY,
				"Your client does not support for html format mail!");
		params.put("RUN_IMMEDIATELY", Boolean.TRUE);
		params.put("DISCARD_ON_FAIL", Boolean.TRUE);
		String bodyHtml = "Dear Mr. ABC"
				+ " <p>Our AmazingBooking system has successfully placed the order for you, the order number is "
				+ orderNo + "</p>" + "<p>Thanks for using our service,</p>"
				+ "<p><B>The Amazing Hackathon Team</B></p>";
		params.put("HTML_BODY", bodyHtml);

		boolean success = ServiceCallUtil.callPost(service, params);
		if (success) {
			System.out.println("* Notification mail sent successfully.");
		}
	}

	/**
	 * @param props
	 * @param orderBean
	 */
	private static void updatePriceInfo(Properties props, OrderBean orderBean) {
		final String psiService = props.getProperty("inspection.service.url");
		String orderId = null; // TODO

		final String url = psiService
				+ "/order/mix/calculate-price/{orderId}".replace("{orderId}",
						orderId) + "?who=AmazingBookingSystem";
		boolean success = ServiceCallUtil.callPost(url, null);
		if (success) {
			System.out.println("* Notification mail sent successfully.");
		}
	}

	/**
	 * @param origMail
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	private static String placeOrder(MailContent origMail) throws IOException,
			SQLException {
		String orderNo = "TODO";

		String[] attachments = origMail.getAttachments();
		InputStream is = new FileInputStream(attachments[0]);
		OrderBean orderBean = OrderUtils.toOrderBean(is);

		OrderDao orderDao = new OrderDao();
		orderBean = orderDao.save(orderBean);
		
		orderNo = orderBean.getOrderNo();

		return orderNo;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		final Properties props = loadConfiguration();
		while (true) {
			System.out.println("* Checking new order request...");

			try {
				// Check if there are new order request (by mail)
				List<MailContent> mails = listenAndParse(props);
				System.out.println("* New Request Received: " + mails.size());

				for (MailContent mail : mails) {
					// Generate an new order and add it to Oracle DB.
					String orderNo = placeOrder(mail);

					// Send notification mail to client.
					sendSuccessMail(props, mail, orderNo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Sleep for 15 seconds before the next checking.
			try {
				Thread.sleep(15 * 1000L);
			} catch (InterruptedException e) {
				System.err.println("! Main thread is interrupted.");
				System.exit(1);
			}
		}
	}
}
