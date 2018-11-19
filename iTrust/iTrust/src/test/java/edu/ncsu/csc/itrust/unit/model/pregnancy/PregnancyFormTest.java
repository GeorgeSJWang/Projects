package edu.ncsu.csc.itrust.unit.model.pregnancy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.model.old.beans.forms.PregnancyForm;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class PregnancyFormTest extends TestCase {
	private Date today;

	@Override
	protected void setUp() throws Exception {
		today = new Date();
	}
	
	public void testForm() throws Exception{
		PregnancyForm p = new PregnancyForm("","","","","","","","");
		p.setPatientID("1");
		p.setWeight_gain("1");
		p.setNum_children("1");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		p.setDate_delivery(dateFormat.format(this.today));
		p.setNum_weeks_pregnant("1");
		p.setNum_hours_labor("1");
		p.setDelivery_type("caesarean section");
		p.setYOC("2018");
		
		assertEquals("1", p.getPatientID());
		assertEquals(dateFormat.format(this.today), p.getDate_delivery());
		assertEquals("2018", p.getYOC());
		assertEquals("1", p.getNum_weeks_pregnant());
		assertEquals("1", p.getNum_hours_labor());
		assertEquals("caesarean section", p.getDelivery_type());
		assertEquals("1", p.getWeight_gain());
		assertEquals("1", p.getNum_children());
	}

}
