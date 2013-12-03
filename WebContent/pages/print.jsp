<%@ page import="com.yada180.sms.struts.form.OnlineAppForm" %>
<%@ page import="com.yada180.sms.domain.Intake" %>
<%@ page import="java.beans.BeanInfo" %>
<%@ page import="java.beans.Introspector" %>
<%@ page import="java.beans.PropertyDescriptor" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.StringWriter" %>

<%
			Intake intake =  (Intake)session.getAttribute("PRINT_INTAKE");
			String name = (String)session.getAttribute("PRINT_INTAKE_NAME");
			String farm =  (String)session.getAttribute("PRINT_INTAKE_FARM");
			String phone =  (String)session.getAttribute("PRINT_INTAKE_PHONE");
%>
<font size="6"><%=farm %> Online Intake Application</font></br></br>
<font size="5"><b>Name:</b><i><%=name %>&nbsp;&nbsp;(<%=phone %>)</i></font></br>

<font size="2">
<%
			try {
			BeanInfo info = Introspector.getBeanInfo(intake.getClass());
			PropertyDescriptor[] props = info.getPropertyDescriptors();  
			for (int i=0;i<props.length;i++) {  
				PropertyDescriptor descriptor = props [i]; %>
				<% if ((descriptor.getReadMethod().invoke(intake, null)!=null)&&(descriptor.getReadMethod().invoke(intake, null).toString().length()>0)) { %>
				<%=props[i].getDisplayName()%>:&nbsp;&nbsp;<b><%=descriptor.getReadMethod().invoke(intake, null)%></b></br>
				<% } //end null %>
			<%	}			    
			}
			catch (Exception ex) { ex.getStackTrace(); }
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
%>
</font>

   	       