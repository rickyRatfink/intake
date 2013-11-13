<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<jsp:include page="../../includes/header.jsp" flush="true"/>


    <h2>
        Course Work Therapy
    </h2>
    
            <p>
            Now you can manage programs, metrics, modules, exams, and certifications for Faith Farm students enrolled in the CWT Program.
            <br />
            </p>
            <ul style="color: Blue">
                <li><a href="<%=request.getContextPath()%>/Cwt.do?action=programs">UBIT Skills</a></li>
                <li><a href="<%=request.getContextPath()%>/Cwt.do?action=metrics">Skill Metrics</a></li>
                <li><a href="<%=request.getContextPath()%>/Cwt.do?action=modules">Class Modules</a></li>
                <li><a href="<%=request.getContextPath()%>/Cwt.do?action=sections">Module Sections</a></li>
            </ul>
            <ul style="color: Blue">
                <li><a href="<%=request.getContextPath()%>/Cwt.do?action=departments">Departments</a></li>
                <li><a href="<%=request.getContextPath()%>/Cwt.do?action=jobs">Jobs</a></li>
                <li><a href="<%=request.getContextPath()%>/Cwt.do?action=supervisors">Supervisors</a></li>
             </ul>
        
			<br/><br/>
	        <p>
            Rotation <i>(Applies only to Orientation to Class 5)</i>
            <ul style="color: Blue">
                <li><a href="<%=request.getContextPath()%>/Report.do?action=Rotate&farm=Boynton Beach">Course Rotation for Boynton Beach</a></i>
                <li><a href="<%=request.getContextPath()%>/Report.do?action=Rotate&farm=Fort Lauderdale">Course Rotation for Fort Lauderdale</a></i>
                <li><a href="<%=request.getContextPath()%>/Report.do?action=Rotate&farm=Okeechobee">Course Rotation for Okeechobee</a></i>
            </ul>
 		
			
        </div>
        <div class="clear">
        </div>
    </div>
    <div class="footer">
        
    </div>
   
</form>
</body>
</html>
