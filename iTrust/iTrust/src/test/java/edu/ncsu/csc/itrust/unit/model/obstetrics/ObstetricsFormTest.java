package edu.ncsu.csc.itrust.unit.model.obstetrics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsForm;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;

public class ObstetricsFormTest extends TestCase {
	private Date today;

	@Override
	protected void setUp() throws Exception {
		today = new Date();
	}

	public void testForm() {
		ObstetricsForm o = new ObstetricsForm("","","");
		o.setPatientID("1");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		o.setLMP(dateFormat.format(this.today));
		o.setCreated_on(dateFormat.format(this.today));
		
		assertEquals("1", o.getPatientID());
		assertEquals(dateFormat.format(this.today), o.getCreated_on());
		assertEquals(dateFormat.format(this.today), o.getLMP());
	}
}
