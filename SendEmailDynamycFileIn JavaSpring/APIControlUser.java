//API Create Task its main Class to Implement email Feature 
package intelli.uno.apicontrol;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import intelli.uno.dto.DtoCombinedTaskData;
import intelli.uno.dto.DtoEmailLogMaster;
import intelli.uno.dto.DtoIncidentMaster;
import intelli.uno.entity.EntityEmaiilLogMaster;
import intelli.uno.entity.EntityEmailbackgroundService;
import intelli.uno.entity.EntityIncidentMaster;
import intelli.uno.entity.EntityTaskLogMaster;
import intelli.uno.repository.RepositoryEmailBackground;
import intelli.uno.repository.RepositoryEmailMaster;
import intelli.uno.repository.RepositoryEntityEngineerMaster;
import intelli.uno.repository.RepositoryEntityIncidentMaster;
import intelli.uno.service.entityengineermaster.EmailBackground;
import intelli.uno.service.entityengineermaster.ServiceEmail;
import intelli.uno.service.entityengineermaster.ServiceEntityEngineerMaster;
import intelli.uno.service.entityengineermaster.ServiceEntityIncidentMaster;

@RestController

public class APIControlUser {

	@Autowired
	private ServiceEntityIncidentMaster serviceEntityIncidentMaster;

	@Autowired
	private ServiceEntityEngineerMaster serviceEntityEngineerMaster;

	@Autowired
	private RepositoryEntityIncidentMaster repositoryEntityIncidentMaster;

	@Autowired
	private RepositoryEntityEngineerMaster repositoryEntityEngineerMaster;

	@Autowired
	private RepositoryEmailMaster repositoryEmailMaster;

	@Autowired
	private ServiceEmail emailService;

	@Autowired
	private RepositoryEmailBackground repositoryEmailBackground;

	@Autowired
	private EmailBackground emailBackground;

	@Autowired
	private EntityIncidentMaster entityIncidentMaster;

	@Autowired
	private EntityEmaiilLogMaster entityEmaiilLogMaster;

	// Get Login ID
	private String getEngineerFromAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginID = "";
		if (authentication != null && authentication.isAuthenticated()
				&& !(authentication instanceof AnonymousAuthenticationToken)) {
			loginID = authentication.getName();
		} else {
		}

