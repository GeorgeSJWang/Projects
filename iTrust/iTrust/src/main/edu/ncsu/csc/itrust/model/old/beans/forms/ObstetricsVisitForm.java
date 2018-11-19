package edu.ncsu.csc.itrust.model.old.beans.forms;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ObstetricsVisitForm{
	private String patientID;
	private String scheduledDate;
	private String createdDate;
	private String weight;
	private String bloodPressure;
	private String FHR;
	private String numChildren;
	private String LLP;
	
	public ObstetricsVisitForm(String patientID, String scheduledDate, String createdDate, String weight, 
			String bloodPressure, String FHR, String numChildren, String LLP){
		this.patientID = patientID;
		this.scheduledDate = scheduledDate;
		this.createdDate = createdDate;
		this.weight = weight;
		this.bloodPressure = bloodPressure;
		this.FHR = FHR;
		this.numChildren = numChildren;
		this.LLP = LLP;
	};
	
	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}
	public String getPatientID() {
		return patientID;
	}
	
	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	public String getScheduledDate() {
		return scheduledDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getWeight() {
		return weight;
	}
	
	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	public String getBloodPressure() {
		return bloodPressure;
	}
	
	public void setFHR(String FHR) {
		this.FHR = FHR;
	}
	public String getFHR() {
		return FHR;
	}
	
	public void setNumChildren(String numChildren) {
		this.numChildren = numChildren;
	}
	public String getNumChildren() {
		return numChildren;
	}
	
	public void setLLP(String LLP) {
		this.LLP = LLP;
	}
	public String getLLP() {
		return LLP;
	}
}