package edu.ncsu.csc.itrust.unit.validate.bean;

import junit.framework.TestCase;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO;
import edu.ncsu.csc.itrust.model.old.validate.ObstetricsValidator;
import edu.ncsu.csc.itrust.model.old.validate.ValidationFormat;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.PregnancyForm;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ObstetricsValidatorTest extends TestCase {
	
	ObstetricsValidator validator = new ObstetricsValidator();
	private Date today;

	@Override
	protected void setUp() throws Exception {
		today = new Date();
	}
		
	public void testSubmitObstetric() throws Exception {
		ObstetricsForm o = new ObstetricsForm("","","");
		o.setPatientID("1");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		o.setLMP(dateFormat.format(this.today));
		o.setCreated_on(dateFormat.format(this.today));
		
		Exception ex = null;
		try {
            validator.validate(o);
        } catch (Exception e) {
            ex = e;
        }
		assertEquals(null, ex);
	}
	
	public void testSubmitObstetricsAllErrors() throws Exception {
		ObstetricsForm o = new ObstetricsForm("someinvalid","notdate","alsonotdate");
		Exception ex = null;
		try {
            validator.validate(o);
        } catch (FormValidationException e) {
            ex = e;
            assertEquals("PatientID: " + ValidationFormat.MID.getDescription(), e.getErrorList().get(0));
    		assertEquals("LMP: " + ValidationFormat.DATE.getDescription(), e.getErrorList().get(1));
    		assertEquals("created_on: " + ValidationFormat.DATE.getDescription(), e.getErrorList().get(2));
    		assertEquals("number of errors", 3, e.getErrorList().size());
        }
	}
	
	public void testSubmitPregnancy() throws Exception {
		PregnancyForm p = new PregnancyForm("","","","","","","","");
		p.setPatientID("1");
		p.setWeight_gain("10.5");
		p.setNum_children("1");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		p.setDate_delivery(dateFormat.format(this.today));
		p.setNum_weeks_pregnant("1");
		p.setNum_hours_labor("1");
		p.setDelivery_type("caesarean section");
		p.setYOC("2018");
		
		Exception ex = null;
		try {
            validator.validatePregnancy(p);
        } catch (Exception e) {
            ex = e;
        }
		assertEquals(null, ex);
	}
	
	public void testSubmitPregnancyAllErrors() throws Exception {
		PregnancyForm p = new PregnancyForm("invalidid", "invalidyoc", "invalidnum",
				"invalidnum", "invalidnum", "some", "invalidnum", "invaliddate");
		Exception ex = null;
		try {
            validator.validatePregnancy(p);
        } catch (FormValidationException e) {
            ex = e;
            assertEquals("PatientID: " + ValidationFormat.MID.getDescription(), e.getErrorList().get(0));
    		assertEquals("Date_delivery: " + ValidationFormat.DATE.getDescription(), e.getErrorList().get(1));
    		assertEquals("num_weeks_pregnant: " + ValidationFormat.WEEKS_ONLY_PREGNANT.getDescription(), e.getErrorList().get(2));
    		assertEquals("num_hours_labor: " + ValidationFormat.HOURS_LABOR.getDescription(), e.getErrorList().get(3));
    		assertEquals("YOC: " + ValidationFormat.YEAR.getDescription(), e.getErrorList().get(4));
    		assertEquals("weight_gain: " + ValidationFormat.WEIGHT.getDescription(), e.getErrorList().get(5));
    		assertEquals("num_children: " + ValidationFormat.CHILD_NUM.getDescription(), e.getErrorList().get(6));
    		assertEquals("number of errors", 7, e.getErrorList().size());
        }
	}
	
}
