package com.yada180.sms.struts.action;

import java.io.PrintWriter;
import java.io.StringWriter;
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
import com.yada180.sms.domain.Intake;
import com.yada180.sms.domain.SystemUser;
import com.yada180.sms.hibernate.data.IntakeDao;
import com.yada180.sms.struts.form.IntakeForm;
import com.yada180.sms.util.PDFBuilder;
import com.yada180.sms.util.Validator;

public class PdfAppAction extends Action { 

	private final static Logger LOGGER = Logger.getLogger(ImageAction.class.getName());
	private Validator valid8r = new Validator();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		String action=request.getParameter("action");
		String farm=request.getParameter("farm");
		SystemUser user = (SystemUser)session.getAttribute("system_user");
		try {
		LOGGER.setLevel(Level.SEVERE);
		IntakeForm intakeForm = (IntakeForm)form;
		PDFBuilder pdf = new PDFBuilder();
		
		IntakeDao dao = new IntakeDao();
		if ("application".equals(action))
			pdf.applicationPdf(intakeForm, response);
		if ("classlist".equals(action))
			pdf.classListPdf(user, farm, response);
		if ("waitlist".equals(action))
			pdf.waitlistPdf(user, farm, response);
		if ("occupancy".equals(action)) 
			pdf.generateOccupancyReport(user, response);
		if ("completion".equals(action)) 
				pdf.generateCompletionReport(user, response);
		
		} catch(Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			session.setAttribute("SYSTEM_ERROR", sw.toString());
			e.printStackTrace();
			return mapping.findForward(Constants.ERROR);
		}
		return null;
	}

	
}
