package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.ncsu.csc.itrust.model.old.beans.FetusBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.FetusForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.UltrasoundForm;
import edu.ncsu.csc.itrust.model.old.validate.FetusValidator;
import edu.ncsu.csc.itrust.model.old.validate.UltrasoundValidator;
import edu.ncsu.csc.itrust.model.old.validate.ValidationFormat;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class UltrasoundValidatorTest extends TestCase {
	
	UltrasoundValidator validator = new UltrasoundValidator();

	@Override
	protected void setUp() throws Exception {
	}
		
	public void testSubmitUltrasound() throws Exception {
		UltrasoundForm u = new UltrasoundForm("","","");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

		u.setPatientID("1");
		u.setCreated_on(dateFormat.format(new Date()));
		u.setImageType("image/png");
		Exception ex = null;
		try {
            validator.validate(u);
        } catch (Exception e) {
            ex = e;
        }
		assertEquals(null, ex);
	}
	
	public void testSubmitUltrasoundAllErrors() throws Exception {
		UltrasoundForm u = new UltrasoundForm("someinvalid","notdate","image/png");
		Exception ex = null;
		try {
            validator.validate(u);
        } catch (FormValidationException e) {
            ex = e;
            assertEquals("PatientID: " + ValidationFormat.MID.getDescription(), e.getErrorList().get(0));
    		assertEquals("created_on: " + ValidationFormat.DATETIMESTAMP.getDescription(), e.getErrorList().get(1));
    		assertEquals("number of errors", 2, e.getErrorList().size());
        }
	}
	
}
