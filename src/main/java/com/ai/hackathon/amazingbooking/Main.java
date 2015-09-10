/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with AsiaInspection. This 
 * information shall not be distributed or copied without written 
 * permission from the AsiaInspection.
 *
 ***************************************************************************/

package com.ai.hackathon.amazingbooking;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;

import com.ai.hackathon.amazingbooking.bean.MailContent;
import com.ai.hackathon.amazingbooking.consts.Consts;
import com.ai.hackathon.amazingbooking.parser.MailParser;
import com.ai.hackathon.amazingbooking.parser.POP3MailParser;

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
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		final Properties props = loadConfiguration();
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

		for (int i = 0; i < messages.length; i++) {
			MimeMessage mimeMessage = new MimeMessage((MimeMessage) messages[i]);
			String subject = mimeMessage.getSubject();
			System.out.println("* Got mail / subject: " + subject);

			if (subject != null
					&& subject.toUpperCase().contains(Consts.SUBJECT_KEYWORD)) {
				MailParser mailParser = new POP3MailParser(mimeMessage, props);
				MailContent mailObject = mailParser.parse();
				System.out.println("* Got mail: " + mailObject);
				break;
			}
		}

		inbox.close(false);
		store.close();
	}
}
