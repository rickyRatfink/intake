package com.yada180.sms.struts.action;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.apache.struts.upload.FormFile;

import com.yada180.sms.application.Constants;
import com.yada180.sms.domain.Intake;
import com.yada180.sms.domain.IntakeJobSkill;
import com.yada180.sms.domain.IntakeMedicalCondition;
import com.yada180.sms.domain.IntakeQuestionAnswer;
import com.yada180.sms.domain.JobSkill;
import com.yada180.sms.domain.MedicalCondition;
import com.yada180.sms.domain.SearchParameter;
import com.yada180.sms.domain.StudentDisciplineHistory;
import com.yada180.sms.domain.StudentHistory;
import com.yada180.sms.domain.StudentPassHistory;
import com.yada180.sms.domain.SystemUser;
import com.yada180.sms.hibernate.dao.IntakeDao;
import com.yada180.sms.hibernate.dao.IntakeJobSkillDao;
import com.yada180.sms.hibernate.dao.IntakeMedicalConditionDao;
import com.yada180.sms.hibernate.dao.IntakeQuestionAnswerDao;
import com.yada180.sms.hibernate.dao.StudentDisciplineHistoryDao;
import com.yada180.sms.hibernate.dao.StudentHistoryDao;
import com.yada180.sms.hibernate.dao.StudentPassHistoryDao;
import com.yada180.sms.struts.form.IntakeForm;
import com.yada180.sms.util.HtmlDropDownBuilder;
import com.yada180.sms.util.Validator;
import com.yada180.sms.validator.IntakeValidator;

public class IntakeAction extends Action {
	
