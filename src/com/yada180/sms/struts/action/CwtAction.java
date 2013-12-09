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
import com.yada180.sms.domain.CwtJobMetric;
import com.yada180.sms.domain.CwtMaster;
import com.yada180.sms.domain.CwtMetrics;
import com.yada180.sms.domain.CwtModuleSection;
import com.yada180.sms.domain.CwtModules;
import com.yada180.sms.domain.CwtProgram;
import com.yada180.sms.domain.CwtProgramMetric;
import com.yada180.sms.domain.CwtProgramMetricModules;
import com.yada180.sms.domain.CwtSupervisor;
import com.yada180.sms.domain.SystemUser;
import com.yada180.sms.hibernate.dao.CwtDepartmentDao;
import com.yada180.sms.hibernate.dao.CwtJobDao;
import com.yada180.sms.hibernate.dao.CwtJobMetricDao;
import com.yada180.sms.hibernate.dao.CwtMetricsDao;
import com.yada180.sms.hibernate.dao.CwtModuleSectionDao;
import com.yada180.sms.hibernate.dao.CwtModuleStudentDao;
import com.yada180.sms.hibernate.dao.CwtModulesDao;
import com.yada180.sms.hibernate.dao.CwtProgramDao;
import com.yada180.sms.hibernate.dao.CwtProgramMetricDao;
import com.yada180.sms.hibernate.dao.CwtProgramMetricModulesDao;
import com.yada180.sms.hibernate.dao.CwtSupervisorDao;
import com.yada180.sms.struts.form.CwtForm;
import com.yada180.sms.util.HtmlDropDownBuilder;
import com.yada180.sms.util.Validator;
import com.yada180.sms.validator.IntakeValidator;

public class CwtAction extends Action {
	
