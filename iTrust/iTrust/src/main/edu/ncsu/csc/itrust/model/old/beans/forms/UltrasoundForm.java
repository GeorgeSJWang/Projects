package edu.ncsu.csc.itrust.model.old.beans.forms;

public class UltrasoundForm{
	
	private String patientID = "";
	private String created_on = "";
	private String imageType = "";
	
	public UltrasoundForm(String patientID, String created_on, String imageType){
		this.patientID = patientID;
		this.created_on = created_on;
		this.imageType = imageType;
	};
	
	public String getPatientID(){
		return patientID;
	}
	public void setPatientID(String patientID){
		this.patientID = patientID;
	}
	
	public String getCreated_on(){
		return created_on;
	}
	public void setCreated_on(String created_on){
		this.created_on = created_on;
	}
	
	public String getImageType(){
		return imageType;
	}
	public void setImageType(String imageType){
		this.imageType = imageType;
	}
	
}