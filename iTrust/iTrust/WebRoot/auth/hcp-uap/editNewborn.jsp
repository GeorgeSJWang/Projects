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
<%@page import="edu.ncsu.csc.itrust.model.old.beans.DeliveryRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Edit Newborns Information";
%>

<%@include file="/header.jsp"%>

<%	
/* Require a Patient ID first */
String pidString = (String) session.getAttribute("pid");
if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
	out.println("pidstring is null");
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/editNewborn.jsp");
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

/* get specific delivery visit */
String vString = request.getParameter("childbirthVisitID");
if (vString == null){
	vString = request.getParameter("visitID");
}
long vid = Long.parseLong(vString);

/* get specific record */
String rrString = request.getParameter("requestID");
if (rrString == null){
	rrString = request.getParameter("recordID");
}
long rid = Long.parseLong(rrString);




DeliveryRecordBean delivery = action.getDeliveryRecord(rid);

/* Prompt error if not OB/GYN */
if (specialty != null && specialty.equals("OB/GYN")){
	if (true){
		%>
		<br /><br />
		<%
		boolean formIsFilled = request.getParameter("formIsFilled") != null
		&& request.getParameter("formIsFilled").equals("true");

		if (formIsFilled) {
			/* Create new DeliveryRecordBean manually */
			DeliveryRecordBean newRecord = new DeliveryRecordBean();
			try{
				action.editDeliveryRecord(newRecord, rrString, request.getParameter("patientID"), request.getParameter("childbirthVisitID"),
						request.getParameter("childID"), request.getParameter("gender"), request.getParameter("deliveryDateTime"), 
						request.getParameter("deliveryMethod"), request.getParameter("childFirstName"), request.getParameter("childLastName"));
				
				loggingAction.logEvent(TransactionType.EDIT_CHILDBIRTH_VISIT, loggedInMID.longValue(), pid, Long.toString(newRecord.getID()));
				response.sendRedirect("addNewborns.jsp?requestID=" + vString);
				
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
		<form action="editNewborn.jsp" method="post" id="editNBForm">
		<input type="hidden" name="formIsFilled" value="true">
		<input type="hidden" name="childID" value="<%=delivery.getChildID()%>">
		<input type="hidden" name="childbirthVisitID" value="<%=vid%>"> 
		<input type="hidden" name="recordID" value="<%=rid%>">
		<br />
			<div style="width: 50%; text-align:center;">Please Enter the Newborn's Information</div>
			<br />
			<table class="fTable">
				<tr>
					<th colspan=2>Edit Newborns Information</th>
				</tr>
				<tr>
					<td class="subHeaderVertical">Patient ID:</td>
					<td><input type="hidden" name="patientID" value = "<%=pid%>"><%=pid%></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Preferred Childbirth Method:</td>
					<td><select name="deliveryMethod">
					<option value = "vaginal delivery">vaginal delivery</option>
					<option value = "vaginal delivery vacuum assist">vaginal delivery vacuum assist</option>
					<option value = "vaginal delivery forceps assist">vaginal delivery forceps assist</option>
					<option value = "caesarean section">caesarean section</option>
					<option value = "miscarriage">miscarriage</option>					
				</tr>
				<tr>
					<td class="subHeaderVertical">Delivery Datetime:</td>
					<td><input type="text" name="deliveryDateTime">
					<input type=button value="Select Date" onclick="displayDatePicker('deliveryDateTime');"></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Child First Name:</td>
					<td><input type="text" name="childFirstName"</td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Child Last Name:</td>
					<td><input type="text" name="childLastName"</td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Gender:</td>
					<td> 
						<label for="Male">Male</label>
						<input type="radio" name="gender" id="Male" value="Male" checked>
						<label for="Female">Female</label>
						<input type="radio" name="gender" id="Female" value="Female">
					</td>
				</tr>
			</table>
			<br />
			<input type="submit" style="font-size: 14pt; font-weight: bold;" value="Submit">
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
