<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.yada180.sms.domain.SystemUser" %>
<jsp:useBean id="loginForm" class="com.yada180.sms.struts.form.LoginForm" scope="session" /> 
<%  
    SystemUser user = null;
	
	try {
	  user = (SystemUser)session.getAttribute("system_user"); 
	  if (user==null) user = new SystemUser();
	} catch (Exception e) {
		user=new SystemUser();
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head><title>
	Faith Farm Student Information System
</title>
<meta http-equiv="refresh" content="14400;url=/intake/pages/security/index.jsp" />

	<link href="<%=request.getContextPath()%>/styles/site.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/styles/tcal.css" rel="stylesheet" type="text/css" />
   
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/tcal.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/chart.js"></script>

    <script  type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.1.min.js">
    </script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.format.1.02.js">
    </script>    
    <script  type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.dateentry.min.js">
    </script>    
    <style type="text/css">
        @import "/Styles/jquery.datepick.css";
    </style>
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.datepick.min.js"></script>    
  
  
  <logic:notEmpty name="intakeForm">
  		<logic:equal name="loginForm" property="systemUser.farmBase" value="Boynton Beach" >
			<logic:equal name="intakeForm" property="intake.farmBase" value="Boynton Beach">
				 <script language="javascript" type="text/javascript"> 
					function checkSubmit(e)
						{
						   if(e && e.keyCode == 13)
						   {
						      document.forms[0].submit();
						   }
						}
				   </script>
			</logic:equal>
			<logic:notEqual name="intakeForm" property="intake.farmBase" value="Boynton Beach">
				 <script language="javascript" type="text/javascript"> 
					function checkSubmit(e)
						{
						   if(e && e.keyCode == 13)
						   {
						      //document.forms[0].submit();
						   }
						}
				   </script>
			</logic:notEqual>
		</logic:equal>
		
		<logic:equal name="loginForm" property="systemUser.farmBase" value="Okeechobee" >
			<logic:equal name="intakeForm" property="intake.farmBase" value="Okeechobee">
				 <script language="javascript" type="text/javascript"> 
					function checkSubmit(e)
						{
						   if(e && e.keyCode == 13)
						   {
						      document.forms[0].submit();
						   }
						}
				   </script>
			</logic:equal>
			<logic:notEqual name="intakeForm" property="intake.farmBase" value="Okeechobee">
				 <script language="javascript" type="text/javascript"> 
					function checkSubmit(e)
						{
						   if(e && e.keyCode == 13)
						   {
						      //document.forms[0].submit();
						   }
						}
				   </script>
			</logic:notEqual>
		</logic:equal>
		
		<logic:equal name="loginForm" property="systemUser.farmBase" value="Fort Lauderdale" >
			<logic:equal name="intakeForm" property="intake.farmBase" value="Fort Lauderdale">
				 <script language="javascript" type="text/javascript"> 
					function checkSubmit(e)
						{
						   if(e && e.keyCode == 13)
						   {
						      document.forms[0].submit();
						   }
						}
				   </script>
			</logic:equal>
			<logic:notEqual name="intakeForm" property="intake.farmBase" value="Fort Lauderdale">
				 <script language="javascript" type="text/javascript"> 
					function checkSubmit(e)
						{
						   if(e && e.keyCode == 13)
						   {
						      //document.forms[0].submit();
						   }
						}
				   </script>
			</logic:notEqual>
		</logic:equal>
		
		<logic:equal name="loginForm" property="systemUser.farmBase" value="Women's Home" >
			<logic:equal name="intakeForm" property="intake.farmBase" value="Women's Home">
				 <script language="javascript" type="text/javascript"> 
					function checkSubmit(e)
						{
						   if(e && e.keyCode == 13)
						   {
						      document.forms[0].submit();
						   }
						}
				   </script>
			</logic:equal>
			<logic:notEqual name="intakeForm" property="intake.farmBase" value="Women's Home">
					 <script language="javascript" type="text/javascript"> 
					function checkSubmit(e)
						{
						   if(e && e.keyCode == 13)
						   {
						      //document.forms[0].submit();
						   }
						}
			   </script>
			</logic:notEqual>
		</logic:equal>
 </logic:notEmpty>
 
 <div class="page">
        <div class="header">
            <div class="title">
                <h1>
                   Faith Farm Student Management 2.0
                </h1>
                
            </div>
            <div class="loginDisplay">
                    
                    <logic:notEmpty name="loginForm" property="systemUser.username">
                	    Welcome <b>
                	    <bean:write name="loginForm" property="systemUser.username" />@
                	    <bean:write name="loginForm" property="systemUser.farmBase" /> </b>&nbsp;&nbsp;[ <a href="<%=request.getContextPath()%>/Login.do?action=logout" >Log Out</a> ]
                    </logic:notEmpty>
                    
            </div>
             <div class="clear hideSkiplink">
               
<div class="menu" > 
	<ul> 
		<logic:equal name="loginForm" property="systemUser.userRole" value="Administrator" >
             <li><a href="<%=request.getContextPath()%>/Intake.do?action=SearchApps">Applications</a></li>
             <li><a href="<%=request.getContextPath()%>/Intake.do?action=Search">Search</a></li>
             <li><a href="<%=request.getContextPath()%>/Intake.do?action=Create">New Student</a></li>
             <li><a href="<%=request.getContextPath()%>/pages/student/results.jsp">Search Result</a></li>
             <li><a href="<%=request.getContextPath()%>/Cwt.do">CWT</a></li>
             <li><a href="<%=request.getContextPath()%>/Report.do">Reports for Printing</a></li>
             <li><a href="<%=request.getContextPath()%>/Report.do?action=FastFind">Fast Find</a></li>
             <!-- <li><a href="<%=request.getContextPath()%>/Login.do?action=ManageUsers">Manage Users</a></li> -->
       </logic:equal>
     </ul>
</div>
                     
          
            
            </div>
        </div>
        <div class="main">