<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewObstetricsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PregnancyBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO"%>
<%@page import="edu.ncsu.csc.itrust.action.AddObstetricsAction"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Add an Obstetrics Record";
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
	AddObstetricsAction action = new AddObstetricsAction(prodDAO, loggedInMID.longValue(), pidString);
	long pid = action.getPid();
	
	/* Get current date */
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	Date now = new Date();
%>
<br /><br />
<%
	boolean form1IsFilled = request.getParameter("form1IsFilled") != null
	&& request.getParameter("form1IsFilled").equals("true");
	boolean form2IsFilled = request.getParameter("form2IsFilled") != null
	&& request.getParameter("form2IsFilled").equals("true");

	if (form1IsFilled) {
		
		/* Create new ObstetricsBean manually */
		ObstetricsBean newRecord = new ObstetricsBean();

		try{
			action.addRecord(newRecord, request.getParameter("PatientID"), request.getParameter("LMP"), request.getParameter("created_on"));
			loggingAction.logEvent(TransactionType.CREATE_INITIAL_OBSTETRIC_RECORD, loggedInMID.longValue(), pid, newRecord.getEDD());
			%>
			<div align=center>
				<span class="iTrustMessage">New Obstetrics Record successfully added! Click <a href="viewObstetricsRecords.jsp" id="backtoview">here</a> to view.</span>
				<br />
			</div>
			<%
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
	<form action="addObstetricsRecord.jsp" method="post" id="addObRecordForm"><input type="hidden"
		name="form1IsFilled" value="true"> <br />
		<div style="width: 50%; text-align:center;">Please Enter New Obstetrics Record</div>
		<br />
		<table class="fTable">
			<tr>
				<th colspan=2>New Obstetrics Record</th>
			</tr>
			<tr>
				<td class="subHeaderVertical">Patient ID:</td>
				<td><input type="hidden" name="PatientID" value = "<%=pid%>"><%=pid%></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">LMP:</td>
				<td><input type="text" name="LMP">
				<input type=button value="Select Date" onclick="displayDatePicker('LMP');"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Creation Date:</td>
				<td><input type="hidden" name="created_on" value = "<%=dateFormat.format(now)%>"><%=dateFormat.format(now)%></td>
				
			</tr>
	
		</table>
		<br />
		<input type="submit" style="font-size: 14pt; font-weight: bold;" value="Add Record">
	</form>
	<br />
	<%
	if (form2IsFilled){
		/* Create new PregnancyBean manually */
		PregnancyBean newPregnancy = new PregnancyBean();
		try{
			action.addPregnancy(newPregnancy, pidString, request.getParameter("YOC"), request.getParameter("num_weeks_pregnant"),
					request.getParameter("num_hours_labor"), request.getParameter("weight_gain"), 
					request.getParameter("delivery_type"), request.getParameter("num_children"), 
					request.getParameter("Date_delivery"));
			%>
			<div align=center>
			<span class="iTrustMessage">New History Record successfully added!</span>
			</div>
			<%
		} catch(FormValidationException e){
		%>
			<div align=center>
				<span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage()) %></span>
			</div>
		<%
		}
	}
	%>
	<form action="addObstetricsRecord.jsp" method="post" id="addPregnancyForm">
		<input type="hidden" name="form2IsFilled" value="true"> <br />
		<input type="hidden" name="Date_delivery" value=<%=dateFormat.format(now)%>> <br />
		<div style="width: 50%; text-align:center;">Please Enter New Obstetrics Record</div>
		<br />
		<table class="fTable">
			<tr>
				<th colspan=2>New Pregnancy History</th>
			</tr>
			<tr>
				<td class="subHeaderVertical">Year Of Conception:</td>
				<td><input type="text" name="YOC"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Number of Weeks Pregnant:</td>
				<td><input type="text" name="num_weeks_pregnant"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Hour Labor:</td>
				<td><input type="text" name="num_hours_labor"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Weight Gain:</td>
				<td><input type="text" name="weight_gain"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Delivery Type:</td>
				<td><select name="delivery_type">
				<option value = "vaginal delivery">vaginal delivery</option>
				<option value = "vaginal delivery vacuum assist">vaginal delivery vacuum assist</option>
				<option value = "vaginal delivery forceps assist">vaginal delivery forceps assist</option>
				<option value = "caesarean section">caesarean section</option>
				<option value = "miscarriage">miscarriage</option>					
			</tr>
			<tr>
				<td class="subHeaderVertical">Number of Children:</td>
				<td><input type="text" name="num_children"></td>
			</tr>
		</table>
		<br />
		<input type="submit" style="font-size: 14pt; font-weight: bold;" value="Add Pregnancy History">
	</form>
	<br />
	<table class="fTable" align="center" id="AddOBViewPreg">
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
	ObstetricsDAO obstetricsDAO = new ObstetricsDAO(prodDAO);
	List<PregnancyBean> priorPregnancies = obstetricsDAO.getAllPregnancy(pid);
	for (PregnancyBean pregBean : priorPregnancies) {
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
	%>
	<table/>
	<%
%>

<%@include file="/footer.jsp"%>
