package edu.ncsu.csc.itrust.unit.action;

import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddChildbirthVisitAction;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.ChildbirthVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.DeliveryRecordBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ChildbirthVisitDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class AddChildbirthVisitActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private AddChildbirthVisitAction action;

	/**
	 * Sets up defaults
	 */
	@Override
	protected void setUp() throws Exception {
		gen.transactionLog();
		gen.uc96();
		action = new AddChildbirthVisitAction(factory, 9000000012L, "1");
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
	
	@Test
	public void testAddVisit() throws Exception {
		ChildbirthVisitDAO childbirthDAO = factory.getChildbirthVisitDAO();
		ChildbirthVisitBean cbvb = new ChildbirthVisitBean();
		long newOID = action.addVisit(cbvb,"1","vaginal delivery","(Pethidine, 2)", "06/25/2015","false");
		assertEquals(action.getChildbirthVisit(newOID).getVisitID(), newOID);
	}
	
	@Test
	public void testAddVisit2() throws Exception {
		ChildbirthVisitDAO childbirthDAO = factory.getChildbirthVisitDAO();
		ChildbirthVisitBean cbvb = new ChildbirthVisitBean();
		long newOID = action.addVisit(cbvb,"1","vaginal delivery","(Pethidine, 2)(Arginine, 4)", "06/25/2015","false");
		assertEquals(action.getChildbirthVisit(newOID).getVisitID(), newOID);
	}
	
	@Test
	public void testAddDelivery() throws Exception {
		ChildbirthVisitBean cbvb = new ChildbirthVisitBean();
		long newOID = action.addVisit(cbvb,"1","vaginal delivery","(Pethidine, 2)(Arginine, 4)", "06/25/2015","false");
		DeliveryRecordBean drb = new DeliveryRecordBean();
		long newID = action.addDelivery(drb, "1", "1", "Female", "11/11/2018 11:11", "caesarean section", "Baby", "Boss");
		assertEquals(action.getDeliveryRecord(newID).getID(), newID);
		
	}

}
