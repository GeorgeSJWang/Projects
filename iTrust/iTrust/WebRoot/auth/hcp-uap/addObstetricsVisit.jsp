<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewObstetricsVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.action.AddObstetricsVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.action.GetNextVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Add an Obstetrics Visit";
%>

<%@include file="/header.jsp"%>

<%	
/* Require a Patient ID first */
String pidString = (String) session.getAttribute("pid");
if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
	out.println("pidstring is null");
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/addObstetricsVisit.jsp");
	return;
}

/* Get selected patient and current personnel */
AddObstetricsVisitAction action = new AddObstetricsVisitAction(prodDAO, loggedInMID.longValue(), pidString);
PatientBean p = action.getPatient();
long pid = action.getPid();

PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
PersonnelBean personnel = personnelDAO.getPersonnel(loggedInMID);
String specialty = personnel.getSpecialty();

/* Get current date */
DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
Date now = new Date();

/* Prompt error if not OB/GYN */
if (specialty != null && specialty.equals("OB/GYN")){
	if (action.isObstericsPatient(pid)){
		%>
		<br /><br />
		<%
		boolean formIsFilled = request.getParameter("formIsFilled") != null
		&& request.getParameter("formIsFilled").equals("true");
		boolean autoAdd = request.getParameter("autoAdd") != null 
				&& request.getParameter("autoAdd").equals("true");
		if (formIsFilled && autoAdd){
			/* Create new ObstetricsVisitBean automatically*/
			ObstetricsVisitBean newVisit = new ObstetricsVisitBean();
			try{
				action.addVisit(newVisit, request.getParameter("patientID"), request.getParameter("scheduledDate"), request.getParameter("createdDate"), 
						request.getParameter("weight"), request.getParameter("bloodPressure"), request.getParameter("FHR"), 
								request.getParameter("numChildren"), request.getParameter("LLP"));
				loggingAction.logEvent(TransactionType.SCHEDULE_NEXT_OFFICE_VISIT, loggedInMID.longValue(), pid, Long.toString(newVisit.getID()));
				response.sendRedirect("viewObstetricsVisit.jsp");
			} catch(FormValidationException e){
			%>
				<div align=center>
					<span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage()) %></span>
				</div>
			<%
			}	
		}
		else if (formIsFilled) {
			
			/* Create new ObstetricsVisitBean manually */
			ObstetricsVisitBean newVisit = new ObstetricsVisitBean();
			try{
				action.addVisit(newVisit, request.getParameter("patientID"), request.getParameter("scheduledDate"), request.getParameter("createdDate"), 
						request.getParameter("weight"), request.getParameter("bloodPressure"), request.getParameter("FHR"), 
								request.getParameter("numChildren"), request.getParameter("LLP"));
				loggingAction.logEvent(TransactionType.CREATE_OBSTETRIC_OFFICE_VISIT, loggedInMID.longValue(), pid, Long.toString(newVisit.getID()));
				response.sendRedirect("viewObstetricsVisit.jsp");
			} catch(FormValidationException e){
			%>
				<div align=center>
					<span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage()) %></span>
				</div>
			<%
			}
		}
		
		ViewObstetricsVisitAction viewAction = new ViewObstetricsVisitAction(prodDAO,
				loggedInMID.longValue(), pidString);
		List<ObstetricsVisitBean> visits = viewAction.getSortedObstetricsVisits(pid);
		ObstetricsVisitBean latestVisit = null;
		GetNextVisitAction nextAction = null;
		String scheduledDate = null;
		try{
			latestVisit = visits.get(0);
			nextAction = new GetNextVisitAction(prodDAO, loggedInMID);
			scheduledDate = nextAction.GetNextDateString(latestVisit);
			%>	<div align="center">
				<form action="addObstetricsVisit.jsp" method="post" id="addObVisitForm"><input type="hidden"
					name="formIsFilled" value="true"> <br />
					<div style="width: 50%; text-align:center;">Automatically Add New Obstetrics Office Visit</div>
					<br />
					<input type="hidden" name="autoAdd" value="true">
					<input type="hidden" name="patientID" value = "<%=pid%>">
					<input type="hidden" name="scheduledDate" value="<%=scheduledDate%>">
					<input type="hidden" name="weight" value="0">
					<input type="hidden" name="bloodPressure" value="000/000">
					<input type="hidden" name="FHR" value="0">
					<input type="hidden" name="numChildren" value="<%=StringEscapeUtils.escapeHtml("" + (latestVisit.getNumChildren()))%>">
					<input type="hidden" name="LLP" value="<%=latestVisit.getLLP()%>">
					<input type="hidden" name="createdDate" value ="<%=dateFormat.format(now)%>">
					<input type="submit" style="font-size: 14pt; font-weight: bold;" value="Auto Add">
				</form>
				</div>
			<%
		}catch(Exception e){
			%>
				<div align=center>
					<span class="iTrustError">No Previous Visits, Auto Add Not Available!</span>
				</div>
			<%
		}
		%>
		
		<div align=center>
		
		<form action="addObstetricsVisit.jsp" method="post" id="addObVisitForm"><input type="hidden"
			name="formIsFilled" value="true"> <br />
			<div style="width: 50%; text-align:center;">Please Enter New Obstetrics Office Visit</div>
			<br />
			<table class="fTable">
				<tr>
					<th colspan=2>New Obstetrics Visit</th>
				</tr>
				<tr>
					<td class="subHeaderVertical">Patient ID:</td>
					<td><input type="hidden" name="patientID" value = "<%=pid%>"><%=pid%></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Scheduled Datetime:</td>
					<td><input type="text" name="scheduledDate">
					<input type=button value="Select Date" onclick="displayDatePicker('scheduledDate');"></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Weight(pounds):</td>
					<td><input type="text" name="weight"></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Blood Pressure:</td>
					<td><input type="text" name="bloodPressure"></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Fetal heart rate:</td>
					<td><input type="text" name="FHR"></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Num Children:</td>
					<td><input type="text" name="numChildren"></td>
				</tr>
				<tr>
					<td class="subHeaderVertical">Low Lying Placenta:</td>
					<td><input type="checkbox" name="LLP" value="true"></td>
				</tr
				<tr>
					<td class="subHeaderVertical">Creation Date:</td>
					<td><input type="hidden" name="createdDate" value = "<%=dateFormat.format(now)%>"><%=dateFormat.format(now)%></td>
				</tr>
			</table>
			<br />
			<input type="submit" style="font-size: 14pt; font-weight: bold;" value="Add Visit">
		</form>
		<br />
		
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
