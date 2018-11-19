package edu.ncsu.csc.itrust.unit.dao.childbirth;

import junit.framework.TestCase;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Timestamp;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ChildbirthVisitDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsVisitDAO;
import edu.ncsu.csc.itrust.model.old.enums.Gender;
import edu.ncsu.csc.itrust.model.old.beans.ChildbirthVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.DeliveryRecordBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ChildbirthVisitDAOTest extends TestCase {
	ChildbirthVisitDAO childbirthVisitDAO = TestDAOFactory.getTestInstance().getChildbirthVisitDAO();
	TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
	}
		
	public void testAddChildbirthVisit() throws Exception {
		// define childbirth bean information
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		long visitID = 1;
		long patientID = 1;
		String preferredChildbirthMethod = "vaginal delivery";
		String drugs = "Pitocin";
		Timestamp scheduledDate = new Timestamp(System.currentTimeMillis());
		boolean preScheduled = false;
		List<DeliveryRecordBean> deliveryRecords = new ArrayList<DeliveryRecordBean>();
		
		// set delivery bean information
		DeliveryRecordBean deliveryRecordBean = new DeliveryRecordBean();
		
		deliveryRecordBean.setPatientID(patientID);
		deliveryRecordBean.setChildbirthVisitID(visitID);
		deliveryRecordBean.setDeliveryDateTime(scheduledDate);
		deliveryRecordBean.setDeliveryMethod(preferredChildbirthMethod);
		deliveryRecords.add(deliveryRecordBean);
		
		// set childbirth bean information
		ChildbirthVisitBean cbvb = new ChildbirthVisitBean();
		cbvb.setVisitID(visitID);
		cbvb.setPatientID(patientID);	
		cbvb.setPreferredChildbirthMethod(preferredChildbirthMethod);
		cbvb.setDrugs(drugs);
		cbvb.setScheduledDate(scheduledDate);
		cbvb.setPreScheduled(preScheduled);
		cbvb.setDeliveryRecord(deliveryRecords);
		
		// add childbirth visit to the database
		childbirthVisitDAO.addChildbirthVisit(cbvb);
		long newDRID = childbirthVisitDAO.addDeliveryRecord(deliveryRecordBean);
		deliveryRecordBean.setID(newDRID);
		
		// test getAllChildbirthVisits method
		List<ChildbirthVisitBean> cbvList = childbirthVisitDAO.getAllChildbirthVisits(patientID);
		assertEquals(1, cbvList.size());
		ChildbirthVisitBean cbvb1 = cbvList.get(0);
		
		assertEquals(visitID, cbvb1.getVisitID());
		assertEquals(patientID, cbvb1.getPatientID());
		assertEquals(preferredChildbirthMethod, cbvb1.getPreferredChildbirthMethod());
		assertEquals(drugs, cbvb1.getDrugs());	
		assertEquals(dateFormat.format(scheduledDate), dateFormat.format(cbvb1.getScheduledDate()));
		assertEquals(preScheduled, cbvb1.isPreScheduled());
		assertEquals(deliveryRecords.get(0).getID(), cbvb1.getDeliveryRecord().get(0).getID());
		
		// test getChildbirthVisit method
		cbvb1 = childbirthVisitDAO.getChildbirthVisit(visitID);
		assertEquals(visitID, cbvb1.getVisitID());
		assertEquals(patientID, cbvb1.getPatientID());
		assertEquals(preferredChildbirthMethod, cbvb1.getPreferredChildbirthMethod());
		assertEquals(drugs, cbvb1.getDrugs());	
		assertEquals(dateFormat.format(scheduledDate), dateFormat.format(cbvb1.getScheduledDate()));
		assertEquals(preScheduled, cbvb1.isPreScheduled());
		assertEquals(deliveryRecords.get(0).getID(), cbvb1.getDeliveryRecord().get(0).getID());
	}
	
	public void testUpdateChildbirthVisit() throws Exception {
		// define childbirth bean information
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		long visitID = 1;
		long patientID = 1;
		long childID = 7;
		Gender gender = Gender.Male;
		String preferredChildbirthMethod = "vaginal delivery";
		String drugs = "Pitocin";
		Timestamp scheduledDate = new Timestamp(System.currentTimeMillis());
		boolean preScheduled = false;
		boolean isEstimated = true;
		List<DeliveryRecordBean> deliveryRecords = new ArrayList<DeliveryRecordBean>();
		
		// set delivery bean information
		DeliveryRecordBean deliveryRecordBean = new DeliveryRecordBean();
		
		deliveryRecordBean.setPatientID(patientID);
		deliveryRecordBean.setChildbirthVisitID(visitID);
		deliveryRecordBean.setChildID(childID);
		deliveryRecordBean.setGender(gender);
		deliveryRecordBean.setDeliveryDateTime(scheduledDate);
		deliveryRecordBean.setDeliveryMethod(preferredChildbirthMethod);
		deliveryRecordBean.setIsEstimated(isEstimated);
		deliveryRecords.add(deliveryRecordBean);
		
		// set childbirth bean information
		ChildbirthVisitBean cbvb = new ChildbirthVisitBean();
		cbvb.setVisitID(visitID);
		cbvb.setPatientID(patientID);	
		cbvb.setPreferredChildbirthMethod(preferredChildbirthMethod);
		cbvb.setDrugs(drugs);
		cbvb.setScheduledDate(scheduledDate);
		cbvb.setPreScheduled(preScheduled);
		cbvb.setDeliveryRecord(deliveryRecords);
		
		// add childbirth visit to the database
		childbirthVisitDAO.addChildbirthVisit(cbvb);
		long newDRID = childbirthVisitDAO.addDeliveryRecord(deliveryRecordBean);
		deliveryRecordBean.setID(newDRID);
		
		// test updateDeliveryRecord method
		cbvb.setDrugs("test");
		childbirthVisitDAO.updateChildbirthVisit(cbvb);
		
		// test getChildbirthVisit method
		ChildbirthVisitBean cbvb1 = childbirthVisitDAO.getChildbirthVisit(visitID);
		assertEquals(visitID, cbvb1.getVisitID());
		assertEquals(patientID, cbvb1.getPatientID());
		assertEquals(preferredChildbirthMethod, cbvb1.getPreferredChildbirthMethod());
		assertEquals("test", cbvb1.getDrugs());	
		assertEquals(dateFormat.format(scheduledDate), dateFormat.format(cbvb1.getScheduledDate()));
		assertEquals(preScheduled, cbvb1.isPreScheduled());
		assertEquals(deliveryRecords.get(0).getID(), cbvb1.getDeliveryRecord().get(0).getID());
	}
	
	public void testDeliveryRecord() throws Exception {
		// define DeliveryRecordBean
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
		long patientID = 1;
		long childbirstVisitID = 1;
		long childID = 7;
		Gender gender = Gender.Male;
		Timestamp deliveryDateTime = new Timestamp(System.currentTimeMillis());
		String deliveryMethod = "caesarean section";
		boolean isEstimated = true;
		
		// set DeliveryRecordBean
		DeliveryRecordBean drb = new DeliveryRecordBean();
		drb.setPatientID(patientID);
		drb.setChildbirthVisitID(childbirstVisitID);
		drb.setChildID(childID);
		drb.setGender(gender);
		drb.setDeliveryDateTime(deliveryDateTime);
		drb.setDeliveryMethod(deliveryMethod);
		drb.setIsEstimated(isEstimated);
				
		// add DeliveryRecordBean to the database
		long recordID = childbirthVisitDAO.addDeliveryRecord(drb);
		drb.setID(recordID);
		
		// test the getDeliveryRecords method
		DeliveryRecordBean drb1 = childbirthVisitDAO.getDeliveryRecord(recordID);
		assertEquals(recordID, drb1.getID());
		assertEquals(patientID, drb1.getPatientID());
		assertEquals(childbirstVisitID, drb1.getChildbirthVisitID());
		assertEquals(childID, drb1.getChildID());
		assertEquals(gender, drb1.getGender());
		assertEquals(dateFormat.format(deliveryDateTime), dateFormat.format(drb1.getDeliveryDateTime()));
		assertEquals(deliveryMethod, drb1.getDeliveryMethod());
		assertEquals(isEstimated, drb1.getIsEstimated());
		
		// test the getAllDeliveryRecords method
		List<DeliveryRecordBean> drbList = childbirthVisitDAO.getAllDeliveryRecord(patientID);
		boolean notNull = drbList.size() > 0;
		assertEquals(true, notNull);
		DeliveryRecordBean drbTest = drbList.get(0);
		assertEquals(patientID, drbTest.getPatientID());
		assertEquals(childbirstVisitID, drbTest.getChildbirthVisitID());
		assertEquals(childID, drbTest.getChildID());
		assertEquals(gender, drbTest.getGender());
		assertEquals(dateFormat.format(deliveryDateTime), dateFormat.format(drbTest.getDeliveryDateTime()));
		assertEquals(deliveryMethod, drbTest.getDeliveryMethod());
		assertEquals(isEstimated, drbTest.getIsEstimated());
		
		// test the updateDeliveryRecord method
		long updateID = recordID;
		String updateDeliveryMethod = "vaginal delivery forceps assist";
		drb1.setDeliveryMethod(updateDeliveryMethod);
		childbirthVisitDAO.updateDeliveryRecord(drb1);
		drb1 = childbirthVisitDAO.getDeliveryRecord(updateID);
		assertEquals(updateDeliveryMethod, drb1.getDeliveryMethod());
		
	}
	
}
