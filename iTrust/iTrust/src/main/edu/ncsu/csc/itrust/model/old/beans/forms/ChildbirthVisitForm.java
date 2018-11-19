package edu.ncsu.csc.itrust.model.old.beans.forms;

public class ChildbirthVisitForm{
	
	private String visitID = "";
	private String patientID = "";
	private String preferredChildbirthMethod = "";
	private String drugs = "";
	private String scheduledDate = "";
	private String preScheduled = "";
	
	public ChildbirthVisitForm(String pID, String pCM, String drugs, String scheduledDate, String pS){
		this.patientID = pID;
		this.preferredChildbirthMethod = pCM;
		this.drugs = drugs;
		this.scheduledDate = scheduledDate;
		this.preScheduled = pS;
	}
	
	public String getVisitID() {
		return visitID;
	}
	public void setVisitID(String visitID) {
		this.visitID = visitID;
	}
	public String getPatientID() {
		return patientID;
	}
	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}
	public String getPreferredChildbirthMethod() {
		return preferredChildbirthMethod;
	}
	public void setPreferredChildbirthMethod(String preferredChildbirthMethod) {
		this.preferredChildbirthMethod = preferredChildbirthMethod;
	}
	public String getDrugs() {
		return drugs;
	}
	public void setDrugs(String drugs) {
		this.drugs = drugs;
	}
	public String getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	public String getPreScheduled() {
		return preScheduled;
	}
	public void setPreScheduled(String preScheduled) {
		this.preScheduled = preScheduled;
	}
	
}