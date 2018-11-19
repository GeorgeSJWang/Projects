package edu.ncsu.csc.itrust.model.old.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A bean for storing data about obstetrics records.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class PregnancyBean implements Serializable, Comparable<PregnancyBean> {
	
	private int ID = 0;
	private int PatientID = 0;
	private Date date;
	private int YOC = 0;
	private int num_weeks_pregnant = 0;
	private int num_hours_labor = 0;
	private String delivery_type = "";
	private float weight_gain = 0;
	private int num_children = 0;
	
	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public int getPatientID() {
		return PatientID;
	}


	public void setPatientID(int patientID) {
		PatientID = patientID;
	}


	public String getDate() {
		if (date == null){
			return "";
		}
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		return dateFormat.format(date);
	}
	
	public Date getDateAsDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public int getYOC() {
		return YOC;
	}


	public void setYOC(int yOC) {
		YOC = yOC;
	}


	public int getNum_weeks_pregnant() {
		return num_weeks_pregnant;
	}


	public void setNum_weeks_pregnant(int num_weeks_pregnant) {
		this.num_weeks_pregnant = num_weeks_pregnant;
	}


	public int getNum_hours_labor() {
		return num_hours_labor;
	}


	public void setNum_hours_labor(int num_hours_labor) {
		this.num_hours_labor = num_hours_labor;
	}


	public String getDelivery_type() {
		return delivery_type;
	}


	public void setDelivery_type(String delivery_type) {
		this.delivery_type = delivery_type;
	}
	
	public float getWeight_gain(){
		return weight_gain;
	}
	
	public void setWeight_gain(float weight_gain){
		this.weight_gain = weight_gain;
	}
	
	public int getNum_children(){
		return num_children;
	}
	public void setNum_children(int num_children){
		this.num_children = num_children;
	}
	
	@Override
	public int compareTo(PregnancyBean o) {
		return (int)(o.ID-this.ID);
	}
	
	
	public int equals(PregnancyBean o) {
		return (int)(o.ID-this.ID);
	}
	
	@Override
	public int hashCode() {
		assert false : "hashCode not designed";
		return 42; // any arbitrary constant will do
	}
	
}
