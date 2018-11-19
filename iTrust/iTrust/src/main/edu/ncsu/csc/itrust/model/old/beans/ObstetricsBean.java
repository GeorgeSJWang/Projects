package edu.ncsu.csc.itrust.model.old.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A bean for storing data about obstetrics records.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class ObstetricsBean implements Serializable, Comparable<ObstetricsBean> {
	
	private int ID = 0;
	private int PatientID = 0;
	private Date created_on;
	private Date LMP;
	private int number_of_weeks_pregnant = 0;
	private List<PregnancyBean> pregnancies;
	
	public List<PregnancyBean> getPregnancies() {
		return pregnancies;
	}
	public void setPregnancies(List<PregnancyBean> pregnancies) {
		this.pregnancies = pregnancies;
	}
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
	public String getCreated_on() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormat.format(created_on);
	}	
	public Date getCreated_onAsDate() {
		return created_on;
	}	
	public String getLMP() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormat.format(LMP);
	}
	public Date getLMPAsDate() {
		return LMP;
	}
	public String getEDD() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(LMP);
		c.add(Calendar.DATE, 280);
		return dateFormat.format(c.getTime());
	}
	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}
	public void setLMP(Date lMP) {
		LMP = lMP;
	}
	public int getNumber_of_weeks_pregnant() {
		return number_of_weeks_pregnant;
	}
	public void setNumber_of_weeks_pregnant(int number_of_weeks_pregnant) {
		this.number_of_weeks_pregnant = number_of_weeks_pregnant;
	}
	@Override
	public int compareTo(ObstetricsBean o) {
		return (int)(o.ID-this.ID);
	}
	
}
