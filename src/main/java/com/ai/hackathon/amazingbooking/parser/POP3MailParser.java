/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with AsiaInspection. This 
 * information shall not be distributed or copied without written 
 * permission from the AsiaInspection.
 *
 ***************************************************************************/

package com.ai.hackathon.amazingbooking.parser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import com.ai.commons.DateUtils;
import com.ai.commons.StringUtils;
import com.ai.hackathon.amazingbooking.bean.MailContent;
import com.ai.hackathon.amazingbooking.utils.FileUtil;

/***************************************************************************
 * <PRE>
 *  Project Name    : AmazingBooking
 * 
 *  Package Name    : com.ai.hackathon.amazingbooking.parser
 * 
 *  File Name       : POP3MailParser.java
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
public class POP3MailParser implements MailParser {
	private final MimeMessage mimeMessage;
	private final Properties props;
	private final StringBuilder body;
	private final List<String> attachments;

	public POP3MailParser(MimeMessage mimeMessage, Properties props) {
		this.mimeMessage = mimeMessage;
		this.body = new StringBuilder();
		this.attachments = new ArrayList<>();
		this.props = props;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private String getFrom() throws Exception {
		InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
		String from = address[0].getAddress();
		String personal = address[0].getPersonal();
		String fromAddr = "";
		if (personal != null && from != null) {
			fromAddr = personal + "<" + from + ">";
		} else if (personal == null) {
			fromAddr = from;
		} else {
			fromAddr = personal;
		}
		return fromAddr;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private String getSender() throws Exception {
		InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
		String personal = address[0].getPersonal();
		return personal;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private String getSenderMail() throws Exception {
		InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
		String fromMail = address[0].getAddress();
		return fromMail;
	}

	/**
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private String getMailAddress(String type) throws Exception {
		InternetAddress[] address = null;
		if (type.equalsIgnoreCase("TO")) {
			address = (InternetAddress[]) mimeMessage
					.getRecipients(Message.RecipientType.TO);
		} else if (type.equalsIgnoreCase("CC")) {
			address = (InternetAddress[]) mimeMessage
					.getRecipients(Message.RecipientType.CC);
		} else if (type.equalsIgnoreCase("BCC")) {
			address = (InternetAddress[]) mimeMessage
					.getRecipients(Message.RecipientType.BCC);
		} else {
			throw new IllegalArgumentException("Unknown type: " + type);
		}

		if (address == null) {
			return "";
		}

		String mailAddr = "";
		for (int i = 0; i < address.length; i++) {
			String emailAddr = address[i].getAddress();
			if (emailAddr != null) {
				emailAddr = MimeUtility.decodeText(emailAddr);
			}
			mailAddr += "," + emailAddr;
		}
		return mailAddr.substring(1);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private String getSubject() throws Exception {
		String subject = null;
		try {
			MimeUtility.decodeText(mimeMessage.getSubject());
		} catch (Exception e) {
			System.err.println("Subject" + e);
		}
		return subject == null ? "" : subject;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private Date getSentDate() throws Exception {
		Date sentDate = mimeMessage.getSentDate();
		return sentDate;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private String getBodyString() throws Exception {
		this.parseMailBody((Part) mimeMessage);
		return body.toString();
	}

	/**
	 * @param part
	 * @throws Exception
	 */
	private void parseMailBody(Part part) throws Exception {
		String contentType = part.getContentType();
		int nameIndex = contentType.indexOf("name");
		boolean conName = false;
		if (nameIndex != -1) {
			conName = true;
		}

		if (part.isMimeType("text/plain") && conName == false) {
			body.append((String) part.getContent());
		} else if (part.isMimeType("text/html") && conName == false) {
			body.append((String) part.getContent());
		} else if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int counts = multipart.getCount();
			for (int i = 0; i < counts; i++) {
				parseMailBody(multipart.getBodyPart(i));
			}
		} else if (part.isMimeType("message/rfc822")) {
			parseMailBody((Part) part.getContent());
		}
	}

	/**
	 * @param part
	 * @throws Exception
	 */
	private void handleAttachMent(Part part) throws Exception {
		if (part.isMimeType("multipart/*")) {
			readAndSaveAttachment((Multipart) part.getContent());
		} else if (part.isMimeType("message/rfc822")) {
			handleAttachMent((Part) part.getContent());
		}
	}

	/**
	 * @param mp
	 */
	private void readAndSaveAttachment(Multipart mp) throws Exception {
		for (int i = 0; i < mp.getCount(); i++) {
			BodyPart mPart = mp.getBodyPart(i);
			String disposition = mPart.getDisposition();
			if ((disposition != null)
					&& ((disposition.equals(Part.ATTACHMENT)) || (disposition
							.equals(Part.INLINE)))) {
				String fileName = mPart.getFileName();
				if (fileName != null
						&& (fileName.toLowerCase().indexOf("gb2312") != -1)) {
					fileName = MimeUtility.decodeText(fileName);
				}
				String path = saveAttachment(fileName, mPart.getInputStream());
				if (StringUtils.isNotEmpty(path)) {
					this.attachments.add(path);
				}
			} else if (mPart.isMimeType("multipart/*")) {
				handleAttachMent(mPart);
			} else {
				String fileName = mPart.getFileName();
				if ((fileName != null)
						&& (fileName.toLowerCase().indexOf("gb2312") != -1)) {
					fileName = MimeUtility.decodeText(fileName);
				}
				String path = saveAttachment(fileName, mPart.getInputStream());
				if (StringUtils.isNotEmpty(path)) {
					this.attachments.add(path);
				}
			}
		}
	}

	/**
	 * @param fileName
	 * @param in
	 * @throws Exception
	 */
	private String saveAttachment(String fileName, InputStream in)
			throws Exception {
		if (StringUtils.isBlank(fileName)) {
			return null;
		}

		String storeDir = props.getProperty("file.temp.dir") + File.separator
				+ DateUtils.date2String(DateUtils.now(), "yyyyMMddHHmmssSSS");
		FileUtil.mkdirs(storeDir);

		String filePath = storeDir + File.separator + fileName;
		File storeFile = new File(filePath);

		try (BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(storeFile));
				BufferedInputStream bis = new BufferedInputStream(in)) {
			int c;
			while ((c = bis.read()) != -1) {
				bos.write(c);
			}
			bos.flush();

			return filePath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return
	 */
	private String[] getAttachments() throws Exception {
		this.handleAttachMent((Part) mimeMessage);
		return this.attachments.toArray(new String[this.attachments.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ai.hackathon.amazingbooking.parser.MailParser#parser(javax.mail.internet
	 * .MimeMessage)
	 */
	public MailContent parse() {
		try {
			String from = this.getFrom();
			String sender = this.getSender();
			String senderMail = this.getSenderMail();
			String to = this.getMailAddress("TO");
			String cc = this.getMailAddress("CC");
			String bcc = this.getMailAddress("BCC");
			String subject = this.getSubject();
			Date sentDate = this.getSentDate();
			String bodyString = this.getBodyString();
			String[] attachedFiles = this.getAttachments();

			MailContent mailObject = new MailContent();
			mailObject.setFrom(from);
			mailObject.setSenderName(sender);
			mailObject.setSenderMail(senderMail);
			mailObject.setSubject(subject);
			mailObject.setSentDate(sentDate);
			mailObject.setTo(to);
			mailObject.setCc(cc);
			mailObject.setBcc(bcc);
			mailObject.setBody(bodyString);
			mailObject.setAttachments(attachedFiles);
			return mailObject;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
