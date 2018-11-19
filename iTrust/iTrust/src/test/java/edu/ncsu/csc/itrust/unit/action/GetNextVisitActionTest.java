package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.sun.jna.platform.win32.Sspi.TimeStamp;

import edu.ncsu.csc.itrust.action.GetNextVisitAction;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class GetNextVisitActionTest extends TestCase {
	
	private GetNextVisitAction action;
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	
	/**
	 * Sets up defaults
	 */
	@Override
	protected void setUp() throws Exception {
		action = new GetNextVisitAction(factory, 9000000012L);
	}
	
	@Test
	public void testGetNextDateString() {
		ObstetricsVisitBean o = new ObstetricsVisitBean();
		o.setNumWeeks("2");
		o.setScheduledDate(new Timestamp(0));
		
		assertEquals("01/28/1970 18:00", action.GetNextDateString(o));
	}
	
	@Test
	public void testGetNextTime() {
		List<Timestamp> l = new ArrayList<>();
		l.add(new Timestamp(0));
		l.add(new Timestamp(1));
		Timestamp time = action.GetNextTime(l, new Timestamp(0));
		assertEquals("1969-12-31 09:00:00.0", time.toString());
	}
	
	@Test
	public void testTimestampToRFC3399() {
		String timeStr = action.TimestampToRFC3399(new Timestamp(0));
		assertEquals("1969-12-31T18:00:00-06:00", timeStr);
	}
	
	@Test
	public void testRFC3399ToTimestamp() {
		Timestamp time = action.RFC3399ToTimestamp("1969-12-31T18:00:00-06:00");
		assertEquals("1969-12-31 18:00:00.0", time.toString());
	}
	
	@Test
	public void testGetSchedule() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse("23/09/2007");
			long time = date.getTime();
			Timestamp tMin = new Timestamp(time);
			
			dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			date = dateFormat.parse("30/11/2007");
			time = date.getTime();
			Timestamp tMax = new Timestamp(time);
			
			List<Timestamp> l = action.getSchedule(tMin, tMax, 1);
			assertEquals(0, l.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testGetGoogleCalendarLink() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse("23/09/2007");
			long time = date.getTime();
			Timestamp tMin = new Timestamp(time);
			
			String link = action.getGoogleCalendarLink(tMin);
			assertEquals("https://calendar.google.com/calendar/r/eventedit?&dates=20070923T050000Z%2F20070923T053000Z"
					+ "&text=iTrust Office Visit&location=iTrust Hospital&detail=iTrust Office Visit", link);
		} catch (Exception e){
			fail();
		}
	}
	
	@Test
	public void testIsBusinessDay() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse("06/11/2018");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			assertEquals(true, action.isBusinessDay(cal));
			
			date = dateFormat.parse("10/11/2018");
			cal = Calendar.getInstance();
			cal.setTime(date);
			assertEquals(false, action.isBusinessDay(cal));
			
			date = dateFormat.parse("11/11/2018");
			cal = Calendar.getInstance();
			cal.setTime(date);
			assertEquals(false, action.isBusinessDay(cal));
			
			date = dateFormat.parse("01/01/2018");
			cal = Calendar.getInstance();
			cal.setTime(date);
			assertEquals(false, action.isBusinessDay(cal));
			
			date = dateFormat.parse("25/12/2018");
			cal = Calendar.getInstance();
			cal.setTime(date);
			assertEquals(false, action.isBusinessDay(cal));
			
			date = dateFormat.parse("24/12/2018");
			cal = Calendar.getInstance();
			cal.setTime(date);
			assertEquals(false, action.isBusinessDay(cal));
			
			date = dateFormat.parse("22/11/2018");
			cal = Calendar.getInstance();
			cal.setTime(date);
			assertEquals(false, action.isBusinessDay(cal));
			
			date = dateFormat.parse("04/07/2018");
			cal = Calendar.getInstance();
			cal.setTime(date);
			assertEquals(false, action.isBusinessDay(cal));
			
			date = dateFormat.parse("27/05/2019");
			cal = Calendar.getInstance();
			cal.setTime(date);
			assertEquals(false, action.isBusinessDay(cal));
			
			date = dateFormat.parse("02/09/2019");
			cal = Calendar.getInstance();
			cal.setTime(date);
			assertEquals(false, action.isBusinessDay(cal));
			
			date = dateFormat.parse("18/02/2019");
			cal = Calendar.getInstance();
			cal.setTime(date);
			assertEquals(false, action.isBusinessDay(cal));
			
			date = dateFormat.parse("14/10/2019");
			cal = Calendar.getInstance();
			cal.setTime(date);
			assertEquals(false, action.isBusinessDay(cal));
		} catch (Exception e) {
			fail();
		}
	}
}
