import java.util.Calendar;
import java.util.Date;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dateOfBirth="06/12/1970";
			
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
	}

}
