package com.yada180.sms.struts.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.yada180.sms.application.Constants;
import com.yada180.sms.domain.IntakeJobSkill;
import com.yada180.sms.domain.IntakeMedicalCondition;
import com.yada180.sms.domain.IntakeQuestionAnswer;
import com.yada180.sms.domain.JobSkill;
import com.yada180.sms.domain.MedicalCondition;
import com.yada180.sms.hibernate.dao.IntakeDao;
import com.yada180.sms.hibernate.dao.IntakeJobSkillDao;
import com.yada180.sms.hibernate.dao.IntakeMedicalConditionDao;
import com.yada180.sms.hibernate.dao.IntakeQuestionAnswerDao;
import com.yada180.sms.struts.form.OnlineAppForm;
import com.yada180.sms.util.HtmlDropDownBuilder;
import com.yada180.sms.util.Validator;
import com.yada180.sms.validator.IntakeValidator;

public class OnlineAppAction extends Action {

	private final static Logger LOGGER = Logger.getLogger(LoginAction.class
			.getName());
	private final static HtmlDropDownBuilder html = new HtmlDropDownBuilder();
	private final static Validator validator = new Validator();
	private final static IntakeValidator intakeValidator = new IntakeValidator();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.setLevel(Level.INFO);

		HttpSession session = request.getSession(true);
		
