<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="edu.ncsu.csc.itrust.action.EditObstetricsVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Edit an Obstetrics Visit";
%>

<%@include file="/header.jsp"%>

<%	
/* Require a Patient ID first */
String pidString = (String) session.getAttribute("pid");
if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
	out.println("pidstring is null");
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/editObstetricsVisit.jsp");
	return;
}

/* Get selected patient and current personnel */
EditObstetricsVisitAction action = new EditObstetricsVisitAction(prodDAO, loggedInMID.longValue(), pidString);
PatientBean p = action.getPatient();
long pid = action.getPid();

PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
PersonnelBean personnel = personnelDAO.getPersonnel(loggedInMID);
String specialty = personnel.getSpecialty();

/* Get current date */
DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
Date now = new Date();

/* get specific obstetric visit */
String rrString = request.getParameter("requestID");
if (rrString == null){
	rrString = request.getParameter("ID");
}
long vid = Long.parseLong(rrString);
ObstetricsVisitBean visit = action.getObstetricsVisit(vid);

/* Prompt error if not OB/GYN */
if (specialty != null && specialty.equals("OB/GYN")){
	if (action.isObstericsPatient(pid)){
		%>
		<br /><br />
		<%
		boolean formIsFilled = request.getParameter("formIsFilled") != null
		&& request.getParameter("formIsFilled").equals("true");

		if (formIsFilled) {
			
			/* Create new ObstetricsVisitBean manually */
			ObstetricsVisitBean newVisit = new ObstetricsVisitBean();
			try{
				action.editVisit(newVisit, rrString, request.getParameter("patientID"), 
						request.getParameter("scheduledDate"), request.getParameter("createdDate"), 
						request.getParameter("weight"), request.getParameter("bloodPressure"), request.getParameter("FHR"), 
								request.getParameter("numChildren"), request.getParameter("LLP"));
				
				loggingAction.logEvent(TransactionType.EDIT_OBSTETRIC_OFFICE_VISIT, loggedInMID.longValue(), pid, Long.toString(newVisit.getID()));
				response.sendRedirect("viewObstetricsVisit.jsp");
				
			} catch(FormValidationException e){
			%>
				<div align=center>
					<span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage()) %></span>
				</div>
			<%
			}
		}

		%>
		<div align=center>
		<form action="editObstetricsVisit.jsp" method="post" id="editObVisitForm">
		<input type="hidden" name="formIsFilled" value="true">
		<input type="hidden" name="ID" value="<%=rrString%>">
		<br />
			<div style="width: 50%; text-align:center;">Please Enter Obstetrics Office Visit Information</div>
			<br />
			<table class="fTable">
				<tr>
					<th colspan=2>Edit Obstetrics Visit</th>
				</tr>
				<tr>
					<td class="subHeaderVertical">Patient ID:</td>
					<td><input type="hidden" name="patientID" value = "<%=pid%>"><%=pid%></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Scheduled Datetime:</td>
					<td><input type="text" name="scheduledDate" value="<%=visit.getScheduledDateString()%>">
					<input type=button value="Select Date" onclick="displayDatePicker('scheduledDate');"></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Weight(pounds):</td>
					<td><input type="text" name="weight" value="<%=visit.getWeight()%>"></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Blood Pressure:</td>
					<td><input type="text" name="bloodPressure" value="<%=visit.getBloodPressure()%>"></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Fetal heart rate:</td>
					<td><input type="text" name="FHR" value="<%=visit.getFHR()%>"></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Num Children:</td>
					<td><input type="text" name="numChildren" value="<%=visit.getNumChildren()%>"></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Low Lying Placenta:</td>
					<%
					if (visit.getLLP()){
						%>
						<td><input type="checkbox" name="LLP" value="true" checked></td>
						<%
					}
					else{
						%>
						<td><input type="checkbox" name="LLP" value="true"></td>
						<%
					}
					%>
				</tr
				<tr>
					<td class="subHeaderVertical">Edit Date:</td>
					<td><input type="hidden" name="createdDate" value="<%=dateFormat.format(now)%>"><%=dateFormat.format(now)%></td>
				</tr>
			</table>
			<br />
			<input type="submit" style="font-size: 14pt; font-weight: bold;" value="Edit Visit">
		</form>
		<br />		
		<%
	}
	else{
		session.setAttribute("pid", "");
		%>
		<br />
		<div align=center>
			<span class="iTrustMessage" id="editObVisitPatientError">
			The patient is not an obstetrics patient. Please <a href="/iTrust/">try again</a>.</span>
		</div>
		<br />
		<% 
	}
}
else{
	%>
	<br />
	<div align=center>
		<span class="iTrustMessage" id="editObVisitHCPError">
		You are not an OB/GYN HCP. Please <a href="/iTrust/">go back</a> and create a regular office visit.</span>
	</div>
	<br />
	<% 
}
%>

<%@include file="/footer.jsp"%>
