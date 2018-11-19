package edu.ncsu.csc.itrust.unit.model.fetus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.model.old.beans.forms.FetusForm;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;

public class FetusFormTest extends TestCase {
	private Date today;

	@Override
	protected void setUp() throws Exception {
		//nothing needed
	}

	public void testForm() {
		FetusForm f = new FetusForm("","","","","","","","","","","");
		f.setAC("0");
		f.setBPD("1");
		f.setCreated_on("2");
		f.setCRL("3");
		f.setEFW("4");
		f.setFL("5");
		f.setHC("6");
		f.setHL("7");
		f.setOFD("8");
		f.setPatientID("9");
		f.setUltrasoundID("10");
		
		assertEquals("0", f.getAC());
		assertEquals("1", f.getBPD());
		assertEquals("2", f.getCreated_on());
		assertEquals("3", f.getCRL());
		assertEquals("4", f.getEFW());
		assertEquals("5", f.getFL());
		assertEquals("6", f.getHC());
		assertEquals("7", f.getHL());
		assertEquals("8", f.getOFD());
		assertEquals("9", f.getPatientID());
		assertEquals("10", f.getUltrasoundID());
	}
}