		try {
			
		OnlineAppForm onlineAppForm = (OnlineAppForm) form;
		String action = request.getParameter("action");
		IntakeDao intakeDao = new IntakeDao();
		IntakeMedicalConditionDao intakeMedicalConditionDao = new IntakeMedicalConditionDao();
		IntakeQuestionAnswerDao intakeQuestionAnswerDao = new IntakeQuestionAnswerDao();
		IntakeJobSkillDao intakeJobSkillDao = new IntakeJobSkillDao();

		// clear out messages
		onlineAppForm.setMessages(new ArrayList());
		onlineAppForm.setMessageType("");

		/*
		 * Load drop down values, questions, and medical conditions from
		 * database
		 */
		html.refresh(session);
		html.getOnlineApplicationQuestions(onlineAppForm);

		if ("Next".equals(action)) {
			boolean valid=false;
			
			valid = intakeValidator.validate(onlineAppForm);
			
			if (!valid)
				 return mapping.findForward(onlineAppForm.getPageSource());
			
			if ("religious".equals(onlineAppForm.getNextStep()))
				return mapping.findForward(Constants.RELIGIOUS);
			else if ("health".equals(onlineAppForm.getNextStep()))
				return mapping.findForward(Constants.HEALTH);
			else if ("substance".equals(onlineAppForm.getNextStep()))
				return mapping.findForward(Constants.SUBSTANCE);
			else if ("legal".equals(onlineAppForm.getNextStep()))
				return mapping.findForward(Constants.LEGAL);
			else if ("employment".equals(onlineAppForm.getNextStep()))
				return mapping.findForward(Constants.EMPLOYMENT);
			else if ("process".equals(onlineAppForm.getNextStep()))
				return mapping.findForward(Constants.PROCESS);

		} else if ("Back".equals(action)) {

			if ("personal".equals(onlineAppForm.getPrevStep()))
				return mapping.findForward(Constants.PERSONAL);
			else if ("religious".equals(onlineAppForm.getPrevStep()))
				return mapping.findForward(Constants.RELIGIOUS);
			else if ("health".equals(onlineAppForm.getPrevStep()))
				return mapping.findForward(Constants.HEALTH);
			else if ("substance".equals(onlineAppForm.getPrevStep()))
				return mapping.findForward(Constants.SUBSTANCE);
			else if ("legal".equals(onlineAppForm.getPrevStep()))
				return mapping.findForward(Constants.LEGAL);
			else if ("employment".equals(onlineAppForm.getPrevStep()))
				return mapping.findForward(Constants.EMPLOYMENT);

		} else if ("Submit".equals(action)) {

			onlineAppForm.setMessageType("");
			// boolean valid = inakeValidator.validate(intakeForm);
			if (true) {
				onlineAppForm.getIntake().setCreationDate(
						validator.getEpoch() + "");
				onlineAppForm.getIntake().setCreatedBy("online application");
				onlineAppForm.getIntake().setApplicationStatus("Pending");
				Long id = intakeDao.addIntake(onlineAppForm.getIntake());
				onlineAppForm.getIntake().setIntakeId(id);
				
				saveMedicalConditions(onlineAppForm);
				saveUsagePatternAndLosses(onlineAppForm);
				saveJobSkills(onlineAppForm);
				session.invalidate(); 
				return mapping.findForward(Constants.SUCCESS);
			}

		}
		return mapping.findForward(Constants.PERSONAL);
		}
			catch (Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);			
				session.setAttribute("SYSTEM_ERROR", sw.toString());
				e.printStackTrace();
				return mapping.findForward(Constants.ERROR);
			}
	}

	private void saveIntakeQuestionAnswer(OnlineAppForm intakeForm) {
		
		IntakeQuestionAnswerDao dao = new IntakeQuestionAnswerDao();
		
		 /*
		  * First delete all medical conditions for given intake
		  */
		 List<IntakeQuestionAnswer> intakeQuestionAnswers = dao.findById(intakeForm.getIntake().getIntakeId());
		 for (Iterator iterator =
				 intakeQuestionAnswers.iterator(); iterator.hasNext();){
			 IntakeQuestionAnswer obj = (IntakeQuestionAnswer) iterator.next();
			 dao.deleteIntakeQuestionAnswer(obj.getIntakeQuestionAnswerId());
		 }
		 
		 String healthAnswer[] = intakeForm.getHealthAnswer();
		 String emotionalAnswer[] = intakeForm.getEmotionalAnswer();
		 String emotionalAnswerDate[] = intakeForm.getEmotionalAnswerDate();
		 String emotionalAnswerDetails[] = intakeForm.getEmotionalAnswerDetails();
		 String physicalAnswer[] = intakeForm.getPhysicalAnswer();
		 String physicalAnswerDetails[] = intakeForm.getPhysicalAnswerDetails();
		 String mentalAnswer[] = intakeForm.getMentalAnswer();
		 String mentalAnswerDate[] = intakeForm.getMentalAnswerDate();
		 String mentalAnswerDetails[] = intakeForm.getMentalAnswerDetails();
		 
		for (int index=0;index<15;index++) {
	    	   
	    	   if ("YES".equals(healthAnswer[index])) {
	    		   IntakeQuestionAnswer iqa = new IntakeQuestionAnswer();
	    		   iqa.setIntakeId(intakeForm.getIntake().getIntakeId());
	    		   iqa.setAnswer("YES");	 
	    		   iqa.setQuestionId(new Long(index+1));
	    		   dao.addIntakeQuestionAnswer(iqa);
	    		 }
	    	   
	    	   if ("YES".equals(emotionalAnswer[index])) {
	    		   IntakeQuestionAnswer iqa = new IntakeQuestionAnswer();
	    		   iqa.setIntakeId(intakeForm.getIntake().getIntakeId());
	    		   iqa.setAnswer("YES");	
	    		   iqa.setDates(emotionalAnswerDate[index]);
	    		   iqa.setDetail(emotionalAnswerDetails[index]);
	    		   iqa.setQuestionId(new Long(index+1));
	    		   dao.addIntakeQuestionAnswer(iqa);
	    		 }
	    	   
	    	   if ("YES".equals(physicalAnswer[index])) {
	    		   IntakeQuestionAnswer iqa = new IntakeQuestionAnswer();
	    		   iqa.setIntakeId(intakeForm.getIntake().getIntakeId());
	    		   iqa.setAnswer("YES");	
	    		   iqa.setDetail(physicalAnswerDetails[index]);
	    		   iqa.setQuestionId(new Long(index+1));
	    		   dao.addIntakeQuestionAnswer(iqa);
	    		 }
	    	   
	    	   if ("YES".equals(mentalAnswer[index])) {
	    		   IntakeQuestionAnswer iqa = new IntakeQuestionAnswer();
	    		   iqa.setIntakeId(intakeForm.getIntake().getIntakeId());
	    		   iqa.setAnswer("YES");	
	    		   iqa.setDates(mentalAnswerDate[index]);
	    		   iqa.setDetail(mentalAnswerDetails[index]);
	    		   iqa.setQuestionId(new Long(index+1));
	    		   dao.addIntakeQuestionAnswer(iqa);
	    		 }
	    }

	}
	
	private void saveMedicalConditions(OnlineAppForm intakeForm) {
		 
		 IntakeMedicalConditionDao dao = new IntakeMedicalConditionDao();

		 
		 /*
		  * First delete all medical conditions for given intake
		  */
		 List<IntakeMedicalCondition> intakeMedicalConditions = dao.findById(intakeForm.getIntake().getIntakeId());
		 for (Iterator iterator =
				 intakeMedicalConditions.iterator(); iterator.hasNext();){
			 IntakeMedicalCondition obj = (IntakeMedicalCondition) iterator.next();
			 dao.deleteIntakeMedicalCondition(obj.getIntakeMedicalConditionId());
		 }
		 
		 /*
		  * write to db medical conditions for given intake marked yes
		  */
		 List<MedicalCondition> medicalConditions = intakeForm.getMedicalConditions();
		 String medicalCondition[] = intakeForm.getMedicalCondition();
	     int index=0;  
		 for (Iterator iterator =
	    		   medicalConditions.iterator(); iterator.hasNext();){
	    	   MedicalCondition obj = (MedicalCondition) iterator.next();	    			
	    	   
	    	   if ("YES".equals(medicalCondition[index])) {
	    		   IntakeMedicalCondition imc = new IntakeMedicalCondition();
	    		   imc.setIntakeId(intakeForm.getIntake().getIntakeId());
	    		   imc.setMedicalConditionId(obj.getMedicalConditionId());
	    		   imc.setAnswer("YES");
	    		   dao.addIntakeMedicalCondition(imc);
	    		 }
	    	   
	    	   
	    	 index++;
	       }
	       
                
	}
	
	private void saveJobSkills(OnlineAppForm intakeForm) {
		 
		 IntakeJobSkillDao dao = new IntakeJobSkillDao();
		 
		 /*
		  * First delete all medical conditions for given intake
		  */
		 List<IntakeJobSkill> intakeJobSkills = dao.findById(intakeForm.getIntake().getIntakeId());
		 for (Iterator iterator =
				 intakeJobSkills.iterator(); iterator.hasNext();){
			 IntakeJobSkill obj = (IntakeJobSkill) iterator.next();
			 dao.deleteIntakeJobSkill(obj.getIntakeJobSkillId());
		 }
		 
		 /*
		  * write to db medical conditions for given intake marked yes
		  */
		 List<JobSkill> medicalConditions = intakeForm.getJobSkills();
		 String workExperience[] = intakeForm.getWorkExperience();
	     int index=0;  
		 for (Iterator iterator =
	    		   medicalConditions.iterator(); iterator.hasNext();){
	    	   JobSkill obj = (JobSkill) iterator.next();	    			
	    	   
	    	   if ("YES".equals(workExperience[index])) {
	    		   IntakeJobSkill ijs = new IntakeJobSkill();
	    		   ijs.setIntakeId(intakeForm.getIntake().getIntakeId());	    		   
	    		   ijs.setJobSkillId(obj.getJobSkillId());
	    		   dao.addIntakeJobSkill(ijs);
	    		 }	    	   
	    	   
	    	 index++;
	       }
	       
               
	}
	
	private void saveUsagePatternAndLosses(OnlineAppForm intakeForm) {
		intakeForm.getIntake().setUsageLosses(intakeForm.getUsageLosses1()+","+
				intakeForm.getUsageLosses2()+","+
				intakeForm.getUsageLosses3()+","+
				intakeForm.getUsageLosses4()+","+
				intakeForm.getUsageLosses5()+","+
				intakeForm.getUsageLosses6()+","+
				intakeForm.getUsageLosses7()+","+
				intakeForm.getUsageLosses8()+","+
				intakeForm.getUsageLosses9()				
				);
		
		intakeForm.getIntake().setUsagePattern(intakeForm.getUsagePattern1()+","+ 
				intakeForm.getUsagePattern2()+","+ 
				intakeForm.getUsagePattern3()+","+ 
				intakeForm.getUsagePattern4()+","+ 
				intakeForm.getUsagePattern5()+","+ 
				intakeForm.getUsagePattern6() 
				);
		
	}
	

}