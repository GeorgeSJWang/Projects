package edu.ncsu.csc.itrust.model.old.beans.forms;

public class PregnancyForm{
	private String PatientID = "";
	private String Date_delivery = "";
	private String num_weeks_pregnant = "";
	private String num_hours_labor = "";
	private String delivery_type = "";
	private String YOC = "";
	private String weight_gain = "";
	private String num_children = "";
	
	public PregnancyForm(String PatientID, String YOC, String num_weeks_pregnant,
			String num_hours_labor, String weight_gain, String delivery_type, String num_children, String Date_delivery){
		this.PatientID = PatientID;
		this.Date_delivery = Date_delivery;
		this.num_weeks_pregnant = num_weeks_pregnant;
		this.num_hours_labor = num_hours_labor;
		this.delivery_type = delivery_type;
		this.YOC = YOC;
		this.weight_gain = weight_gain;
		this.num_children = num_children;
	};
	
	public String getPatientID(){
		return PatientID;
	}
	public void setPatientID(String PatientID){
		this.PatientID = PatientID;
	}
	
	public String getWeight_gain(){
		return weight_gain;
	}
	
	public void setWeight_gain(String weight_gain){
		this.weight_gain = weight_gain;
	}
	
	public String getNum_children(){
		return num_children;
	}
	public void setNum_children(String num_children){
		this.num_children = num_children;
	}
	
	public String getDate_delivery(){
		return Date_delivery;
	}
	public void setDate_delivery(String Date_delivery){
		this.Date_delivery = Date_delivery;
	}

	public String getNum_weeks_pregnant(){
		return num_weeks_pregnant;
	}
	public void setNum_weeks_pregnant(String num_weeks_pregnant){
		this.num_weeks_pregnant = num_weeks_pregnant;
	}
	
	public String getNum_hours_labor(){
		return this.num_hours_labor;
	}
	public void setNum_hours_labor(String num_hours_labor){
		this.num_hours_labor = num_hours_labor;
	}
	
	public String getDelivery_type(){
		return this.delivery_type;
	}
	public void setDelivery_type(String delivery_type){
		this.delivery_type = delivery_type;
	}
	
	public String getYOC(){
		return this.YOC;
	}
	public void setYOC(String YOC){
		this.YOC = YOC;
	}
}