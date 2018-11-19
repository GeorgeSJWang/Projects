package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO;
import edu.ncsu.csc.itrust.model.old.validate.ObstetricsValidator;
import edu.ncsu.csc.itrust.model.old.validate.ObstetricsVisitValidator;
import edu.ncsu.csc.itrust.model.old.validate.ValidationFormat;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsVisitForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.PregnancyForm;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ObstetricsVisitValidatorTest extends TestCase {
	
	ObstetricsVisitValidator validator = new ObstetricsVisitValidator();
	private Timestamp scheduledDateVal;
	private Timestamp createdDateVal;
	
	@Override
	protected void setUp() throws Exception {
		this.scheduledDateVal = new Timestamp(System.currentTimeMillis());
		this.createdDateVal = new Timestamp(System.currentTimeMillis() - 1000);
	}
		
	public void testSubmitObstetricsVisit() throws Exception {
		ObstetricsVisitForm o = new ObstetricsVisitForm("","","","","","","","");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		o.setPatientID("1");
		o.setScheduledDate(dateFormat.format(this.scheduledDateVal));
		o.setCreatedDate(dateFormat.format(this.createdDateVal));
		o.setWeight("5.4");
		o.setBloodPressure("120/80");
		o.setFHR("80");
		o.setNumChildren("3");
		
		Exception ex = null;
		try {
            validator.validate(o);
        } catch (Exception e) {
            ex = e;
        }
		assertEquals(null, ex);
	}
	
	public void testSubmitObstetricsVisitAllErrors() throws Exception {
		ObstetricsVisitForm o = new ObstetricsVisitForm("someinvalid","notdate","alsonotdate",
				"notweight","notbp","notfhr","notchild", "true");
		Exception ex = null;
		try {
            validator.validate(o);
        } catch (FormValidationException e) {
            ex = e;
            assertEquals("patientID: " + ValidationFormat.MID.getDescription(), e.getErrorList().get(0));
    		assertEquals("scheduledDate: " + ValidationFormat.DATETIMESTAMP.getDescription(), e.getErrorList().get(1));
    		assertEquals("createdDate: " + ValidationFormat.DATETIMESTAMP.getDescription(), e.getErrorList().get(2));
    		assertEquals("weight: " + ValidationFormat.WEIGHT_OV.getDescription(), e.getErrorList().get(3));
    		assertEquals("bloodPressure: " + ValidationFormat.BLOOD_PRESSURE_OV.getDescription(), e.getErrorList().get(4));
    		assertEquals("FHR: " + ValidationFormat.FHR.getDescription(), e.getErrorList().get(5));
    		assertEquals("numChildren: " + ValidationFormat.CHILD_NUM.getDescription(), e.getErrorList().get(6));
    		assertEquals("number of errors", 7, e.getErrorList().size());
        }
	}
	
}
