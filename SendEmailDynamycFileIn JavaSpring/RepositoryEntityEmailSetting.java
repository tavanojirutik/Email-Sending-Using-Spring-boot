//Repository File
package intelli.uno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import intelli.uno.entity.EntityEmailSetting;
import jakarta.transaction.Transactional;

	public interface RepositoryEntityEmailSetting extends JpaRepository<EntityEmailSetting, Long>{
	
	
		@Query("SELECT eem FROM EntityEmailSetting eem")
		EntityEmailSetting getAllEmailSetting();
		
		@Modifying
		@Query("UPDATE EntityEmailSetting ees SET ees.usernameEsm=:username , ees.passwordEsm=:password , "
				+ "ees.senderEsm=:sender ,ees.serverEsm=:server,ees.portEsm=:port , ees.encodingtypeEsm=:encodingtype , ees.authenticationflagEsm=:authenticationflag , ees.sslEsm=:sslflag")
		void settingUpdate(@Param("username") String username ,@Param("password") String password , @Param("sender") String sender,
				@Param("server") String server , @Param("port") String port , @Param("encodingtype") String encodingtype , @Param("authenticationflag") String authenticationflag , @Param("sslflag") String sslflag);
		
		@Modifying
		@Transactional
		@Query("UPDATE EntityEmailSetting ees SET ees.receiverEsm=:receiver WHERE ees.esettingidEsm=:id")
		void senderUpdate(@Param("receiver") String receiver, @Param("id") long id);
		
			

	}
