<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<jsp:include page="../../includes/header_info.jsp" flush="true"/>
 
<html:form method="POST" action="Intake">
<div onKeyPress="return checkSubmit(event)">

            <br /><br />
            <table width="700" cellpadding="0" cellspacing="0" border="0">
            <tr>
				<td>
    				<jsp:include page="../../includes/messages.jsp" flush="true"/>
	   			</td>
			</tr>
            <tr>
            	<td  style="background: silver; text-align: center;color:#000000;font-weight:bold;height=18px;">Pass History</td>
            </tr>
            <tr>
            	<table width="700" cellpadding="0" cellspacing="0" >
            		<tr>
                    	<td style="height:20px;border: 1px solid #666;color:#000000;font-weight:bold;padding-left:5px;">Pass Date</td>
                        <td style="height:20px;border: 1px solid #666;color:#000000;font-weight:bold;padding-left:5px;">Hours</td>
                        <td style="height:20px;border: 1px solid #666;color:#000000;font-weight:bold;padding-left:5px;">Type</td>
                        <td style="height:20px;border: 1px solid #666;color:#000000;font-weight:bold;padding-left:5px;">Del</td>
                     </tr>
                     <tr>
                    	<td style="background: silver;height:20px;border: 1px solid #666;color:#000000;font-weight:bold;padding-left:5px;">
                    		<html:text property="passHistory.passDate" size="12" maxlength="12" styleClass="tcal"/>
                    	</td>
                        <td style="background: silver;height:20px;border: 1px solid #666;color:#000000;font-weight:bold;padding-left:5px;">
                                <html:text property="passHistory.hours" size="3" maxlength="3"/>
                		</td>
                        <td colspan="2" style="background: silver;height:20px;border: 1px solid #666;color:#000000;font-weight:bold;padding-left:5px;">
                               <html:select property="passHistory.passType" >
                               		<html:option value="">Select</html:option> 
									<html:optionsCollection name="ddl_passType" value="value"  label="label" />
								</html:select>  
						</td>
                        
                     </tr>
                     <logic:notEmpty name="intakeForm" property="studentPassHistory">
	                     <logic:iterate id="loop" name="intakeForm" property="studentPassHistory" indexId="i">
	                     <tr> 
	                     	<td><bean:write name="loop" property="passDate"/></td>
	                        <td><bean:write name="loop" property="hours"/></td>
	                        <td><bean:write name="loop" property="passType"/></td> 
	                        <td><input type="submit" name="action" value="Delete Pass History" onClick="javascript:document.getElementById('deleteId').value='<bean:write name="loop" property="studentPassHistoryId"/>'" class="imageButtonDelete"/></td>
	                     </tr>
	                     </logic:iterate>
	                  </logic:notEmpty>    
	                  <logic:empty name="intakeForm" property="studentPassHistory">
	                     <tr>
	                     	<td colspan="7">No history</td> 
	                     </tr>
	                  </logic:empty>
                    
                </table>
         </tr>
         </table>

   <br /><br />
   <div align="center">
   		<input type="submit" name="action" value="Save" class="imageButtonSave" title="Save Discipline" />&nbsp;
   </div>


    <div class="footer">
        
    </div>
     <html:hidden property="pageSource" value="pass"/>  
     <html:hidden property="deleteId" styleId="deleteId" />
</div>
</html:form>

</body>
</html>
