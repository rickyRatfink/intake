<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

 
<jsp:include page="../../../includes/header.jsp" flush="true"/>



<html:form method="POST" action="/CwtRoster">

    <h2>
        Course Work Therapy - <bean:write name="cwtRoster" property="cwtModule.moduleName" /> Roster
    </h2>
    <br/>
     <i>Check the students listed below that are to be enrolled in this module and click generated roster.&nbsp;Once generate you may
    end attendance and exam information.</i>
            <br />
            <jsp:include page="../messages.jsp" flush="true"/>
            <br/>
            
            <div align="left">
            <table width="90%" cellpadding="0" cellspacing="0" >
            <tr>
            	<td>
                <table width="100%" cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td class="colHeading2" width="100">Enroll</td>
                    <td class="colHeading2" width="150">Student</td>
                    <td class="colHeading2" width="100">Department</td>
                    <td class="colHeading2" width="100">Job</td>
                    <td class="colHeading2" width="100">Supervisor</td>
                    <td class="colHeading2" width="100">Farm</td>
                </tr>
               
                <logic:iterate id="loop" name="cwtRoster" property="masterList" indexId="i">
                <tr>
                   	<td class="searchRowOdd2" width="50" ><html:checkbox name="cwtRoster" property="enrollFlag[${i}]" value="Yes" /></td>
                    <td class="searchRowOdd2" ><bean:write name="loop" property="intake.firstname"/>&nbsp;<bean:write name="loop" property="intake.lastname"/></td>
                    <td class="searchRowOdd2" ><bean:write name="loop" property="cwtDepartment.title"/></td>
                    <td class="searchRowOdd2" ><bean:write name="loop" property="cwtJob.title"/></td>
                    <td class="searchRowOdd2" ><bean:write name="loop" property="cwtSupervisor.firstname"/>&nbsp;<bean:write name="loop" property="cwtSupervisor.lastname"/></td>
                    <td class="searchRowOdd2"><bean:write name="loop" property="intake.farmBase"/></td>
                </tr> 
                </logic:iterate>
               

                </table>
                </td>
            </tr>
            </table>
            <br/>
            <table width="100%" cellpadding="0" cellspacing="0">
            <tr>
            	<td height="23" valign="center" align="left">
            		<input type="submit" name="action" value="Generate Roster"/>
            	</td>
            </tr>
           	</table>
        	</div>

        </div>
        <div class="clear">
        </div>
    </div>
    <div class="footer">
        
    </div>
 <html:hidden name="cwtForm" property="pageSource" value="sections" />
</html:form>
</body>
</html>
