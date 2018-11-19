package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import edu.ncsu.csc.itrust.action.AddChildbirthVisitAction;
import edu.ncsu.csc.itrust.action.AddObstetricsAction;
import edu.ncsu.csc.itrust.action.AddPatientAction;
import edu.ncsu.csc.itrust.action.EditChildbirthVisitAction;
import edu.ncsu.csc.itrust.action.ViewObstetricsAction;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.ChildbirthVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.DeliveryRecordBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ChildbirthVisitDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class EditChildbirthVisitActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditChildbirthVisitAction action;

	/**
	 * Sets up defaults
	 */
	@Override
	protected void setUp() throws Exception {
		gen.transactionLog();
		gen.uc94();
		action = new EditChildbirthVisitAction(factory, 9000000012L, "1");
	}
	
	@Test
	public void testGetPatient() {
		PatientBean bean1 = null;
		try {
			bean1 = action.getPatient();
		} catch (ITrustException e) {
			fail();
		}
		if (bean1 == null)
			fail();
		assertEquals(bean1.getMID(), 1L); // and just test that the right record came back
		
		PatientBean bean2 = null;
		try {
			bean2 = action.getPatient();
		} catch (ITrustException e) {
			assertNull(bean2);
		}

		PatientBean bean3 = null;
		try {
			bean3 = action.getPatient();
		} catch (ITrustException e) {
			assertNull(bean3);
		}
	}

	public void testEditVisit() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		ChildbirthVisitDAO childbirthDAO = factory.getChildbirthVisitDAO();
		ChildbirthVisitBean cbvb = new ChildbirthVisitBean();
		cbvb.setPatientID(1);	
		cbvb.setPreferredChildbirthMethod("vaginal delivery");
		cbvb.setDrugs("(t, 2)");
		cbvb.setScheduledDate(new Timestamp(System.currentTimeMillis()));
		cbvb.setPreScheduled(false);
		
		long newID = childbirthDAO.addChildbirthVisit(cbvb);
		
		cbvb.setPreScheduled(true);
		action.editVisit(cbvb, Long.toString(newID), "1", "vaginal delivery", "(t, 2)", 
				sdf.format(new Timestamp(System.currentTimeMillis())), "true");
		ChildbirthVisitBean cbvb2 = childbirthDAO.getChildbirthVisit(newID);
		assertEquals(cbvb2.getVisitID(), newID);
		assertEquals(cbvb2.isPreScheduled(), true);
	}
	
	public void testEditDeliveryRecord() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
		ChildbirthVisitDAO childbirthDAO = factory.getChildbirthVisitDAO();
		DeliveryRecordBean drb = new DeliveryRecordBean();
		drb.setPatientID(1);	
		drb.setChildbirthVisitID(1);
		drb.setChildID(7);
		drb.setGenderStr("Male");
		drb.setDeliveryMethod("caesarean section");
		drb.setDeliveryDateTime(new Timestamp(System.currentTimeMillis()));
		drb.setIsEstimated(true);
		
		long newID = childbirthDAO.addDeliveryRecord(drb);
		
		action.editDeliveryRecord(drb, Long.toString(newID), "1", "1", "7", "Male",
				sdf.format(new Timestamp(System.currentTimeMillis())), "vaginal delivery", "Baby", "Boss");
		// self added getDeliveryRecord() in ChildbirthDAO
		DeliveryRecordBean drb2 = childbirthDAO.getDeliveryRecord(newID);
		assertEquals(drb2.getID(), newID);
		assertEquals(drb2.getDeliveryMethod(), "vaginal delivery");
		
	}

}
