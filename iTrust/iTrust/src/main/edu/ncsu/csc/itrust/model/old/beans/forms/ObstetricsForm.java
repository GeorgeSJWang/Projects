package edu.ncsu.csc.itrust.model.old.beans.forms;

public class ObstetricsForm{
	private String PatientID = "";
	private String LMP = "";
	private String created_on = "";
		
	public ObstetricsForm(String PatientID, String LMP, String created_on){
		this.PatientID = PatientID;
		this.LMP = LMP;
		this.created_on = created_on;
	};
	
	public String getPatientID(){
		return PatientID;
	}
	
	public void setPatientID(String PatientID){
		this.PatientID = PatientID;
	}
	public String getLMP(){
		return LMP;
	}
	
	public void setLMP(String LMP){
		this.LMP = LMP;
	}
	public String getCreated_on(){
		return this.created_on;
	}
	
	public void setCreated_on(String created_on){
		this.created_on = created_on;
	}
	
}