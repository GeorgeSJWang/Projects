package edu.ncsu.csc.itrust.unit.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class PregnancyBeanTest extends TestCase {
	private Date today;

	@Override
	protected void setUp() throws Exception {
		today = new Date();
	}
	
	public void testBean() throws Exception{
		PregnancyBean p = new PregnancyBean();
		p.setID(1);
		p.setPatientID(1);
		p.setDate(this.today);
		p.setYOC(2018);
		p.setNum_weeks_pregnant(1);
		p.setNum_hours_labor(1);
		p.setDelivery_type("caesarean section");
		p.setWeight_gain(1);
		p.setNum_children(1);
		
		assertEquals(1, p.getID());
		assertEquals(1, p.getPatientID());
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		assertEquals(dateFormat.format(this.today), p.getDate());
		assertEquals(this.today, p.getDateAsDate());
		assertEquals(2018, p.getYOC());
		assertEquals(1, p.getNum_weeks_pregnant());
		assertEquals(1, p.getNum_hours_labor());
		assertEquals("caesarean section", p.getDelivery_type());
		assertEquals((float) 1, p.getWeight_gain());
		assertEquals(1, p.getNum_children());
	}

	public void testEquals() throws Exception {
		PregnancyBean p = new PregnancyBean();
		p.setID(1);
		assertEquals(0, p.equals(p));
		PregnancyBean q = new PregnancyBean();
		q.setID(2);
		assertThat(0, not(p.equals(q)));	
	}
	
	public void testCompareTo() throws Exception {
		PregnancyBean p = new PregnancyBean();
		p.setID(1);
		assertEquals(0, p.compareTo(p));
		PregnancyBean q = new PregnancyBean();
		q.setID(2);
		assertEquals(1, p.compareTo(q));	
	}

}
