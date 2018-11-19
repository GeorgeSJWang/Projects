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
import edu.ncsu.csc.itrust.model.old.validate.FetusValidator;
import edu.ncsu.csc.itrust.model.old.validate.UltrasoundValidator;
import edu.ncsu.csc.itrust.model.old.validate.ValidationFormat;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class FetusValidatorTest extends TestCase {
	
	FetusValidator validator = new FetusValidator();
	
	@Override
	protected void setUp() throws Exception {
	}
		
	public void testSubmitFetus() throws Exception {
		FetusForm f = new FetusForm("","","","","","","","","","","");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

		f.setPatientID("1");
		f.setUltrasoundID("2");
		f.setCreated_on(dateFormat.format(new Date()));
		f.setCRL("1.0");
		f.setBPD("2.0");
		f.setHC("3.0");
		f.setFL("4.0");
		f.setOFD("5.0");
		f.setAC("6.0");
		f.setHL("7.0");
		f.setEFW("8.0");
		
		Exception ex = null;
		try {
            validator.validate(f);
        } catch (Exception e) {
            ex = e;
        }
		assertEquals(null, ex);
	}
	
	public void testSubmitFetusAllErrors() throws Exception {
		FetusForm f = new FetusForm("someinvalid","notdate","alsonotdate",
				"notweight","notbp","notfhr","notchild","invalid","invalid","invalid","invalid");
		Exception ex = null;
		try {
            validator.validate(f);
        } catch (FormValidationException e) {
            ex = e;
            assertEquals("PatientID: " + ValidationFormat.MID.getDescription(), e.getErrorList().get(0));
    		assertEquals("UltrasoundID: " + ValidationFormat.MID.getDescription(), e.getErrorList().get(1));
    		assertEquals("created_on: " + ValidationFormat.DATETIMESTAMP.getDescription(), e.getErrorList().get(2));
    		assertEquals("CRL: " + ValidationFormat.HEIGHT.getDescription(), e.getErrorList().get(3));
    		assertEquals("BPD: " + ValidationFormat.HEIGHT.getDescription(), e.getErrorList().get(4));
    		assertEquals("HC: " + ValidationFormat.HEIGHT.getDescription(), e.getErrorList().get(5));
    		assertEquals("FL: " + ValidationFormat.HEIGHT.getDescription(), e.getErrorList().get(6));
    		assertEquals("OFD: " + ValidationFormat.HEIGHT.getDescription(), e.getErrorList().get(7));
    		assertEquals("AC: " + ValidationFormat.HEIGHT.getDescription(), e.getErrorList().get(8));
    		assertEquals("HL: " + ValidationFormat.HEIGHT.getDescription(), e.getErrorList().get(9));
    		assertEquals("EFW: " + ValidationFormat.HEIGHT.getDescription(), e.getErrorList().get(10));
    		assertEquals("number of errors", 11, e.getErrorList().size());
        }
	}
	
}
