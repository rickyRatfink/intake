package com.yada180.sms.validator;

import java.util.ArrayList;
import java.util.List;

import com.yada180.sms.domain.ErrorMessage;
import com.yada180.sms.domain.Intake;
import com.yada180.sms.domain.SystemUser;
import com.yada180.sms.struts.form.IntakeForm;
import com.yada180.sms.struts.form.OnlineAppForm;

public class IntakeValidator { 

	public boolean validate(IntakeForm form) {
		
		boolean valid=false;
		
		List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
		Intake intake = form.getIntake();
		 
		if ("personal".equals(form.getPageSource())) 
			messages = this.validatePersonal(intake,  messages);
		if ("religious".equals(form.getPageSource())) 
			messages = this.validateSpiritual(intake, messages);
		if ("health".equals(form.getPageSource())) 
			messages = this.validateHealth(intake, messages);
		if ("status".equals(form.getPageSource())) {
			messages = this.validateStatus(intake, messages);
		}
		
		if (messages.size()>0) 
			form.setMessageType("error");
		else {
			valid=true;
			messages.add(new ErrorMessage(form.getPageSource()+" information saved",""));
			form.setMessageType("success");
		}
		form.setMessages(messages);
		
	return valid;
	}
	
	public boolean validate(OnlineAppForm form) {
		
		boolean valid=true;
		
		List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
		Intake intake = form.getIntake();
		 
		if ("personal".equals(form.getPageSource())) 
			messages = this.validatePersonal(intake, messages);
		//if ("religious".equals(form.getPageSource())) 
		//	messages = this.validateSpiritual(intake, messages);		
		
		if (messages.size()>0) {
			form.setMessageType("error");
			form.setMessages(messages);
			valid=false;
		}
		
	return valid;
	}
	
	
	private List<ErrorMessage> validatePersonal(Intake intake, List<ErrorMessage> messages) {		
	
		if (intake.getFarmBase()==null||intake.getFarmBase().length()==0)
			messages.add(new ErrorMessage("farm preference","is required"));
		
		if (intake.getLastname()==null||intake.getLastname().length()==0)
			 messages.add(new ErrorMessage("lastname","is required"));

		if (intake.getFirstname()==null||intake.getFirstname().length()==0)
			 messages.add(new ErrorMessage("firstname","is required"));

		if (intake.getSsn()==null||intake.getSsn().length()==0)
			 messages.add(new ErrorMessage("ssn","is required"));

		if (intake.getDob()!=null) {
			if (intake.getDob().length()!=10) 		
				messages.add(new ErrorMessage("date of birth","needs to be in MM/DD/YYYY format"));
			intake.setDob(intake.getDob().replace("-", "/"));
		}
		
		if ("Yes".equals(intake.getDlFlag())) {
			if (intake.getDlState()!=null&&intake.getDlState().length()==0) 		
				 messages.add(new ErrorMessage("drivers license state","is required"));
			if (intake.getDlNumber()!=null&&intake.getDlNumber().length()==0) 		
				 messages.add(new ErrorMessage("drivers license number","is required"));
		}
		
		if ("No".equals(intake.getDlFlag())) {
			if (intake.getStateIdFlag()!=null&&intake.getStateIdFlag().length()==0) 		
				 messages.add(new ErrorMessage("government id"," or a valid drivers license is required"));
		}
		
		if ("Yes".equals(intake.getStateIdFlag())) {
			if (intake.getStateIdType()!=null&&intake.getStateIdType().length()==0) 		
				 messages.add(new ErrorMessage("type of government id","is required"));
		}
		
		

		/*
		if (intake.getAddress()==null||intake.getAddress().length()==0)
			 messages.add(new ErrorMessage("address","is required"));

		if (intake.getCity()==null||intake.getCity().length()==0)
			 messages.add(new ErrorMessage("city","is required"));

		if (intake.getState()==null||intake.getState().length()==0)
			 messages.add(new ErrorMessage("state","is required"));

		if (intake.getZipcode()==null||intake.getZipcode().length()==0)
			 messages.add(new ErrorMessage("zipcode","is required"));

		if (intake.getReferredByPhone()==null||intake.getReferredByPhone().length()==0)
			 messages.add(new ErrorMessage("referred by phone","is required"));

		if (intake.getDob()==null||intake.getDob().length()==0)
			 messages.add(new ErrorMessage("date of birth","is required"));

		if (intake.getAge()==null||intake.getAge().length()==0)
			 messages.add(new ErrorMessage("age","is required"));

		if (intake.getEmergencyContact()==null||intake.getEmergencyContact().length()==0)
			 messages.add(new ErrorMessage("emergency contact","is required"));

		if (intake.getEmergencyContact()==null||intake.getEmergencyContact().length()==0)
			 messages.add(new ErrorMessage("emergency phone","is required"));

		if (intake.getEmergencryRelationship()==null||intake.getEmergencryRelationship().length()==0)
			 messages.add(new ErrorMessage("emergency relationship","is required"));

		
		if (intake.getMaritalStatus()==null||intake.getMaritalStatus().length()==0)
			 messages.add(new ErrorMessage("marital status","is required"));

		if (intake.getEthnicity()==null||intake.getEthnicity().length()==0)
			 messages.add(new ErrorMessage("race/ethnicity","is required"));

		if (intake.getHeight()==null||intake.getHeight().length()==0)
			 messages.add(new ErrorMessage("height","is required"));

		if (intake.getWeight()==null||intake.getWeight().length()==0)
			 messages.add(new ErrorMessage("weight","is required"));

		if (intake.getHairColor()==null||intake.getHairColor().length()==0)
			 messages.add(new ErrorMessage("hair color","is required"));

		if (intake.getEyeColor()==null||intake.getEyeColor().length()==0)
			 messages.add(new ErrorMessage("eye color","is required"));

		if (intake.getHomeLocation()==null||intake.getHomeLocation().length()==0)
			 messages.add(new ErrorMessage("where are you living ","is required"));

		if (intake.getEducationLevel()==null||intake.getEducationLevel().length()==0)
			 messages.add(new ErrorMessage("highest level of education","is required"));

		if (intake.getEyeColor()==null||intake.getEyeColor().length()==0)
			 messages.add(new ErrorMessage("eye color","is required"));

		if ("YES".equals(intake.getVeteranStatus())) {
			
			if (intake.getBranchOfService()==null||intake.getBranchOfService().length()==0)
				 messages.add(new ErrorMessage("branch of service","is required"));
			if (intake.getRank()==null||intake.getRank().length()==0)
				 messages.add(new ErrorMessage("rank","is required"));
			if (intake.getLengthOfService()==null||intake.getLengthOfService().length()==0)
				 messages.add(new ErrorMessage("length of service","is required"));
			
		}
		
		if ("YES".equals(intake.getDlFlag())) {
			
			if (intake.getDlNumber()==null||intake.getDlNumber().length()==0)
				 messages.add(new ErrorMessage("drivers license number","is required"));
			if (intake.getDlState()==null||intake.getDlState().length()==0)
				 messages.add(new ErrorMessage("license state","is required"));
			//if (intake.getDlExpDate()==null||intake.getDlExpDate().length()==0)
			//	 messages.add(new ErrorMessage("license exp date","is required"));
			
		}
		*/
		
		return messages;
	}
	
	
	
