package com.yada180.sms.struts.action;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.yada180.sms.application.Constants;
import com.yada180.sms.domain.Intake;
import com.yada180.sms.domain.IntakeJobSkill;
import com.yada180.sms.domain.IntakeMedicalCondition;
import com.yada180.sms.domain.IntakeQuestionAnswer;
import com.yada180.sms.domain.JobSkill;
import com.yada180.sms.domain.MedicalCondition;
import com.yada180.sms.hibernate.data.IntakeMedicalConditionDao;
import com.yada180.sms.hibernate.data.IntakeQuestionAnswerDao;
import com.yada180.sms.hibernate.data.IntakeDao;
import com.yada180.sms.hibernate.data.IntakeJobSkillDao;
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
		LOGGER.setLevel(Level.SEVERE);

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
			session.removeAttribute("session_expired");
			System.out.println ("!!!!---->"+onlineAppForm.getIntake().getFirstname()+" "+onlineAppForm.getIntake().getLastname()+" click Next on "+onlineAppForm.getPageSource()+" @ "+(new java.util.Date()));
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
			System.out.println ("---->"+onlineAppForm.getIntake().getFirstname()+" "+onlineAppForm.getIntake().getLastname()+" click Next on "+onlineAppForm.getPageSource()+" @ "+(new java.util.Date()));
			
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
			System.out.println ("---->"+onlineAppForm.getIntake().getFirstname()+" "+onlineAppForm.getIntake().getLastname()+" click Next on "+onlineAppForm.getPageSource()+" @ "+(new java.util.Date()));
			
			
			/*
			 * Prevent NULL applications being submitted after user goes away from computer
			 * returns and presses submit
			 */
			if (onlineAppForm.getIntake().getFirstname()==null) {
				session.setAttribute("session_expired", "true");
				return mapping.findForward(Constants.PERSONAL);
			}
				
			onlineAppForm.setMessageType("");
			String submitDate = Validator.convertEpoch(Validator.getEpoch());
			onlineAppForm.getIntake().setApplicationSubmissionDate(submitDate);
			
			// boolean valid = inakeValidator.validate(intakeForm);
			if (true) {
				onlineAppForm.getIntake().setCreationDate(
						validator.getEpoch() + "");
				onlineAppForm.getIntake().setCreatedBy("online application");
				onlineAppForm.getIntake().setApplicationStatus("Pending");
				
				//temp log all data before attempted save
				this.logApplicationDataOnException(onlineAppForm);
				
				Long id = intakeDao.save(onlineAppForm.getIntake());
				onlineAppForm.getIntake().setIntakeId(id);
				System.out.println ("---->"+onlineAppForm.getIntake().getFirstname()+" "+onlineAppForm.getIntake().getLastname()+" saved application with id "+id+" @ "+(new java.util.Date()));
				
				if (id!=null) {
					saveMedicalConditions(onlineAppForm);
					saveUsagePatternAndLosses(onlineAppForm);
					saveIntakeQuestionAnswer(onlineAppForm);
					saveJobSkills(onlineAppForm,request);
					
					Properties properties = new Properties();
					properties.put("mail.smtp.auth", "true");
					properties.put("mail.smtp.starttls.enable", "true");
					properties.put("mail.smtp.host", "smtp.gmail.com");
					properties.put("mail.smtp.port", "587");
					
					Session mailSession = Session.getDefaultInstance(properties,
							new javax.mail.Authenticator() {
								protected PasswordAuthentication getPasswordAuthentication() {
									return new PasswordAuthentication("faithfarm.intake@gmail.com","It0525Ff");
								}
							});
					try{
				         // Create a default MimeMessage object.
				         MimeMessage message = new MimeMessage(mailSession);

				         message.addRecipient(Message.RecipientType.TO,
			                     new InternetAddress("itdepartment@faithfarm.org"));
				         
				         if ("Boynton Beach".equals(onlineAppForm.getIntake().getFarmBase())) {
				        	 message.addRecipient(Message.RecipientType.TO,
				                     new InternetAddress("intake.boyntonbeach@faithfarm.org"));
				        	 message.addRecipient(Message.RecipientType.TO,
				                     new InternetAddress("PZielinski@faithfarm.org"));
					         message.addRecipient(Message.RecipientType.TO,
				                     new InternetAddress("RJurisDick@faithfarm.org"));
					     }
				         if ("Fort Lauderdale".equals(onlineAppForm.getIntake().getFarmBase())) {
				        	 message.addRecipient(Message.RecipientType.TO,
				                     new InternetAddress("intake.fortlauderdale@faithfarm.org"));
				        	 message.addRecipient(Message.RecipientType.TO,
				                     new InternetAddress("sjohnson@faithfarm.org"));
					         message.addRecipient(Message.RecipientType.TO,
				                     new InternetAddress("iftl@faithfarm.org"));
					     }
				         if ("Okeechobee".equals(onlineAppForm.getIntake().getFarmBase())) {
				        	 message.addRecipient(Message.RecipientType.TO,
				                     new InternetAddress("intake.okeechobee@faithfarm.org"));
				        	 //message.addRecipient(Message.RecipientType.TO,
				             //        new InternetAddress("MMurphy@faithfarm.org"));
				         }
				         if ("Women's Home".equals(onlineAppForm.getIntake().getFarmBase())) {
				        	message.addRecipient(Message.RecipientType.TO,
				                     new InternetAddress("intake.womensministry@faithfarm.org"));
					        message.addRecipient(Message.RecipientType.TO,
				                     new InternetAddress("AGorrin@faithfarm.org"));
					        message.addRecipient(Message.RecipientType.TO,
				                     new InternetAddress("VAndres@faithfarm.org"));
				         }
				             
				         // Set Subject: header field
				         message.setSubject("Intake Application Received for "+onlineAppForm.getIntake().getFirstname()+" "+onlineAppForm.getIntake().getLastname()+" at "+onlineAppForm.getIntake().getFarmBase());

				         // Now set the actual message
				         message.setText("This is an automated response sent to notify you of an application submitted online. Please log into http://apps.faithfarm.org/intake to view the application.  Do not reply to this message.");
				         
				         // Send message
				         try {
				     		String ipAddy=InetAddress.getLocalHost().getHostAddress();
				     		if ("50.63.180.165".equals(ipAddy)) {
				     				Transport.send(message);
				     				System.out.println ("----> email sent for "+onlineAppForm.getIntake().getFirstname()+" "+onlineAppForm.getIntake().getLastname()+" @ "+(new java.util.Date()));
				     			}
				     		} catch (Exception e) {
				     			LOGGER.log(Level.SEVERE,"Error occurred in getting IP when trying to send email: "+e.getMessage());
				     		}
				         
				        }catch (MessagingException mex) {
				         mex.printStackTrace();
				         LOGGER.log(Level.SEVERE,"Error occurred sending email for application: "+mex.getMessage());
				      }
					onlineAppForm.setIntake(new Intake());
					session.invalidate(); 
					return mapping.findForward(Constants.WEBSITE);
				}
				else {
					LOGGER.log(Level.INFO,"Error occurred saving online application:  Data displayed below");
					this.logApplicationDataOnException(onlineAppForm);
					session.setAttribute("SYSTEM_ERROR", "A database error occurred saving the application!");
					return mapping.findForward(Constants.ERROR);
				}
			}

		}
		return mapping.findForward(Constants.PERSONAL);
		}
		catch (Exception e) {
				LOGGER.log(Level.INFO,"Error occurred in online application:  Data displayed below");
				/*
				OnlineAppForm onlineAppForm = (OnlineAppForm) form;
				Intake intake =  onlineAppForm.getIntake();
				try {
				BeanInfo info = Introspector.getBeanInfo(intake.getClass());
				PropertyDescriptor[] props = info.getPropertyDescriptors();  
				for (int i=0;i<props.length;i++) {  
					PropertyDescriptor descriptor = props [i];
					LOGGER.log(Level.SEVERE,props[i].getDisplayName()+"="+descriptor.getReadMethod().invoke(intake, null));
					}			    
				}
				catch (Exception ex) { System.out.println(""); }
				*/
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);			
				session.setAttribute("SYSTEM_ERROR", sw.toString());
				e.printStackTrace();
				return mapping.findForward(Constants.ERROR);
			}
	}

	private void saveIntakeQuestionAnswer(OnlineAppForm intakeForm) {
		try{
		IntakeQuestionAnswerDao dao = new IntakeQuestionAnswerDao();
		
		 /*
		  * First delete all medical conditions for given intake
		  */
		 List<IntakeQuestionAnswer> intakeQuestionAnswers = dao.findByIntakeId(new IntakeQuestionAnswer().getClass(), intakeForm.getIntake().getIntakeId());
		 for (Iterator iterator =
				 intakeQuestionAnswers.iterator(); iterator.hasNext();){
			 IntakeQuestionAnswer obj = (IntakeQuestionAnswer) iterator.next();
			 dao.delete(obj);
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
			   if ("Yes".equals(healthAnswer[index])) {
	    		   IntakeQuestionAnswer iqa = new IntakeQuestionAnswer();
	    		   iqa.setIntakeId(intakeForm.getIntake().getIntakeId());
	    		   iqa.setAnswer("Yes");	 
	    		   iqa.setQuestionId(new Long(index+1));
	    		   dao.save(iqa);
	    		 }
	    	   
	    	   if ("Yes".equals(emotionalAnswer[index])) {
	    		   IntakeQuestionAnswer iqa = new IntakeQuestionAnswer();
	    		   iqa.setIntakeId(intakeForm.getIntake().getIntakeId());
	    		   iqa.setAnswer("Yes");	
	    		   iqa.setDates(emotionalAnswerDate[index]);
	    		   iqa.setDetail(emotionalAnswerDetails[index]);
	    		   iqa.setQuestionId(new Long(index+16));
	    		   dao.save(iqa);
	    		 }
	    	   
	    	   if ("Yes".equals(physicalAnswer[index])) {
	    		   IntakeQuestionAnswer iqa = new IntakeQuestionAnswer();
	    		   iqa.setIntakeId(intakeForm.getIntake().getIntakeId());
	    		   iqa.setAnswer("Yes");	
	    		   iqa.setDetail(physicalAnswerDetails[index]);
	    		   iqa.setQuestionId(new Long(index+22));
	    		   dao.save(iqa);
	    		 }
	    	   
	    	   if ("Yes".equals(mentalAnswer[index])) {
	    		   IntakeQuestionAnswer iqa = new IntakeQuestionAnswer();
	    		   iqa.setIntakeId(intakeForm.getIntake().getIntakeId());
	    		   iqa.setAnswer("Yes");	
	    		   iqa.setDates(mentalAnswerDate[index]);
	    		   iqa.setDetail(mentalAnswerDetails[index]);
	    		   iqa.setQuestionId(new Long(index+27));
	    		   dao.save(iqa);
	    		 }
	    }
		} catch (Exception e) {
			 LOGGER.log(Level.SEVERE,"Error in saveIntakeQuestionAnswer:"+intakeForm.getIntake().getIntakeId()+":"+e.getMessage());
		 }

	}
	
	private void saveMedicalConditions(OnlineAppForm intakeForm) {
		 
		try {
			
		 IntakeMedicalConditionDao dao = new IntakeMedicalConditionDao();

		 
		 /*
		  * First delete all medical conditions for given intake
		  */
		 List<IntakeMedicalCondition> intakeMedicalConditions = dao.findByIntakeId(new IntakeMedicalCondition().getClass(), intakeForm.getIntake().getIntakeId());
		 for (Iterator iterator =
				 intakeMedicalConditions.iterator(); iterator.hasNext();){
			 IntakeMedicalCondition obj = (IntakeMedicalCondition) iterator.next();
			 dao.delete(obj);
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
	    	   
	    	   if ("Yes".equals(medicalCondition[index])) {
	    		   IntakeMedicalCondition imc = new IntakeMedicalCondition();
	    		   imc.setIntakeId(intakeForm.getIntake().getIntakeId());
	    		   imc.setMedicalConditionId(obj.getMedicalConditionId());
	    		   imc.setAnswer("Yes");
	    		   dao.save(imc);
	    		 }
	    	   
	    	   
	    	 index++;
	       }
		}
		 catch (Exception e) {
			 LOGGER.log(Level.SEVERE,"Error in saveMedicalConditions:"+intakeForm.getIntake().getIntakeId()+":"+e.getMessage());
		 }
	       
                
	}
	private void saveJobSkills(OnlineAppForm intakeForm, HttpServletRequest request) {

		try {
		IntakeJobSkillDao dao = new IntakeJobSkillDao();

		/*
		 * First delete all Job SKills for given intake
		 */
		List<IntakeJobSkill> intakeJobSkills = dao.findByIntakeId(new IntakeJobSkill().getClass(), intakeForm.getIntake().getIntakeId());
				//.findById(intakeForm.getIntake().getIntakeId());
		for (Iterator iterator = intakeJobSkills.iterator(); iterator.hasNext();) {
			IntakeJobSkill obj = (IntakeJobSkill) iterator.next();
			dao.delete(obj);//deleteIntakeJobSkill(obj.getIntakeJobSkillId());
		}

		/*
		 * write to db job skills for given intake marked yes
		 */
		List<JobSkill> jobSkills = intakeForm.getJobSkills();
		String workexperience[] = intakeForm.getWorkExperience();
		int index = 0;
		for (Iterator iterator = jobSkills.iterator(); iterator
				.hasNext();) {
			JobSkill obj = (JobSkill) iterator.next();
			//String workExperienceFlag=request.getParameter("workExperience["+index+"]");
			if ("Yes".equals(workexperience[index])) {
				IntakeJobSkill ijs = new IntakeJobSkill();
				ijs.setIntakeId(intakeForm.getIntake().getIntakeId());
				ijs.setJobSkillId(obj.getJobSkillId());
				dao.save(ijs);//.addIntakeJobSkill(ijs);
			}
			index++;
		}
		}catch (Exception e) {
			 LOGGER.log(Level.SEVERE,"Error in saveJobSkills:"+intakeForm.getIntake().getIntakeId()+":"+e.getMessage());
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
		
		this.convertPhysicalEffects(intakeForm);
		
	}
	
	private void logApplicationDataOnException (ActionForm form) {
			LOGGER.log(Level.SEVERE,"Online application submitted:  Data displayed below");
			OnlineAppForm onlineAppForm = (OnlineAppForm) form;
			Intake intake =  onlineAppForm.getIntake();
			try {
			BeanInfo info = Introspector.getBeanInfo(intake.getClass());
			PropertyDescriptor[] props = info.getPropertyDescriptors();  
			for (int i=0;i<props.length;i++) {  
				PropertyDescriptor descriptor = props [i];
				System.out.println(props[i].getDisplayName()+"="+descriptor.getReadMethod().invoke(intake, null));
				}			    
			}
			catch (Exception ex) { LOGGER.log(Level.INFO,"Error in logApplicationDataOnException:"+ex.getMessage()); }
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
	}
	
	private void convertPhysicalEffects(OnlineAppForm intakeForm) {
		String physicalEffects="";
		if ("Yes".equals(intakeForm.getMotivationalLossFlag()))
				physicalEffects+="motivational loss,";
		if ("Yes".equals(intakeForm.getShakesConvulsionsFlag()))
			physicalEffects+="shakes-convulsions,";
		if ("Yes".equals(intakeForm.getMemoryLossFlag()))
			physicalEffects+="memory loss,";
		if ("Yes".equals(intakeForm.getIncoherentThinkingFlag()))
			physicalEffects+="incoherent thinking,";
		if ("Yes".equals(intakeForm.getOrganProblemsFlag()))
			physicalEffects+="organ problems,";
		
		intakeForm.getIntake().setPhysicalEffects(physicalEffects);		
	}

}