package edu.ncsu.csc.itrust.model.old.beans.forms;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DeliveryRecordForm{
	private String patientID;
	private String childbirthVisitID;
	private String deliveryDateTime;
	private String deliveryMethod;
	private String childFirstName;
	private String childLastName;
	
	public DeliveryRecordForm(String patientID, String childbirthVisitID, String deliveryDateTime, String deliveryMethod, String childFirstName, String childLastName){
		this.patientID = patientID;
		this.childbirthVisitID = childbirthVisitID;
		this.deliveryDateTime = deliveryDateTime;
		this.deliveryMethod = deliveryMethod;
		this.childFirstName = childFirstName;
		this.childLastName = childLastName;
	};
	
	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}
	public String getPatientID() {
		return patientID;
	}
	
	public void setChildbirthVisitID(String childbirthVisitID) {
		this.childbirthVisitID = childbirthVisitID;
	}
	public String getChildbirthVisitID() {
		return childbirthVisitID;
	}

	public void setDeliveryDateTime(String deliveryDateTime) {
		this.deliveryDateTime = deliveryDateTime;
	}
	
	public String getDeliveryDateTime() {
		return deliveryDateTime;
	}
	
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	
	public void setChildFirstName(String childFirstName) {
		this.childFirstName = childFirstName;
	}
	
	public String getChildFirstName() {
		return childFirstName;
	}
	
	public void setChildLastName(String childLastName) {
		this.childLastName = childLastName;
	}
	
	public String getChildLastName() {
		return childLastName;
	}
}