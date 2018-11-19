<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewObstetricsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View Obstetrics Records";
%>

<%@include file="/header.jsp"%>

<%
/* Require a Patient ID first */
String pidString = (String) session.getAttribute("pid");
if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
	out.println("pidstring is null");
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewObstetricsRecords.jsp");
	return;
}
/* Get selected patient and current personnel */
ViewObstetricsAction action = new ViewObstetricsAction(prodDAO,
		loggedInMID.longValue(), pidString);
PatientBean p = action.getPatient();
PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
PersonnelBean personnel = personnelDAO.getPersonnel(loggedInMID);
String specialty = personnel.getSpecialty();

/* Check if enable posted */
if (request.getParameter("enableObstetric") != null) {
	p.setObstetricEligible(true);
	action.updateInformation(p);
} else if (request.getParameter("cancel") != null) {
	pidString = null;
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewObstetricsRecords.jsp");
	return;
}

/* List obstetric records if eligible */
if (p.getObstetricEligible()){
	
	long pid = action.getPid();
	%>
	<br /><br />
	<table class="fTable" align="center" id="OBlist">
		<tr>
			<th colspan="10">Obstetrics Records</th>
		</tr>
		<tr class="subHeader">
	    		<td>ID</td>
	   			<td>Patient ID</td>
	  			<td>Creation Date</td>
	  			<td>Action</td>
		</tr>
		<% 
		List<ObstetricsBean> records = action.getAllObstetrics(pid);
		int index = 0;
		for (ObstetricsBean obsbean : records) {
		%>
			<tr>
				<td><%=StringEscapeUtils.escapeHtml("" + (obsbean.getID()))%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + (pid))%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + (obsbean.getCreated_on()))%></td>
				<td><a id="viewButton"
					href="viewObstetrics.jsp?patient=<%=StringEscapeUtils.escapeHtml("" + (index))%>&requestID=<%=StringEscapeUtils.escapeHtml("" + (obsbean.getID()))%>">View</a></td>
			</tr>
		<%
			index++;
		}
		if (specialty != null && specialty.equals("OB/GYN")){
		%>
			<div align=center>
				<a id="addButton"
				     href="addObstetricsRecord.jsp?patient=<%=StringEscapeUtils.escapeHtml("" + (1))%>">Add Record</a></td>
			</div>
			<br />
		<%
		}
		%>
	<table/>
	<%
	
}
else{
	%>
	<br />
	<div align=center>
		<span class="iTrustMessage" id="ViewObRecordError">The patient is not eligible for obstetric care. Do you want to enable it?</span>
		<form action="viewObstetricsRecords.jsp" method="post">
		    <input type="submit" id="SetEligible" name="enableObstetric" value="yes" />
		    <input type="submit" id="CancelEligible" name="cancel" value="no" />
		</form>
	</div>
	<br />
	<% 
}
%>

<%@include file="/footer.jsp"%>
