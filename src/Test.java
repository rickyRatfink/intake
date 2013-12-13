import java.net.InetAddress;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		 * String dateOfBirth="06/12/1970";
		 	
		Calendar dob = Calendar.getInstance();  
		dob.setTime(new Date(dateOfBirth));
		Calendar today = Calendar.getInstance();  
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
		if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
		  age--;  
		} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
		    && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
		  age--;  
		}
		System.out.println("age="+age);
		
		Intake intake = new Intake();
		try {
		BeanInfo info = Introspector.getBeanInfo(intake.getClass());
		PropertyDescriptor[] props = info.getPropertyDescriptors();  
	    for (int i=0;i<props.length;i++) {  
	        /*String name = pd.getName();  
	        Method getter = pd.getReadMethod();  
	        Class<?> type = pd.getPropertyType();  
	        Object value = getter.invoke(info);  
	        System.out.println(name + " = " + value + "; type = " + type);  
	        
	        System.out.println (">"+props[i].getDisplayName()+"="+props[i].getValue(props[i].getDisplayName()));
			}
	    
		}
		catch (Exception e) {
			
		}*/
		//Pattern p = Pattern.compile("[A-Z]",Pattern.UNICODE_CHARACTER_CLASS);
	    //String msg="BuLL";	    
	    //System.out.println(msg.matches("[A-Z]+$"));
		/*
		IntakeMedicalConditionDao dao = new IntakeMedicalConditionDao();
		IntakeMedicalCondition obj = new IntakeMedicalCondition();
		List<IntakeMedicalCondition> list = dao.findByIntakeId(obj, new Long(4116097));
		System.out.println(list.size());
		*/
		try {
		System.out.println(InetAddress.getLocalHost().getHostAddress());
		} catch (Exception e) {}
	}
	
	

}
