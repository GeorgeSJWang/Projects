package edu.ncsu.csc.itrust.unit.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;

public class ObstetricsBeanTest extends TestCase {
	private Date today;

	@Override
	protected void setUp() throws Exception {
		today = new Date();
	}

	public void testBean() {

		ObstetricsBean o = new ObstetricsBean();
		List<PregnancyBean> pregnancies1 = new ArrayList<PregnancyBean>();
		pregnancies1.add(new PregnancyBean());
		o.setPregnancies(pregnancies1);
		List<PregnancyBean> pregnancies2 = o.getPregnancies();
		assertEquals(pregnancies1, pregnancies2);
		o.setID(1);
		o.setPatientID(1);	
		o.setCreated_on(this.today);
		o.setLMP(this.today);
		o.setNumber_of_weeks_pregnant(1);
		
		assertEquals(1, o.getID());
		assertEquals(1, o.getPatientID());
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		assertEquals(dateFormat.format(this.today), o.getCreated_on());
		assertEquals(this.today, o.getCreated_onAsDate());
		assertEquals(dateFormat.format(this.today), o.getLMP());
		assertEquals(this.today, o.getLMPAsDate());
		assertEquals(1, o.getNumber_of_weeks_pregnant());
		Calendar c = Calendar.getInstance();
		c.setTime(this.today);
		c.add(Calendar.DATE, 280);
		assertEquals(dateFormat.format(c.getTime()), o.getEDD());
	}
	
	public void testCompareTo() throws Exception {
		ObstetricsBean o = new ObstetricsBean();
		o.setID(1);
		ObstetricsBean p = new ObstetricsBean();
		p.setID(2);
		assertEquals(0, o.compareTo(o));
		assertEquals(1, o.compareTo(p));
	}

}
