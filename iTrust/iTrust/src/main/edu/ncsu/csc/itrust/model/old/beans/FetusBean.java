package edu.ncsu.csc.itrust.model.old.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A bean for storing data about Fetus records.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class FetusBean implements Serializable, Comparable<FetusBean> {
	
	private long recordID = 0;
	private long patientID = 0;
	private long ultrasoundID = 0;
	private Date createdOn;
	/* data type is assumed to be float 
	 * is there a documentation? */
	private float CRL;
	private float BPD;
	private float HC;
	private float FL;
	private float OFD;
	private float AC;
	private float HL;
	private float EFW;
	
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
	
	public long getUltrasoundID() {
		return ultrasoundID;
	}
	public void setUltrasoundID(long ultrasoundID) {
		this.ultrasoundID = ultrasoundID;
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
	
	public float getCRL() {
		return CRL;
	}
	public void setCRL(float CRL) {
		this.CRL = CRL;
	}
	
	public float getBPD() {
		return BPD;
	}
	public void setBPD(float BPD) {
		this.BPD = BPD;
	}
	
	public float getHC() {
		return HC;
	}
	public void setHC(float HC) {
		this.HC = HC;
	}
	
	public float getFL() {
		return FL;
	}
	public void setFL(float FL) {
		this.FL = FL;
	}
	
	public float getOFD() {
		return OFD;
	}
	public void setOFD(float OFD) {
		this.OFD = OFD;
	}
	
	public float getAC() {
		return AC;
	}
	public void setAC(float AC) {
		this.AC = AC;
	}
	
	public float getHL() {
		return HL;
	}
	public void setHL(float HL) {
		this.HL = HL;
	}
	
	public float getEFW() {
		return EFW;
	}
	public void setEFW(float EFW) {
		this.EFW = EFW;
	}
	
	@Override
	public int compareTo(FetusBean fb) {
		return (int)(fb.recordID - this.recordID);
	}
}