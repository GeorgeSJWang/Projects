package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import edu.ncsu.csc.itrust.model.old.validate.DeliveryRecordValidator;
import edu.ncsu.csc.itrust.model.old.validate.ValidationFormat;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.forms.DeliveryRecordForm;

public class DeliveryRecordValidatorTest extends TestCase {
	
	DeliveryRecordValidator validator = new DeliveryRecordValidator();
	
	@Override
	protected void setUp() throws Exception {
		
	}
		
	public void testSubmitDeliveryRecord() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
		DeliveryRecordForm drf = new DeliveryRecordForm("1", "1",
				dateFormat.format(new Timestamp(System.currentTimeMillis())), "caesarean section", "Baby", "Boss");

		Exception ex = null;
		try {
            validator.validate(drf);
        } catch (Exception e) {
            ex = e;
        }
		assertEquals(null, ex);
		
	}
	
	public void testSubmitDeliveryRecordAllErrors() throws Exception {
		DeliveryRecordForm f = new DeliveryRecordForm("invalid", "invalid","invalid", "invalid", "0123456789abcdefghij", "0123456789abcdefghij");

		Exception ex = null;
		try {
            validator.validate(f);
        } catch (FormValidationException e) {
            ex = e;
    		assertEquals("patientID: " + ValidationFormat.MID.getDescription(), e.getErrorList().get(0));
    		assertEquals("childbirthVisitID: " + ValidationFormat.MID.getDescription(), e.getErrorList().get(1));
    		assertEquals("deliveryDateTime: " + ValidationFormat.DATETIMESTAMP.getDescription(), e.getErrorList().get(2));
    		assertEquals("deliveryMethod: " + ValidationFormat.PREFERREDCHILDBIRTHMETHOD.getDescription(), e.getErrorList().get(3));
    		assertEquals("childFirstName: " + ValidationFormat.NAME.getDescription(), e.getErrorList().get(4));
    		assertEquals("childLastName: " + ValidationFormat.NAME.getDescription(), e.getErrorList().get(5));

        }
	}
	
}
