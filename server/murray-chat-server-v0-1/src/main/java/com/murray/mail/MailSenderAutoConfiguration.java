//package com.murray.mail;
//
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.mail.MailProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Conditional;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.mail.MailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.util.MimeType;
//
//import javax.mail.Session;
//import javax.mail.internet.MimeMessage;
//
//@Configuration
//@ConditionalOnClass({ MimeMessage.class, MimeType.class })
//@ConditionalOnMissingBean(MailSender.class)
//@Conditional(MailSenderCondition.class)
//@EnableConfigurationProperties(MailProperties.class)
//@Import(JndiSessionConfiguration.class)
//public class MailSenderAutoConfiguration {
//
//    private final MailProperties properties;
//
//	private final Session session;
//
//	public MailSenderAutoConfiguration(MailProperties properties,
//			ObjectProvider<Session> session) {
//		this.properties = properties;
//		this.session = session.getIfAvailable();
//	}
//
//	@Bean
//	public JavaMailSenderImpl mailSender() {
//		JavaMailSenderImpl sender = new JavaMailSenderImpl();
//		if (this.session != null) {
//			sender.setSession(this.session);
//		}
//		else {
//			applyProperties(sender);
//		}
//		return sender;
//	}
//
//    //other code...
//}