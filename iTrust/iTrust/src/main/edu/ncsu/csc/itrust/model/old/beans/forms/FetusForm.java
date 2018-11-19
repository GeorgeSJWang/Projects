package edu.ncsu.csc.itrust.model.old.beans.forms;

public class FetusForm{
	private String patientID = "";
	private String ultrasoundID = "";
	private String created_on = "";
	private String CRL = "";
	private String BPD = "";
	private String HC = "";
	private String FL = "";
	private String OFD = "";
	private String AC = "";
	private String HL = "";
	private String EFW = "";
	
	public FetusForm(String patientID, String ultrasoundID, String created_on, String CRL, String BPD, 
			String HC,String FL, String OFD, String AC, String HL, String EFW){
		this.patientID = patientID;
		this.ultrasoundID = ultrasoundID;
		this.created_on = created_on;
		this.CRL = CRL;
		this.BPD = BPD;
		this.HC = HC;
		this.FL = FL;
		this.OFD = OFD;
		this.AC = AC;
		this.HL = HL;
		this.EFW = EFW;
	};
	
	public String getPatientID(){
		return patientID;
	}
	public void setPatientID(String patientID){
		this.patientID = patientID;
	}
	
	public String getUltrasoundID(){
		return ultrasoundID;
	}
	public void setUltrasoundID(String ultrasoundID){
		this.ultrasoundID = ultrasoundID;
	}
	
	public String getCreated_on(){
		return created_on;
	}
	public void setCreated_on(String created_on){
		this.created_on = created_on;
	}
	
	public String getCRL(){
		return CRL;
	}
	public void setCRL(String CRL){
		this.CRL = CRL;
	}
	
	public String getBPD(){
		return BPD;
	}
	public void setBPD(String BPD){
		this.BPD = BPD;
	}

	public String getHC(){
		return HC;
	}
	public void setHC(String HC){
		this.HC = HC;
	}
	
	public String getFL(){
		return this.FL;
	}
	public void setFL(String FL){
		this.FL = FL;
	}
	
	public String getOFD(){
		return this.OFD;
	}
	public void setOFD(String OFD){
		this.OFD = OFD;
	}
	
	public String getAC(){
		return this.AC;
	}
	public void setAC(String AC){
		this.AC = AC;
	}
	
	public String getHL(){
		return this.HL;
	}
	public void setHL(String HL){
		this.HL = HL;
	}
	
	public String getEFW(){
		return this.EFW;
	}
	public void setEFW(String EFW){
		this.EFW = EFW;
	}
}