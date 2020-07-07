package org.ashishjangir.springredditclone.service;

import org.ashishjangir.springredditclone.exception.SpringRedditException;
import org.ashishjangir.springredditclone.model.NotificationEmail;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
class MailService {

	private final JavaMailSender mailSender;
	private final MailContentBuilder mailContentBuilder;
	
	public MailService(JavaMailSender mailSender, MailContentBuilder mailContentBuilder) {
		super();
		this.mailSender = mailSender;
		this.mailContentBuilder = mailContentBuilder;
	}

	void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("springreddit@email.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			throw new SpringRedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient());
		}
	}

}