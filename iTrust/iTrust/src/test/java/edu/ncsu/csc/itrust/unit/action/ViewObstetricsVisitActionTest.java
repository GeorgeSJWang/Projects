package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddObstetricsAction;
import edu.ncsu.csc.itrust.action.ViewObstetricsAction;
import edu.ncsu.csc.itrust.action.ViewObstetricsVisitAction;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.ObstetricsVisitLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsVisitDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ViewObstetricsVisitActionTest {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ViewObstetricsVisitAction action;
	private AddObstetricsAction addAction;
	private ObstetricsVisitDAO obstetricsVisitDAO;
	
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.patient1();
		action = new ViewObstetricsVisitAction(factory, 9000000003L, "1");
		//insert an obstetrics visit
		ObstetricsVisitBean ob = new ObstetricsVisitBean();
		ob.setPatientID(1);
		ob.setScheduledDate(new Timestamp(new Date(0).getTime()));
		ob.setCreatedDate(new Timestamp(new Date(0).getTime()));
		ob.setNumWeeks("17");
		ob.setWeight((float)150);
		ob.setBloodPressure("120/50");
		ob.setFHR(60);
		ob.setNumChildren(4);
		ob.setLLP((Boolean)false);
		obstetricsVisitDAO = new ObstetricsVisitDAO(factory);
		obstetricsVisitDAO.addObstetricsVisit(ob);
		addAction = new AddObstetricsAction(factory, 9000000013L, "1");
		ObstetricsBean o = new ObstetricsBean();
		long newOID = addAction.addRecord(o, "1", "09/06/2018", "10/20/2018");
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
	public void testGetAllObstetricsVisits() {
		List<ObstetricsVisitBean> list = null;
		try {
			list = action.getAllObstetricsVisits(1L);
		} catch (ITrustException e) {
			fail();
		}
		if (list == null) {
			fail();
		} else {
			assertTrue(list.size() > 0 );
		}
	}
	
	@Test
	public void testGetSortedObstetricsVisits() {
		List<ObstetricsVisitBean> list = null;
		try {
			list = action.getSortedObstetricsVisits(1L);
		} catch (ITrustException e) {
			fail();
		}
		if (list == null) {
			fail();
		} else {
			assertTrue(list.size() > 0 );
		}
	}
	
	@Test
	public void testGetObstetricsVisit() {
		ObstetricsVisitBean bean1 = null;
		try {
			bean1 = action.getObstetricsVisit(1L);
		} catch (ITrustException e) {
			fail();
		}
		if (bean1 == null)
			fail();
		assertEquals(bean1.getID(), 1L); // and just test that the right record came back

		ObstetricsVisitBean bean2 = null;
		try {
			bean2 = action.getObstetricsVisit(1L);
		} catch (ITrustException e) {
			assertNull(bean2);
		}

		ObstetricsVisitBean bean3 = null;
		try {
			bean3 = action.getObstetricsVisit(1L);
		} catch (ITrustException e) {
			assertNull(bean3);
		}
	}

	
	@Test
	public void testIsObstericsPatient() {
		Boolean result1 = false;
		try {
			result1 = action.isObstericsPatient(1L);
		} catch (ITrustException e) {
			fail();
		}
		assertEquals(true, result1);
	}
	
	@Test
	public void testNeedRHShot(){
		try{
			Boolean result = action.needRHShot(1L);
			assertEquals(false, result);
		} catch (ITrustException e) {
			fail();
		}
	}
}
