package intelli.uno.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "emailsettingsmst")
public class EntityEmailSetting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long esettingidEsm;
	
	@Column(name="sender_esm")
	private String senderEsm;
	
	@Column(name="receiver_esm")
	private String receiverEsm;

	@Column(name="port_esm")
	private String portEsm;
	
	@Column(name="server_esm")
	private String serverEsm;
	
	@Column(name ="username_esm")
	private String usernameEsm;
	
	@Column(name="password_esm")
	private String passwordEsm;
	
	@Column(name ="authenticationflag_esm")
	private String authenticationflagEsm;
	
	@Column(name="encodingtype_esm")
	private String encodingtypeEsm;
	
	//@Column(name="popport_esm")
	//private String popportEsm;
	
	@Column(name="popserver_esm")
	private String popserverEsm;
	
	//@Column(name="pusername_esm")
	//private String pusernameEsm;
	
	//@Column(name="ppassword_esm")
	//private String ppasswordEsm;
	
	//private String MailServerErrorEsm;
	@Column(name="ssl_esm")
	private String sslEsm;
	
	@Column(name="protocol_esm")
	private String protocolEsm;
	
	
//	public void setEncodingtypeEsm(String encodingtypeEsm) {
//        this.encodingtypeEsm = encodingtypeEsm;
//    }
	
	
}
