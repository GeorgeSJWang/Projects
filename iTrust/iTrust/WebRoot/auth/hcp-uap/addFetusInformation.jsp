<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="edu.ncsu.csc.itrust.action.AddUltrasoundAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.FetusBean"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.UltrasoundDAO"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Add a Fetus Information";
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
	AddUltrasoundAction action = new AddUltrasoundAction(prodDAO, loggedInMID.longValue(), pidString);
	long pid = action.getPid();
	
	/* get specific obstetric visit */
	String rrString = request.getParameter("requestID");
	if (rrString == null){
		rrString = request.getParameter("ID");
	}
	long uid = Long.parseLong(rrString);
	
	/* Get current date */
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	Date now = new Date();
%>
<br /><br />
<%
	boolean formIsFilled = request.getParameter("formIsFilled") != null
	&& request.getParameter("formIsFilled").equals("true");
	System.out.println(formIsFilled);
	if (formIsFilled) {
		/* Create new FetusBean manually */
		FetusBean newRecord = new FetusBean();
		try{
			action.addFetus(newRecord, request.getParameter("PatientID"), request.getParameter("UID"), request.getParameter("created_on"), request.getParameter("CRL"), request.getParameter("BPD"), request.getParameter("HC"), 
										request.getParameter("FL"), request.getParameter("OFD"), request.getParameter("AC"), request.getParameter("HL"), request.getParameter("EFW"));
			response.sendRedirect("viewUltrasound.jsp?patient="+StringEscapeUtils.escapeHtml("" + (1))+"&requestID="+StringEscapeUtils.escapeHtml("" + (uid)));
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
	<form action="addFetusInformation.jsp" method="post" id="addFetusForm">
		<input type="hidden" name="formIsFilled" value="true">
		<input type="hidden" name="ID" value="<%=rrString%>">
		<input type="hidden" name="UID" value="<%=uid%>">
		<input type="hidden" name="PatientID" value="<%=pid%>">
		<br />
		<div style="width: 50%; text-align:center;">Please Enter New Fetus Information</div>
		<br />
		<table class="fTable">
			<tr>
				<th colspan=2>New Fetus Information</th>
			</tr>
			<tr>
				<td class="subHeaderVertical">Crown Rump Length:</td>
				<td><input type="text" name="CRL"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Biparietal Diameter:</td>
				<td><input type="text" name="BPD"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Head Circumference:</td>
				<td><input type="text" name="HC"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Femur Length:</td>
				<td><input type="text" name="FL"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Occipitofrontal Diameter:</td>
				<td><input type="text" name="OFD"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Abdominal Circumference:</td>
				<td><input type="text" name="AC"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Humerus Length:</td>
				<td><input type="text" name="HL"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Estimated Fetal Weight:</td>
				<td><input type="text" name="EFW"></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Creation Date:</td>
				<td><input type="hidden" name="created_on" value = "<%=dateFormat.format(now)%>"><%=dateFormat.format(now)%></td>
			</tr>
		</table>
		<br />
		<input type="submit" style="font-size: 14pt; font-weight: bold;" value="Add Fetus Info">
	</form>

<%@include file="/footer.jsp"%>
