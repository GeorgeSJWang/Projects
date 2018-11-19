package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import edu.ncsu.csc.itrust.action.AddObstetricsAction;
import edu.ncsu.csc.itrust.action.AddPatientAction;
import edu.ncsu.csc.itrust.action.ViewObstetricsAction;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class AddObstetricsActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private AddObstetricsAction action;

	/**
	 * Sets up defaults
	 */
	@Override
	protected void setUp() throws Exception {
		gen.transactionLog();
		gen.uc63();
		action = new AddObstetricsAction(factory, 9000000012L, "1");
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

	/**
	 * Tests adding a new obstetrics record
	 * 
	 * @throws Exception
	 */
	public void testAddRecord() throws Exception {

		ObstetricsBean o = new ObstetricsBean();
		long newOID = action.addRecord(o, "1", "09/06/2018", "10/20/2018");
		assertEquals(action.getObstetricsRecord(newOID).getID(), newOID);
	}
	
	/**
	 * Tests adding a new pregnancy record
	 * 
	 * @throws Exception
	 */
	public void testAddPregnancy() throws Exception {

		PregnancyBean p = new PregnancyBean();
		long newPID = action.addPregnancy(p, "1", "2014", "40", "20", "15.0", "caesarean section", "1", "06/25/2015");
		
		List<ObstetricsBean> Oblist = new ViewObstetricsAction(factory, 9000000003L, "1").getAllObstetrics(1L);
		Iterator<ObstetricsBean> Obrecord = Oblist.iterator();
		ObstetricsBean o;
		List<PregnancyBean> Plist;
		Iterator<PregnancyBean> Precord;
		while(Obrecord.hasNext()){
			o = Obrecord.next();
			Plist = o.getPregnancies();
			Precord = Plist.iterator();
			while(Precord.hasNext()){
				p = Precord.next();
				if(p.getID() == newPID){
					return;
				}
			}
		}
		fail();
	}

}
