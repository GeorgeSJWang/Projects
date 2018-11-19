package edu.ncsu.csc.itrust.unit.dao.obstetrics;

import junit.framework.TestCase;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Timestamp;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsVisitDAO;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ObstetricsVisitDAOTest extends TestCase {
	ObstetricsVisitDAO obstetricsDAO = TestDAOFactory.getTestInstance().getObstetricsVisitDAO();
	TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
	}
		
	public void testAddObstetricsVisit() throws Exception {
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
		
		obstetricsDAO.addObstetricsVisit(ob);
		
		List<ObstetricsVisitBean> obList = obstetricsDAO.getAllObstetricsVisits(1);
		assertEquals(1, obList.size());
		ObstetricsVisitBean ob1 = obList.get(0);
		assertEquals(1, ob1.getPatientID());
		assertEquals(new Timestamp(new Date(0).getTime()), ob1.getScheduledDate());
		assertEquals(new Timestamp(new Date(0).getTime()), ob1.getCreatedDate());
		assertEquals("17", ob1.getNumWeeks());
		assertEquals((float)150, ob1.getWeight());
		assertEquals("120/50", ob1.getBloodPressure());
		assertEquals(60, ob1.getFHR());
		assertEquals(4, ob1.getNumChildren());
		assertEquals((Boolean)false, ob1.getLLP());
		
		ob1 = obstetricsDAO.getObstetricsVisit(ob1.getID());
		assertEquals(1, ob1.getPatientID());
		assertEquals(new Timestamp(new Date(0).getTime()), ob1.getScheduledDate());
		assertEquals(new Timestamp(new Date(0).getTime()), ob1.getCreatedDate());
		assertEquals("17", ob1.getNumWeeks());
		assertEquals((float)150, ob1.getWeight());
		assertEquals("120/50", ob1.getBloodPressure());
		assertEquals(60, ob1.getFHR());
		assertEquals(4, ob1.getNumChildren());
		assertEquals((Boolean)false, ob1.getLLP());
	}
	
	public void testUpdateObstetricsVisit() throws Exception {
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
		
		obstetricsDAO.addObstetricsVisit(ob);
		
		List<ObstetricsVisitBean> obList = obstetricsDAO.getSortedObstetricsVisits(1);
		assertEquals(1, obList.size());
		
		ObstetricsVisitBean ob1 = new ObstetricsVisitBean();
		ob1.setPatientID(1);
		ob1.setID(obList.get(0).getID());
		System.out.println(obList.get(0).getID());
		ob1.setScheduledDate(new Timestamp(new Date(0).getTime()));
		ob1.setCreatedDate(new Timestamp(new Date(0).getTime()));
		ob1.setNumWeeks("19");
		ob1.setWeight((float)150);
		ob1.setBloodPressure("130/70");
		ob1.setFHR(60);
		ob1.setNumChildren(4);
		ob1.setLLP((Boolean)false);
		
		try {
			obstetricsDAO.updateObstetricsVisit(ob1);
		} catch (DBException e) {
			throw e;
		}
			
		List<ObstetricsVisitBean> obList1 = obstetricsDAO.getAllObstetricsVisits(1);
		assertEquals(1, obList1.size());
		ObstetricsVisitBean ob2 = obList1.get(0);
		System.out.println(ob2.getID());
		assertEquals("19", ob2.getNumWeeks());
		assertEquals("130/70", ob2.getBloodPressure());
	}
	
	
}
