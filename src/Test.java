import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Test {

	public static void main(String[] args) {
		
		String dates[] = new String [] { "12/23/2013 12:00","01/03/2014","01/06/2014","12/12/2013","12/16/2013" };
		String sorted[] = new String [70];
		
		try {
		DateFormat dF = new SimpleDateFormat("MM/dd/yy"); 
			for (int i=0;i<dates.length;i++) {
			Date date = dF.parse(dates[i]);
			System.out.println("epoch="+date.getTime());
		}
		} catch (Exception e) {
			System.out.println(">"+e.getMessage());
		}
		
	
	}
	
	

}
