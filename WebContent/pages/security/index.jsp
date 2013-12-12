<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>  

<%@ page import="java.util.ArrayList" %>


<jsp:include page="../../includes/header.jsp" flush="true"/>
            
    <h2> 
        Log In
    </h2>
    
    <p>
    	<img src="<%=request.getContextPath()%>/images/local/message.png"/><b>System notices:</b>&nbsp;&nbsp;
    	<ul>
    		<li>The intake application can now be printed by clicking the 'print application' option in the top menu.</li>
    		<li>Withdraw status added for applications.</li>
    		<li>An email notification is sent to receiving farms of application transfers.</li>
    		<li>You may now change your password by clicking <img src="<%=request.getContextPath()%>/images/local/icon_changePassword.png" style="width:15px;height:auto;"/> in the header.</li>
    		<li>Waitlist status added to applications.</li>
    		<li>A waiting list report is now available under 'Reports for Printing'.</li>
    	</ul>
    </p>
    
            <span class="failureNotification">
            			<logic:iterate id="messages" name="loginForm" property="messages">
							<li><b><bean:write name="messages" property="property"/>&nbsp;<bean:write name="messages" property="message"/></b></li>
						</logic:iterate>             
            </span>
           
	<html:form action="/Login" focus="username">
	<div onKeyPress="return checkSubmit(event)">
            <div class="accountInfo">
                <fieldset class="login">
                    <legend>Account Information</legend>
				    <p>
				        Please enter your username and password.&nbsp;&nbsp;&nbsp;If you don't have an account ask your supervisor for one.
				    </p>
            
                    <p>
                        Username:<br />
                        <html:text property="systemUser.username" size="20" maxlength="20" styleClass="textbox"></html:text>
                     </p>
                    <p>
                        Password:<br />
                        <html:password property="systemUser.password" size="20" maxlength="20" styleClass="textbox"></html:password>
                    </p>
                 </fieldset>
                <p>
                    <input type="submit" name="action" value="Login" class="submitButton" />
                </p>
            </div>
          </div>
        </html:form>

        </div>
        <div class="clear">
        </div>
    </div>
    <div class="footer">
        
    </div>
    


</body>
</html>
