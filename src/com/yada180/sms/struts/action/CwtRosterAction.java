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
import com.yada180.sms.domain.CwtDepartment;
import com.yada180.sms.domain.CwtJob;
import com.yada180.sms.domain.CwtMaster;
import com.yada180.sms.domain.CwtMetrics;
import com.yada180.sms.domain.CwtModuleSection;
import com.yada180.sms.domain.CwtModules;
import com.yada180.sms.domain.CwtProgram;
import com.yada180.sms.domain.CwtRoster;
import com.yada180.sms.domain.CwtSupervisor;
import com.yada180.sms.domain.Intake;
import com.yada180.sms.domain.StudentHistory;
import com.yada180.sms.domain.SystemUser;
import com.yada180.sms.hibernate.dao.CwtDepartmentDao;
import com.yada180.sms.hibernate.dao.CwtJobDao;
import com.yada180.sms.hibernate.dao.CwtMetricsDao;
import com.yada180.sms.hibernate.dao.CwtModuleSectionDao;
import com.yada180.sms.hibernate.dao.CwtModulesDao;
import com.yada180.sms.hibernate.dao.CwtProgramDao;
import com.yada180.sms.hibernate.dao.CwtProgramMetricDao;
import com.yada180.sms.hibernate.dao.CwtProgramMetricModulesDao;
import com.yada180.sms.hibernate.dao.CwtRosterDao;
import com.yada180.sms.hibernate.dao.CwtSupervisorDao;
import com.yada180.sms.hibernate.dao.IntakeDao;
import com.yada180.sms.hibernate.dao.StudentHistoryDao;
import com.yada180.sms.struts.form.CwtRosterForm;
import com.yada180.sms.util.HtmlDropDownBuilder;
import com.yada180.sms.util.Validator;
import com.yada180.sms.validator.IntakeValidator;

public class CwtRosterAction extends Action {
	
