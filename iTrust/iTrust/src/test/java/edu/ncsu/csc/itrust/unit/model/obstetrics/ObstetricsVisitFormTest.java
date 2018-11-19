package edu.ncsu.csc.itrust.unit.model.obstetrics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsVisitForm;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;

public class ObstetricsVisitFormTest extends TestCase {
	private Date today;

	@Override
	protected void setUp() throws Exception {
		today = new Date();
	}

	public void testForm() {
		ObstetricsVisitForm o = new ObstetricsVisitForm("","","","","","","","");
		o.setPatientID("1");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		o.setScheduledDate(dateFormat.format(this.today));
		o.setCreatedDate(dateFormat.format(this.today));
		o.setWeight("150");
		o.setBloodPressure("120/50");
		o.setFHR("100");
		o.setNumChildren("4");
		o.setLLP("false");
		
		assertEquals("1", o.getPatientID());
		assertEquals(dateFormat.format(this.today), o.getScheduledDate());
		assertEquals(dateFormat.format(this.today), o.getCreatedDate());
		assertEquals("150", o.getWeight());
		assertEquals("120/50", o.getBloodPressure());
		assertEquals("100", o.getFHR());
		assertEquals("4", o.getNumChildren());
		assertEquals("false", o.getLLP());
	}
}
