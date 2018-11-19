<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.action.GetNextVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewChildbirthVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ChildbirthVisitBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.ChildbirthVisitDAO"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View Childbirth Office Visits";
%>

<%@include file="/header.jsp"%>

<%
/* Require a Patient ID first */
String pidString = (String) session.getAttribute("pid");
if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
	out.println("pidstring is null");
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewChildbirthVisit.jsp");
	return;
}
/* Get selected patient and current personnel */
ViewChildbirthVisitAction action = new ViewChildbirthVisitAction(prodDAO,
		loggedInMID.longValue(), pidString);
PatientBean p = action.getPatient();
long pid = action.getPid();

PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
PersonnelBean personnel = personnelDAO.getPersonnel(loggedInMID);
String specialty = personnel.getSpecialty();

/* Check if the patient is current obstetrics */
if (true){
	%>
	<br />
	<%

	%>
	<br />
	<table class="fTable" align="center" id="CBlist">
		<tr>
			<th colspan="11">Childbirth Office Visits</th>
		</tr>
		<tr class="subHeader">
	    		<td>ID</td>
	    		<td>Preferred Childbirth Method</td>
	    		<td>Drugs</td>
	    		<td>Scheduled Date</td>
	    		<td>Prescheduled</td>
	  			<%
	  			if (specialty != null && specialty.equals("OB/GYN")){
					%>
					<td>Action</td>
					<td>Add Newborns</td>
					<%
				}
	  			%>
		</tr>
		<% 
		List<ChildbirthVisitBean> visits = action.getAllChildbirthVisits(pid);
		int index = 0;
		for (ChildbirthVisitBean cbvisit : visits) {
		%>
			<tr>
				<td><%=StringEscapeUtils.escapeHtml("" + (cbvisit.getVisitID()))%></td>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (cbvisit.getPreferredChildbirthMethod()))%></td>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (cbvisit.getDrugs()))%></td>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (cbvisit.getScheduledDateString()))%></td>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (cbvisit.isPreScheduled()))%></td>
				<%
	  			if (specialty != null && specialty.equals("OB/GYN")){
					%>
					<td>
						<a id="editButton" href="editChildbirthVisit.jsp?requestID=<%=StringEscapeUtils.escapeHtml("" + (cbvisit.getVisitID()))%>">Edit</a>
					</td>
					<td>
						<a id="addButton" href="addNewborns.jsp?requestID=<%=StringEscapeUtils.escapeHtml("" + (cbvisit.getVisitID()))%>">Add</a>
					</td>
					<%
				}
	  			%>
			</tr>
		<%
			index++;
		}
		
		%>
	<table/>
	<%
	
}
else{

}
%>

<%@include file="/footer.jsp"%>
