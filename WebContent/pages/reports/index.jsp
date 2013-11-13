<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<jsp:include page="../../includes/header.jsp" flush="true"/>

<html:form  action="/Report">
    <h2>
        Report List
    </h2>
    <br/><br/>  
            <div align="left">
            <b>Class List</b>
            	<ul>
            		<li><a href="<%=request.getContextPath()%>/Report.do?rpt=ClassList&farm=Boynton Beach">Boynton Beach</a></li>
            		<li><a href="<%=request.getContextPath()%>/Report.do?rpt=ClassList&farm=Fort Lauderdale">Fort Lauderdale</a></li>
            		<li><a href="<%=request.getContextPath()%>/Report.do?rpt=ClassList&farm=Okeechobee">Okeechobee</a></li>
            		<li><a href="<%=request.getContextPath()%>/Report.do?rpt=ClassList&farm=Women's Home">Women's Home</a></li>
            	</ul>
            	<br/><br/>
            	<b>Bed List <i>(Under Construction)</i></b>
            	<ul>
            		<li><a href="<%=request.getContextPath()%>/Report.do?rpt=BedList&farm=Boynton Beach">Boynton Beach</a></li>
            		<li><a href="<%=request.getContextPath()%>/Report.do?rpt=BedList&farm=Fort Lauderdale">Fort Lauderdale</a></li>
            		<li><a href="<%=request.getContextPath()%>/Report.do?rpt=BedList&farm=Okeechobee">Okeechobee</a></li>
            	</ul>
            	<br/><br/>
            	<b>Job List</b>
            	<ul>
            		<li><a href="<%=request.getContextPath()%>/Report.do?rpt=JobList&farm=Boynton Beach">Boynton Beach</a></li>
            		<li><a href="<%=request.getContextPath()%>/Report.do?rpt=JobList&farm=Fort Lauderdale">Fort Lauderdale</a></li>
            		<li><a href="<%=request.getContextPath()%>/Report.do?rpt=JobList&farm=Okeechobee">Okeechobee</a></li>
            	</ul>
            </div>
            
            
    <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
    <div class="footer">
        Faith Farm Ministries &copy;2013
    </div>
   
</html:form>
</body>
</html>