	private final static Logger LOGGER = Logger.getLogger(LoginAction.class.getName());
	private final static HtmlDropDownBuilder html = new HtmlDropDownBuilder();
	private final static IntakeValidator inakeValidator = new IntakeValidator();
	private final static Validator validator = new Validator();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {		
		LOGGER.setLevel(Level.INFO);

		 HttpSession session = request.getSession(false);
		 SystemUser user = (SystemUser) session.getAttribute("system_user");
		 
		 try {
		
		/*
		 * test ddl's stored in session.  If empty, session expired, redirect to login
		 */
		ArrayList ddllist = (ArrayList)session.getAttribute("ddl_farm");
		if (ddllist==null) 
			return mapping.findForward(Constants.LOGIN);
		
		 String action=request.getParameter("action");
		 
		 CwtForm cwtForm = (CwtForm)form;
		 CwtJobDao cwtJobDao = new CwtJobDao();
		 CwtSupervisorDao cwtSupervisorDao = new CwtSupervisorDao();
		 CwtDepartmentDao cwtDepartmentDao = new CwtDepartmentDao();
		 CwtProgramDao cwtProgramDao = new CwtProgramDao();
		 CwtMetricsDao cwtMetricsDao = new CwtMetricsDao();
		 CwtModulesDao cwtModulesDao = new CwtModulesDao();
		 CwtProgramMetricDao cwtProgramMetricDao = new CwtProgramMetricDao();
		 CwtProgramMetricModulesDao cwtProgramMetricModuleDao = new CwtProgramMetricModulesDao();
		 CwtModuleSectionDao cwtModuleSectionDao = new CwtModuleSectionDao();
		 CwtModuleStudentDao cwtModuleStudentDao = new CwtModuleStudentDao();
		 CwtJobMetricDao cwtJobMetricDao = new CwtJobMetricDao();
		 
		 if ("programs".equals(action)) {
			 cwtForm.setProgramList(cwtProgramDao.listCwtPrograms());
			 return mapping.findForward(Constants.PROGRAMS);
		 }
		 else if ("metrics".equals(action)) {
			 cwtForm.setMetricList(cwtMetricsDao.listCwtMetricss());
			 cwtForm.setProgramList(cwtProgramDao.listCwtPrograms());
			 return mapping.findForward(Constants.METRICS);
		 }
		 else if ("modules".equals(action)) {
			 cwtForm.setModuleList(cwtModulesDao.listCwtModuless());
			 cwtForm.setMetricList(cwtMetricsDao.listCwtMetricss());
			 return mapping.findForward(Constants.MODULES);
		 }
		 else if ("departments".equals(action)) {
			 cwtForm.setDepartmentList(cwtDepartmentDao.listCwtDepartments());
			 return mapping.findForward(Constants.DEPARTMENTS);
		 }
		 else if ("jobs".equals(action)) {
			 cwtForm.setJobList(cwtJobDao.listCwtJobs());
			 cwtForm.setMetricList(cwtMetricsDao.listCwtMetricss());
			 return mapping.findForward(Constants.JOBS);
		 }
		 else if ("supervisors".equals(action)) {
			 List<CwtSupervisor> list = cwtSupervisorDao.listCwtSupervisors();
			 List<CwtMaster> masters = new ArrayList<CwtMaster>();
			 for (Iterator iterator = list.iterator();iterator.hasNext();) {
				 CwtSupervisor supervisor = (CwtSupervisor)iterator.next();
				 CwtDepartment department = cwtDepartmentDao.findById(supervisor.getDepartmentId());
				 CwtJob job = cwtJobDao.findById(supervisor.getJobId());
				 
				 CwtMaster master = new CwtMaster();
				 master.setCwtDepartment(department);
				 master.setCwtJob(job);
				 master.setCwtSupervisor(supervisor);
				 masters.add(master);
			 }
			 //cwtForm.setSupervisorList(cwtSupervisorDao.listCwtSupervisors());
			 cwtForm.setMasterList(masters);
			 return mapping.findForward(Constants.SUPERVISORS);
		 }
		 else if ("sections".equals(action)) {
			 this.getSectionDetail(cwtForm);
			 return mapping.findForward(Constants.SECTIONS);
		 }
		 else if ("rotate".equals(action))
			 return mapping.findForward(Constants.ROTATE);
		 else if ("Edit".equals(action)) {
			 String id=request.getParameter("id");
			 String type=request.getParameter("type");
			 
			 if ("Job".equals(type)) {
				 cwtForm.setCwtJob(cwtJobDao.findById(new Long(id)));
				 return mapping.findForward("create_job");
			 }
			 if ("Department".equals(type)) {
				 cwtForm.setCwtDepartment(cwtDepartmentDao.findById(new Long(id)));
				 return mapping.findForward("create_department");
			 }
			 if ("Supervisor".equals(type)) {
				 cwtForm.setCwtSupervisor(cwtSupervisorDao.findById(new Long(id)));
				 return mapping.findForward("create_supervisor");
			 }
			 if ("Metric".equals(type)) {
				 cwtForm.setCwtMetric(cwtMetricsDao.findById(new Long(id)));
				 this.loadProgramMetrics(cwtForm);
				 return mapping.findForward("create_"+Constants.METRICS);
			 }
			 if ("Module".equals(type)) {
				 cwtForm.setCwtModule(cwtModulesDao.findById(new Long(id)));
				 return mapping.findForward("create_"+Constants.MODULES);
			 }
			 if ("Program".equals(type)) {
				 cwtForm.setCwtProgram(cwtProgramDao.findById(new Long(id)));
				 return mapping.findForward("create_"+Constants.PROGRAMS);
			 }
			 if ("Section".equals(type)) {
				 cwtForm.setCwtModuleSection(cwtModuleSectionDao.findById(new Long(id)));
				 this.getMeetingDays(cwtForm);
				 return mapping.findForward("create_"+Constants.SECTIONS);
			 }
			 
		 }
		 else if ("Delete".equals(action)) {
			 String id=request.getParameter("id");
			 if ("metric".equals(cwtForm.getPageSource()))
				 cwtMetricsDao.deleteCwtMetrics(new Long(id));
			 if ("module".equals(cwtForm.getPageSource()))
				 cwtModulesDao.deleteCwtModules(new Long(id));
			 if ("program".equals(cwtForm.getPageSource()))
				 cwtProgramDao.deleteCwtProgram(new Long(id));
			 if ("section".equals(cwtForm.getPageSource())) {
				 cwtModuleSectionDao.deleteCwtModuleSection(cwtForm.getCwtModuleSection().getModuleOfferingId());
				 this.getSectionDetail(cwtForm);
				 return mapping.findForward(Constants.SECTIONS);
			 }
			 return mapping.findForward("create_"+cwtForm.getPageSource());
		 }
		 else if ("Create".equals(action)) {
			 cwtForm.setCwtDepartment(new CwtDepartment());
			 cwtForm.setCwtJob(new CwtJob());
			 cwtForm.setCwtSupervisor(new CwtSupervisor());
			 cwtForm.setCwtProgram(new CwtProgram());
			 cwtForm.setCwtModule(new CwtModules());
			 cwtForm.setCwtMetric(new CwtMetrics());
			 cwtForm.setCwtModuleSection(new CwtModuleSection());
			 
			 cwtForm.setMetricList(cwtMetricsDao.listCwtMetricss());
			 html.refresh(session);
			 return mapping.findForward("create_"+cwtForm.getPageSource());
		 }
		 else if ("Save".equals(action)) {
			 if ("program".equals(cwtForm.getPageSource())) {
				 if (cwtForm.getCwtMetric().getMetricId()==null) {
					 cwtForm.getCwtProgram().setCreatedBy(user.getUsername());
					 cwtForm.getCwtProgram().setCreationDate(validator.getEpoch()+"");
					 cwtProgramDao.addCwtProgram(cwtForm.getCwtProgram());
				 } else
					 cwtProgramDao.updateCwtProgram(cwtForm.getCwtProgram());
				 
				 cwtForm.setProgramList(cwtProgramDao.listCwtPrograms());
				 return mapping.findForward(Constants.PROGRAMS);
			 }
			 if ("module".equals(cwtForm.getPageSource())) {
				 if (cwtForm.getCwtModule().getModuleId()==null) {
					 cwtForm.getCwtModule().setCreatedBy(user.getUsername());
					 cwtForm.getCwtModule().setCreationDate(validator.getEpoch()+"");
					 cwtModulesDao.addCwtModules(cwtForm.getCwtModule());
				 } else
					 cwtModulesDao.updateCwtModules(cwtForm.getCwtModule());
				 this.saveModuleMetrics(cwtForm, request);
				 cwtForm.setModuleList(cwtModulesDao.listCwtModuless());
				 return mapping.findForward(Constants.MODULES);
			 }
			 if ("metric".equals(cwtForm.getPageSource())) {
				 if (cwtForm.getCwtMetric().getMetricId()==null) {
					 cwtForm.getCwtMetric().setCreatedBy(user.getUsername());
					 cwtForm.getCwtMetric().setCreationDate(validator.getEpoch()+"");
					 long id = cwtMetricsDao.addCwtMetrics(cwtForm.getCwtMetric());
					 cwtForm.getCwtMetric().setMetricId(id);
				 } else 
					 cwtMetricsDao.updateCwtMetrics(cwtForm.getCwtMetric());
				 
				 cwtForm.setMetricList(cwtMetricsDao.listCwtMetricss());
				 this.saveProgramMetrics(cwtForm,request);
				 return mapping.findForward(Constants.METRICS);
			 }
			 if ("department".equals(cwtForm.getPageSource())) {
				 cwtForm.getCwtDepartment().setCreatedBy(user.getUsername());
				 cwtForm.getCwtDepartment().setCreationDate(validator.getEpoch()+"");
				 if (cwtForm.getCwtDepartment().getDepartmentId()==null)
					 cwtDepartmentDao.addCwtDepartment(cwtForm.getCwtDepartment());
				 else
					 cwtDepartmentDao.updateCwtDepartment(cwtForm.getCwtDepartment());
						 
				 cwtForm.setDepartmentList(cwtDepartmentDao.listCwtDepartments());
				 return mapping.findForward(Constants.DEPARTMENTS);
			 }

			 if ("job".equals(cwtForm.getPageSource())) {
				 cwtForm.getCwtJob().setCreatedBy(user.getUsername());
				 cwtForm.getCwtJob().setCreationDate(validator.getEpoch()+"");
				 if (cwtForm.getCwtJob().getJobId()==null) {
					 Long id = cwtJobDao.addCwtJob(cwtForm.getCwtJob());
					 cwtForm.getCwtJob().setJobId(id);
				 } else
					 cwtJobDao.updateCwtJob(cwtForm.getCwtJob());
				 this.saveJobMetrics(cwtForm);
				 cwtForm.setJobList(cwtJobDao.listCwtJobs());
				 return mapping.findForward(Constants.JOBS);
			 }
			 
			 if ("supervisor".equals(cwtForm.getPageSource())) {
				 cwtForm.getCwtSupervisor().setCreatedBy(user.getUsername());
				 cwtForm.getCwtSupervisor().setCreationDate(validator.getEpoch()+"");
				 if (cwtForm.getCwtSupervisor().getSupervisorId()==null)
					 cwtSupervisorDao.addCwtSupervisor(cwtForm.getCwtSupervisor());
				 else
					 cwtSupervisorDao.updateCwtSupervisor(cwtForm.getCwtSupervisor());
				 
				 cwtForm.setSupervisorList(cwtSupervisorDao.listCwtSupervisors());
				 return mapping.findForward(Constants.SUPERVISORS);
			 }
			 if ("section".equals(cwtForm.getPageSource())) {
				 cwtForm.getCwtModuleSection().setMeetingDays(this.convertMeetingDays(cwtForm));
				 if (cwtForm.getCwtModuleSection().getModuleOfferingId()==null) {
					 Long id = cwtModuleSectionDao.addCwtModuleSection(cwtForm.getCwtModuleSection());
					 cwtForm.getCwtModuleSection().setModuleOfferingId(id);
				 }
				 else
					 cwtModuleSectionDao.updateCwtModuleSection(cwtForm.getCwtModuleSection());
				 
				 this.saveModuleMetrics(cwtForm, request);
				 cwtForm.setModuleSectionList(cwtModuleSectionDao.listCwtModuleSections());
				 this.getSectionDetail(cwtForm);
				 return mapping.findForward(Constants.SECTIONS);
			 }
} 
		 
		 cwtForm.reset(mapping, request);
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
	
	
	private void saveJobMetrics(CwtForm cwtForm) {
		
		CwtJobMetricDao dao = new CwtJobMetricDao();
		
		 /*
		  * First delete all medical conditions for given intake
		  */
		 List<CwtJobMetric> jobMetrics = dao.findByJobId(cwtForm.getCwtJob().getJobId());
		 
		 
		 for (Iterator iterator =
				 jobMetrics.iterator(); iterator.hasNext();){
			 CwtJobMetric obj = (CwtJobMetric) iterator.next();
			 dao.deleteCwtJobMetric(obj.getMetricJobId());
		 }
		 
		 String jobMetric[] = cwtForm.getJobMetric();
		 int index=0;
		 for (Iterator iterator =
				 cwtForm.getMetricList().iterator(); iterator.hasNext();){
			 CwtMetrics obj = (CwtMetrics) iterator.next();
			   
	    	   if ("YES".equals(jobMetric[index])) {
	    		   CwtJobMetric cjm = new CwtJobMetric();
	    		   cjm.setJobId(cwtForm.getCwtJob().getJobId());
	    		   cjm.setMetricId(obj.getMetricId());
	    		   dao.addCwtJobMetric(cjm);
	    		 }
	    index++;
		}
	}

	
private void saveProgramMetrics(CwtForm cwtForm, HttpServletRequest request) {
		
		CwtProgramMetricDao dao = new CwtProgramMetricDao();
		String [] metricUbit = cwtForm.getMetricUbit();
		
		
		//delete all programMetrics by metricId before reinserting the updates
		List<CwtProgramMetric> programMetrics = dao.findByMetricId(cwtForm.getCwtMetric().getMetricId());
			 for (Iterator iterator2 =
					 programMetrics.iterator(); iterator2.hasNext();) {
				 CwtProgramMetric obj2 = (CwtProgramMetric) iterator2.next();
			 	 dao.deleteCwtProgramMetric(obj2.getProgramMetricId());
			 }
			 
		 int index=0;		 
		 for (Iterator iterator =
				 cwtForm.getProgramList().iterator(); iterator.hasNext();){
			 CwtProgram obj = (CwtProgram) iterator.next();
			 if ("YES".equals(request.getParameter("metricUbit["+index+"]"))) {
	    		   CwtProgramMetric cpm = new CwtProgramMetric();
	    		   cpm.setMetricId(cwtForm.getCwtMetric().getMetricId());
	    		   cpm.setProgramId(obj.getProgramId());
	    		   dao.addCwtProgramMetric(cpm);
	    		 }
	    index++;
			 
		}		 
		
	}


private void loadProgramMetrics(CwtForm cwtForm) {
	
	CwtProgramMetricDao dao = new CwtProgramMetricDao();
	String [] metricUbit = new String[200];
	
	List<CwtProgramMetric> programMetrics = dao.findByMetricId(cwtForm.getCwtMetric().getMetricId());
	
	 int index=0;		 
	 for (Iterator iterator =
			 cwtForm.getProgramList().iterator(); iterator.hasNext();){
		 CwtProgram obj = (CwtProgram) iterator.next();
		 
		 for (Iterator iterator2 =
				 programMetrics.iterator(); iterator2.hasNext();){
			 CwtProgramMetric obj2 = (CwtProgramMetric) iterator2.next();
			 if (obj2.getProgramId().equals(obj.getProgramId())) 
				 metricUbit[index]="YES";
			 	
		 }	 
	index++;
		 
	}	
	cwtForm.setMetricUbit(metricUbit);
	
}

private List getSectionDetail(CwtForm cwtForm) { 
	
	CwtModuleSectionDao dao1 = new CwtModuleSectionDao();
	CwtSupervisorDao cwtSupervisorDao = new CwtSupervisorDao();
	CwtProgramDao cwtProgramDao = new CwtProgramDao();
	CwtMetricsDao cwtMetricsDao = new CwtMetricsDao();
	CwtModulesDao cwtModulesDao = new CwtModulesDao();

	List<CwtModuleSection> sections = new ArrayList<CwtModuleSection>();
	List<CwtMaster> masters = new ArrayList<CwtMaster>();
	 
	sections = dao1.listCwtModuleSections();
	
	for (Iterator iterator =
			 sections.iterator(); iterator.hasNext();){
		 CwtModuleSection section = (CwtModuleSection)iterator.next();
		 CwtModules module = cwtModulesDao.findById(section.getModuleId());
		 if (module==null) module = new CwtModules();
		 
		 CwtProgram program = cwtProgramDao.findById(module.getProgramId());
		 if (program==null) program= new CwtProgram();
		 
		 CwtSupervisor supervisor = cwtSupervisorDao.findById(section.getInstructorId());
		 if (supervisor==null) supervisor = new CwtSupervisor();
		 
		 CwtMaster master = new CwtMaster();
		 master.setModule(module);
		 master.setProgram(program);
		 master.setSupervisor(supervisor);
		 master.setSection(section);
		 masters.add(master);
	}
	 cwtForm.setMasterList(masters);
	
	return null;
}

private void saveModuleMetrics(CwtForm cwtForm, HttpServletRequest request) {
	
	CwtProgramMetricModulesDao dao = new CwtProgramMetricModulesDao();
	String [] moduleMetric = cwtForm.getModuleMetric();
	
	
	//delete all moduleMetrics by metricId before reinserting the updates
	List<CwtProgramMetricModules> programModuleMetrics = dao.findByModuleId(cwtForm.getCwtModuleSection().getModuleId());
		 for (Iterator iterator2 =
				 programModuleMetrics.iterator(); iterator2.hasNext();) {
			 CwtProgramMetricModules obj2 = (CwtProgramMetricModules) iterator2.next();
		 	 dao.deleteCwtProgramMetricModules(obj2.getProgramMetricModuleId());
		 }
		 
	 int index=0;		 
	 for (Iterator iterator =
			 cwtForm.getMetricList().iterator(); iterator.hasNext();){
		 CwtMetrics obj = (CwtMetrics) iterator.next();
		 System.out.println (index+"="+request.getParameter("moduleMetric["+index+"]"));
		 if ("YES".equals(request.getParameter("moduleMetric["+index+"]"))) {
    		   CwtProgramMetricModules cpm = new CwtProgramMetricModules();
    		   cpm.setMetricId(obj.getMetricId());
    		   cpm.setModuleId(cwtForm.getCwtModuleSection().getModuleId());
    		   dao.addCwtProgramMetricModules(cpm);
    		 }
    index++;
		 
	}		 
	 

	
}

private String convertMeetingDays(CwtForm cwtForm) {
	
	String meetingDay="";
	
	if ("M".equals(cwtForm.getMonday()))
		meetingDay+="M ";
	if ("T".equals(cwtForm.getTuesday()))
		meetingDay+="T ";
	if ("W".equals(cwtForm.getWednesday()))
		meetingDay+="W ";
	if ("TH".equals(cwtForm.getThursday()))
		meetingDay+="TH ";
	if ("F".equals(cwtForm.getFriday()))
		meetingDay+="F ";
	if ("SA".equals(cwtForm.getSaturday()))
		meetingDay+="SA ";
	if ("SU".equals(cwtForm.getSunday()))
		meetingDay+="SU ";
	
	
	return meetingDay;
	
}

private void getMeetingDays(CwtForm cwtForm) {
	
	String meetingDays=cwtForm.getCwtModuleSection().getMeetingDays();
	if (meetingDays!=null) { 
		if (meetingDays.contains("M"))
			cwtForm.setMonday("M");
		if (meetingDays.contains("TU"))
			cwtForm.setTuesday("TU");
		if (meetingDays.contains("W"))
			cwtForm.setWednesday("W");
		if (meetingDays.contains("TH"))
			cwtForm.setThursday("TH");
		if (meetingDays.contains("F"))
			cwtForm.setFriday("F");
		if (meetingDays.contains("SA"))
			cwtForm.setSaturday("SA");
		if (meetingDays.contains("SU"))
			cwtForm.setSunday("SU");
	}
	
}
	
	
}
