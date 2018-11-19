<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="edu.ncsu.csc.itrust.action.EditChildbirthVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ChildbirthVisitBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Edit a Childbirth Visit";
%>

<%@include file="/header.jsp"%>

<%	
/* Require a Patient ID first */
String pidString = (String) session.getAttribute("pid");
if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
	out.println("pidstring is null");
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/editChildbirthVisit.jsp");
	return;
}

/* Get selected patient and current personnel */
EditChildbirthVisitAction action = new EditChildbirthVisitAction(prodDAO, loggedInMID.longValue(), pidString);
PatientBean p = action.getPatient();
long pid = action.getPid();

PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
PersonnelBean personnel = personnelDAO.getPersonnel(loggedInMID);
String specialty = personnel.getSpecialty();

/* Get current date */
DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
Date now = new Date();

/* get specific childbirth visit */
String rrString = request.getParameter("requestID");
if (rrString == null){
	rrString = request.getParameter("ID");
}
long vid = Long.parseLong(rrString);
ChildbirthVisitBean visit = action.getChildbirthVisit(vid);

/* Prompt error if not OB/GYN */
if (specialty != null && specialty.equals("OB/GYN")){
	if (true){
		%>
		<br /><br />
		<%
		boolean formIsFilled = request.getParameter("formIsFilled") != null
		&& request.getParameter("formIsFilled").equals("true");

		if (formIsFilled) {
			
			/* Create new ChildbirthVisitBean manually */
			ChildbirthVisitBean newVisit = new ChildbirthVisitBean();
			try{
				action.editVisit(newVisit, rrString, request.getParameter("patientID"), request.getParameter("preferredChildbirthMethod"),
						request.getParameter("drugs"), request.getParameter("scheduledDate"), request.getParameter("preScheduled"));
				
				loggingAction.logEvent(TransactionType.EDIT_CHILDBIRTH_VISIT, loggedInMID.longValue(), pid, Long.toString(newVisit.getVisitID()));
				response.sendRedirect("viewChildbirthVisit.jsp");
				
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
		<form action="editChildbirthVisit.jsp" method="post" id="editCbVisitForm">
		<input type="hidden" name="formIsFilled" value="true">
		<input type="hidden" name="ID" value="<%=rrString%>">
		<br />
			<div style="width: 50%; text-align:center;">Please Enter Childbirth Office Visit Information</div>
			<br />
			<table class="fTable">
				<tr>
					<th colspan=2>Edit Childbirth Visit</th>
				</tr>
				<tr>
					<td class="subHeaderVertical">Patient ID:</td>
					<td><input type="hidden" name="patientID" value = "<%=pid%>"><%=pid%></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Preferred Childbirth Method:</td>
					<td><select name="preferredChildbirthMethod">
					<option value = "vaginal delivery">vaginal delivery</option>
					<option value = "vaginal delivery vacuum assist">vaginal delivery vacuum assist</option>
					<option value = "vaginal delivery forceps assist">vaginal delivery forceps assist</option>
					<option value = "caesarean section">caesarean section</option>
					<option value = "miscarriage">miscarriage</option>					
				</tr>
				<tr>
					<td class="subHeaderVertical">Drugs:</td>
					<td><input type="text" name="drugs"></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Scheduled Datetime:</td>
					<td><input type="text" name="scheduledDate">
					<input type=button value="Select Date" onclick="displayDatePicker('scheduledDate');"></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Prescheduled:</td>
					<td> <label for="preScheduled">Prescheduled</label>
					<input type="radio" name="preScheduled" id="preScheduled" value="true" checked>
					<label for="ER">ER</label>
					<input type="radio" name="preScheduled" id="ER" value="false"></td>
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
			<span class="iTrustMessage" id="editChildbirthVisitPatientError">
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
