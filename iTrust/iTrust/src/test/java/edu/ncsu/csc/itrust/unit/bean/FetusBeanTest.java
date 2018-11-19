package edu.ncsu.csc.itrust.unit.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.model.old.beans.FetusBean;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;
import java.sql.Timestamp;

public class FetusBeanTest extends TestCase {
	@Override
	protected void setUp() throws Exception {

	}

	public void testBean() {
		long recordID = 0;
		long patientID = 1;
		long ultrasoundID = 2;
		Date createdOn = new Date();
		float CRL = 1.0f;
		float BPD = 2.0f;
		float HC = 3.0f;
		float FL = 4.0f;
		float OFD = 5.0f;
		float AC = 6.0f;
		float HL = 7.0f;
		float EFW = 7.0f;
		
		FetusBean f = new FetusBean();
		f.setRecordID(recordID);
		f.setPatientID(patientID);
		f.setUltrasoundID(ultrasoundID);
		f.setCreated_on(createdOn);
		f.setCRL(CRL);
		f.setBPD(BPD);
		f.setHC(HC);
		f.setFL(FL);
		f.setOFD(OFD);
		f.setAC(AC);
		f.setHL(HL);
		f.setEFW(EFW);
		
		assertEquals(recordID, f.getRecordID());
		assertEquals(patientID, f.getPatientID());
		assertEquals(ultrasoundID, f.getUltrasoundID());
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		assertEquals(dateFormat.format(createdOn), f.getCreated_on());
		assertEquals(createdOn, f.getCreated_onAsDate());
		assertEquals(CRL, f.getCRL());
		assertEquals(BPD, f.getBPD());
		assertEquals(HC, f.getHC());
		assertEquals(FL, f.getFL());
		assertEquals(OFD, f.getOFD());
		assertEquals(AC, f.getAC());
		assertEquals(HL, f.getHL());
		assertEquals(EFW, f.getEFW());
	}
	
	public void testCompareTo() throws Exception {
		FetusBean f = new FetusBean();
		f.setRecordID(1);
		FetusBean f2 = new FetusBean();
		f2.setRecordID(2);
		assertEquals(0, f.compareTo(f));
		assertEquals(1, f.compareTo(f2));
	}

}
