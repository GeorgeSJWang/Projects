package edu.ncsu.csc.itrust.model.old.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A bean for storing data about Ultrasound records.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class UltrasoundBean implements Serializable, Comparable<UltrasoundBean> {
	
	private long recordID = 0;
	private long patientID = 0;
	private Date createdOn;
	private InputStream inputStream = null;
	private String imageType = "";
	private List<FetusBean> fetus;
	
	public long getRecordID() {
		return recordID;
	}
	public void setRecordID(long ID) {
		this.recordID = ID;
	}
	
	public long getPatientID() {
		return patientID;
	}
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}
	
	public String getCreated_on() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormat.format(createdOn);
	}	
	public Date getCreated_onAsDate() {
		return createdOn;
	}	
	public void setCreated_on(Date createdOn) {
		this.createdOn = createdOn;
	}
		
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	
	public List<FetusBean> getFetus() {
		return fetus;
	}
	public void setFetus(List<FetusBean> fetus) {
		this.fetus = fetus;
	}
	
	@Override
	public int compareTo(UltrasoundBean ub) {
		return (int)(ub.recordID - this.recordID);
	}
}