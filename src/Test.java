import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.yada180.sms.domain.CwtMaster;
import com.yada180.sms.domain.CwtModules;
import com.yada180.sms.domain.CwtProgram;
import com.yada180.sms.domain.CwtRoster;
import com.yada180.sms.domain.Intake;
import com.yada180.sms.hibernate.data.CwtModulesDao;
import com.yada180.sms.hibernate.data.CwtProgramDao;
import com.yada180.sms.hibernate.data.GenericDao;
import com.yada180.sms.hibernate.data.IntakeDao;

public class Test {

	public static void main(String[] args) {
		
		Long[] moduleId = new Long[] { 
		new Long(799),
		new Long(720),
				new Long(788),
						new Long(762),
								new Long(844),
										new Long(871),
												new Long(783),
														new Long(707),
																new Long(708),
																new Long(730),
																new Long(878),
																new Long(872),
																new Long(795),
																new Long(743),
																new Long(873),
																new Long(763),
																new Long(721),
																new Long(784),
																new Long(731),
																new Long(845),
																new Long(858),
																new Long(789),
																new Long(757),
																new Long(800),
																new Long(859)
		};
		
		GenericDao  dao = new GenericDao();
		CwtProgramDao cwtProgramDao = new CwtProgramDao();
		CwtModulesDao cwtModulesDao = new CwtModulesDao();
		IntakeDao intakeDao = new IntakeDao();
		
		List programs = dao.findIntakePrograms(new Long(7121));
		List<CwtModules> modules = new ArrayList<CwtModules>();
		List<CwtMaster> masters = new ArrayList<CwtMaster>();
		
		for (int i=0;i<moduleId.length;i++) {
			System.out.println ("running..."+moduleId[i]);
			List<CwtRoster> rosters = dao.findByObjectId(CwtRoster.class, "moduleId", moduleId[i]);
		 	
		 	for (Iterator iterator =
					 rosters.iterator(); iterator.hasNext();){
		 		
		 		CwtRoster roster = (CwtRoster) iterator.next();
		 		
		 		
				 	Intake intake = intakeDao.find(roster.getIntakeId());
			 		CwtModules module = cwtModulesDao.find(roster.getModuleId());
			 		 
			 		 CwtProgram program = new CwtProgram();
			 		 if (module!=null) 
			 			program = cwtProgramDao.find(module.getProgramId());
			 		 
			 		 if (program!=null) {
			 			 intake.setCwtProgramId(program.getProgramId());
			 			 //System.out.println(intake.getFirstname()+" "+intake.getLastname()+"="+program.getProgramName()+","+intake.getCwtProgramId());
			 			 intakeDao.update(intake);
			 		 }		 		
	 	}
		
		}//end moduleId
		System.out.println ("complete!");
	 	/*
		 for (Iterator iterator =
				 programs.iterator(); iterator.hasNext();){
			 	BigInteger id = (BigInteger) iterator.next();
			 	CwtProgram program = cwtProgramDao.find(id.longValue());
			 	System.out.println("program: "+id);
			 	modules = dao.findByObjectId(CwtModules.class, "programId", id.longValue());
			 	
				for (Iterator iterator1 =
						 modules.iterator(); iterator1.hasNext();){
					 	CwtModules module = (CwtModules) iterator1.next();
					 	System.out.println("    module: "+module.getModuleId());
					 	
					 	for (int i=0;i<rosters.size();i++) {
					 		CwtRoster roster = (CwtRoster)rosters.get(i);
					 			if (roster.getModuleId().equals(module.getModuleId())) {
						 			System.out.println (module.getModuleId()+" is completed");
						 		}
						 }
					 	
					 
				 }
		 }*/
	}
}
