package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.ViewObstetricsAction;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ViewObstetricsActionTest {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ViewObstetricsAction action;
	
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.patient1();
		gen.uc93();
		action = new ViewObstetricsAction(factory, 9000000003L, "1");
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
	public void testGetAllObstetrics() {
		List<ObstetricsBean> list = null;
		try {
			list = action.getAllObstetrics(1L);
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
	public void testUpdateInformation() throws Exception {
		PatientDAO po = new PatientDAO(factory);
		PatientBean pb = po.getPatient(1);
		assertEquals("Random", pb.getFirstName());
		assertEquals("Person", pb.getLastName());
		assertEquals("0", pb.getFatherMID());
		pb.setFatherMID("1");
		assertEquals("1", pb.getFatherMID());
		action.updateInformation(pb);
		PatientBean pb2 = po.getPatient(1);
		assertEquals("Random", pb2.getFirstName());
		assertEquals("Person", pb2.getLastName());
		assertEquals("1", pb2.getFatherMID());
	}
	
	
	@Test
	public void testGetObstetricsRecord() {
		ObstetricsBean bean1 = null;
		try {
			bean1 = action.getObstetricsRecord(1L);
		} catch (ITrustException e) {
			fail();
		}
		if (bean1 == null)
			fail();
		assertEquals(bean1.getID(), 1L); // and just test that the right record came back

		ObstetricsBean bean2 = null;
		try {
			bean2 = action.getObstetricsRecord(1L);
		} catch (ITrustException e) {
			assertNull(bean2);
		}

		ObstetricsBean bean3 = null;
		try {
			bean3 = action.getObstetricsRecord(1L);
		} catch (ITrustException e) {
			assertNull(bean3);
		}
	}

}
