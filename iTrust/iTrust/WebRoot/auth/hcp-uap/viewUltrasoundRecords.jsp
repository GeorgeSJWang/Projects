<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewUltrasoundAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.UltrasoundBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.UltrasoundDAO"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View Ultrasound Records";
%>

<%@include file="/header.jsp"%>

<%
/* Require a Patient ID first */
String pidString = (String) session.getAttribute("pid");
if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
	out.println("pidstring is null");
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewUltrasoundRecords.jsp");
	return;
}
/* Get selected patient and current personnel */
ViewUltrasoundAction action = new ViewUltrasoundAction(prodDAO, loggedInMID.longValue(), pidString);
PatientBean p = action.getPatient();
PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
PersonnelBean personnel = personnelDAO.getPersonnel(loggedInMID);
String specialty = personnel.getSpecialty();
long pid = action.getPid();

/* List ultrasound records if eligible */
if (action.isObstericsPatient(pid)){
	
	%>
	<br /><br />
	<table class="fTable" align="center" id="ultrasoundList">
		<tr>
			<th colspan="10">Ultrasound Records</th>
		</tr>
		<tr class="subHeader">
	    		<td>ID</td>
	   			<td>Patient ID</td>
	  			<td>Creation Date</td>
	  			<td>Action</td>
		</tr>
		<% 
		List<UltrasoundBean> records = action.getAllUltrasounds(pid);
		int index = 0;
		for (UltrasoundBean utsbean : records) {
		%>
			<tr>
				<td><%=StringEscapeUtils.escapeHtml("" + (utsbean.getRecordID()))%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + (pid))%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + (utsbean.getCreated_on()))%></td>
				<td><a id="viewButton"
					href="viewUltrasound.jsp?patient=<%=StringEscapeUtils.escapeHtml("" + (index))%>&requestID=<%=StringEscapeUtils.escapeHtml("" + (utsbean.getRecordID()))%>">View</a></td>
			</tr>
		<%
			index++;
		}
		if (specialty != null && specialty.equals("OB/GYN")){
		%>
			<div align=center>
				<a id="addButton"
				     href="addUltrasoundRecord.jsp?patient=<%=StringEscapeUtils.escapeHtml("" + (1))%>">Add Record</a></td>
			</div>
			<br />
		<%
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
		<span class="iTrustMessage" id="ViewUltrasoundPatientError">
		The patient is not an obstetrics patient. Please <a href="viewUltrasoundRecords.jsp">try again</a>.</span>
	</div>
	<br />
	<% 
}
%>

<%@include file="/footer.jsp"%>
