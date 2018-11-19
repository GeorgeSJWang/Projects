package edu.ncsu.csc.itrust.unit.model.childbirth;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.DateUtil;
import edu.ncsu.csc.itrust.model.old.beans.forms.ChildbirthVisitForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsVisitForm;
import edu.ncsu.csc.itrust.model.old.beans.DeliveryRecordBean;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;

public class ChildbirthVisitFormTest extends TestCase {
	private Date today;

	@Override
	protected void setUp() throws Exception {
		today = new Date();
	}

	public void testForm() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		ChildbirthVisitForm f = new ChildbirthVisitForm("","","","","");
		String visitID = "1";
		String patientID = "1";
		String preferredChildbirthMethod = "vaginal delivery";
		String drugs = "Pitocin";
		String scheduledDate = dateFormat.format(new Timestamp(System.currentTimeMillis()));
		String preScheduled = "false";
		
		f.setVisitID(visitID);
		f.setPatientID(patientID);	
		f.setPreferredChildbirthMethod(preferredChildbirthMethod);
		f.setDrugs(drugs);
		f.setScheduledDate(scheduledDate);
		f.setPreScheduled(preScheduled);
		
		assertEquals(visitID, f.getVisitID());
		assertEquals(patientID, f.getPatientID());
		assertEquals(preferredChildbirthMethod, f.getPreferredChildbirthMethod());
		assertEquals(drugs, f.getDrugs());
		assertEquals(scheduledDate, f.getScheduledDate());
		assertEquals(preScheduled, f.getPreScheduled());
	}
}
