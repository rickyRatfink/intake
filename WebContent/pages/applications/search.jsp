<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<%@ page import="java.util.ArrayList" %>

<jsp:include page="../../includes/header.jsp" flush="true"/>


    <h2>
        Application Search
    </h2>
    	<div align="center">
        <div class="applicationSearch">
        
        <html:form action="/Intake">
          	<table width="380" cellpadding="0" cellspacing="0" border="0">
            <tr>
            	<td></td>
                <td>From:</td>
                <td>To:</td>
                <td></td>
            </tr>
            <tr>
            	<td>Application Date:</td>
                <td><html:text property="searchParameter.beginDate" size="20" maxlength="13" styleClass="tcal" />
                </td>
                
                <td><html:text property="searchParameter.endDate" size="20" maxlength="13" styleClass="tcal" /></td>
                <td></td>
            </tr>
             <tr><td colspan="4"><br /></td></tr>
            <tr>
            	<td>Last Name:</td>
                <td colspan="3"><html:text property="searchParameter.lastname" size="30" maxlength="13"  /></td>
            </tr>
             <tr><td colspan="4"><br /></td></tr>
             <tr>
            	<td>First Name:</td>
                <td colspan="3"><html:text property="searchParameter.firstname" size="30" maxlength="13"  /></td>
            </tr>
             <tr><td colspan="4"><br /></td></tr>
             <tr>
            	<td>SSN:</td>
                <td colspan="3">
                	<html:text property="searchParameter.ssn" size="11" maxlength="11"  onkeypress="return maskSsn(event,this)" />
                </td>
            </tr>
             <tr><td colspan="4"><br /></td></tr>
               <tr>
            	<td>Birthdate:</td>
                <td colspan="3"><html:text property="searchParameter.dob" size="20" maxlength="13" styleClass="tcal" /></td>
            </tr>
            
            <tr><td colspan="4"><br /></td></tr>
             <tr>
            	<td>Location:</td>
                <td>
                	<html:select property="searchParameter.farmBase" multiple="true" style="height:90px;" > 
						<html:optionsCollection name="ddl_farm" value="name" label="name" />
						<html:option value="ALL">ALL</html:option>
					</html:select>
                </td>
                
             <tr><td colspan="4"><br /></td></tr>
             
            <tr><td colspan="4"><br /></td></tr>
             <tr>
            	<td>Application Status:</td>
                <td>
                	<html:select property="searchParameter.applicationStatus" multiple="true" style="height:90px;" > 
						<html:optionsCollection name="ddl_applicationStatus" value="value" label="label" />
					</html:select>
                </td>
                
             <tr><td colspan="4"><br /></td></tr>
                      
         </table>
                        
                  
                    
                 
                 </td>
            </tr>
            </table>
            <table width="100%" >
            	<tr>
                	<td width="100%" align="center">
                      <input type="submit" name="action" value="Search Applications"/>
                    </td>
                 </tr>
             </table>
                	
           </html:form>  
        </div>
        </div>
    <div class="footer">
        
    </div>
   
</form>
</body>
</html>
