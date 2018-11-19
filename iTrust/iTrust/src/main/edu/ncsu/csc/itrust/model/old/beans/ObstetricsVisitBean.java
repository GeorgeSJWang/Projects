package edu.ncsu.csc.itrust.model.old.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


/**
 * A bean for storing data about obstetrics visit records.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class ObstetricsVisitBean implements Serializable, Comparable<ObstetricsVisitBean> {
	
	private long ID = 0;
	private long patientID = 0;
	private Timestamp scheduledDate;
	private Timestamp createdDate;
	private String numWeeks = "";
	private float weight = 0;
	private String bloodPressure = "";
	private int FHR = 0;
	private int numChildren = 0;
	private Boolean LLP = false;
	
	public void setID(long ID) {
		this.ID = ID;
	}
	public long getID() {
		return ID;
	}
	
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}
	public long getPatientID() {
		return patientID;
	}
	
	public void setScheduledDate(Timestamp scheduledDate) {
		this.scheduledDate = (Timestamp)scheduledDate.clone();
	}
	public Timestamp getScheduledDate() {
		return (Timestamp)scheduledDate.clone();
	}
	public String getScheduledDateString() {
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		return f.format(scheduledDate);
	}
	
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = (Timestamp)createdDate.clone();
	}
	public Timestamp getCreatedDate() {
		return (Timestamp)createdDate.clone();
	}
	public String getCreatedDateString() {
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		return f.format(createdDate);
	}
	
	public void setNumWeeks(String numWeeks) {
		this.numWeeks = numWeeks;
	}
	public String getNumWeeks() {
		return numWeeks;
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public float getWeight() {
		return weight;
	}
	
	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	public String getBloodPressure() {
		return bloodPressure;
	}
	
	public void setFHR(int FHR) {
		this.FHR = FHR;
	}
	public int getFHR() {
		return FHR;
	}
	
	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}
	public int getNumChildren() {
		return numChildren;
	}
	
	public void setLLP(Boolean LLP) {
		this.LLP = LLP;
	}
	public Boolean getLLP() {
		return LLP;
	}
	@Override
	public int compareTo(ObstetricsVisitBean o) {
		return (int)(o.ID-this.ID);
	}
	
}