		return repositoryEntityEngineerMaster.findEngineerFirstAndLastnameByLoginId(loginID).replace(",", " ");
	}

	// Create new Task handler
	// Code by Vasim
	/*
	 * @PostMapping("/createnewtask")
	 * public ResponseEntity<String> createTask(@RequestBody DtoIncidentMaster
	 * dtoIncidentMaster, Principal principle) {
	 * try {
	 * String engineerName=dtoIncidentMaster.getEngineerEngmIm();
	 * String[] nameParts = engineerName.split(" ");
	 * String fname=nameParts[0];
	 * String lname=nameParts[1];
	 * String username = principle.getName();
	 * 
	 * String user = this.getEngineerFromAuthentication();
	 * String assignedEngineer=dtoIncidentMaster.getEngineerEngmIm();
	 * String status=dtoIncidentMaster.getStatusSmIm();
	 * String classification1=dtoIncidentMaster.getClassficationCmIm();
	 * String frequency=dtoIncidentMaster.getFrequencyFmIm();
	 * //String approvedBy=dtoIncidentMaster.getApprovedByIm();
	 * 
	 * long
	 * engineerID=this.repositoryEntityIncidentMaster.findEngineerIdByFnameAndLname(
	 * fname, lname);
	 * long
	 * statusID=this.repositoryEntityIncidentMaster.findStatusIdByStatus(status);
	 * long classificationID=this.repositoryEntityIncidentMaster.
	 * findClassificationIdByClassification(classification1);
	 * long
	 * frequencyID=this.repositoryEntityIncidentMaster.findFrequencyIdByFrequency(
	 * frequency);
	 * dtoIncidentMaster.setEngineerEngmIm(String.valueOf(engineerID));
	 * dtoIncidentMaster.setStatusSmIm(String.valueOf(statusID));
	 * dtoIncidentMaster.setClassficationCmIm(String.valueOf(classificationID));
	 * dtoIncidentMaster.setFrequencyFmIm(String.valueOf(frequencyID));
	 * dtoIncidentMaster.setCreatedByIm(user);
	 * dtoIncidentMaster.setDeleteflagIm("N");
	 * dtoIncidentMaster.setApprovedByFlagIm("N");
	 * 
	 * String engineerLoginID=this.repositoryEntityEngineerMaster.
	 * findLoginIdByFnameAndLname1(fname, lname);
	 * // String engineerLoginID=this.repositoryEntityEngineerMaster.
	 * findLoginIdByFnameAndLnameAndLoginId(fname, lname,username);
	 * String
	 * department=this.repositoryEntityEngineerMaster.findDepartmentIdByLoginId(
	 * engineerLoginID);
	 * dtoIncidentMaster.setDepartmentIm(department);
	 * serviceEntityIncidentMaster.insidentSaveData(dtoIncidentMaster);
	 * 
	 * long l_lngTypeID = this.serviceEntityIncidentMaster.getMaxTypeID();
	 * LocalDate currentDate = LocalDate.now();
	 * DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	 * String formattedDate = currentDate.format(dateFormatter);
	 * LocalTime currentTime = LocalTime.now();
	 * DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	 * String formattedTime = currentTime.format(timeFormatter);
	 * this.serviceEntityIncidentMaster.insertIntoTaskLogMaster(l_lngTypeID,
	 * "createdby_tm", "insert", "", user,
	 * formattedDate, formattedTime, user);
	 * String typeID = String.valueOf(l_lngTypeID);
	 * 
	 * // Build the JSON response dynamically
	 * 
	 * String from = this.serviceEntityEngineerMaster.getEmailIdByLoginId(username);
	 * String engineer = dtoIncidentMaster.getEngineerEngmIm();
	 * // String[] nameParts = engineer.split(" ");
	 * // String fname = nameParts[0];
	 * // String lname = nameParts[1];
	 * String loginID =
	 * this.serviceEntityEngineerMaster.getLoginIdByFnameAndLname(fname, lname);
	 * 
	 * String team = "XYZ Demo Team";
	 * 
	 * String title = dtoIncidentMaster.getTitleIm();
	 * String description = dtoIncidentMaster.getDescriptionIm();
	 * String classification = dtoIncidentMaster.getClassficationCmIm();
	 * String reference = dtoIncidentMaster.getReferenceNoIm();
	 * String referenceTitle = dtoIncidentMaster.getReferenceTitleIm();
	 * 
	 * // Construct the dear and body parts of the email
	 * String dear = "Dear: " + fname + " " + lname;
	 * String body = "You have been assigned the following task : " + description +
	 * "<br/><br/>ID : "+typeID + "<br/>Title : "+title+
	 * "<br/>Description : "+description+"<br/>classification : "
	 * +classification+"<br/>Refercence No : "+reference+"<br/> Reference Title : "
	 * +referenceTitle+
	 * "<br/><br/><br/>Team : "
	 * +team+"<br/><br/><br/>This is System Generated Message.";
	 * 
	 * // Call the modified sendMail3 method
	 * String sendMailResult = this.emailService.sendMail5(
	 * this.serviceEntityEngineerMaster.getEmailIdByLoginId(loginID),
	 * "Task Assigned ID: " + typeID,
	 * body,
	 * dear
	 * );
	 * 
	 * // Build the JSON response dynamically
	 * String jsonResponse = String.
	 * format("{\"message\":\"Task created Successfully\",\"typeid\":\"%s\",\"emailResult\":\"%s\"}"
	 * ,
	 * typeID, sendMailResult);
	 * 
	 * return ResponseEntity.ok(jsonResponse);
	 * 
	 * } catch (Exception e) {
	 * e.printStackTrace();
	 * String errorMessage = "Error occurred while creating the task: " +
	 * e.getMessage();
	 * return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
	 * }
	 * }
	 */

	// New Create task code
	// Create new Task handler
	// Code by Rutik
	// Email Service is Implement "/Create Task Mapping"
	@PostMapping("/createnewtask")
	public ResponseEntity<String> createTask(@RequestBody DtoIncidentMaster dtoIncidentMaster, Principal principle,
			DtoEmailLogMaster dtoEmailLogMaster) {
		// String usernameGlobal = entityIncidentMaster.getEngineerEngmIm();
		String emailAddress = "";
		String engineer = dtoIncidentMaster.getEngineerEngmIm();
		String[] nameParts = engineer.split(" ");
		String fname = nameParts[0];
		String lname = nameParts[1];
		String loginID = this.serviceEntityEngineerMaster.getLoginIdByFnameAndLname(fname, lname);
		String engineerEmail = this.serviceEntityEngineerMaster.getEmailIdByLoginId(loginID);
		try {
			String user = this.getEngineerFromAuthentication();

			String assignedEngineer = dtoIncidentMaster.getEngineerEngmIm();
			String status = dtoIncidentMaster.getStatusSmIm();
			String classification1 = dtoIncidentMaster.getClassficationCmIm();
			String frequency = dtoIncidentMaster.getFrequencyFmIm();
			// String approvedBy=dtoIncidentMaster.getApprovedByIm();

			long engineerID = this.repositoryEntityIncidentMaster.findEngineerIdByFnameAndLname(fname, lname);
			long statusID = this.repositoryEntityIncidentMaster.findStatusIdByStatus(status);
			long classificationID = this.repositoryEntityIncidentMaster
					.findClassificationIdByClassification(classification1);
			long frequencyID = this.repositoryEntityIncidentMaster.findFrequencyIdByFrequency(frequency);
			long departmentID = this.repositoryEntityEngineerMaster.findDepartmentIdByFnameAndLname(fname, lname);
			dtoIncidentMaster.setDepartmentIm(String.valueOf(departmentID));
			dtoIncidentMaster.setEngineerEngmIm(String.valueOf(engineerID));
			dtoIncidentMaster.setStatusSmIm(String.valueOf(classificationID));
			dtoIncidentMaster.setClassficationCmIm(String.valueOf(classificationID));
			dtoIncidentMaster.setFrequencyFmIm(String.valueOf(frequencyID));
			// dtoIncidentMaster.setEngineerEngmIm(String.valueOf(engineerID));)

			dtoIncidentMaster.setCreatedByIm(user);
			dtoIncidentMaster.setDeleteflagIm("N");
			dtoIncidentMaster.setApprovedByFlagIm("N");
			serviceEntityIncidentMaster.insidentSaveData(dtoIncidentMaster);

			long l_lngTypeID = this.serviceEntityIncidentMaster.getMaxTypeID();
			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDate = currentDate.format(dateFormatter);
			LocalTime currentTime = LocalTime.now();
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
			String formattedTime = currentTime.format(timeFormatter);
			this.serviceEntityIncidentMaster.insertIntoTaskLogMaster(l_lngTypeID, "createdby_tm", "insert", "", user,
					formattedDate, formattedTime, user);
			String typeID = String.valueOf(l_lngTypeID);

			// Build the JSON response dynamically
			String username = principle.getName();
			String from = this.serviceEntityEngineerMaster.getEmailIdByLoginId(username);

			String team = "";

			String title = dtoIncidentMaster.getTitleIm();
			String description = dtoIncidentMaster.getDescriptionIm();
			String classification = dtoIncidentMaster.getClassficationCmIm();
			String reference = dtoIncidentMaster.getReferenceNoIm();
			String referenceTitle = dtoIncidentMaster.getReferenceTitleIm();
			String engineerAssigned = dtoIncidentMaster.getEngineerEngmIm();

			// Construct the dear and body parts of the email
			String dear = "Dear: " + fname + " " + lname;
			String body = dear + "\n" + "You have been assigned the following task:\nID : " + typeID + "\nTitle : "
					+ title + "\nDescription : " + description + "\nClassification : " + classification1
					+ "\nReference No : " + reference + "\nReference Title : " + referenceTitle + "\nTeam : " + team
					+ "\n\nThis is a System Generated Message.";

			// Call the modified sendMail3 method
			String sendMailResult = this.emailService.sendMail6(engineerEmail, "Task Assigned ID: " + typeID, body,
					dear);

			// Build the JSON response dynamically
			String jsonResponse = String.format(
					"{\"message\":\"Task created Successfully\",\"typeid\":\"%s\",\"emailResult\":\"%s\"}", typeID,
					sendMailResult);

			// Entity EntityEmaiilLogMaster Email Data Fatch

			EntityEmaiilLogMaster entityEmaiilLogMaster = new EntityEmaiilLogMaster();
			entityEmaiilLogMaster.setIdElm(typeID);
			entityEmaiilLogMaster.setSubject_elm(title);
			entityEmaiilLogMaster.setToaddressElm(engineerEmail);
			entityEmaiilLogMaster.setTypevalueElm(body);
			entityEmaiilLogMaster.setSendflagElm("Y");
			entityEmaiilLogMaster.setFromaddresasElm(from);

			repositoryEmailMaster.save(entityEmaiilLogMaster);

			emailBackground.setIdToUpdate(l_lngTypeID);

			// emailBackground.updateSendFlag();

			// EmailBackgroundsERVICE
			EntityEmailbackgroundService entityEmailbackgroundService = new EntityEmailbackgroundService();
			entityEmailbackgroundService.setTaskidEbs(typeID);
			entityEmailbackgroundService.setEngineerEbs(engineerAssigned);
			if (sendMailResult != null && sendMailResult.toLowerCase().contains("success")) {
				entityEmailbackgroundService.setEmailDeleteFlageEbs("Y");
			} else {
				entityEmailbackgroundService.setEmailDeleteFlageEbs("N");
			}

			repositoryEmailBackground.save(entityEmailbackgroundService);

			return ResponseEntity.ok(jsonResponse);

		} catch (Exception e) {
			e.printStackTrace();
			String errorMessage = "Error occurred while creating the task: " + e.getMessage();

			EntityEmaiilLogMaster entityEmaiilLogMaster = new EntityEmaiilLogMaster();
			// entityEmaiilLogMaster.setSubject_elm(sendMailResult);

			entityEmaiilLogMaster.setToaddressElm(engineerEmail);
			entityEmaiilLogMaster.setTypevalueElm("Failed to send email: " + e.getMessage());
			entityEmaiilLogMaster.setSendflagElm("N"); // Set "N" for unsuccessful email send
			// entityEmaiilLogMaster.setFromaddresasElm(from);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@GetMapping("/approve/{taskid}")
	public ResponseEntity<String> approve(@PathVariable("taskid") long typeid, Principal principle) {
		String user = principle.getName();
		this.repositoryEntityIncidentMaster.updateApprovedByFlag(typeid, user);
		String approvedByFlag = this.repositoryEntityIncidentMaster.findApprovedByFlagValue(typeid);
		String approvedBy = this.repositoryEntityIncidentMaster.findApprovedByValue(typeid);

		String approvedByFlagValue = "";
		if (approvedByFlag.equals("N")) {
			approvedByFlagValue = "Unapproved";
		} else {
			approvedByFlagValue = "Approved By " + approvedBy;
		}
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = currentDate.format(dateFormatter);
		LocalTime currentTime = LocalTime.now();
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		String formattedTime = currentTime.format(timeFormatter);
		this.serviceEntityIncidentMaster.insertIntoTaskLogMaster(typeid, "approvedby_tm", "update",
				"", approvedBy, formattedDate, formattedTime,
				approvedBy);
		// model.addAttribute("approvedBy",approvedByFlagValue );
		String responseData = approvedByFlagValue;
		return ResponseEntity.ok(responseData);
	}

	// Update Task handler
	@PostMapping("/updatetask")
	public ResponseEntity<String> updateTask(@RequestBody DtoCombinedTaskData combinedData, Model model) {
		try {
			DtoIncidentMaster updatedData = combinedData.getTaskData();// Updated Data
			DtoIncidentMaster previousData = combinedData.getTaskData1();// Previous Data

			// approveByFlag
			// taskId
			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDate = currentDate.format(dateFormatter);
			LocalTime currentTime = LocalTime.now();
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
			String formattedTime = currentTime.format(timeFormatter);

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			// Assuming your login ID is present in the principal (username)
			String loginID = authentication.getName();
			String engineer = this.repositoryEntityEngineerMaster.findEngineerFirstAndLastnameByLoginId(loginID);
			engineer = engineer.replace(",", " ");

			// Extract value before the first space
			int indexOfSpace = engineer.indexOf(' ');
			String engineerFname = indexOfSpace != -1 ? engineer.substring(0, indexOfSpace) : engineer;

			// If title value is change then store in tasklogmst
			long taskId = updatedData.getTypeidIm();
			if (previousData.getTitleIm().equals(updatedData.getTitleIm())) {

			} else {
				this.serviceEntityIncidentMaster.insertIntoTaskLogMaster(taskId, "titleIm", "update",
						previousData.getTitleIm(), updatedData.getTitleIm(), formattedDate, formattedTime,
						engineerFname);
			}

			if (previousData.getDescriptionIm().equals(updatedData.getDescriptionIm())) {
				System.out.println("Desc values are equal");

			} else {
				System.out.println("Desc values are not equal");
				this.serviceEntityIncidentMaster.insertIntoTaskLogMaster(taskId, "description_tm", "update",
						previousData.getDescriptionIm(), updatedData.getDescriptionIm(), formattedDate, formattedTime,
						engineerFname);
			}
			if (previousData.getReferenceNoIm().equals(updatedData.getReferenceNoIm())) {

			} else {
				this.serviceEntityIncidentMaster.insertIntoTaskLogMaster(taskId, "referenceno_tm", "update",
						previousData.getReferenceNoIm(), updatedData.getReferenceNoIm(), formattedDate, formattedTime,
						engineerFname);
			}
			if (previousData.getReferenceTitleIm().equals(updatedData.getReferenceTitleIm())) {

			} else {
				this.serviceEntityIncidentMaster.insertIntoTaskLogMaster(taskId, "referencetitle_tm", "update",
						previousData.getReferenceTitleIm(), updatedData.getReferenceTitleIm(), formattedDate,
						formattedTime, engineerFname);
			}

			if (previousData.getEngineerEngmIm().equals(updatedData.getEngineerEngmIm())) {

			} else {
				this.serviceEntityIncidentMaster.insertIntoTaskLogMaster(taskId, "engineer_engm_tm", "update",
						previousData.getEngineerEngmIm(), updatedData.getEngineerEngmIm(), formattedDate, formattedTime,
						engineerFname);
			}

			if (previousData.getStatusSmIm().equals(updatedData.getStatusSmIm())) {

			} else {

				this.serviceEntityIncidentMaster.insertIntoTaskLogMaster(taskId, "status_sm_tm", "update",
						previousData.getStatusSmIm(), updatedData.getStatusSmIm(), formattedDate, formattedTime,
						engineerFname);
			}
			if (previousData.getClassficationCmIm().equals(updatedData.getClassficationCmIm())) {

			} else {
				this.serviceEntityIncidentMaster.insertIntoTaskLogMaster(taskId, "classfication_cm_tm", "update",
						previousData.getClassficationCmIm(), updatedData.getClassficationCmIm(), formattedDate,
						formattedTime, engineerFname);
			}

			if (previousData.getFrequencyFmIm().equals(updatedData.getFrequencyFmIm())) {

			} else {
				this.serviceEntityIncidentMaster.insertIntoTaskLogMaster(taskId, "frequency_fm_tm", "update",
						previousData.getFrequencyFmIm(), updatedData.getFrequencyFmIm(), formattedDate, formattedTime,
						engineerFname);
			}

			if (previousData.getDuedateIm().equals(updatedData.getDuedateIm())) {

			} else {
				this.serviceEntityIncidentMaster.insertIntoTaskLogMaster(taskId, "duedateIm", "update",
						previousData.getDuedateIm(), updatedData.getDuedateIm(), formattedDate, formattedTime,
						engineerFname);
			}
			//
			// if (previousData.getApprovedByIm().equals(updatedData.getApprovedByIm())) {
			//
			// } else {
			// this.serviceEntityIncidentMaster.insertIntoTaskLogMaster(taskId,
			// "approvedby_tm", "update",
			// previousData.getApprovedByIm(), updatedData.getApprovedByIm(), formattedDate,
			// formattedTime,
			// engineerFname);
			// }

			DtoIncidentMaster dtoIncidentMaster2 = this.serviceEntityIncidentMaster.getIncidentById(taskId);
			String department = dtoIncidentMaster2.getDepartmentIm();
			dtoIncidentMaster2.setTypeidIm(taskId);
			dtoIncidentMaster2.setTitleIm(updatedData.getTitleIm());
			dtoIncidentMaster2.setDescriptionIm(updatedData.getDescriptionIm());
			dtoIncidentMaster2.setReferenceNoIm(updatedData.getReferenceNoIm());
			dtoIncidentMaster2.setReferenceTitleIm(updatedData.getReferenceTitleIm());
			String engineerName = updatedData.getEngineerEngmIm();
			String[] nameParts = engineerName.split(" ");
			String fname = nameParts[0];
			String lname = nameParts[1];
			long engineerID = this.repositoryEntityIncidentMaster.findEngineerIdByFnameAndLname(fname, lname);
			long statusID = this.repositoryEntityIncidentMaster.findStatusIdByStatus(updatedData.getStatusSmIm());
			long classificationID = this.repositoryEntityIncidentMaster
					.findClassificationIdByClassification(updatedData.getClassficationCmIm());
			long frequencyID = this.repositoryEntityIncidentMaster
					.findFrequencyIdByFrequency(updatedData.getFrequencyFmIm());
			dtoIncidentMaster2.setEngineerEngmIm(String.valueOf(engineerID));
			dtoIncidentMaster2.setStatusSmIm(String.valueOf(statusID));
			dtoIncidentMaster2.setClassficationCmIm(String.valueOf(classificationID));
			dtoIncidentMaster2.setFrequencyFmIm(String.valueOf(frequencyID));
			// dtoIncidentMaster2.setIncdateIm(taskData.getIncdateIm());
			dtoIncidentMaster2.setDuedateIm(updatedData.getDuedateIm());
			// dtoIncidentMaster2.setLastUpdateddateIm(taskData.getLastUpdateddateIm());
			// dtoIncidentMaster2.setApprovedByIm(updatedData.getApprovedByIm());
			dtoIncidentMaster2.setDeleteflagIm("N");

			String approveByFlag = updatedData.getApprovedByFlagIm();
			String newApproveByFlag = "";
			if (approveByFlag.equals("Unapproved")) {
				newApproveByFlag = "N";
			}
			if (approveByFlag.equals("Approve")) {
				newApproveByFlag = "N";
			} else {
				newApproveByFlag = "Y";
			}

			dtoIncidentMaster2.setApprovedByFlagIm(newApproveByFlag);
			EntityIncidentMaster entityIncidentMaster = this.serviceEntityIncidentMaster
					.dtoToEntity1(dtoIncidentMaster2);
			this.repositoryEntityIncidentMaster.save(entityIncidentMaster);

			String jsonResponse = String.format(
					"{\"message\":\"Task updated Successfully\",\"typeid\":\"%s\",\"status\":\"%s\"}", taskId,
					updatedData.getStatusSmIm());

			// Return the ResponseEntity with the dynamic JSON response
			return ResponseEntity.ok(jsonResponse);
		} catch (Exception e) {
			e.printStackTrace();
			// Return a ResponseEntity with detailed error message
			String errorMessage = "Error occurred while creating the task: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}

	}

	//Email Sender Setting Update when Configration update that Time 
	@PutMapping("/updatesender")
	public ResponseEntity<String> updateSender(@RequestParam(value = "strRecever") String strRecever, long id) {
		System.out.println("Sender: " + strRecever + " Id: " + id);

		try {
			// Update the sender email in the database
			serviceEmail.emailservice(strRecever, id);

			// Fetch the updated email settings
			EntityEmailSetting updatedEmailSetting = serviceEmail.getAllEmailSetting();

			String username = updatedEmailSetting.getUsernameEsm();
			String password = updatedEmailSetting.getPasswordEsm();
			String port = updatedEmailSetting.getPortEsm();
			String senderEmail = updatedEmailSetting.getSenderEsm();
			String server = updatedEmailSetting.getServerEsm();
			String encodeType = updatedEmailSetting.getEncodingtypeEsm();
			String authentication = updatedEmailSetting.getAuthenticationflagEsm();
			String ssl = updatedEmailSetting.getSslEsm();

			// Send a test email using the updated email settings
			String subject = "Email Configration Testing";
			String to = username;
			// String dear = "port "+ port;
			// String body = "Server : "+server;
			//
			// String senderEmails = "Sender Email : "+senderEmail;
			// String servers = "Server : "+server;
			// String encodeTypes = "encodeType : "+encodeType;
			// String authentications = "authentication : "+authentication;
			String body = "\nUsername : " + username + "\nSSL : " + ssl + "Port : " + port + "\nServer : " + server
					+ "\nDemo Eamil Test : " + senderEmail
					+ "\nServer : " + server + "\nencodeType : " + encodeType + "\nauthentication : " + authentication;

			// Call the sendMail7 method directly
			serviceEmail.sendMail7(to, subject, body);

			return ResponseEntity.ok("Sender updated successfully, and a test email has been sent.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Error updating sender: " + e.getMessage());
		}
	}

	// Get Logs by ID handler
	@GetMapping("/getLogs/{taskIdTlm}")
	public ResponseEntity<?> getLogsByTaskId(@PathVariable String taskIdTlm) {
		try {
			long taskId = Long.parseLong(taskIdTlm);
			List<EntityTaskLogMaster> logs = serviceEntityIncidentMaster.getLogsByTaskId(taskId);
			if (logs.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			System.out.println("LOGS DATA" + logs);
			return ResponseEntity.status(HttpStatus.OK).header("custom-header", "treeData")
					.contentType(MediaType.APPLICATION_JSON).body(logs);
		} catch (Exception e) {
			e.printStackTrace();
			// Return a ResponseEntity with detailed error message
			String errorMessage = "Error occurred while creating the task: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}

	}

}
