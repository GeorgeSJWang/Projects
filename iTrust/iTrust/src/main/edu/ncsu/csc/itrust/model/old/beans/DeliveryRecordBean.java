package edu.ncsu.csc.itrust.model.old.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ncsu.csc.itrust.model.old.enums.Gender;


/**
 * A bean for storing data about delivery records.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class DeliveryRecordBean implements Serializable, Comparable<DeliveryRecordBean> {
	
	private long ID = 0;
	private long patientID = 0;
	private long childbirthVisitID = 0;
	private long childID = 0;
	private Gender gender = Gender.NotSpecified;
	private Date deliveryDateTime;
	private String deliveryMethod = "";
	private boolean isEstimated = false;
	
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
	
	public void setChildbirthVisitID(long childbirthVisitID) {
		this.childbirthVisitID = childbirthVisitID;
	}
	
	public long getChildbirthVisitID() {
		return childbirthVisitID;
	}
	
	public void setChildID(long childID) {
		this.childID = childID;
	}
	
	public long getChildID() {
		return childID;
	}
	
	public Gender getGender() {
		return gender;
	}

	public void setGenderStr(String gender) {
		this.gender = Gender.parse(gender);
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public void setDeliveryDateTime(Date deliveryDateTime) {
		this.deliveryDateTime = deliveryDateTime;
	}
	
	public Date getDeliveryDateTime() {
		return deliveryDateTime;
	}
	
	public String getDeliveryDateTimeString() {
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		return f.format(deliveryDateTime);
	}
	
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	
	public void setIsEstimated(boolean isEstimated) {
		this.isEstimated = isEstimated;
	}
	
	public boolean getIsEstimated() {
		return isEstimated;
	}
	
	@Override
	public int compareTo(DeliveryRecordBean d) {
		return (int)(d.ID-this.ID);
	}
	
}
