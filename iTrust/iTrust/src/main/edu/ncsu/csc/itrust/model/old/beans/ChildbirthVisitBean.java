package edu.ncsu.csc.itrust.model.old.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A bean for storing data about childbirth visit records.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class ChildbirthVisitBean implements Serializable, Comparable<ChildbirthVisitBean> {
	
	private long visitID = 0;
	private long patientID = 0;
	private String preferredChildbirthMethod;
	private String drugs;
	private Date scheduledDate;
	private boolean preScheduled;
	private List<DeliveryRecordBean> deliveryRecords;
	
	public long getVisitID() {
		return visitID;
	}
	public void setVisitID(long visitID) {
		this.visitID = visitID;
	}
	public long getPatientID() {
		return patientID;
	}
	public void setPatientID(long patientID) {
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
	public Date getScheduledDate() {
		return scheduledDate;
	}
	public String getScheduledDateString() {
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
		return f.format(scheduledDate);
	}
	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	public boolean isPreScheduled() {
		return preScheduled;
	}
	public void setPreScheduled(boolean preScheduled) {
		this.preScheduled = preScheduled;
	}
	public List<DeliveryRecordBean> getDeliveryRecord() {
		return deliveryRecords;
	}
	public void setDeliveryRecord(List<DeliveryRecordBean> deliveryRecords) {
		this.deliveryRecords = deliveryRecords;
	}
	@Override
	public int compareTo(ChildbirthVisitBean o) {
		return (int)(o.visitID-this.visitID);
	}
}
