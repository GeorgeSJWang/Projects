package edu.ncsu.csc.itrust.unit.dao.obstetrics;

import junit.framework.TestCase;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ObstetricsDAOTest extends TestCase {
	ObstetricsDAO obstetricsDAO = TestDAOFactory.getTestInstance().getObstetricsDAO();
	TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
	}
		
	public void testAddObstetric() throws Exception {
		long pid = obstetricsDAO.addEmptyPatient();
		ObstetricsBean ob = new ObstetricsBean();
		ob.setPatientID((int)pid);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse("2018-10-31");
		ob.setCreated_on(date);
		ob.setLMP(date);
		ob.setNumber_of_weeks_pregnant(5);
		List<PregnancyBean> pregList = new ArrayList<>();
		ob.setPregnancies(pregList);
		obstetricsDAO.addRecord(ob);
		List<ObstetricsBean> obList = obstetricsDAO.getAllObstetrics(pid);
		assertEquals(1, obList.size());
		assertEquals(date, obList.get(0).getCreated_onAsDate());
		assertEquals(5, obList.get(0).getNumber_of_weeks_pregnant());
		
		ObstetricsBean obBean = obstetricsDAO.getObstetrics(obList.get(0).getID());
		assertEquals(date, obBean.getCreated_onAsDate());
		assertEquals(5, obBean.getNumber_of_weeks_pregnant());
	}
	
	public void testAddPregnancy() throws Exception {
		long pid = obstetricsDAO.addEmptyPatient();
		PregnancyBean pb = new PregnancyBean();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse("2018-10-31");
		pb.setDate(date);
		pb.setDelivery_type("vaginal delivery");
		pb.setNum_hours_labor(10);
		pb.setNum_weeks_pregnant(40);
		pb.setPatientID((int)pid);
		pb.setYOC(1997);
		obstetricsDAO.addPregnancy(pb);
		List<PregnancyBean> pbList = obstetricsDAO.getAllPregnancy(pid);
		assertEquals(1, pbList.size());
		assertEquals(date, pbList.get(0).getDateAsDate());
		assertEquals(10, pbList.get(0).getNum_hours_labor());
	}
}
