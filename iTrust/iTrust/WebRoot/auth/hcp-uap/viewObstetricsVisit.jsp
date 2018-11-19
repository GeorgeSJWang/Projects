<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.action.GetNextVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewObstetricsVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsVisitDAO"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View Obstetrics Office Visits";
%>

<%@include file="/header.jsp"%>

<%
/* Require a Patient ID first */
String pidString = (String) session.getAttribute("pid");
if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
	out.println("pidstring is null");
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewObstetricsVisit.jsp");
	return;
}
/* Get selected patient and current personnel */
ViewObstetricsVisitAction action = new ViewObstetricsVisitAction(prodDAO,
		loggedInMID.longValue(), pidString);
PatientBean p = action.getPatient();
long pid = action.getPid();

PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
PersonnelBean personnel = personnelDAO.getPersonnel(loggedInMID);
String specialty = personnel.getSpecialty();

/* Check if the patient is current obstetrics */
if (action.isObstericsPatient(pid)){
	%>
	<br />
	<%
	if (action.needRHShot(pid)){
		%>
		<div align=center>
			<span class="iTrustError">Notice : The patient should be given an RH immune globulin shot if they have not already.</span>
		</div>
		<%
	}
	%>
	<br />
	<table class="fTable" align="center" id="OBlist">
		<tr>
			<th colspan="11">Obstetrics Office Visits</th>
		</tr>
		<tr class="subHeader">
	    		<td>ID</td>
	    		<td>Scheduled Date</td>
	    		<td>Weeks Pregnant</td>
	    		<td>Weight(pounds)</td>
	    		<td>Blood Pressure</td>
	    		<td>Fetal heart rate</td>
	    		<td>Num Children</td>
	    		<td>Low Lying Placenta</td>
	  			<td>Creation Date</td>
	  			<%
	  			if (specialty != null && specialty.equals("OB/GYN")){
					%>
					<td>Action</td>
					<%
				}
	  			%>
		</tr>
		<% 
		List<ObstetricsVisitBean> visits = action.getAllObstetricsVisits(pid);
		GetNextVisitAction nvAction = new GetNextVisitAction();
		int index = 0;
		for (ObstetricsVisitBean obvisit : visits) {
			loggingAction.logEvent(TransactionType.VIEW_OBSTETRIC_OFFICE_VISIT,
			loggedInMID.longValue(), pid, Long.toString(obvisit.getID()));
		%>
			<tr>
				<td><%=StringEscapeUtils.escapeHtml("" + (obvisit.getID()))%></td>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (obvisit.getScheduledDateString()))%></td>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (obvisit.getNumWeeks()))%></td>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (obvisit.getWeight()))%></td>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (obvisit.getBloodPressure()))%></td>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (obvisit.getFHR()))%></td>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (obvisit.getNumChildren()))%></td>
	    		<td><%=StringEscapeUtils.escapeHtml("" + (obvisit.getLLP()))%></td>
	  			<td><%=StringEscapeUtils.escapeHtml("" + (obvisit.getCreatedDateString()))%></td>
				<%
	  			if (specialty != null && specialty.equals("OB/GYN")){
					%>
					<td><a id="editButton"
					href="editObstetricsVisit.jsp?requestID=<%=StringEscapeUtils.escapeHtml("" + (obvisit.getID()))%>">Edit</a></td>
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
	session.setAttribute("pid", "");
	%>
	<br />
	<div align=center>
		<span class="iTrustMessage" id="ViewObVisitPatientError">
		The patient is not an obstetrics patient. Please <a href="viewObstetricsVisit.jsp">try again</a>.</span>
	</div>
	<br />
	<% 
}
%>

<%@include file="/footer.jsp"%>