	private List<ErrorMessage> validateSpiritual(Intake intake, List<ErrorMessage> messages) {		
		
		if (intake.getReligion()==null||intake.getReligion().length()==0)
			 messages.add(new ErrorMessage("religious background","is required"));

		if (intake.getReligiousExperience()==null||intake.getReligiousExperience().length()==0)
			messages.add(new ErrorMessage("spiritual experience","is required"));

		
		return messages;
	}
	
	
	private List<ErrorMessage> validateHealth(Intake intake, List<ErrorMessage> messages) {		
		
		if (intake.getCurrentHealth()!=null && intake.getCurrentHealth().length()==0)
			 messages.add(new ErrorMessage("current health","is required"));

		
		return messages;
	}
	
	
	private List<ErrorMessage> validateStatus(Intake intake, List<ErrorMessage> messages) {		
		
		if (intake.getSupervisorId()==null||intake.getSupervisorId().equals(new Long(0)) )
			 messages.add(new ErrorMessage("supervisor","is required"));
		if (intake.getJobId()==null ||intake.getJobId().equals(new Long(0)) )
			 messages.add(new ErrorMessage("job","is required"));
		
		if (intake.getEntryDate()==null ||intake.getEntryDate().length()==0 )
			 messages.add(new ErrorMessage("date last entered","is required"));
		else if (intake.getEntryDate().length()!=10) 		
				 messages.add(new ErrorMessage("date of birth","is needs to be in MM/DD/YYYY format"));
		
		
		return messages;
	}
}
