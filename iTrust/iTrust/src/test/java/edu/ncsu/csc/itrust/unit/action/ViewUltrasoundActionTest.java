package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddObstetricsAction;
import edu.ncsu.csc.itrust.action.AddUltrasoundAction;
import edu.ncsu.csc.itrust.action.ViewUltrasoundAction;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ViewUltrasoundActionTest {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ViewUltrasoundAction action;
	private AddUltrasoundAction addAction;
	private AddObstetricsAction addObAction;
	
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.uc94();
		action = new ViewUltrasoundAction(factory, 9000000003L, "1");
		addAction = new AddUltrasoundAction(factory, 9000000003L, "1");
		addObAction = new AddObstetricsAction(factory, 9000000003L, "1");
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
	public void testUpdateInformation(){
		PatientBean p = new PatientBean();
		p.setFirstName("Jiminy");
		p.setLastName("Cricket");
		p.setEmail("make.awish@gmail.com");
		p.setMID(1);
		
		try {
			action.updateInformation(p);
			PatientBean p2 = action.getPatient();
			assertEquals("Jiminy", p2.getFirstName());
		} catch (Exception e){
			fail();
		}
	}
	
	@Test
	public void testGetAllUltrasounds(){
		UltrasoundBean o = new UltrasoundBean();
		InputStream iS = new ByteArrayInputStream("ultrasound".getBytes());;
		try {
			long newOID = addAction.addRecord(o, "1", iS, "image/png", "10/20/2018 06:20");
			List<UltrasoundBean> l = action.getAllUltrasounds(1);
			assertEquals(1, l.size());
			assertEquals("image/png", l.get(0).getImageType());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetUltrasoundRecord(){
		UltrasoundBean o = new UltrasoundBean();
		InputStream iS = new ByteArrayInputStream("ultrasound".getBytes());;
		try {
			long newOID = addAction.addRecord(o, "1", iS, "image/png", "10/20/2018 06:20");
			assertEquals(action.getUltrasoundRecord(newOID).getRecordID(), newOID);
		} catch (Exception e) {
			fail();
		}
	}
	
	//Find patient that is obstetrics to improve code coverage
	@Test
	public void testIsObstetricsPatient(){
		try{
			ObstetricsBean o = new ObstetricsBean();
			addObAction.addRecord(o, "1", "09/06/2018", "10/20/2018");
			Boolean result = action.isObstericsPatient(1L);
			assertEquals(true, result);
		} catch (Exception e) {
			fail();
		}
	}
	
}