	private final static Logger LOGGER = Logger.getLogger(LoginAction.class.getName());
	private final static HtmlDropDownBuilder html = new HtmlDropDownBuilder();
	private final static IntakeValidator inakeValidator = new IntakeValidator();
	private final static Validator validator = new Validator(); 
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  {		
		LOGGER.setLevel(Level.INFO);

		 HttpSession session = request.getSession(false);
		 SystemUser user = (SystemUser) session.getAttribute("system_user");
		 session.setAttribute("SYSTEM_ERROR",null);
		 
		try {
		 
		 String action=request.getParameter("action");
		 LOGGER.log(Level.INFO,"!!!Action="+action);
		 IntakeForm intakeForm = (IntakeForm)form;
		 IntakeDao intakeDao = new IntakeDao();
		 StudentHistoryDao studentHistoryDao = new StudentHistoryDao();
		 StudentDisciplineHistoryDao studentDisciplineHistoryDao = new StudentDisciplineHistoryDao();
		 StudentPassHistoryDao studentPassHistoryDao = new StudentPassHistoryDao();
		 IntakeMedicalConditionDao intakeMedicalConditionDao = new IntakeMedicalConditionDao();
		 IntakeQuestionAnswerDao intakeQuestionAnswerDao = new IntakeQuestionAnswerDao();
		 IntakeJobSkillDao intakeJobSkillDao = new IntakeJobSkillDao();		
		
		 //clear out messages
		 intakeForm.setMessages(new ArrayList());
		 intakeForm.setMessageType("");
		 intakeForm.setMessage(null);
		 
		 if (intakeForm.getEditIndex()==null)
			 intakeForm.setEditIndex(new Integer(-1));
		 
		 if ("personal".equals(action)) 
			 return mapping.findForward(Constants.PERSONAL);
		 else if ("religious".equals(action))
			 return mapping.findForward(Constants.RELIGIOUS);
		 else if ("substance".equals(action))
			 return mapping.findForward(Constants.SUBSTANCE);
		 else if ("health".equals(action))
			 return mapping.findForward(Constants.HEALTH);
		 else if ("legal".equals(action))
			 return mapping.findForward(Constants.LEGAL);
		 else if ("employment".equals(action))
			 return mapping.findForward(Constants.EMPLOYMENT);
		 else if ("status".equals(action)) {
			 intakeForm.setEditIndex(new Integer(-1));
			 return mapping.findForward(Constants.STATUS);
		 }
		 else if ("pass".equals(action))
			 return mapping.findForward(Constants.PASS);
		 
		 else if ("discipline".equals(action)) {
			 intakeForm.setStudentDisciplineHistory(studentDisciplineHistoryDao.findByIntakeId(intakeForm.getIntake().getIntakeId()));
			 return mapping.findForward(Constants.DISCIPLINE);
		 }
		 else if ("Photo".equals(action)) 
			 return mapping.findForward(Constants.PHOTO);
		 else if ("Search".equals(action)) {
			 intakeForm.setIntake(new Intake());
			 intakeForm.setGedFlag(null);
			 intakeForm.setArchivedFlag(null);
			 intakeForm.setPictureFlag(null);
			 intakeForm.setProgramStatus(null);
			 return mapping.findForward(Constants.SEARCH);
		 } else if ("Search Students".equals(action)) {
			 String entryDate=intakeForm.getIntake().getEntryDate();
			 String exitDate=intakeForm.getIntake().getExitDate();
			 String lastname=intakeForm.getIntake().getLastname();
			 String firstname=intakeForm.getIntake().getFirstname();
			 String ssn=intakeForm.getIntake().getSsn();
			 String dob=intakeForm.getIntake().getDob();
			 String farm=intakeForm.getIntake().getFarmBase();
			 String ged=intakeForm.getGedFlag();
			 String archived=intakeForm.getArchivedFlag();
			 String status=intakeForm.getProgramStatus();
			 
			 List intakeList = intakeDao.search(entryDate, exitDate, lastname,firstname,ssn,dob,farm,ged,archived,status);
			 intakeForm.setIntakeList(intakeList);
			 
			 if (intakeList.size()>199) 
					intakeForm.setMessage("More than 200 results were returned. Please narrow your search.");

			 return mapping.findForward(Constants.RESULTS);
		 }
		 else if ("SearchApps".equals(action)) {
			 intakeForm.setSearchParameter(new SearchParameter());
			 return mapping.findForward(Constants.SEARCH_APPLICATIONS);
		 }
		 else if ("Search Applications".equals(action)) {
			 List intakeList = intakeDao.searchApplications(intakeForm.getSearchParameter().getBeginDate(), 
					 intakeForm.getSearchParameter().getEndDate(), 
					 intakeForm.getSearchParameter().getLastname(),
					 intakeForm.getSearchParameter().getFirstname(),
					 intakeForm.getSearchParameter().getSsn(),
					 intakeForm.getSearchParameter().getDob(),
					 intakeForm.getSearchParameter().getApplicationStatus(),
					 intakeForm.getSearchParameter().getFarmBase());
			 intakeForm.setApplicantList(intakeList);

			 if (intakeList.size()>199) 
					intakeForm.setMessage("More than 200 results were returned. Please narrow your search.");

			 return mapping.findForward(Constants.APPLICATIONS);
		 }
		 else if ("Admit".equals(action)) {
			 String entryDate = validator.convertEpoch(validator.getEpoch());
			 
			 intakeForm.getIntake().setApplicationStatus("In Program");
			 intakeForm.getIntake().setIntakeStatus("In Program");
			 intakeForm.getIntake().setClass_("Orientation");
			 intakeForm.getIntake().setLastUpdatedDate(validator.getEpoch()+"");
			 intakeForm.getIntake().setLastUpdatedBy(user.getUsername());
			 intakeForm.getIntake().setFarmBase(user.getFarmBase());
			 intakeForm.getIntake().setEntryDate(entryDate);
			 intakeDao.updateIntake(intakeForm.getIntake());
			 
			 //create history record
			 StudentHistory history = new StudentHistory();
			 history.setBeginDate(entryDate);
			 history.setFarm(user.getFarmBase());
			 history.setPhase("Phase I");
			 history.setProgramStatus("In Program");
			 history.setCreatedBy(user.getUsername());
			 history.setCreationDate(validator.getEpoch()+"");
			 history.setIntakeId(intakeForm.getIntake().getIntakeId());
			 
			 studentHistoryDao.addStudentHistory(history);
			 return mapping.findForward(Constants.PERSONAL);
		 }
		 else if ("Accept".equals(action)) {
			 intakeForm.getIntake().setApplicationStatus("Accepted");
			 intakeForm.getIntake().setLastUpdatedDate(validator.getEpoch()+"");
			 intakeForm.getIntake().setLastUpdatedBy(user.getUsername());
			 intakeDao.updateIntake(intakeForm.getIntake());
			 return mapping.findForward(Constants.PERSONAL);
		 }
		 else if ("Deny".equals(action)) {
			 intakeForm.getIntake().setApplicationStatus("Denied");
			 intakeForm.getIntake().setLastUpdatedDate(validator.getEpoch()+"");
			 intakeForm.getIntake().setLastUpdatedBy(user.getUsername());
			 intakeDao.updateIntake(intakeForm.getIntake());
			 return mapping.findForward(Constants.PERSONAL);
		 }
		 else if ("Reinstate".equals(action)) {
			 intakeForm.getIntake().setApplicationStatus("Pending");
			 intakeForm.getIntake().setLastUpdatedDate(validator.getEpoch()+"");
			 intakeForm.getIntake().setLastUpdatedBy(user.getUsername());
			 intakeDao.updateIntake(intakeForm.getIntake());
			 return mapping.findForward(Constants.PERSONAL);
		 }
 		 else if ("Create".equals(action)) {
			 this.clearForm(intakeForm);
			 
			 return mapping.findForward(Constants.PERSONAL);
		 }
		 else if ("Home".equals(action))
			 return mapping.findForward(Constants.HOME);
		 else if ("Upload".equals(action)) {
			 FormFile file = intakeForm.getImageFile();
			 try {
				 //InputStream stream = file.getInputStream();
				 InputStream stream = new BufferedInputStream(file.getInputStream());
				 final byte[] bytes = new byte[5224024];
				 int read = stream.read(bytes);				 
				 intakeForm.getIntake().setImageHeadshot(bytes);
			 } catch (Exception e) { LOGGER.log(Level.SEVERE, e.getMessage()); }
			
			 return mapping.findForward(Constants.PERSONAL);
		 }
		 else if ("Edit".equals(action)) {
			 String key = request.getParameter("key");
			 Intake intake = intakeDao.findById(new Long(key));
			 List studentHistory = studentHistoryDao.search(intake.getIntakeId());
			 List studentPassHistory = studentPassHistoryDao.search(intake.getIntakeId());
			 List<IntakeMedicalCondition> intakeMedicalCondition = intakeMedicalConditionDao.findById(intake.getIntakeId());
			 List<IntakeQuestionAnswer> intakeQuestionAnswer = intakeQuestionAnswerDao.findById(intake.getIntakeId());
			 List<IntakeJobSkill> intakeJobSkill = intakeJobSkillDao.findById(intake.getIntakeId());
			 
			 /*
			  *  Check program status and graduation date to check for 'time is up', display msg if it is
			  */
			 
			 setIntakeMedicalConditions(intakeMedicalCondition,intakeForm);
			 setIntakeQuestionAnswer(intakeQuestionAnswer,intakeForm);
			 setIntakeJobSkills(intakeJobSkill,intakeForm);
			 
			 intakeForm.setIntake(intake);	
			 intakeForm.setStudentHistory(studentHistory);
			 intakeForm.setStudentPassHistory(studentPassHistory);
			 
			 //convert phsycial effects to Flags
			 this.setPhysicalEffects(intakeForm);			 
			 
			 /*
			  * Find the most recent status and set it for the current
			  */
			 StudentHistory currentStatus = new StudentHistory();
				for (java.util.Iterator<StudentHistory> iterator =
						studentHistory.iterator(); iterator.hasNext();) {
					    currentStatus = (StudentHistory) iterator.next();
				}
				intakeForm.setCurrentStatus(currentStatus);
				
				
				if ("FastFind".equals(request.getParameter("src")))
			 		return mapping.findForward(Constants.STATUS);
			 	else
			 		return mapping.findForward(Constants.EDIT);
		 } 
		 else if ("Delete Pass History".equals(action)) {
			 Long id = intakeForm.getDeleteId();
			 studentPassHistoryDao.deleteStudentPassHistory(id);
			 intakeForm.setStudentPassHistory(studentPassHistoryDao.findByIntakeId(intakeForm.getIntake().getIntakeId()));
			 return mapping.findForward(Constants.PASS);
		 }
		 else if ("Delete History".equals(action)) {
			 Long id = intakeForm.getDeleteId();
			 studentHistoryDao.deleteStudentHistory(id);
			 intakeForm.setStudentHistory(studentHistoryDao.findByIntakeId(intakeForm.getIntake().getIntakeId()));
			 StudentHistory currentStatus = new StudentHistory();
				for (java.util.Iterator<StudentHistory> iterator =
						intakeForm.getStudentHistory().iterator(); iterator.hasNext();) {
					    currentStatus = (StudentHistory) iterator.next();
				}
				intakeForm.setCurrentStatus(currentStatus);
			 return mapping.findForward(Constants.STATUS);
		 }
		 else if ("Edit History".equals(action)) {
			 Integer id = intakeForm.getEditIndex();
			 Long key = intakeForm.getEditId();
			 //System.out.println ("id="+id+", key="+key);
			 StudentHistory status = studentHistoryDao.findById(key);
			 intakeForm.setEditStatus(status);
			 intakeForm.setStudentHistory(studentHistoryDao.findByIntakeId(intakeForm.getIntake().getIntakeId()));
			 return mapping.findForward(Constants.STATUS);
		 }
		 else if ("Save History Change".equals(action)) {
			 Long key = intakeForm.getEditId();
			 studentHistoryDao.updateStudentHistory(intakeForm.getEditStatus());
			 intakeForm.setEditStatus(new StudentHistory());
			 intakeForm.setStudentHistory(studentHistoryDao.findByIntakeId(intakeForm.getIntake().getIntakeId()));
			 intakeForm.setEditId(null);
			 intakeForm.setEditIndex(new Integer(-1));
			 
			 StudentHistory currentStatus = new StudentHistory();
				for (java.util.Iterator<StudentHistory> iterator =
						intakeForm.getStudentHistory().iterator(); iterator.hasNext();) {
					    currentStatus = (StudentHistory) iterator.next();
				}
				intakeForm.setCurrentStatus(currentStatus);

			 return mapping.findForward(Constants.STATUS);
		 }
		 else if ("Delete Discipline History".equals(action)) {
			 Long id = intakeForm.getDeleteId();
			 studentDisciplineHistoryDao.deleteStudentDisciplineHistory(id);
			 intakeForm.setStudentDisciplineHistory(studentDisciplineHistoryDao.findByIntakeId(intakeForm.getIntake().getIntakeId()));
			 return mapping.findForward(Constants.DISCIPLINE);
		 }
		 else if ("Save".equals(action)) {
			 intakeForm.setMessageType("");
			 
			 if (user!=null)
				 intakeForm.getIntake().setFarmBase(user.getFarmBase());
			 boolean valid = inakeValidator.validate(intakeForm);
			
			 if (intakeForm.getIntake().getDob().length()==10) {
				 intakeForm.getIntake().setDob(intakeForm.getIntake().getDob().replace("-", "/"));
			 		 
			 //auto calculate Age
			 intakeForm.getIntake().setAge(validator.calculateAge(intakeForm.getIntake().getDob())+""); 
			 }
			 if (intakeForm.getIntake().getEntryDate()!=null&&intakeForm.getIntake().getEntryDate().length()==10)
				 intakeForm.getIntake().setDob(intakeForm.getIntake().getEntryDate().replace("-", "/"));
			
			 
			 if (valid) {
				
				 if (intakeForm.getIntake().getIntakeId()!=null) {
					 intakeForm.getIntake().setLastUpdatedDate(validator.getEpoch()+"");
					 intakeForm.getIntake().setLastUpdatedBy(user.getUsername());
					 if ("health".equals(intakeForm.getPageSource()))
						 saveMedicalConditions(intakeForm);
					 if ("substance".equals(intakeForm.getPageSource()))
					 	 saveUsagePatternAndLosses(intakeForm);
					 if ("employment".equals(intakeForm.getPageSource()))
					 	 saveJobSkills(intakeForm);
					 if ("status".equals(intakeForm.getPageSource()))
					 	 saveStatus(intakeForm,user);
					 if ("pass".equals(intakeForm.getPageSource()))
					 	 savePass(intakeForm,user);
					 if ("discipline".equals(intakeForm.getPageSource()))
					 	 saveDiscipline(intakeForm,user.getUsername()); 
					 intakeDao.updateIntake(intakeForm.getIntake());
				 }
				 else {

    				 intakeForm.getIntake().setCreationDate(validator.getEpoch()+"");
					 intakeForm.getIntake().setCreatedBy(user.getUsername());
					 
					 if ("personal".equals(intakeForm.getPageSource())) {
						 intakeForm.getIntake().setIntakeStatus("In Program");
						 intakeForm.getIntake().setApplicationStatus("In Program");
						 intakeForm.getIntake().setClass_("Orientation");
					 }
					 if ("health".equals(intakeForm.getPageSource()))
						 saveMedicalConditions(intakeForm);
					 if ("substance".equals(intakeForm.getPageSource()))
					 	 saveUsagePatternAndLosses(intakeForm);
					 if ("employment".equals(intakeForm.getPageSource()))
					 	 saveJobSkills(intakeForm);
					 if ("status".equals(intakeForm.getPageSource()))
					 	 saveStatus(intakeForm,user);
					 if ("pass".equals(intakeForm.getPageSource()))
					 	 savePass(intakeForm,user);
					 if ("discipline".equals(intakeForm.getPageSource()))
					 	 saveDiscipline(intakeForm,user.getUsername());
					 					 
					 if (intakeForm.getIntake().getFarmBase()==null)
						 intakeForm.getIntake().setFarmBase(user.getFarmBase());
					 
					 Long id = intakeDao.addIntake(intakeForm.getIntake());
					 intakeForm.getIntake().setIntakeId(id);
					 
					 if ("personal".equals(intakeForm.getPageSource())) {
						 this.setInitialStatus(intakeForm, user);
						 List studentHistory = studentHistoryDao.search(intakeForm.getIntake().getIntakeId());
						 intakeForm.setStudentHistory(studentHistory);
						 intakeForm.getIntake().setArchivedFlag("No");
						 /*
						  * Find the most recent status and set it for the current
						  */
						 StudentHistory currentStatus = new StudentHistory();
							for (java.util.Iterator<StudentHistory> iterator =
									studentHistory.iterator(); iterator.hasNext();) {
								    currentStatus = (StudentHistory) iterator.next();
							}
							intakeForm.setCurrentStatus(currentStatus);
							
							
					 }
					 
				 }
				 
			 }
			 return mapping.findForward(intakeForm.getPageSource());
		 } 
		
		 
		 return mapping.findForward(Constants.SUCCESS);
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
	private void setIntakeMedicalConditions(List<IntakeMedicalCondition> intakeMedicalCondition, IntakeForm intakeForm) {
		 
		String medicalCondition[] = new String[] { "No", "No", "No", "No",
					"No", "No", "No", "No", "No", "No", "No", "No", "No", "No", "No",
					"No", "No", "No", "No", "No", "No", "No", "No", "No", "No", "No",
					"No", "No", "No", "No", "No", "No", "No", "No" };
		  
		for (java.util.Iterator<IntakeMedicalCondition> iterator =
				  intakeMedicalCondition.iterator(); iterator.hasNext();) {
			  IntakeMedicalCondition obj = (IntakeMedicalCondition) iterator.next();
			  medicalCondition[obj.getMedicalConditionId().intValue()-1]="YES";
		  }
		
		intakeForm.setMedicalCondition(medicalCondition);
	}
	
	
	private void setIntakeQuestionAnswer(List<IntakeQuestionAnswer> intakeQuestionAnswer, IntakeForm intakeForm) {
		 
		 String healthAnswer[] = new String[] { "No", "No", "No", "No", "No",
				"No", "No", "No", "No", "No", "No", "No", "No", "No", "No", "No",
				"No", "No", "No", "No", "No", "No", "No", "No", "No", "No", "No",
				"No", "No", "No", "No", "No", "No", "No" };
		
		 String emotionalAnswer[] = new String[] { "No", "No", "No", "No", "No",
				"No", "No", "No", "No", "No", "No", "No", "No", "No", "No", "No",
				"No", "No", "No", "No", "No", "No", "No", "No", "No", "No", "No",
				"No", "No", "No", "No", "No", "No", "No" };

		 String physicalAnswer[] = new String[] { "No", "No", "No", "No", "No",
				"No", "No", "No", "No", "No", "No", "No", "No", "No", "No", "No",
				"No", "No", "No", "No", "No", "No", "No", "No", "No", "No", "No",
				"No", "No", "No", "No", "No", "No", "No" };
		
		 String mentalAnswer[] = new String[] { "No", "No", "No", "No", "No",
				"No", "No", "No", "No", "No", "No", "No", "No", "No", "No", "No",
				"No", "No", "No", "No", "No", "No", "No", "No", "No", "No", "No",
				"No", "No", "No", "No", "No", "No", "No" };
		
		 String emotionalAnswerDate[] = new String[] { "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "" };

		 String emotionalAnswerDetails[] = new String[] { "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "" };

		 String mentalAnswerDate[] = new String[] { "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "" };

		 String mentalAnswerDetails[] = new String[] { "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "" };

		 String physicalAnswerDetails[] = new String[] { "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "" };

		 String questionAnswerDates[] = new String[] { "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "" };

		for (java.util.Iterator<IntakeQuestionAnswer> iterator =
				intakeQuestionAnswer.iterator(); iterator.hasNext();) {
			IntakeQuestionAnswer obj = (IntakeQuestionAnswer) iterator.next();
			int id=(int)obj.getQuestionId()-1;
			
			if (id<15)
				healthAnswer[id]="YES";
			else if (id>14&&id<21) {
				emotionalAnswer[id-15]="YES";
				emotionalAnswerDate[id-15]=obj.getDates();
				emotionalAnswerDetails[id-15]=obj.getDetail();
			}
			else if (id>20&&id<26) {
				physicalAnswer[id-21]="YES";
				physicalAnswerDetails[id-21]=obj.getDetail();
			}
			else if (id>25&&id<32) {
				mentalAnswer[id-26]="YES";
				mentalAnswerDate[id-26]=obj.getDates();
				mentalAnswerDetails[id-26]=obj.getDetail();
			}
			
		  }
		
		intakeForm.setHealthAnswer(healthAnswer);
		intakeForm.setMentalAnswer(mentalAnswer);
		intakeForm.setMentalAnswerDate(mentalAnswerDate);
		intakeForm.setMentalAnswerDetails(mentalAnswerDetails);
		intakeForm.setPhysicalAnswer(physicalAnswer);
		intakeForm.setPhysicalAnswerDetails(physicalAnswerDetails);
		intakeForm.setEmotionalAnswer(emotionalAnswer);
		intakeForm.setEmotionalAnswerDate(emotionalAnswerDate);
		intakeForm.setEmotionalAnswerDetails(emotionalAnswerDetails);
	}
	

	
	private void setIntakeJobSkills(List<IntakeJobSkill> intakeJobSkill, IntakeForm intakeForm) {
		 
		String workExperience[] = new String[] { "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "" };
		
		for (java.util.Iterator<IntakeJobSkill> iterator =
				  intakeJobSkill.iterator(); iterator.hasNext();) {
			IntakeJobSkill obj = (IntakeJobSkill) iterator.next();
			  workExperience[(int)obj.getJobSkillId()-1]="YES";
		  }
		
		intakeForm.setWorkExperience(workExperience);
	}
	
	private void saveIntakeQuestionAnswer(IntakeForm intakeForm) {
		
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
	
	private void saveMedicalConditions(IntakeForm intakeForm) {
		 
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
	
	private void saveJobSkills(IntakeForm intakeForm) {
		 
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
	
	private void saveUsagePatternAndLosses(IntakeForm intakeForm) {
		intakeForm.getIntake().setUsageLosses(intakeForm.getUsageLosses1()+
				intakeForm.getUsageLosses2()+
				intakeForm.getUsageLosses3()+
				intakeForm.getUsageLosses4()+
				intakeForm.getUsageLosses5()+
				intakeForm.getUsageLosses6()+
				intakeForm.getUsageLosses7()+
				intakeForm.getUsageLosses8()+
				intakeForm.getUsageLosses9()				
				);
		
		intakeForm.getIntake().setUsagePattern(intakeForm.getUsagePattern1()+ 
				intakeForm.getUsagePattern2()+ 
				intakeForm.getUsagePattern3()+ 
				intakeForm.getUsagePattern4()+ 
				intakeForm.getUsagePattern5()+ 
				intakeForm.getUsagePattern6() 
				);
		
		this.convertPhysicalEffects(intakeForm);		
	}
	
	private void convertPhysicalEffects(IntakeForm intakeForm) {
		String physicalEffects="";
		if ("YES".equals(intakeForm.getMotivationalLossFlag()))
				physicalEffects+="motivational loss,";
		if ("YES".equals(intakeForm.getShakesConvulsionsFlag()))
			physicalEffects+="shakes-convulsions,";
		if ("YES".equals(intakeForm.getMemoryLossFlag()))
			physicalEffects+="memory loss,";
		if ("YES".equals(intakeForm.getIncoherentThinkingFlag()))
			physicalEffects+="incoherent thinking,";
		if ("YES".equals(intakeForm.getOrganProblemsFlag()))
			physicalEffects+="organ problems,";
		
		intakeForm.getIntake().setPhysicalEffects(physicalEffects);		
	}
	
	private void setPhysicalEffects(IntakeForm intakeForm) {
		String physicalEffects=intakeForm.getIntake().getPhysicalEffects();
		
		if (physicalEffects!=null) {			
			if (physicalEffects.contains("motivational"))
				intakeForm.setMotivationalLossFlag("YES");
			if (physicalEffects.contains("shakes"))
				intakeForm.setShakesConvulsionsFlag("YES");
			if (physicalEffects.contains("memory"))
				intakeForm.setMemoryLossFlag("YES");
			if (physicalEffects.contains("incoherent"))
				intakeForm.setIncoherentThinkingFlag("YES");
			if (physicalEffects.contains("organ"))
				intakeForm.setOrganProblemsFlag("YES");
		}
	}
	
	private void saveStatus(IntakeForm intakeForm, SystemUser user) {
		
		StudentHistoryDao dao = new StudentHistoryDao();
		IntakeDao intakeDao = new IntakeDao();
		StudentPassHistoryDao dao1 = new StudentPassHistoryDao();
		StudentPassHistory passHistory = intakeForm.getPassHistory();
		StudentHistory studentHistory = intakeForm.getHistory();
		
		
		if (studentHistory.getFarm().length()>0 && 
				studentHistory.getPhase().length()>0 &&
				studentHistory.getProgramStatus().length()>0 &&
				studentHistory.getBeginDate().length()>0||studentHistory.getEndDate().length()>0) {
			studentHistory.setIntakeId(intakeForm.getIntake().getIntakeId());
			studentHistory.setCreationDate(validator.getEpoch()+"");
			studentHistory.setCreatedBy(user.getUsername());
			dao.addStudentHistory(studentHistory);
			
			//update intakeStatus to program status
			intakeForm.getIntake().setIntakeStatus(studentHistory.getProgramStatus());
			intakeForm.getIntake().setApplicationStatus(studentHistory.getProgramStatus());
			intakeForm.getIntake().setFarmBase(studentHistory.getFarm());
			intakeDao.updateIntake(intakeForm.getIntake());
			
		}
		
		/*
		if (passHistory.getPassDate().length()>0 && 
				passHistory.getHours().length()>0 &&
				passHistory.getPassType().length()>0) {
			passHistory.setIntakeId(intakeForm.getIntake().getIntakeId());
			passHistory.setCreationDate(validator.getEpoch()+"");
			passHistory.setCreatedBy(user.getUsername());
			dao1.addStudentPassHistory(passHistory);
		}
		*/
		
		//intakeForm.setPassHistory(new StudentPassHistory());
		intakeForm.setHistory(new StudentHistory());
		intakeForm.setCurrentStatus(studentHistory);
		intakeForm.setStudentHistory(dao.findByIntakeId(intakeForm.getIntake().getIntakeId()));
		//intakeForm.setStudentPassHistory(dao1.findByIntakeId(intakeForm.getIntake().getIntakeId()));		
		
		
	}
	
	private void savePass(IntakeForm intakeForm, SystemUser user) {
		
		StudentPassHistoryDao dao1 = new StudentPassHistoryDao();
		StudentPassHistory passHistory = intakeForm.getPassHistory();

		if (passHistory.getPassDate().length()>0 && 
				passHistory.getHours().length()>0 &&
				passHistory.getPassType().length()>0) {
			passHistory.setIntakeId(intakeForm.getIntake().getIntakeId());
			passHistory.setCreationDate(validator.getEpoch()+"");
			passHistory.setCreatedBy(user.getUsername());
			dao1.addStudentPassHistory(passHistory);
		}
		
		intakeForm.setPassHistory(new StudentPassHistory());
		intakeForm.setStudentPassHistory(dao1.findByIntakeId(intakeForm.getIntake().getIntakeId()));		
	}
	
	private void clearForm(IntakeForm intakeForm) {
		 intakeForm.setCurrentStatus(new StudentHistory());
		 intakeForm.setEmotionalAnswer(new String[15]);
		 intakeForm.setEmotionalAnswerDate(new String[15]);
		 intakeForm.setEmotionalAnswerDetails(new String[15]);
		 intakeForm.setMentalAnswer(new String[15]);
		 intakeForm.setMentalAnswerDate(new String[15]);
		 intakeForm.setMentalAnswerDetails(new String[15]);
		 intakeForm.setPhysicalAnswer(new String[15]);
		 intakeForm.setPhysicalAnswerDetails(new String[15]);
		 intakeForm.setHealthAnswer(new String[15]);
		 intakeForm.setHistory(new StudentHistory());
		 intakeForm.setStudentHistory(new ArrayList<StudentHistory>());
		 intakeForm.setStudentPassHistory(null);//new ArrayList<StudentPassHistory>());
		 intakeForm.setPassHistory(new StudentPassHistory());
		 intakeForm.setWorkExperience(new String[30]);
		 intakeForm.setIntake(new Intake());
		 intakeForm.setUsageLosses1("");
		 intakeForm.setUsageLosses2("");
		 intakeForm.setUsageLosses3("");
		 intakeForm.setUsageLosses4("");
		 intakeForm.setUsageLosses5("");
		 intakeForm.setUsageLosses6("");
		 intakeForm.setUsageLosses7("");
		 intakeForm.setUsageLosses8("");
		 intakeForm.setUsageLosses9("");
		 intakeForm.setUsagePattern1("");
		 intakeForm.setUsagePattern2("");
		 intakeForm.setUsagePattern3("");
		 intakeForm.setUsagePattern4("");
		 intakeForm.setUsagePattern5("");
		 intakeForm.setUsagePattern6("");
		 intakeForm.setMotivationalLossFlag("");
		 intakeForm.setShakesConvulsionsFlag("");
		 intakeForm.setMemoryLossFlag("");
		 intakeForm.setIncoherentThinkingFlag("");
		 intakeForm.setOrganProblemsFlag("");
	}
	

	private void saveDiscipline(IntakeForm intakeForm,String username) {
		
		StudentDisciplineHistoryDao dao = new StudentDisciplineHistoryDao();
		intakeForm.getDisciplineHistory().setIntakeId(intakeForm.getIntake().getIntakeId());
		intakeForm.getDisciplineHistory().setCreatedBy(username);
		intakeForm.getDisciplineHistory().setCreationDate(validator.getEpoch()+"");
		dao.addStudentDisciplineHistory(intakeForm.getDisciplineHistory());
		intakeForm.setDisciplineHistory(new StudentDisciplineHistory());
		
		
	}
	
	private String flagStudentStay(IntakeForm intakeForm) {
		String msg="";
		
		StudentHistoryDao dao = new StudentHistoryDao();
		Intake intake = intakeForm.getIntake();
		
		List<StudentHistory> list = dao.findByIntakeId(intake.getIntakeId());
		
		StudentHistory history = new StudentHistory();
		
		if (list!=null)
			history = (StudentHistory)list.get(0);
		
		String phase = history.getPhase();
		String status = history.getProgramStatus();
		String beginDate = history.getBeginDate();
		
		if ("In Program".equals(status)) {
			if ("Graduate".equals(phase)) {
				
			}
			if ("Omega Work".equals(phase)) {
				
			}
			if ("Omega School".equals(phase)) {
							
			}
		}
		
		return msg;
	}
	
	private void setInitialStatus(IntakeForm intakeForm, SystemUser user) {
		StudentHistoryDao dao = new StudentHistoryDao();
		Intake intake = intakeForm.getIntake();
		StudentHistory history = new StudentHistory();
		
		GregorianCalendar calendar = new GregorianCalendar();
		Date now = calendar.getTime();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String beginDate=df.format(now);
		
		history.setProgramStatus("In Program");
		history.setPhase("Phase One");
		history.setFarm(user.getFarmBase());
		history.setCreatedBy(user.getUsername());
		history.setCreationDate(validator.getEpoch()+"");
		history.setBeginDate(beginDate);
		history.setIntakeId(intake.getIntakeId());
		
		dao.addStudentHistory(history);
		
	}
	
	
	
}
