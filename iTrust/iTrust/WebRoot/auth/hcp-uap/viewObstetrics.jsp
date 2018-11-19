<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewObstetricsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PregnancyBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View an Obstetrics Record";
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

	// get obstetrics request id
	String rrString = request.getParameter("requestID");
	
	ViewObstetricsAction action = new ViewObstetricsAction(prodDAO,
			loggedInMID.longValue(), pidString);
	
	long pid = action.getPid();
	long oid = Long.parseLong(rrString);
	// grab the specific obstetrics bean
	ObstetricsBean record = action.getObstetricsRecord(oid);
	loggingAction.logEvent(TransactionType.VIEW_INITIAL_OBSTETRIC_RECORD, loggedInMID.longValue(), pid, record.getEDD());

%>
<br /><br />
<table class="fTable" align="center">
	<tr>
		<th colspan="10">Obstetrics Record</th>
	</tr>
	<tr class="subHeader">
    		<td>ID</td>
   			<td>Patient ID</td>
   			<td>Estimated Due Date</td>
  			<td>Num Weeks Pregnant</td>
  			<td>Report Created On</td>
  	</tr>
  	<tr>
			<td><%=StringEscapeUtils.escapeHtml("" + (record.getID()))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + (pid))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + (record.getEDD()))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + (record.getNumber_of_weeks_pregnant()))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + (record.getCreated_on()))%></td>
	</tr>
</table><br><br>
<table class="fTable" align="center" id="ViewPreg">
	<tr>
		<th colspan="10">Prior Pregnancies</th>
	</tr>
	<tr class="subHeader">
			<td>Year of Conception</td>
   			<td>Weeks Pregnant</td>
  			<td>Hours of Labor</td>
  			<td>Weight Gain</td>
  			<td>Delivery Type</td>
  			<td>Num Children</td>
  	</tr>

	<% 
	List<PregnancyBean> priorPregnancies = record.getPregnancies();
	Date obstetricsDate = record.getCreated_onAsDate();

	for (PregnancyBean pregBean : priorPregnancies) {
		Date pregDate = pregBean.getDateAsDate();
		if(pregDate.before(obstetricsDate) || pregDate.equals(obstetricsDate)) {
	%>
		<tr>
			<td><%=StringEscapeUtils.escapeHtml("" + (pregBean.getYOC()))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + (pregBean.getNum_weeks_pregnant()))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + (pregBean.getNum_hours_labor()))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + (pregBean.getWeight_gain()))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + (pregBean.getDelivery_type()))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + (pregBean.getNum_children()))%></td>
		</tr>
	<%
		}
	}
%>
<table/>

<%@include file="/footer.jsp"%>
