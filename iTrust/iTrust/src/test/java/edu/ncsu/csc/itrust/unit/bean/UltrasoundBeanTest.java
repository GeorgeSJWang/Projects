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
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundBean;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;

import java.io.InputStream;
import java.sql.Timestamp;

public class UltrasoundBeanTest extends TestCase {
	@Override
	protected void setUp() throws Exception {

	}

	public void testBean() {
		long recordID = 0;
		long patientID = 0;
		Date createdOn = new Date();
		InputStream inputStream = null;
		String imageType = "";
		List<FetusBean> fetus = new ArrayList<FetusBean>();
		
		UltrasoundBean u = new UltrasoundBean();
		u.setRecordID(recordID);
		u.setPatientID(patientID);
		u.setCreated_on(createdOn);
		u.setInputStream(inputStream);
		u.setImageType(imageType);
		u.setFetus(fetus);
		
		assertEquals(recordID, u.getRecordID());
		assertEquals(patientID, u.getPatientID());
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		assertEquals(dateFormat.format(createdOn), u.getCreated_on());
		assertEquals(createdOn, u.getCreated_onAsDate());
		assertEquals(inputStream, u.getInputStream());
		assertEquals(imageType, u.getImageType());
		assertEquals(fetus, u.getFetus());
	}
	
	public void testCompareTo() throws Exception {
		UltrasoundBean u = new UltrasoundBean();
		u.setRecordID(1);
		UltrasoundBean u2 = new UltrasoundBean();
		u2.setRecordID(2);
		assertEquals(0, u.compareTo(u));
		assertEquals(1, u.compareTo(u2));
	}

}
