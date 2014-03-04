import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.yada180.sms.domain.CwtMaster;
import com.yada180.sms.domain.CwtModules;
import com.yada180.sms.domain.CwtProgram;
import com.yada180.sms.domain.CwtRoster;
import com.yada180.sms.domain.Intake;
import com.yada180.sms.domain.IpPermission;
import com.yada180.sms.hibernate.data.CwtModulesDao;
import com.yada180.sms.hibernate.data.CwtProgramDao;
import com.yada180.sms.hibernate.data.GenericDao;
import com.yada180.sms.hibernate.data.IntakeDao;

public class Test {

	public static void main(String[] args) {
		
		GenericDao dao = new GenericDao();
		List<IpPermission> list = dao.listAll(new IpPermission().getClass());
		for (int i=0;i<list.size();i++) {
			IpPermission obj = (IpPermission)list.get(i);
			System.out.println(">"+obj.getIpAddress());
		}
		
	}
}
