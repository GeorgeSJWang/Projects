package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO;
import edu.ncsu.csc.itrust.model.old.validate.ChildbirthVisitValidator;
import edu.ncsu.csc.itrust.model.old.validate.ObstetricsValidator;
import edu.ncsu.csc.itrust.model.old.validate.ObstetricsVisitValidator;
import edu.ncsu.csc.itrust.model.old.validate.ValidationFormat;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.ChildbirthVisitForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsVisitForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.PregnancyForm;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ChildbirthVisitValidatorTest extends TestCase {
	
	ChildbirthVisitValidator validator = new ChildbirthVisitValidator();
	private Timestamp scheduledDateVal;
	private Timestamp createdDateVal;
	
	@Override
	protected void setUp() throws Exception {
		this.scheduledDateVal = new Timestamp(System.currentTimeMillis());
		this.createdDateVal = new Timestamp(System.currentTimeMillis() - 1000);
	}
		
	public void testSubmitChildbirthVisit() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		ChildbirthVisitForm f = new ChildbirthVisitForm("1","vaginal delivery","(t, 2)",
				dateFormat.format(new Timestamp(System.currentTimeMillis())),"false");

		Exception ex = null;
		try {
            validator.validate(f);
        } catch (Exception e) {
            ex = e;
        }
		assertEquals(null, ex);
	}
	
	public void testSubmitChildbirthVisitAllErrors() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		ChildbirthVisitForm f = new ChildbirthVisitForm("someinvalid","someinvalid","someinvalid",
				"someinvalid","someinvalid");

		Exception ex = null;
		try {
            validator.validate(f);
        } catch (FormValidationException e) {
            ex = e;
    		assertEquals("patientID: " + ValidationFormat.MID.getDescription(), e.getErrorList().get(0));
    		assertEquals("preferredChildbirthMethod: " + ValidationFormat.PREFERREDCHILDBIRTHMETHOD.getDescription(), e.getErrorList().get(1));
    		assertEquals("drugs: " + ValidationFormat.DRUGS.getDescription(), e.getErrorList().get(2));
    		assertEquals("scheduledDate: " + ValidationFormat.DATE.getDescription(), e.getErrorList().get(3));
    		assertEquals("preScheduled: " + ValidationFormat.BOOLEAN.getDescription(), e.getErrorList().get(4));
        }
	}
	
}
