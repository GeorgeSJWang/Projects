package edu.ncsu.csc.itrust.unit.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.model.old.beans.ChildbirthVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.DeliveryRecordBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;
import java.sql.Timestamp;

public class ChildbirthVisitBeanTest extends TestCase {
	@Override
	protected void setUp() throws Exception {

	}

	public void testBean() {

		long visitID = 1;
		long patientID = 1;
		String preferredChildbirthMethod = "vaginal delivery";
		String drugs = "Pitocin";
		Timestamp scheduledDate = new Timestamp(System.currentTimeMillis());
		boolean preScheduled = false;
		List<DeliveryRecordBean> deliveryRecords = new ArrayList<DeliveryRecordBean>();
		deliveryRecords.add(new DeliveryRecordBean());
		
		ChildbirthVisitBean cbvb = new ChildbirthVisitBean();
		cbvb.setVisitID(visitID);
		cbvb.setPatientID(patientID);	
		cbvb.setPreferredChildbirthMethod(preferredChildbirthMethod);
		cbvb.setDrugs(drugs);
		cbvb.setScheduledDate(scheduledDate);
		cbvb.setPreScheduled(preScheduled);
		cbvb.setDeliveryRecord(deliveryRecords);
		
		assertEquals(visitID, cbvb.getVisitID());
		assertEquals(patientID, cbvb.getPatientID());
		assertEquals(preferredChildbirthMethod, cbvb.getPreferredChildbirthMethod());
		assertEquals(drugs, cbvb.getDrugs());
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		assertEquals(dateFormat.format(scheduledDate), dateFormat.format(cbvb.getScheduledDate()));
		assertEquals(dateFormat.format(scheduledDate), cbvb.getScheduledDateString());
		
		assertEquals(preScheduled, cbvb.isPreScheduled());
		assertEquals(deliveryRecords, cbvb.getDeliveryRecord());

	}
	
	public void testCompareTo() throws Exception {
		ChildbirthVisitBean cbvb1 = new ChildbirthVisitBean();
		cbvb1.setVisitID(1);
		ChildbirthVisitBean cbvb2 = new ChildbirthVisitBean();
		cbvb2.setVisitID(2);
		assertEquals(0, cbvb1.compareTo(cbvb1));
		assertEquals(1, cbvb1.compareTo(cbvb2));
	}

}
