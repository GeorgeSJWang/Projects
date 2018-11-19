package edu.ncsu.csc.itrust.unit.model.ultrasound;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.model.old.beans.forms.UltrasoundForm;

public class UltrasoundFormTest extends TestCase {
	private Date today;

	@Override
	protected void setUp() throws Exception {
		//nothing needed
	}

	public void testForm() {
		UltrasoundForm u = new UltrasoundForm("","","");
		u.setCreated_on("0");
		u.setPatientID("1");
		u.setImageType("2");
		
		assertEquals("0", u.getCreated_on());
		assertEquals("1", u.getPatientID());
		assertEquals("2", u.getImageType());
	}
}
