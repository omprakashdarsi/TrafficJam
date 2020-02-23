package test.Resources.Generic.TrafficJam;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.testng.annotations.AfterTest;


public class EmailReport extends WebLibrary {
	Zipper zipper = new Zipper();

	@AfterTest
	public void ReportEmail(String hostName) throws Exception {
		String sendMail = getProps1("emailReport");
		if (sendMail.equalsIgnoreCase("yes")) {

			dateName = new SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date());
			String host = "smtp.gmail.com";
			String Password = "raj9952688494";
			String from = "raj.sriselvan@kaaylabs.com";
			String toAddress = getProps1("emailTo");
			String filename = "./target/TextLogs/" + hostName + ".html";
			String filename1 = "./target/TextLogs/" + hostName + "1.html";
			String screens = "./target/Screenshot/" + hostName + ".zip";
			Properties props = System.getProperties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			Session session = Session.getInstance(props, null);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("raj.sriselvan@kaaylabs.com"));
			message.setRecipients(Message.RecipientType.TO, toAddress);
			message.setSubject("Today's Report (" + dateName + ") for " + hostName);

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("Please Find the attachment for Test Logs of " + hostName);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			BodyPart messageBodyPart1 = new MimeBodyPart();
			DataSource source = new FileDataSource(filename);
			messageBodyPart1.setDataHandler(new DataHandler(source));
			messageBodyPart1.setFileName(hostName + ".html");
			multipart.addBodyPart(messageBodyPart1);

			BodyPart messageBodyPart3 = new MimeBodyPart();
			DataSource source2 = new FileDataSource(filename1);
			messageBodyPart3.setDataHandler(new DataHandler(source2));
			messageBodyPart3.setFileName(hostName + "1.html");
			multipart.addBodyPart(messageBodyPart3);

			String normal = getProps1("normalScreenShot");
			String fullScreen = getProps1("fullPageScreenShot");

			Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
			Date date = calendar.getTime();
			String day = date.toString();

			if (normal.equalsIgnoreCase("yes")) {
				String folderToZip = "./target/Screenshot/" + hostName;
				String zipName = "./target/Screenshot/" + hostName + ".zip";
				zipper.zip(folderToZip, zipName);
				BodyPart messageBodyPart2 = new MimeBodyPart();
				DataSource source1 = new FileDataSource(screens);
				messageBodyPart2.setDataHandler(new DataHandler(source1));
				messageBodyPart2.setFileName(hostName + "-Screens.zip");
				multipart.addBodyPart(messageBodyPart2);
			}

			if (fullScreen.equalsIgnoreCase("yes") && day.contains("Thu")) {
				String folderToZip = "./target/Screenshot/" + hostName;
				String zipName = "./target/Screenshot/" + hostName + ".zip";
				zipper.zip(folderToZip, zipName);
				BodyPart messageBodyPart2 = new MimeBodyPart();
				DataSource source1 = new FileDataSource(screens);
				messageBodyPart2.setDataHandler(new DataHandler(source1));
				messageBodyPart2.setFileName(hostName + "-Screens.zip");
				multipart.addBodyPart(messageBodyPart2);
			}
			message.setContent(multipart);

			try {
				Transport tr = session.getTransport("smtps");
				tr.connect(host, from, Password);
				tr.sendMessage(message, message.getAllRecipients());
				System.out.println("Mail Sent Successfully");
				tr.close();
			} catch (SendFailedException e) {
				log.info(e);
			}
		}
	}
}
