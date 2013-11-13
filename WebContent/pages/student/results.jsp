<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@ page import="java.util.ArrayList" %>

<jsp:include page="../../includes/header.jsp" flush="true"/>

<html:form  action="/Intake">
    <h2>
        Student Search Results
    </h2>
    
     <logic:notEmpty name="intakeForm" property="message">
  		    <table width="800" cellpadding="0" cellspacing="0" border="0">
                  <tr>
                  	<td width="18">
                  		<img src="<%=request.getContextPath()%>/images/local/message.png" />
                  	</td>
                  	<td class="instructions" align="left">
                  		<i><bean:write name="intakeForm" property="message" /></i>
                  	</td>
                  </tr>
              </table>
              </br>
     </logic:notEmpty>
  
     <table width="100%" cellpadding="0" cellspacing="0" border="0">
     <tr>
         <td class="colHeading2" width="20"></td>
         <td class="colHeading2" width="60">Student Id</td>
         <td class="colHeading2" width="100">Last Name</td>
         <td class="colHeading2" width="30">MI</td>
         <td class="colHeading2" width="100">First Name</td>
         <td class="colHeading2" width="100">SSN</td>
         <td class="colHeading2" width="70">DOB</td>
         <td class="colHeading2" width="100">Status</td>
         <td class="colHeading2" width="100">Class</td>
         <td class="colHeading2" width="100">Entry Date</td>
    </tr>
    <% int count=0; %>
    <logic:notEmpty name="intakeForm">
    
	    <logic:iterate id="loop" name="intakeForm" property="intakeList">
	    <% count++; %>
	    <tr>
	       <td class="searchRowOdd2" >
	                    	 <a href="<%=request.getContextPath()%>/Intake.do?action=Edit&key=<bean:write name="loop" property="intakeId"/>" style="text-decoration:none">
	                    	 <img src="<%=request.getContextPath()%>/images/local/Edit.gif" width="16" height="14"/>&nbsp;</a>
	                   		 
                 <logic:equal name="intakeForm" property="pictureFlag" value="Yes">
	                   <logic:notEmpty name="intakeForm" property="intake.imageHeadshot">
	         		   		<img src="<%=request.getContextPath()%>/Image.do" width="43" height="40"/>
	         		    </logic:notEmpty>
	         	        <logic:empty name="intakeForm" property="intake.imageHeadshot">
	         	       		<img src="<%=request.getContextPath()%>/images/local/person.jpg" width="43" height="40"/>
	         	       </logic:empty>          
         	     </logic:equal> 		 
	                   		 
	                   		 
	      </td>
	       <td class="searchRowOdd2"><bean:write name="loop" property="intakeId"/></td>
	       <td class="searchRowOdd2"><bean:write name="loop" property="lastname"/></td>
	       <td class="searchRowOdd2"><bean:write name="loop" property="mi"/></td>
	       <td class="searchRowOdd2"><bean:write name="loop" property="firstname"/></td>
	       <td class="searchRowOdd2"><bean:write name="loop" property="ssn"/></td>
	       <td class="searchRowOdd2"><bean:write name="loop" property="dob"/></td>
	       <td class="searchRowOdd2"><bean:write name="loop" property="intakeStatus"/></td>
	       <td class="searchRowOdd2"><bean:write name="loop" property="class_"/></td>
	       <td class="searchRowOdd2"><bean:write name="loop" property="entryDate"/></td>
	   </tr>
	   </logic:iterate>
	   <logic:empty name="intakeForm" property="intakeList">
    	<tr><td class="searchRowOdd2" colspan="10">No results</td></tr>
       </logic:empty>
		
	</logic:notEmpty>
	<logic:empty name="intakeForm">
    	<tr><td class="searchRowOdd2" colspan="10">No results</td></tr>
    </logic:empty>
    <logic:notEmpty name="intakeForm">
    	<tr><td class="searchRowOdd2" colspan="10" ><b><%=count %> results returned</b></td></tr>
    </logic:notEmpty>
	
    </table>
    <div class="footer">
    </div>
   
</html:form>
</body>
</html>