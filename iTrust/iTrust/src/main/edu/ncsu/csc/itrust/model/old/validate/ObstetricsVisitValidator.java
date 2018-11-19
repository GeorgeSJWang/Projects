package edu.ncsu.csc.itrust.model.old.validate;

import edu.ncsu.csc.itrust.action.EditPatientAction;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsVisitForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.PregnancyForm;

import org.apache.commons.validator.CreditCardValidator;
import java.util.Date;

/**
 * Validates a ObstetricsForm
 * 
 *  
 * 
 */
public class ObstetricsVisitValidator extends BeanValidator<ObstetricsVisitForm> {
	/**
	 * The default constructor.
	 */
	public ObstetricsVisitValidator() {
	}

	@Override
	public void validate(ObstetricsVisitForm newForm) throws FormValidationException{
		ErrorList errorList = new ErrorList();
		
		errorList.addIfNotNull(checkFormat("patientID", newForm.getPatientID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkFormat("scheduledDate", newForm.getScheduledDate(), ValidationFormat.DATETIMESTAMP, false));
		errorList.addIfNotNull(checkFormat("createdDate", newForm.getCreatedDate(), ValidationFormat.DATETIMESTAMP, false));
		errorList.addIfNotNull(checkFormat("weight", newForm.getWeight(), ValidationFormat.WEIGHT_OV, false));
		errorList.addIfNotNull(checkFormat("bloodPressure", newForm.getBloodPressure(), ValidationFormat.BLOOD_PRESSURE_OV, false));
		errorList.addIfNotNull(checkFormat("FHR", newForm.getFHR(), ValidationFormat.FHR, false));
		errorList.addIfNotNull(checkFormat("numChildren", newForm.getNumChildren(), ValidationFormat.CHILD_NUM, false));

		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}