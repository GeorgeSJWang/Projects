<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.io.InputStream"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewUltrasoundAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.ReportRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyReportRequestsAction"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.PregnancyBean"%>
<%@page import="edu.ncsu.csc.itrust.model.old.beans.UltrasoundBean"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.mysql.UltrasoundDAO"%>
<%@page import="edu.ncsu.csc.itrust.action.AddUltrasoundAction"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="javax.servlet.ServletException"%>
<%@page import="javax.servlet.annotation.MultipartConfig"%>
<%@page import="javax.servlet.annotation.WebServlet"%>
<%@page import="javax.servlet.http.HttpServlet"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@page import="javax.servlet.http.Part"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@page import="org.apache.commons.fileupload.DefaultFileItemFactory" %>
<%@page import="org.apache.commons.fileupload.FileItemFactory" %>
<%@page import="org.apache.commons.fileupload.FileItem" %>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Add an Ultrasound Record";
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
	
	/* Get current date */
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	Date now = new Date();
%>

<br /><br />
<% 
	boolean isMultipart = ServletFileUpload.isMultipartContent(request);
	if(isMultipart){
		UltrasoundBean newRecord = new UltrasoundBean();
		InputStream inputStream = null;
		FileItem item = null;
		
		FileItemFactory factory = new DefaultFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = upload.parseRequest(request);

		item = (FileItem) items.get(1);
		inputStream = items.get(1).getInputStream();
		
		
		if(inputStream!=null){
			try{
				long uid = action.addRecord(newRecord, items.get(0).getString(), inputStream, item.getContentType(), items.get(2).getString());
				loggingAction.logEvent(TransactionType.ULTRASOUND, loggedInMID.longValue(), pid, Long.toString(newRecord.getRecordID()));
				response.sendRedirect("viewUltrasound.jsp?patient="+StringEscapeUtils.escapeHtml("" + (1))+"&requestID="+StringEscapeUtils.escapeHtml("" + (uid)));
			} catch(FormValidationException e){
			%>
				<div align=center>
					<span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage()) %></span>
				</div>
			<%
			}
		}
	}
%>
	
	<div align=center>
	<form action="addUltrasoundRecord.jsp" method="post" id="addUltrasoundRecordForm" enctype ="multipart/form-data"><br />
		<div style="width: 50%; text-align:center;">Please Enter New Ultrasound Record</div>
		<br />
		<table class="fTable">
			<tr>
				<th colspan=2>New Ultrasound Record</th>
			</tr>
			<tr>
				<td class="subHeaderVertical">Patient ID:</td>
				<td><input type="hidden" name="PatientID" value = "<%=pid%>"><%=pid%></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Upload Image:</td>
				<td><input type = "file" name = "photo" size = "50" /></td>
			</tr>
			<tr>
				<td class="subHeaderVertical">Creation Date:</td>
				<td><input type="hidden" name="created_on" value = "<%=dateFormat.format(now)%>"><%=dateFormat.format(now)%></td>
			</tr>
		</table>
		<br />
		<input type="submit" style="font-size: 14pt; font-weight: bold;" value="Add Record">
	</form>
	

<%@include file="/footer.jsp"%>