	private final static Logger LOGGER = Logger.getLogger(LoginAction.class.getName());
	private final static HtmlDropDownBuilder html = new HtmlDropDownBuilder();
	private final static IntakeValidator inakeValidator = new IntakeValidator();
	private final static Validator validator = new Validator();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {		
		LOGGER.setLevel(Level.INFO);

		 HttpSession session = request.getSession(false);
		 SystemUser user = (SystemUser) session.getAttribute("system_user");

		try {
		 IntakeDao intakeDao = new IntakeDao();
		 CwtProgramDao cwtProgramDao = new CwtProgramDao();
		 CwtMetricsDao cwtMetricsDao = new CwtMetricsDao();
		 CwtModulesDao cwtModulesDao = new CwtModulesDao();
		 CwtProgramMetricDao cwtProgramMetricDao = new CwtProgramMetricDao();
		 CwtProgramMetricModulesDao cwtProgramMetricModuleDao = new CwtProgramMetricModulesDao();
		 CwtModuleSectionDao cwtModuleSectionDao = new CwtModuleSectionDao();
		 CwtDepartmentDao departmentDao = new CwtDepartmentDao();
		 CwtSupervisorDao supervisorDao = new CwtSupervisorDao();
		 CwtJobDao jobDao = new CwtJobDao();
		 StudentHistoryDao studentHistoryDao = new StudentHistoryDao();
		 CwtRosterDao rosterDao = new CwtRosterDao();
		 
		 
		 String action=request.getParameter("action");
		 
		 CwtRosterForm cwtRosterForm = (CwtRosterForm)form;
	
		 if ("Roster".equals(action)) {
			 cwtRosterForm.setCwtMaster(new CwtMaster());
			 cwtRosterForm.setCwtMetric(new CwtMetrics());
			 cwtRosterForm.setCwtModule(new CwtModules());
			 cwtRosterForm.setCwtModuleSection(new CwtModuleSection());
			 cwtRosterForm.setCwtProgram(new CwtProgram());
			 
			 cwtRosterForm.setExamScore(new String[200]);
			 cwtRosterForm.setAttendFlag(new String[200]);
			 
			 String[] flags = new String[] { 
					 "Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes",
					 "Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes",
					 "Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes",
					 "Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes",
					 "Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes",
					 "Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes",
					 "Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes",
					 "Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes",
					 "Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes",
					 "Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes","Yes"
					};
			 cwtRosterForm.setEnrollFlag(flags);
			 
			 String id=request.getParameter("id");
			 cwtRosterForm.setCwtModuleSection(cwtModuleSectionDao.findById(new Long(id)));
			 cwtRosterForm.setCwtModule(cwtModulesDao.findById(cwtRosterForm.getCwtModuleSection().getModuleId()));
			 
			 List<CwtRoster> rosterList = rosterDao.listRosterBySection(new Long(id));
			 
			 /*
			  * If roster currently exists then set it up 
			  * 
			  */
			 int index=0;
			 if (rosterList.size()>0) {
				 String attend[]=cwtRosterForm.getAttendFlag();
				 String score[]=cwtRosterForm.getExamScore();
				 
				 List<CwtMaster> masters = new ArrayList<CwtMaster>();
				 for (Iterator iterator = rosterList.iterator(); iterator.hasNext();) {
					 CwtRoster roster = (CwtRoster)iterator.next();
					 
					 CwtDepartment department=new CwtDepartment();
					 if (roster.getDepartmentId()!=null)
						 department = departmentDao.findById(roster.getDepartmentId()); 
					 
					 CwtSupervisor supervisor = new CwtSupervisor();
					 if (roster.getSupervisorId()!=null)
						supervisor=supervisorDao.findById(roster.getSupervisorId());
					 
					 CwtJob job = new CwtJob();
					 if (roster.getJobId()!=null)
						 job=jobDao.findById(roster.getJobId());
					 
					 Intake intake = new Intake();
					 if (roster.getIntakeId()!=null)
						 intake=intakeDao.findById(roster.getIntakeId());	
					 
					 attend[index]=roster.getAttendFlag();
					 score[index]=roster.getExamScore();
					 CwtMaster master = new CwtMaster();
					 master.setCwtSupervisor(supervisor);
					 master.setCwtDepartment(department);
					 master.setCwtJob(job);
					 master.setIntake(intake);
					 master.setRoster(roster);
					 masters.add(master);
					 index++;
				 }
				 cwtRosterForm.setMasterList(masters);
				 cwtRosterForm.setAttendFlag(attend);
				 cwtRosterForm.setExamScore(score);
						
			 } else {
			 
					 List<CwtMaster> masters = new ArrayList<CwtMaster>();
					 List<Intake> intakes = intakeDao.search(null, null, null, null, null, null, user.getFarmBase(),null,null,null);
					 
					 for (Iterator iterator = intakes.iterator(); iterator.hasNext();) {
						 Intake intake = (Intake)iterator.next();
						 
						 //check current Program Status for each intake
						 List<StudentHistory> history = studentHistoryDao.findByIntakeId(intake.getIntakeId());
						 String status="";
						 for (Iterator iterator2 = history.iterator(); iterator2.hasNext();) {
							 StudentHistory studentHistory = (StudentHistory)iterator2.next();
							 status = studentHistory.getProgramStatus();
						 }
						 
						 if ("In Program".equals(status)) {
							 CwtDepartment department = new CwtDepartment();
							 if (intake.getDepartmentId()!=null)
								 department=departmentDao.findById(intake.getDepartmentId());
							 
							 CwtSupervisor supervisor = new CwtSupervisor();
							 if (intake.getSupervisorId()!=null)
								 supervisor=supervisorDao.findById(intake.getSupervisorId());
							 
							 CwtJob job = new CwtJob();
							 if (intake.getJobId()!=null)
								 job=jobDao.findById(intake.getJobId());
							 if (job==null)
								 job = new CwtJob();
							 
							 CwtMaster master = new CwtMaster();
							 master.setCwtSupervisor(supervisor);
							 master.setCwtDepartment(department);
							 master.setCwtJob(job);
							 master.setIntake(intake);
							 masters.add(master);
							 
						 }
					 }
					 cwtRosterForm.setMasterList(masters);
					 return mapping.findForward(Constants.CREATE_ROSTER);
			 }//end of generate roster
			 
			 return mapping.findForward(Constants.SUCCESS);	
		 }
		 else if ("Generate Roster".equals(action)) {
			 int index=0;
			 String status="";
			 List <CwtMaster> mlist = cwtRosterForm.getMasterList(); 
			 List <CwtMaster> newMasterList = new ArrayList<CwtMaster>();
			 
			 List <CwtRoster> rosterList = new ArrayList<CwtRoster>();
			 String enroll[]=cwtRosterForm.getEnrollFlag();
			 String studentStatus[]=cwtRosterForm.getStatus();
			 for (Iterator iterator = mlist.iterator(); iterator.hasNext();) {
				 CwtMaster obj = (CwtMaster)iterator.next();
				 if ("Yes".equals(request.getParameter("enrollFlag["+index+"]"))) {
					 CwtRoster roster = new CwtRoster();
					 roster.setIntakeId(obj.getIntake().getIntakeId());
					 roster.setDepartmentId(obj.getCwtDepartment().getDepartmentId());
					 roster.setModuleId(cwtRosterForm.getCwtModule().getModuleId());
					 roster.setJobId(obj.getCwtJob().getJobId());
					 roster.setSupervisorId(obj.getCwtSupervisor().getSupervisorId());
					 roster.setSectionId(cwtRosterForm.getCwtModuleSection().getModuleOfferingId());
					 roster.setLastUpdatedBy(user.getUsername());
					 roster.setLastUpdatedDate(validator.getEpoch()+"");
					 roster.setStatus("Enrolled");
					 roster.setExamDate(obj.getSection().getEffectiveDate());
					 rosterList.add(roster);
					 
					 Long rosterId =rosterDao.addCwtRoster(roster);
					 roster.setRosterId(rosterId);
					 
					 //add roster entry to existing master list
					 CwtMaster master = new CwtMaster();
					 master.setCwtDepartment(obj.getCwtDepartment());
					 master.setCwtJob(obj.getCwtJob());
					 master.setCwtSupervisor(obj.getCwtSupervisor());
					 master.setIntake(obj.getIntake());
					 master.setRoster(roster);
					 studentStatus[index]="Enrolled";
					 newMasterList.add(master);
				 }
				 
			 index++;
			 }
			 cwtRosterForm.setStatus(studentStatus);
			 cwtRosterForm.setMasterList(newMasterList);
			 return mapping.findForward(Constants.ROSTER);
		 }
		 else if ("Save".equals(action)) {
			 int index=0;
			 String status="";
			 List <CwtMaster> mlist = cwtRosterForm.getMasterList(); 
			 List <CwtRoster> rosterList = new ArrayList<CwtRoster>();
			 String enroll[]=cwtRosterForm.getEnrollFlag();
			 String attend[]=cwtRosterForm.getAttendFlag();
			 
			 for (Iterator iterator = mlist.iterator(); iterator.hasNext();) {
				 CwtMaster obj = (CwtMaster)iterator.next();
				 CwtRoster roster = obj.getRoster();
				 roster.setLastUpdatedBy(user.getUsername());
				 roster.setLastUpdatedDate(validator.getEpoch()+"");
				 
				 if ("Yes".equals(request.getParameter("attendFlag["+index+"]")))
					 roster.setAttendFlag("Yes");
				 roster.setExamScore(request.getParameter("examScore["+index+"]"));
				 roster.setStatus(request.getParameter("status["+index+"]"));
				 
				 rosterDao.updateCwtRoster(roster);
			 index++;
			 }
			 
			 //cwtRosterForm.setMasterList(newMasterList);
			 return mapping.findForward(Constants.ROSTER);
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

}
