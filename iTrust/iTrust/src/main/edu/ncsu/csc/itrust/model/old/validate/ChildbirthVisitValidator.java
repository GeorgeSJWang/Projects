package edu.ncsu.csc.itrust.model.old.validate;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.forms.ChildbirthVisitForm;

import java.util.Date;

/**
 * Validates a ObstetricsForm
 * 
 *  
 * 
 */
public class ChildbirthVisitValidator extends BeanValidator<ChildbirthVisitForm> {
	/**
	 * The default constructor.
	 */
	public ChildbirthVisitValidator() {
	}

	@Override
	public void validate(ChildbirthVisitForm newForm) throws FormValidationException{
		ErrorList errorList = new ErrorList();
		
		errorList.addIfNotNull(checkFormat("patientID", newForm.getPatientID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkFormat("preferredChildbirthMethod", newForm.getPreferredChildbirthMethod(), ValidationFormat.PREFERREDCHILDBIRTHMETHOD, false));
		errorList.addIfNotNull(checkFormat("drugs", newForm.getDrugs(), ValidationFormat.DRUGS, false));
		errorList.addIfNotNull(checkFormat("scheduledDate", newForm.getScheduledDate(), ValidationFormat.DATE, false));
		errorList.addIfNotNull(checkFormat("preScheduled", newForm.getPreScheduled(), ValidationFormat.BOOLEAN, false));
		
		
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}