//Service File
package intelli.uno.service.entityengineermaster;

import org.springframework.web.multipart.MultipartFile;

import intelli.uno.entity.EntityEmailSetting;

public interface ServiceEmail {
	String sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body);

	String sendMail1(String to, String[] cc, String subject, String body);

	String sendMail2(String from, String to, String subject, String body);

	public String sendMail3(String to, String subject, String body);
	
	String sendMail5(String to, String subject, String body, String dear);
	
	public String sendMail6(String to, String subject, String body, String dear);
	
	public String sendMail7(String to, String subject, String body);
	
	void updateEmailSettings(String username, String password, String sender, String server, String port,String encodingtype,String encodingflag , String sslflag);
	
	void emailservice(String sender ,long id);
	
	 EntityEmailSetting getAllEmailSetting();

	
	
}
