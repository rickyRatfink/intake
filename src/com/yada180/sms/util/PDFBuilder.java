package com.yada180.sms.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.yada180.sms.domain.Intake;
import com.yada180.sms.domain.JobSkill;
import com.yada180.sms.domain.MedicalCondition;
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
			
			String applicantName=intake.getFirstname()+" "+intake.getMi()+" "+intake.getLastname();
			String currentDate=Validator.convertDate(new java.util.Date()+"");
			
			//Application Page 1
			PdfReader pdfTemplate = new PdfReader("c:\\pdfFiles\\IntakeApplicationFinal_PDF.pdf");
			PdfStamper stamper = new PdfStamper(pdfTemplate, response.getOutputStream());
			stamper.setFormFlattening(true); 
			stamper.getAcroFields().setField("todaysDate", currentDate);
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
			stamper.getAcroFields().setField("incomeSource1", intake.getIncomeSource());
			stamper.getAcroFields().setField("income", intake.getIncomeAmount());
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
			if ("Never Married".equals(intake.getMaritalStatus()))
				stamper.getAcroFields().setField("marriage1", "Yes");
			if ("Married".equals(intake.getMaritalStatus()))
				stamper.getAcroFields().setField("marriage2", "Yes");
			if ("Divorced".equals(intake.getMaritalStatus()))
				stamper.getAcroFields().setField("marriage3", "Yes");
			if ("Separated".equals(intake.getMaritalStatus()))
				stamper.getAcroFields().setField("marriage4", "Yes");
			if ("Spouse Deceased".equals(intake.getMaritalStatus()))
				stamper.getAcroFields().setField("marriage5", "Yes");
			if ("Lives with Girlfriend".equals(intake.getMaritalStatus()))
				stamper.getAcroFields().setField("marriage6", "Yes");
			if ("Remarried".equals(intake.getMaritalStatus()))
				stamper.getAcroFields().setField("marriage7", "Yes");
			if ("Re-divorced".equals(intake.getMaritalStatus()))
				stamper.getAcroFields().setField("marriage8", "Yes");
			if (intake.getEducationLevel()!=null) {
				if (intake.getEducationLevel().equals("9th Grade") || intake.getEducationLevel().equals("10th Grade") || 
						intake.getEducationLevel().equals("11th Grade") ||intake.getEducationLevel().equals("12th Grade") )
					stamper.getAcroFields().setField("highSchoolFlag", "Yes");
				if (intake.getEducationLevel().equals("3rd Grade") || intake.getEducationLevel().equals("4th Grade") || 
						intake.getEducationLevel().equals("1st Grade") ||intake.getEducationLevel().equals("2nd Grade") ||
						intake.getEducationLevel().equals("5th Grade") ||intake.getEducationLevel().equals("6th Grade") ||
						intake.getEducationLevel().equals("7th Grade") ||intake.getEducationLevel().equals("8th Grade"))
					stamper.getAcroFields().setField("elementaryFlag", "Yes");
				if (intake.getEducationLevel().equals("GED"))
					stamper.getAcroFields().setField("ged", "Yes");
				if (intake.getEducationLevel().contains("Degree"))
					stamper.getAcroFields().setField("college", "Yes");
			}
			
			if ("Yes".equals(intake.getGraduateFlag()))
				stamper.getAcroFields().setField("graduateYes", "Yes");
			else
				stamper.getAcroFields().setField("graduateNo", "Yes");
			if ("No".equals(intake.getEnglishSpeakingFlag()))
				stamper.getAcroFields().setField("englishSpeakNo", "Yes");
			else
				stamper.getAcroFields().setField("englishSpeakYes", "Yes");
			if ("No".equals(intake.getEnglishReadingFlag()))
				stamper.getAcroFields().setField("englishReadNo", "Yes");
			else
				stamper.getAcroFields().setField("englishReadYes", "Yes");
			
			if ("Yes".equals(intake.getIncomeWeeklyFlag()))
				stamper.getAcroFields().setField("weekly", "Yes");
			if ("Yes".equals(intake.getIncomeMonthlyFlag()))
				stamper.getAcroFields().setField("monthly", "Yes");
			
			if (intake.getSsFlag()!=null&&"yes".equals(intake.getSsFlag().toLowerCase()))
				stamper.getAcroFields().setField("ss", "Yes");
			if (intake.getVaFlag()!=null&&"yes".equals(intake.getVaFlag().toLowerCase()))
					stamper.getAcroFields().setField("va", "Yes");
			if (intake.getWcFlag()!=null&&"yes".equals(intake.getWcFlag().toLowerCase()))
					stamper.getAcroFields().setField("wc", "Yes");
			
			if ("Own Home".equals(intake.getHomeLocation()))
				stamper.getAcroFields().setField("homeLocation1", "Yes");
			if ("Parent's Home".equals(intake.getHomeLocation()))
				stamper.getAcroFields().setField("homeLocation2", "Yes");
			if ("Friend's Home".equals(intake.getHomeLocation()))
				stamper.getAcroFields().setField("homeLocation3", "Yes");
			if ("Halfway Home".equals(intake.getHomeLocation()))
				stamper.getAcroFields().setField("homeLocation4", "Yes");
			if ("Hotel".equals(intake.getHomeLocation()))
				stamper.getAcroFields().setField("homeLocation5", "Yes");
			if ("Rehab Program".equals(intake.getHomeLocation()))
				stamper.getAcroFields().setField("homeLocation6", "Yes");
			if ("Vehicle".equals(intake.getHomeLocation()))
				stamper.getAcroFields().setField("homeLocation7", "Yes");			
			if ("One Street".equals(intake.getHomeLocation()))
				stamper.getAcroFields().setField("homeLocation8", "Yes");
			
			if ("Protestant".equals(intake.getReligion()))
				stamper.getAcroFields().setField("protestant", "Yes");
			if ("Jewish".equals(intake.getReligion()))
				stamper.getAcroFields().setField("jewish", "Yes");
			if ("Buddhist".equals(intake.getReligion()))
				stamper.getAcroFields().setField("buddhist", "Yes");
			if ("Santaria".equals(intake.getReligion()))
				stamper.getAcroFields().setField("santaria", "Yes");
			if ("Rastafarian".equals(intake.getReligion()))
				stamper.getAcroFields().setField("rastafarian", "Yes");
			if ("Catholic".equals(intake.getReligion()))
				stamper.getAcroFields().setField("catholic", "Yes");
			if ("Muslim".equals(intake.getReligion()))
				stamper.getAcroFields().setField("muslim", "Yes");
			if ("Hindu".equals(intake.getReligion()))
				stamper.getAcroFields().setField("hindu", "Yes");
			if ("New Age".equals(intake.getReligion()))
				stamper.getAcroFields().setField("newage", "Yes");
			if ("Other".equals(intake.getReligion()))
				stamper.getAcroFields().setField("other", "Yes");
			
			
			//Application Page 2
			stamper.getAcroFields().setField("alcoholYears", intake.getAlcoholYearsUsed());
			stamper.getAcroFields().setField("alcoholDate", intake.getAlcoholLastUsed());
			stamper.getAcroFields().setField("speedYears", intake.getSpeedYearsUsed());
			stamper.getAcroFields().setField("speedDate", intake.getSpeedLastUsed());
			stamper.getAcroFields().setField("cocaineYears", intake.getCocaineYearsUsed());
			stamper.getAcroFields().setField("cocaineDate", intake.getCocaineLastUsed());
			stamper.getAcroFields().setField("heroinYears", intake.getHeroinYearsUsed());
			stamper.getAcroFields().setField("heroinDate", intake.getHeroinLastUsed());
			stamper.getAcroFields().setField("marijuanaYears", intake.getMarijuanaYearsUsed());
			stamper.getAcroFields().setField("marijuanaDate", intake.getMarijuanaLastUsed());
			stamper.getAcroFields().setField("xanaxYears", intake.getXanaxYearsUsed());
			stamper.getAcroFields().setField("xanaxDate", intake.getXanaxLastUsed());
			stamper.getAcroFields().setField("oxycodoneYears", intake.getOxycodoneYearsUsed());
			stamper.getAcroFields().setField("oxycodoneDate", intake.getOxycodoneLastUsed());
			stamper.getAcroFields().setField("otherYears", intake.getOtherYearsUsed());
			stamper.getAcroFields().setField("otherDate", intake.getOtherLastUsed());
			
			stamper.getAcroFields().setField("sober3Years", intake.getSober3Years());
			stamper.getAcroFields().setField("longestSober", intake.getSober1Year());
			
			if (intake.getUsagePattern()!=null) {
				if (intake.getUsagePattern().contains("Constantly"))
					stamper.getAcroFields().setField("constantly", "Yes");
				if (intake.getUsagePattern().contains("Weekends"))
					stamper.getAcroFields().setField("weekends", "Yes");
				if (intake.getUsagePattern().contains("Special Occasions"))
					stamper.getAcroFields().setField("specialOccasions", "Yes");
				if (intake.getUsagePattern().contains("Whenever available"))
					stamper.getAcroFields().setField("whenever", "Yes");
				if (intake.getUsagePattern().contains("When Things Get Tough"))
					stamper.getAcroFields().setField("tough", "Yes");
				if (intake.getUsagePattern().contains("A Week/Off A Week"))
					stamper.getAcroFields().setField("onaweek", "Yes");
			}
			
			stamper.getAcroFields().setField("perweek", intake.getQuantityPerWeek());
			stamper.getAcroFields().setField("perweek2years", intake.getQuantity2Years());
			
			if (intake.getUsageLosses()!=null) {
				if (intake.getUsageLosses().contains("Job"))			
					stamper.getAcroFields().setField("job", "Yes");
				if (intake.getUsageLosses().contains("Marriage"))			
					stamper.getAcroFields().setField("marriage", "Yes");
				if (intake.getUsageLosses().contains("Friends"))			
					stamper.getAcroFields().setField("friends", "Yes");
				if (intake.getUsageLosses().contains("Possessions"))			
					stamper.getAcroFields().setField("possessions", "Yes");
				if (intake.getUsageLosses().contains("Arrests"))			
					stamper.getAcroFields().setField("arrests", "Yes");
				if (intake.getUsageLosses().contains("DUI"))			
					stamper.getAcroFields().setField("dui", "Yes");
				if (intake.getUsageLosses().contains("Debt"))			
					stamper.getAcroFields().setField("debt", "Yes");
				if (intake.getUsageLosses().contains("Health"))			
					stamper.getAcroFields().setField("health", "Yes");
				if (intake.getUsageLosses().contains("Incarceration"))			
					stamper.getAcroFields().setField("incarceration", "Yes");
			}
			
			if (intake.getPhysicalEffects()!=null) {
				if (intake.getPhysicalEffects().contains("motivational loss"))
					stamper.getAcroFields().setField("motivationalLoss", "Yes");
				if (intake.getPhysicalEffects().contains("shakes-convulsions"))
					stamper.getAcroFields().setField("shakes",  "Yes");
				if (intake.getPhysicalEffects().contains("memory loss"))
					stamper.getAcroFields().setField("memoryLoss",  "Yes");
				if (intake.getPhysicalEffects().contains("incoherent thinking"))
					stamper.getAcroFields().setField("incoherent",  "Yes");
				if (intake.getPhysicalEffects().contains("organ problems"))
					stamper.getAcroFields().setField("organ",  "Yes");
			}
			if ("Yes".equals(intake.getAaFlag()))
				stamper.getAcroFields().setField("aa", "Yes");
				stamper.getAcroFields().setField("aaYears", intake.getAaYears());
				if ("Yes".equals(intake.getPrevFfFlag()))
					stamper.getAcroFields().setField("farmYes", "Yes");
				else
					stamper.getAcroFields().setField("farmNo", "Yes");
					
				stamper.getAcroFields().setField("farmYears", intake.getPrevFfYear());
				stamper.getAcroFields().setField("rehabOther", intake.getPrevFfOther());
			
				String healthAnswer[] = form.getHealthAnswer();
				for (int index=0;index<16;index++) {	
					   if ("Yes".equals(healthAnswer[index])) 
			    		   stamper.getAcroFields().setField("sq"+(index+1)+"Yes", "Yes");
			    	   else
			    		   stamper.getAcroFields().setField("sq"+(index+1)+"No", "Yes");
				}
				
				
				
				//Application Page 3
				String emotionalAnswer[] = form.getEmotionalAnswer();
				String emotionalAnswerDate[] = form.getEmotionalAnswerDate();
				String emotionalAnswerDetail[] = form.getEmotionalAnswerDetails();
				int count=1;
				for (int index=0;index<6;index++) {
					   if ("Yes".equals(emotionalAnswer[index])) 
			    		   stamper.getAcroFields().setField("eq"+count+"Yes", "Yes");
			    	   else
			    		   stamper.getAcroFields().setField("eq"+count+"No", "Yes");					   
					   stamper.getAcroFields().setField("eq"+count+"Dates",emotionalAnswerDate[index] );
					   stamper.getAcroFields().setField("eq"+count+"Desc",emotionalAnswerDetail[index] );
					 count++;
				}
				
				if ("excellent".equals(intake.getCurrentHealth()))
					stamper.getAcroFields().setField("healthExcellent", "Yes");
				if ("good".equals(intake.getCurrentHealth()))
					stamper.getAcroFields().setField("healthGood", "Yes");
				if ("fair".equals(intake.getCurrentHealth()))
					stamper.getAcroFields().setField("healthFair", "Yes");
				if ("failing".equals(intake.getCurrentHealth()))
					stamper.getAcroFields().setField("healthFailing", "Yes");
				if ("poor".equals(intake.getCurrentHealth()))
					stamper.getAcroFields().setField("healthPoor", "Yes");
				
				if ("Yes".equals(intake.getMedicationFlag()))
					stamper.getAcroFields().setField("medYes", "Yes");
				else
					stamper.getAcroFields().setField("medNo", "Yes");
				stamper.getAcroFields().setField("meds", intake.getMedicationDetails());
				
				if ("Yes".equals(intake.getNeedMedicationFlag()))
					stamper.getAcroFields().setField("needMedYes", "Yes");
				else
					stamper.getAcroFields().setField("needMedNo", "Yes");
				
				stamper.getAcroFields().setField("refills", intake.getMedicationRefillDetails());
				
				if ("Yes".equals(intake.getDoctorApptFlag()))
					stamper.getAcroFields().setField("drApptYes", "Yes");
				else
					stamper.getAcroFields().setField("drApptNo", "Yes");
				stamper.getAcroFields().setField("drappt", intake.getDoctorApptDetails());
				
				stamper.getAcroFields().setField("sickness", intake.getDoctorApptDetails());
				stamper.getAcroFields().setField("consulted", intake.getDoctorApptDetails());
				stamper.getAcroFields().setField("diagnosis", intake.getDoctorApptDetails());
				stamper.getAcroFields().setField("advised", intake.getDoctorApptDetails());
				stamper.getAcroFields().setField("backInjuries", intake.getDoctorApptDetails());
						
				if ("Yes".equals(intake.getEyewearFlag()))
					stamper.getAcroFields().setField("eyewearYes", "Yes");
				else
					stamper.getAcroFields().setField("eyewearNo", "Yes");
				
				if ("All the time".equals(intake.getEyewearUsage()))
					stamper.getAcroFields().setField("eyewearAllTime", "Yes");
				if ("Occasionally".equals(intake.getEyewearUsage()))
					stamper.getAcroFields().setField("eyewearOccasionally", "Yes");
				if ("Reading".equals(intake.getEyewearUsage()))
					stamper.getAcroFields().setField("eyewearReading", "Yes");
				String physicalAnswer[] = form.getPhysicalAnswer();
				String physicalAnswerDetail[] = form.getPhysicalAnswerDetails();
				for (int index=0;index<5;index++)
					   stamper.getAcroFields().setField("pq"+(index+1)+"Desc",physicalAnswerDetail[index] );
								
				
				//Application Page 4
				String mentalAnswer[] = form.getMentalAnswer();
				String mentalAnswerDate[] = form.getMentalAnswerDate();
				String mentalAnswerDetail[] = form.getMentalAnswerDetails();
				count=1;
				for (int index=0;index<6;index++) {	
					   if ("Yes".equals(mentalAnswer[index])) 
			    		   stamper.getAcroFields().setField("mq"+count+"Yes", "Yes");
			    	   else
			    		   stamper.getAcroFields().setField("mq"+count+"No", "Yes");					   
					   stamper.getAcroFields().setField("mq"+count+"Dates",mentalAnswerDate[index] );
					   stamper.getAcroFields().setField("mq"+count+"Desc",mentalAnswerDetail[index] );
					 count++;
				}
				
				if ("Less than 2 weeks".equals(intake.getHomelessTime()))
					stamper.getAcroFields().setField("homeless1", "Yes");
				if ("2 weeks to 1 month".equals(intake.getHomelessTime()))
					stamper.getAcroFields().setField("homeless2", "Yes");
				if ("1 to 3 months".equals(intake.getHomelessTime()))
					stamper.getAcroFields().setField("homeless3", "Yes");
				if ("3 months to 1 year".equals(intake.getHomelessTime()))
					stamper.getAcroFields().setField("homeless4", "Yes");
				if ("more than 1 year".equals(intake.getHomelessTime()))
					stamper.getAcroFields().setField("homeless5", "Yes");				
				if ("Never".equals(intake.getHomelessHowOften()))
					stamper.getAcroFields().setField("homeless6", "Yes");
				if ("1 to 2 times".equals(intake.getHomelessHowOften()))
					stamper.getAcroFields().setField("homeless7", "Yes");
				if ("more than 2 times in 2 years".equals(intake.getHomelessHowOften()))
					stamper.getAcroFields().setField("homeless8", "Yes");
				if ("Long Term".equals(intake.getHomelessHowOften()))
					stamper.getAcroFields().setField("homeless9", "Yes");
				
				if ("Lack of a fixed, regular and adequate night time residence.".equals(intake.getHomelessReason()))
					stamper.getAcroFields().setField("reason1", "Yes");
				if ("Primary night time residence is a shelter designed to provide temporary living accomodations (including welfar hotels, congregate shelters, and transitional housing for the mentally ill).".equals(intake.getHomelessReason()))
					stamper.getAcroFields().setField("reason2", "Yes");
				if ("Primary night time residence is an institution that provides a temporary residence for individuals intended to be institutionalized.".equals(intake.getHomelessReason()))
					stamper.getAcroFields().setField("reason3", "Yes");
				if ("Primary night time residence is a public or private place not designated for, or ordinarily used as a regular sleeping accomodation for human beings.".equals(intake.getHomelessReason()))
					stamper.getAcroFields().setField("reason4", "Yes");
				
				
				//Application 5
				if ("Yes".equals(intake.getIndustrialInjuryFlag()))
					stamper.getAcroFields().setField("injuryYes", "Yes");
				else
					stamper.getAcroFields().setField("injuryNo", "Yes");
				stamper.getAcroFields().setField("injuryDate", intake.getIndustrialInjuryDate());
				stamper.getAcroFields().setField("injuryReason", intake.getIndustrialInjuryReason());
				stamper.getAcroFields().setField("injuryWhere", intake.getIndustrialInjuryLocation());
				stamper.getAcroFields().setField("injuryEmployer", intake.getIndustrialInjuryEmployer());
				
				if ("Yes".equals(intake.getDisabilityFlag()))
					stamper.getAcroFields().setField("disabilityYes", "Yes");
				else
					stamper.getAcroFields().setField("disabilityNo", "Yes");				
				stamper.getAcroFields().setField("disability", intake.getDisabilityDetails());
				stamper.getAcroFields().setField("lastExam", intake.getDisabilityExaminationDate());
				stamper.getAcroFields().setField("physician", intake.getDisabilityPhysician());
				stamper.getAcroFields().setField("physicianAddress", intake.getDisabilityPhysicianAddress());
				
				List<MedicalCondition> medicalConditions = form.getMedicalConditions();
				String medicalCondition[] = form.getMedicalCondition();
				count=0;  
				for (int i=0;i<34;i++) {
			    	   if ("Yes".equals(medicalCondition[count]))
			    		   stamper.getAcroFields().setField("mc"+(count+1)+"Y", "Yes");
			    	   else
			    		   stamper.getAcroFields().setField("mc"+(count+1)+"N", "Yes");
			    	count++;
			    }
				
				if ("Yes".equals(intake.getHerniaOperationFlag()))
					stamper.getAcroFields().setField("herniaYes", "Yes");
				else
					stamper.getAcroFields().setField("herniaNo", "Yes");
				stamper.getAcroFields().setField("herniaSide", intake.getHerniaSide());
				stamper.getAcroFields().setField("mcdetails", intake.getMedicalConditionDetails());
				
				if ("Yes".equals(intake.getLawsuitFlag()))
					stamper.getAcroFields().setField("sueY", "Yes");
				else
					stamper.getAcroFields().setField("sueN", "Yes");
				stamper.getAcroFields().setField("sueDetails", intake.getLawsuitDetails());

				if ("Yes".equals(intake.getCurrentLawsuitFlag()))
					stamper.getAcroFields().setField("lawsuitY", "Yes");
				else
					stamper.getAcroFields().setField("lawsuitN", "Yes");
				stamper.getAcroFields().setField("lawsuitDetails", intake.getCurrentLawsuitDetails());

				if ("Yes".equals(intake.getFelonyFlag()))
					stamper.getAcroFields().setField("felonyY", "Yes");
				else
					stamper.getAcroFields().setField("felonyN", "Yes");
				stamper.getAcroFields().setField("felonyDetails", intake.getFelonyDetails());
				stamper.getAcroFields().setField("felonyqty", intake.getFelonyQty()+"");

				if ("Yes".equals(intake.getSexualOffenseFlag()))
					stamper.getAcroFields().setField("sexYes", "Yes");
				else
					stamper.getAcroFields().setField("sexNo", "Yes");
				stamper.getAcroFields().setField("sexDetails", intake.getSexualOffenseDetails());
				stamper.getAcroFields().setField("sexqty", intake.getSexualOffenseQty());

				if ("Yes".equals(intake.getProbationFlag()))
					stamper.getAcroFields().setField("probationYes", "Yes");
				else
					stamper.getAcroFields().setField("probationNo", "Yes");
				stamper.getAcroFields().setField("probationCounty", intake.getProbationCounty());
				stamper.getAcroFields().setField("probationOfficer", intake.getProbationOfficer());
				stamper.getAcroFields().setField("probationPhone", intake.getProbationOfficerPhone());
				
				if ("Yes".equals(intake.getProbationApptFlag()))
					stamper.getAcroFields().setField("probationApptYes", "Yes");
				else
					stamper.getAcroFields().setField("probationApptNo", "Yes");
				stamper.getAcroFields().setField("probationDate", intake.getProbationApptDetails());

				//Application Page7
				List<JobSkill> works = form.getJobSkills();
				String work[] = form.getWorkExperience(); 
				count=1;  
				for (int i=0;i<25;i++) {
					   if ("Yes".equals(work[i]))
			    		   stamper.getAcroFields().setField("job"+count, "Yes");
			    	   count++;
			    }
				stamper.getAcroFields().setField("jobOther", intake.getOtherJobSkill());
				
				stamper.getAcroFields().setField("employer1", intake.getEmployer1Name());
				stamper.getAcroFields().setField("employerContact1", intake.getEmployer1Contact());
				stamper.getAcroFields().setField("employerDate1", intake.getEmployer1Dates());
				stamper.getAcroFields().setField("employerPhone1", intake.getEmployer1Phone());
				stamper.getAcroFields().setField("employer2", intake.getEmployer2Name());
				stamper.getAcroFields().setField("employerContact2", intake.getEmployer2Contact());
				stamper.getAcroFields().setField("employerDate2", intake.getEmployer2Dates());
				stamper.getAcroFields().setField("employerPhone2", intake.getEmployer2Phone());
				stamper.getAcroFields().setField("employer3", intake.getEmployer3Name());
				stamper.getAcroFields().setField("employerContact3", intake.getEmployer3Contact());
				stamper.getAcroFields().setField("employerDate3", intake.getEmployer3Dates());
				stamper.getAcroFields().setField("employerPhone3", intake.getEmployer3Phone());
				stamper.getAcroFields().setField("employer4", intake.getEmployer4Name());
				stamper.getAcroFields().setField("employerContact4", intake.getEmployer4Contact());
				stamper.getAcroFields().setField("employerDate4", intake.getEmployer4Dates());
				stamper.getAcroFields().setField("employerPhone41", intake.getEmployer4Phone());
				
				//All Applicant Print Name and Date fields
				stamper.getAcroFields().setField("txtApplicantName", applicantName);
				stamper.getAcroFields().setField("txtApplicantName1", applicantName);
				stamper.getAcroFields().setField("txtApplicantName2", applicantName);
				stamper.getAcroFields().setField("txtApplicantName3", applicantName);
				stamper.getAcroFields().setField("txtApplicantName4", applicantName);
				stamper.getAcroFields().setField("txtApplicantName5", applicantName);
				stamper.getAcroFields().setField("txtApplicantName6", applicantName);
				stamper.getAcroFields().setField("txtApplicantName7", applicantName);
				stamper.getAcroFields().setField("txtApplicantName8", applicantName);
				stamper.getAcroFields().setField("txtApplicantName9", applicantName);
				stamper.getAcroFields().setField("txtApplicantName10", applicantName);
				stamper.getAcroFields().setField("txtApplicantName11", applicantName);
				stamper.getAcroFields().setField("txtApplicantName12", applicantName);
				stamper.getAcroFields().setField("txtApplicantName13", applicantName);
				stamper.getAcroFields().setField("txtApplicantName14", applicantName);
				stamper.getAcroFields().setField("txtApplicantName15", applicantName);
				
				stamper.getAcroFields().setField("txtDate1", currentDate);
				stamper.getAcroFields().setField("txtDate2", currentDate);
				stamper.getAcroFields().setField("txtDate3", currentDate);
				stamper.getAcroFields().setField("txtDate4", currentDate);
				stamper.getAcroFields().setField("txtDate5", currentDate);
				stamper.getAcroFields().setField("txtDate6", currentDate);
				stamper.getAcroFields().setField("txtDate7", currentDate);
				stamper.getAcroFields().setField("txtDate8", currentDate);
				stamper.getAcroFields().setField("txtDate9", currentDate);
				stamper.getAcroFields().setField("txtDate10", currentDate);
				stamper.getAcroFields().setField("txtDate11", currentDate);
				stamper.getAcroFields().setField("txtDate12", currentDate);
				stamper.getAcroFields().setField("txtDate13", currentDate);
				stamper.getAcroFields().setField("txtDate14", currentDate);
				stamper.getAcroFields().setField("txtDate15", currentDate);
				stamper.getAcroFields().setField("txtDate16", currentDate);
				stamper.getAcroFields().setField("txtDate17", currentDate);
				stamper.getAcroFields().setField("txtDate18", currentDate);
				stamper.getAcroFields().setField("txtDate19", currentDate);
				stamper.getAcroFields().setField("txtDate20", currentDate);
				stamper.getAcroFields().setField("txtDate21", currentDate);
				
				
				
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
