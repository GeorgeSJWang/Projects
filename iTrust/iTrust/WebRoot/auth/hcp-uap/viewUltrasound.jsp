<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewUltrasoundAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.FetusBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.UltrasoundBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.UltrasoundDAO"%>
<%@page import="javax.servlet.ServletException"%>
<%@page import="javax.servlet.annotation.WebServlet"%>
<%@page import="javax.servlet.http.HttpServlet"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View an Ultrasound Record";
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

	// get obstetrics request id
	String rrString = request.getParameter("requestID");
	
	ViewUltrasoundAction action = new ViewUltrasoundAction(prodDAO, loggedInMID.longValue(), pidString);
	
	long pid = action.getPid();
	long uid = Long.parseLong(rrString);
	// grab the specific obstetrics bean
	UltrasoundBean record = action.getUltrasoundRecord(uid);
	
%>
<br />
<table cellspacing=0 align=center cellpadding=0>
	<tr>
    	<td valign=top>
    		<table class="fTable" align="center" id="topTable">
				<tr>
					<th colspan="10">Ultrasound Record</th>
				</tr>
				<tr class="subHeader">
    				<td>ID</td>
   					<td>Patient ID</td>
  					<td>Record Created On</td>
  				</tr>
  				<tr>
					<td><%=StringEscapeUtils.escapeHtml("" + (record.getRecordID()))%></td>
					<td><%=StringEscapeUtils.escapeHtml("" + (pid))%></td>
					<td><%=StringEscapeUtils.escapeHtml("" + (record.getCreated_on()))%></td>
				</tr>
			</table>
    	</td>
   		<td width="15px">&nbsp;</td>
		<td valign=top>
			<img src="DisplayServlet?id=<%=StringEscapeUtils.escapeHtml("" + (uid))%>" height="150px" width="150px" alt="Oops... Where is the image?">
  		</td>
  	</tr>
<br>
<table class="fTable" align="center" id="ViewPreg">
	<tr>
		<th colspan="10">Fetus Information</th>
	</tr>
	<tr class="subHeader">
		<td>Crown Rump Length</td>
   		<td>Biparietal Diameter</td>
  		<td>Head Circumference</td>
  		<td>Femur Length</td>
  		<td>Occipitofrontal Diameter</td>
  		<td>Abdominal Circumference</td>
  		<td>Humerus Length</td>
  		<td>Estimated Fetal Weight</td>
  	</tr>

	<% 
	List<FetusBean> fetusInfo = record.getFetus();
	for (FetusBean fetusBean : fetusInfo) {
	%>
		<tr>
			<td><%=StringEscapeUtils.escapeHtml("" + (fetusBean.getCRL()))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + (fetusBean.getBPD()))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + (fetusBean.getHC()))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + (fetusBean.getFL()))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + (fetusBean.getOFD()))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + (fetusBean.getAC()))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + (fetusBean.getHL()))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + (fetusBean.getEFW()))%></td>
		</tr>
	<%
	}
	%>
		<div align=center><br><br>
			<a id="addButton"
				href="addFetusInformation.jsp?patient=<%=StringEscapeUtils.escapeHtml("" + (1))%>&requestID=<%=StringEscapeUtils.escapeHtml("" + (record.getRecordID()))%>">Add Fetus Information</a></td>
		</div>
		<br />
<table/>

<%@include file="/footer.jsp"%>
