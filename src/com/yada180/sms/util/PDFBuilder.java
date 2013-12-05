package com.yada180.sms.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.yada180.sms.domain.Intake;
import com.yada180.sms.struts.form.IntakeForm;

public class PDFBuilder {

	public void applicationPdf(IntakeForm form, HttpServletResponse response) {
		
		Intake intake = form.getIntake();
		response.setContentType("application/pdf");
		Document document = new Document();
		try{
			PdfWriter writer = PdfWriter.getInstance(document, 
				response.getOutputStream()); // Code 2
			document.open();
			
			PdfReader pdfTemplate = new PdfReader("c:\\pdfFiles\\IntakeApplicationFinal_PDF.pdf");
			PdfStamper stamper = new PdfStamper(pdfTemplate, response.getOutputStream());
			stamper.setFormFlattening(true); 
			stamper.getAcroFields().setField("todaysDate", Validator.convertDate((new java.util.Date()).toString()));
			stamper.getAcroFields().setField("lastname", intake.getLastname());
			stamper.getAcroFields().setField("firstname", intake.getFirstname());
			stamper.getAcroFields().setField("mi", intake.getMi());
			stamper.getAcroFields().setField("ssn", intake.getSsn());
			stamper.getAcroFields().setField("address", intake.getAddress());
			stamper.getAcroFields().setField("city", intake.getCity());
			stamper.getAcroFields().setField("state", intake.getState());
			stamper.getAcroFields().setField("zipcode", intake.getZipcode());
			
			stamper.getAcroFields().setField("referredBy", intake.getReferredBy());
			stamper.getAcroFields().setField("referredPhone", intake.getReferredByPhone());
			stamper.getAcroFields().setField("emergencyContact", intake.getEmergencyContact());
			stamper.getAcroFields().setField("emergencyRelationship", intake.getEmergencryRelationship());
			stamper.getAcroFields().setField("emergencyPhone", intake.getEmergencyPhone());
			stamper.getAcroFields().setField("dob", intake.getDob());
			stamper.getAcroFields().setField("age", intake.getAge());
			stamper.getAcroFields().setField("height", intake.getHeight());
			stamper.getAcroFields().setField("weight", intake.getWeight());
			stamper.getAcroFields().setField("eyes", intake.getEyeColor());
			stamper.getAcroFields().setField("hair", intake.getHairColor());
			stamper.getAcroFields().setField("ethnicity", intake.getEthnicity());
			stamper.getAcroFields().setField("homeLocationOther", intake.getHomeLocation());
			stamper.getAcroFields().setField("incomeSource", intake.getIncomeSource());
			stamper.getAcroFields().setField("otherBenefits", intake.getOtherBenefits());
			stamper.getAcroFields().setField("veteranFlag", intake.getVeteranStatus());
			stamper.getAcroFields().setField("branchOfService", intake.getBranchOfService());
			stamper.getAcroFields().setField("highestRank", intake.getRank());
			stamper.getAcroFields().setField("lengthOfService", intake.getLengthOfService());
			stamper.getAcroFields().setField("dlFlag", intake.getDlFlag());
			stamper.getAcroFields().setField("dlState", intake.getDlState());
			stamper.getAcroFields().setField("dlNumber", intake.getDlNumber());
			stamper.getAcroFields().setField("motherLiving", intake.getMotherLivingFlag());
			stamper.getAcroFields().setField("fatherLiving", intake.getFatherLivingFlag());
			stamper.getAcroFields().setField("relationshipMother", intake.getRelationshipWithMother());
			stamper.getAcroFields().setField("relationshipFather", intake.getRelationshipWithFather());
			stamper.getAcroFields().setField("brothers", intake.getBrothers());
			stamper.getAcroFields().setField("sisters", intake.getSisters());
			stamper.getAcroFields().setField("children", intake.getChildren());
			stamper.getAcroFields().setField("religiousExperience", intake.getReligiousExperience());
			stamper.getAcroFields().setField("religiousBackground", intake.getReligion());
			
			stamper.close();
			document.close(); 
		}catch(DocumentException e){
			e.printStackTrace();
		}
		catch(IOException io){
			io.printStackTrace();
		}
	}
}
