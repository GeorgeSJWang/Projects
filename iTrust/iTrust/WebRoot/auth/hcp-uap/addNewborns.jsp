<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewChildbirthVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.action.AddChildbirthVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.DeliveryRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.action.GetNextVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ChildbirthVisitBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Add Newborns";
%>

<%@include file="/header.jsp"%>

<%	
/* Require a Patient ID first */
String pidString = (String) session.getAttribute("pid");
if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
	out.println("pidstring is null");
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/addNewborns.jsp");
	return;
}

/* Get selected patient and current personnel */
AddChildbirthVisitAction action = new AddChildbirthVisitAction(prodDAO, loggedInMID.longValue(), pidString);
PatientBean p = action.getPatient();
long pid = action.getPid();

PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
PersonnelBean personnel = personnelDAO.getPersonnel(loggedInMID);
String specialty = personnel.getSpecialty();



/* get specific childbirth visit */
String rrString = request.getParameter("requestID");
if (rrString == null){
	rrString = request.getParameter("childbirthVisitID");
}
long vid = Long.parseLong(rrString);



/* Get current date */
DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
Date now = new Date();

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
			DeliveryRecordBean newRecord = new DeliveryRecordBean();
			try{
				action.addDelivery(newRecord, request.getParameter("patientID"), request.getParameter("childbirthVisitID"), 
						request.getParameter("gender"), request.getParameter("deliveryDateTime"), request.getParameter("deliveryMethod"),
						request.getParameter("childFirstName"), request.getParameter("childLastName"));
				
				loggingAction.logEvent(TransactionType.A_BABY_IS_BORN, loggedInMID.longValue(), pid, Long.toString(newRecord.getChildID()));
				loggingAction.logEvent(TransactionType.CREATE_BABY_RECORD, loggedInMID.longValue(), pid, Long.toString(newRecord.getChildID()));
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
		
		<form action="addNewborns.jsp" method="post" id="addNewborns"><input type="hidden"
			name="formIsFilled" value="true"> <br />
			<input type="hidden" name="childbirthVisitID" value="<%=vid%>"> 
			<div style="width: 50%; text-align:center;">Please Enter Newborn Data</div>
			<br />
			<table class="fTable">
				<tr>
					<th colspan=2>Newborn</th>
				</tr>
				<tr>
					<td class="subHeaderVertical">Patient ID:</td>
					<td><input type="hidden" name="patientID" value = "<%=pid%>"><%=pid%></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Delivery Method:</td>
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
		
		
		<table class="fTable" align="center" id="CBlist">
		<tr>
			<th colspan="11">Delivery Records</th>
		</tr>
		<tr class="subHeader">
	    		<td>Child ID</td>
	    		<td>Gender</td>
	    		<td>Delivery DateTime</td>
	    		<td>Delivery Method</td>
	    		<td>Is Estimated</td>
	  			<%
	  			if (specialty != null && specialty.equals("OB/GYN")){
					%>
					<td>Action</td>
					<%
				}
	  			%>
		</tr>
		
		<%
		ViewChildbirthVisitAction vAction = new ViewChildbirthVisitAction(prodDAO, loggedInMID.longValue(), pidString);
		ChildbirthVisitBean cbVisit = vAction.getChildbirthVisit(vid);
		List<DeliveryRecordBean> records = cbVisit.getDeliveryRecord();
		int index=0;
		for (DeliveryRecordBean record : records) {
			%>
				<tr>
					<td><%=StringEscapeUtils.escapeHtml("" + (record.getChildID()))%></td>
		    		<td><%=StringEscapeUtils.escapeHtml("" + (record.getGender()))%></td>
		    		<td><%=StringEscapeUtils.escapeHtml("" + (record.getDeliveryDateTimeString()))%></td>
		    		<td><%=StringEscapeUtils.escapeHtml("" + (record.getDeliveryMethod()))%></td>
		    		<td><%=StringEscapeUtils.escapeHtml("" + (record.getIsEstimated()))%></td>
					<%
		  			if (specialty != null && specialty.equals("OB/GYN")){
						%>
						<td>
							<a id="editButton" href="http://localhost:8080/iTrust/auth/hcp-uap/editNewborn.jsp?requestID=<%=StringEscapeUtils.escapeHtml("" + (record.getID()))%>&visitID=<%=StringEscapeUtils.escapeHtml("" + (rrString))%>">Edit</a>
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
		session.setAttribute("pid", "");
		%>
		<br />
		<div align=center>
			<span class="iTrustMessage" id="addObVisitPatientError">
			The patient is not an obstetrics patient. Please <a href="addObstetricsVisit.jsp">try again</a>.</span>
		</div>
		<br />
		<% 
	}
}
else{
	%>
	<br />
	<div align=center>
		<span class="iTrustMessage" id="addObVisitHCPError">
		You are not an OB/GYN HCP. Please <a href="/iTrust/">go back</a> and create a regular office visit.</span>
	</div>
	<br />
	<% 
}
%>

<%@include file="/footer.jsp"%>
