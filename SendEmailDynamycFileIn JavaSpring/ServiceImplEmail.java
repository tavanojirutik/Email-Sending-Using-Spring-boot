//service File
package intelli.uno.service.entityengineermaster;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.Session;
import jakarta.mail.Transport;

import intelli.uno.entity.EntityEmailSetting;
import intelli.uno.repository.RepositoryEntityEmailSetting;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

	@Service
	public class ServiceImplEmail implements ServiceEmail {
	
		@Autowired
		private RepositoryEntityEmailSetting repositoryEntityEmailSetting;
	
		@Bean
		public JavaMailSender javaMailSender() {
			// Configure and return JavaMailSender bean
			return new JavaMailSenderImpl();
		}

	@Override
	public String sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendMail1(String to, String[] cc, String subject, String body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendMail2(String from, String to, String subject, String body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendMail3(String to, String subject, String body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendMail5(String to, String subject, String body, String dear) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendMail6(String to, String subject, String body, String dear) {
	    EntityEmailSetting emailSetting = repositoryEntityEmailSetting.findById(1L).orElse(null);
	    String protocolEsmValue = emailSetting.getProtocolEsm();
	    if (emailSetting != null) {
	        try {
	            JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
	            mailSenderImpl.setHost(emailSetting.getServerEsm());
	            mailSenderImpl.setPort(Integer.parseInt(emailSetting.getPortEsm()));
	            mailSenderImpl.setUsername(emailSetting.getUsernameEsm());
	            mailSenderImpl.setPassword(emailSetting.getPasswordEsm());

	            System.out.println("Host " + emailSetting.getServerEsm());
	            System.out.println("Port " + emailSetting.getPortEsm());
	            System.out.println("Username " + emailSetting.getUsernameEsm());
	            System.out.println("password " + emailSetting.getPasswordEsm());

	            Properties props = new Properties();
	            props.setProperty("mail.smtp.auth", "true");
	            props.setProperty("mail.smtp.starttls.enable", "true");
	            props.setProperty("mail.smtp.host", emailSetting.getServerEsm());
	            props.setProperty("mail.smtp.port", emailSetting.getPortEsm());
	            props.put("mail.smtp.ssl.enable", "false	");

	            if (protocolEsmValue.equals("Y")) {
	                props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	                props.put("mail.smtp.ssl.trust", emailSetting.getServerEsm());
	                props.put("mail.smtp.ssl.debug","true");	            
	                }
	            props.put("mail.user", emailSetting.getUsernameEsm());
	            props.put("mail.password", emailSetting.getPasswordEsm());

	            System.err.println("This Field No Problem ::");

	            Session mailSession = Session.getInstance(props, new Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(emailSetting.getUsernameEsm(), emailSetting.getPasswordEsm());
	                }
	            });

	            Message message = new MimeMessage(mailSession);
	            // Set the "From" address using the authenticated email address
	            message.setFrom(new InternetAddress(emailSetting.getUsernameEsm()));
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	            message.setSubject(subject);
	            message.setText(body);
	            Transport.send(message);

	            System.out.println("Email Send Perfect ");

	            return "Mail sent successfully.";
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.err.println("Email is Not Send");
	            throw new RuntimeException("Failed to send email", e);
	        }
	    } else {
	        return "Email settings not found or invalid.";
	    }
	}

	@Override
	public String sendMail7(String to, String subject, String body) {
	    EntityEmailSetting emailSetting = repositoryEntityEmailSetting.findById(1L).orElse(null);
	    String protocolEsmValue = emailSetting.getProtocolEsm();
	    if (emailSetting != null) {
	        try {
	            JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
	            mailSenderImpl.setHost(emailSetting.getServerEsm());
	            mailSenderImpl.setPort(Integer.parseInt(emailSetting.getPortEsm()));
	            mailSenderImpl.setUsername(emailSetting.getUsernameEsm());
	            mailSenderImpl.setPassword(emailSetting.getPasswordEsm());

	            System.out.println("Host " + emailSetting.getServerEsm());
	            System.out.println("Port " + emailSetting.getPortEsm());
	            System.out.println("Username " + emailSetting.getUsernameEsm());
	            System.out.println("password " + emailSetting.getPasswordEsm());

	            Properties props = new Properties();
	            props.setProperty("mail.smtp.auth", "true");
	            props.setProperty("mail.smtp.starttls.enable", "true");
	            props.setProperty("mail.smtp.host", emailSetting.getServerEsm());
	            props.setProperty("mail.smtp.port", emailSetting.getPortEsm());
	            props.put("mail.smtp.ssl.enable", "false");
	            //props.put("mail.debug", "true");

	            if (protocolEsmValue.equals("Y")) {
	                //props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	                props.put("mail.smtp.ssl.trust", emailSetting.getServerEsm());
	            }
	            props.put("mail.user", emailSetting.getUsernameEsm());
	            props.put("mail.password", emailSetting.getPasswordEsm());

	            mailSenderImpl.setJavaMailProperties(props);

	            Session mailSession = Session.getInstance(props, new Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(emailSetting.getUsernameEsm(), emailSetting.getPasswordEsm());
	                }
	            });

	            Message message = new MimeMessage(mailSession);
	            
	            message.setFrom(new InternetAddress(emailSetting.getUsernameEsm()));

	            
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailSetting.getReceiverEsm()));
	            message.setSubject(subject);
	            message.setText(body);
	    
	            Transport.send(message);
	            
	            System.out.println(message	);
	            

	            System.out.println("Email Send Perfect ");

	            return "Mail sent successfully.";
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.err.println("Email is Not Send");
	            throw new RuntimeException("Failed to send email", e);
	        }
	    } else {
	        return "Email settings not found or invalid.";
	    }
	}


	@Override
	@Transactional
	public void updateEmailSettings(String username, String password, String sender, String server, String port,
			String encodingtype, String encodingflag, String sslflag) {
		this.repositoryEntityEmailSetting.settingUpdate(username, password, sender, server, port, encodingtype,
				encodingflag, sslflag);
	}

	@Override
	@Transactional
	public void emailservice(String receiver, long id) {
		 repositoryEntityEmailSetting.senderUpdate(receiver, id);
	}

	@Override
	public EntityEmailSetting getAllEmailSetting() {

		return repositoryEntityEmailSetting.getAllEmailSetting();
	}

}
/*
 * @Autowired(required=true) private JavaMailSender javaMailSender;
 * 
 * @Autowired private ServiceEmailSettingService2 emailSettingService2;
 * 
 * 
 * private String fromEmail;
 * 
 * 
 * 
 * @Override public String sendMail(MultipartFile[] file, String to, String[]
 * cc, String subject, String body) { try { MimeMessage mimeMessage =
 * javaMailSender.createMimeMessage(); MimeMessageHelper mimeMessageHelper = new
 * MimeMessageHelper(mimeMessage, true); mimeMessageHelper.setFrom(fromEmail);
 * mimeMessageHelper.setTo(to); mimeMessageHelper.setCc(cc);
 * mimeMessageHelper.setSubject(subject); mimeMessageHelper.setText(body);
 * 
 * for (int i = 0; i < file.length; i++) { mimeMessageHelper.addAttachment(
 * file[i].getOriginalFilename(), new ByteArrayResource(file[i].getBytes()) ); }
 * 
 * javaMailSender.send(mimeMessage);
 * 
 * return "mail send"; } catch (MessagingException | IOException e) {
 * e.printStackTrace(); throw new RuntimeException("Failed to send email", e); }
 * }
 * 
 * 
 * 
 * @Override public String sendMail1(String to, String[] cc, String subject,
 * String body) { try { MimeMessage mimeMessage =
 * javaMailSender.createMimeMessage(); MimeMessageHelper mimeMessageHelper = new
 * MimeMessageHelper(mimeMessage, true); mimeMessageHelper.setFrom(fromEmail);
 * mimeMessageHelper.setTo(to); mimeMessageHelper.setCc(cc);
 * mimeMessageHelper.setSubject(subject); mimeMessageHelper.setText(body);
 * 
 * 
 * javaMailSender.send(mimeMessage);
 * 
 * return "mail send"; } catch (MessagingException e1) { e1.printStackTrace();
 * throw new RuntimeException("Failed to send email", e1); } }
 * 
 * 
 * 
 * @Override public String sendMail3(String to, String subject, String body) {
 * try { MimeMessage mimeMessage = javaMailSender.createMimeMessage();
 * MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
 * true); mimeMessageHelper.setFrom(fromEmail); mimeMessageHelper.setTo(to);
 * //mimeMessageHelper.setCc(cc); mimeMessageHelper.setSubject(subject);
 * mimeMessageHelper.setText(body);
 * 
 * 
 * javaMailSender.send(mimeMessage);
 * 
 * return "mail send"; } catch (MessagingException e1) { e1.printStackTrace();
 * throw new RuntimeException("Failed to send email", e1); } }
 * 
 * @Override public String sendMail2(String from,String to, String subject,
 * String body) { try { MimeMessage mimeMessage =
 * javaMailSender.createMimeMessage(); MimeMessageHelper mimeMessageHelper = new
 * MimeMessageHelper(mimeMessage, true); mimeMessageHelper.setFrom(from);
 * mimeMessageHelper.setTo(to); mimeMessageHelper.setSubject(subject);
 * mimeMessageHelper.setText(body);
 * 
 * 
 * javaMailSender.send(mimeMessage);
 * 
 * return "mail send"; } catch (MessagingException e1) { e1.printStackTrace();
 * throw new RuntimeException("Failed to send email", e1); } }
 * 
 * @Override public String sendMail5(String to, String subject, String body,
 * String dear) { try { MimeMessage mimeMessage =
 * javaMailSender.createMimeMessage(); MimeMessageHelper mimeMessageHelper = new
 * MimeMessageHelper(mimeMessage, true); mimeMessageHelper.setFrom(fromEmail);
 * mimeMessageHelper.setTo(to); mimeMessageHelper.setSubject(subject);
 * 
 * // Concatenate all parts of the email body into a single string String
 * fullBody = dear + "<br/><br/>" + body;
 * 
 * // Use setText(fullBody, true) for HTML content
 * mimeMessageHelper.setText(fullBody, true);
 * 
 * javaMailSender.send(mimeMessage);
 * 
 * return "Mail sent successfully."; } catch (MessagingException e1) {
 * e1.printStackTrace(); throw new RuntimeException("Failed to send email", e1);
 * } }
 * 
 * 
 * 
 * @Override public String sendMail6(String to, String subject, String body,
 * String dear) {
 * 
 * emailSettingService2.setMailPropertiesDynamically();
 * 
 * try { MimeMessage mimeMessage = javaMailSender.createMimeMessage();
 * MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
 * true); mimeMessageHelper.setFrom(fromEmail); mimeMessageHelper.setTo(to);
 * mimeMessageHelper.setSubject(subject);
 * 
 * String fullBody = dear + "<br/><br/>" + body;
 * mimeMessageHelper.setText(fullBody, true);
 * 
 * javaMailSender.send(mimeMessage);
 * 
 * return "Mail sent successfully."; } catch (MessagingException e) {
 * e.printStackTrace(); throw new RuntimeException("Failed to send email", e); }
 * }
 */
