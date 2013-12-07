package com.yada180.sms.struts.action;

import java.io.IOException;
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

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.yada180.sms.application.Constants;
import com.yada180.sms.domain.Intake;
import com.yada180.sms.domain.JobSkill;
import com.yada180.sms.domain.MedicalCondition;
import com.yada180.sms.struts.form.IntakeForm;
import com.yada180.sms.util.PDFBuilder;
import com.yada180.sms.util.Validator;

public class PdfAppAction extends Action { 

	private final static Logger LOGGER = Logger.getLogger(ImageAction.class.getName());
	private Validator valid8r = new Validator();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		
		try {
		LOGGER.setLevel(Level.SEVERE);
		IntakeForm intakeForm = (IntakeForm)form;
		PDFBuilder pdf = new PDFBuilder();
		pdf.applicationPdf(intakeForm, response);
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
