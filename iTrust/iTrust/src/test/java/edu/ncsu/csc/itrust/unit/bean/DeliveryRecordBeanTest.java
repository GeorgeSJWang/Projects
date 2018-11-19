package edu.ncsu.csc.itrust.unit.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import junit.framework.TestCase;

import edu.ncsu.csc.itrust.model.old.beans.DeliveryRecordBean;
import edu.ncsu.csc.itrust.model.old.enums.Gender;

public class DeliveryRecordBeanTest extends TestCase {
	
	@Override
	protected void setUp() throws Exception {
		
	}

	public void testBean() {

		long ID = 1;
		long patientID = 1;
		long childbirthVisitID = 1;
		long childID = 1;
		Gender gender = Gender.Female;
		Timestamp deliveryDateTime = new Timestamp(System.currentTimeMillis());
		String deliveryMethod = "caesarean section";
		boolean isEstimated = true;
		
		DeliveryRecordBean drb = new DeliveryRecordBean();
		drb.setID(ID);
		drb.setPatientID(patientID);
		drb.setChildbirthVisitID(childbirthVisitID);
		drb.setChildID(childID);
		drb.setGender(gender);
		drb.setDeliveryDateTime(deliveryDateTime);
		drb.setDeliveryMethod(deliveryMethod);
		drb.setIsEstimated(isEstimated);
		
		assertEquals(ID, drb.getID());
		assertEquals(patientID, drb.getPatientID());
		assertEquals(childbirthVisitID, drb.getChildbirthVisitID());
		assertEquals(childID, drb.getChildID());
		assertEquals(gender, drb.getGender());
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		assertEquals(dateFormat.format(deliveryDateTime), dateFormat.format(drb.getDeliveryDateTime()));
		assertEquals(dateFormat.format(deliveryDateTime), drb.getDeliveryDateTimeString());
		assertEquals(deliveryMethod, drb.getDeliveryMethod());
		assertEquals(isEstimated, drb.getIsEstimated());
		
		drb.setGenderStr("Male");
		assertEquals(gender.Male, drb.getGender());
		
	}
	
	public void testCompareTo() throws Exception {
		DeliveryRecordBean drb1 = new DeliveryRecordBean();
		drb1.setID(1);
		DeliveryRecordBean drb2 = new DeliveryRecordBean();
		drb2.setID(2);
		assertEquals(0, drb1.compareTo(drb1));
		assertEquals(1, drb1.compareTo(drb2));
		
	}

}
