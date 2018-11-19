package edu.ncsu.csc.itrust.unit.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;
import java.sql.Timestamp;

public class ObstetricsVisitBeanTest extends TestCase {
	@Override
	protected void setUp() throws Exception {

	}

	public void testBean() {

		int idVal = 1;
		int patientIdVal = 1;
		Timestamp scheduledDateVal = new Timestamp(System.currentTimeMillis());
		Timestamp createdDateVal = new Timestamp(System.currentTimeMillis() - 1000);
		String numWeeksVal = "10";
		float weightVal = 7.2f;
		String bloodPressureVal = "90";
		int FHRVal = 60;
		int numChildrenVal = 2;
		Boolean LLPVal = true;
		
		ObstetricsVisitBean o = new ObstetricsVisitBean();
		o.setID(idVal);
		o.setPatientID(patientIdVal);	
		o.setScheduledDate(scheduledDateVal);
		o.setCreatedDate(createdDateVal);
		o.setNumWeeks(numWeeksVal);
		o.setWeight(weightVal);
		o.setBloodPressure(bloodPressureVal);
		o.setFHR(FHRVal);
		o.setNumChildren(numChildrenVal);
		o.setLLP(LLPVal);
		
		assertEquals(idVal, o.getID());
		assertEquals(1, o.getPatientID());
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		assertEquals(dateFormat.format(createdDateVal), o.getCreatedDateString());
		assertEquals(createdDateVal, o.getCreatedDate());
		assertEquals(dateFormat.format(scheduledDateVal), o.getScheduledDateString());
		assertEquals(scheduledDateVal, o.getScheduledDate());
		assertEquals(numWeeksVal, o.getNumWeeks());
		assertEquals(weightVal, o.getWeight());
		assertEquals(bloodPressureVal, o.getBloodPressure());
		assertEquals(FHRVal, o.getFHR());
		assertEquals(numChildrenVal, o.getNumChildren());
		assertEquals(LLPVal, o.getLLP());
		
	}
	
	public void testCompareTo() throws Exception {
		ObstetricsVisitBean o = new ObstetricsVisitBean();
		o.setID(1);
		ObstetricsVisitBean p = new ObstetricsVisitBean();
		p.setID(2);
		assertEquals(0, o.compareTo(o));
		assertEquals(1, o.compareTo(p));
	}

}
