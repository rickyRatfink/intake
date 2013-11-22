<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@ page import="java.util.ArrayList" %>

<jsp:include page="../../includes/header.jsp" flush="true"/>


    <h2>
        Application Search Results
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
    
    <% int row=0; String rowClass="Odd"; %>
     <table width="100%" cellpadding="0" cellspacing="0" border="0">
     <tr>
         <td class="colHeading2" width="100">Actions</td>
         <td class="colHeading2" width="100">Applicant Id</td>
         <td class="colHeading2" width="270">Name</td>
         <td class="colHeading2" width="100">SSN</td>
         <td class="colHeading2" width="100">DOB</td>
         <td class="colHeading2" width="100">Valid DL</td>
         <td class="colHeading2" width="300">Farm</td>
         <td class="colHeading2" width="300">Status</td>
    </tr>
     <logic:notEmpty name="intakeForm" property="applicantList" >
	
       <logic:iterate id="loop" name="intakeForm" property="applicantList">
	    <tr>
	       <td class="searchRow<%=rowClass%>2">
	                    	 <a href="<%=request.getContextPath()%>/Intake.do?action=Edit&key=<bean:write name="loop" property="intakeId"/>" style="text-decoration:none">
	                    	 <img src="<%=request.getContextPath()%>/images/local/Edit.gif" width="16" height="14" border="0"/>&nbsp;</a>	                   		 
	      </td>
	       <td class="searchRow<%=rowClass%>2"><bean:write name="loop" property="intakeId"/></td>
	       <td class="searchRow<%=rowClass%>2"><bean:write name="loop" property="firstname"/>&nbsp;<bean:write name="loop" property="mi"/>&nbsp;<bean:write name="loop" property="lastname"/></td>
	       <td class="searchRow<%=rowClass%>2"><bean:write name="loop" property="ssn"/></td>
	       <td class="searchRow<%=rowClass%>2"><bean:write name="loop" property="dob"/></td>
	       <td class="searchRow<%=rowClass%>2"><bean:write name="loop" property="dlFlag"/></td>
	       <td class="searchRow<%=rowClass%>2"><bean:write name="loop" property="farmBase"/></td>
	       <td class="searchRow<%=rowClass%>2"><bean:write name="loop" property="applicationStatus"/></td>
	   </tr>
	
   <%
   row++;
   if (row==2) 
	  	rowClass="Even"; 
	  else {
	    row=0;
		rowClass="Odd";
	  }
   %>
   </logic:iterate>
   </logic:notEmpty>
   <logic:empty name="intakeForm" property="applicantList">
       <tr>
           <td colspan="9">No Applications</td> 
       </tr>
   </logic:empty>
    </table>
    <div class="footer">
    </div>
   
</form>
</body>
</html>