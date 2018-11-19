package edu.ncsu.csc.itrust.model.old.validate;

import edu.ncsu.csc.itrust.action.EditPatientAction;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.PregnancyForm;

import org.apache.commons.validator.CreditCardValidator;
import java.util.Date;

/**
 * Validates a ObstetricsForm
 * 
 *  
 * 
 */
public class ObstetricsValidator extends BeanValidator<ObstetricsForm> {
	/**
	 * The default constructor.
	 */
	public ObstetricsValidator() {
	}

	@Override
	public void validate(ObstetricsForm newForm) throws FormValidationException{
		ErrorList errorList = new ErrorList();
		
		errorList.addIfNotNull(checkFormat("PatientID", newForm.getPatientID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkFormat("LMP", newForm.getLMP(), ValidationFormat.DATE, false));
		errorList.addIfNotNull(checkFormat("created_on", newForm.getCreated_on(), ValidationFormat.DATE, false));

		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
	
	public void validatePregnancy(PregnancyForm newForm) throws FormValidationException{
		ErrorList errorList = new ErrorList();
		
		errorList.addIfNotNull(checkFormat("PatientID", newForm.getPatientID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkFormat("Date_delivery", newForm.getDate_delivery(), ValidationFormat.DATE, false));
		errorList.addIfNotNull(checkFormat("num_weeks_pregnant", newForm.getNum_weeks_pregnant(), ValidationFormat.WEEKS_ONLY_PREGNANT, false));
		errorList.addIfNotNull(checkFormat("num_hours_labor", newForm.getNum_hours_labor(), ValidationFormat.HOURS_LABOR, false));
		errorList.addIfNotNull(checkFormat("YOC", newForm.getYOC(), ValidationFormat.YEAR, false));
		errorList.addIfNotNull(checkFormat("weight_gain", newForm.getWeight_gain(), ValidationFormat.WEIGHT, false));
		errorList.addIfNotNull(checkFormat("num_children", newForm.getNum_children(), ValidationFormat.CHILD_NUM, false));

		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}
