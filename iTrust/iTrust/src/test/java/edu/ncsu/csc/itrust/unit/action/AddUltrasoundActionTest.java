package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import edu.ncsu.csc.itrust.action.AddUltrasoundAction;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import edu.ncsu.csc.itrust.action.ViewUltrasoundAction;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.FetusBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.UltrasoundDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class AddUltrasoundActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private AddUltrasoundAction action;

	/**
	 * Sets up defaults
	 */
	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.uc94();
		action = new AddUltrasoundAction(factory, 9000000012L, "1");
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

	/**
	 * Tests adding a new ultrasound record
	 * 
	 * @throws Exception
	 */
	public void testAddRecord() throws Exception {

		UltrasoundBean o = new UltrasoundBean();
		InputStream iS = new ByteArrayInputStream("ultrasound".getBytes());;
		long newOID = action.addRecord(o, "1", iS, "image/png", "10/20/2018 06:20");
		assertEquals(action.getUltrasoundRecord(newOID).getRecordID(), newOID);
	}
	
	/**
	 * Tests adding a new fetus record
	 * 
	 * @throws Exception
	 */
	public void testAddFetus() throws Exception {

		FetusBean p = new FetusBean();
		action.addFetus(p, "1", "2", "06/25/2015 06:20", "4.0", "5.0", "6.0", "7.0", "8.0", "9.0", "10.0", "11.0");
		
		UltrasoundDAO ultrasoundDAO = TestDAOFactory.getTestInstance().getUltrasoundDAO();
		List<FetusBean> fList = ultrasoundDAO.getAllFetus(1);
		assertEquals(1, fList.size());
		assertEquals("2015-06-25", fList.get(0).getCreated_onAsDate().toString());
	}

}
