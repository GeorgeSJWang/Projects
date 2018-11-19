package edu.ncsu.csc.itrust.model.old.validate;

import edu.ncsu.csc.itrust.action.EditPatientAction;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.DeliveryRecordBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.DeliveryRecordForm;

import org.apache.commons.validator.CreditCardValidator;
import java.util.Date;

/**
 * Validates a  DeliveryRecordForm
 * 
 *  
 * 
 */
public class DeliveryRecordValidator extends BeanValidator<DeliveryRecordForm> {
	/**
	 * The default constructor.
	 */
	public DeliveryRecordValidator() {
	}

	@Override
	public void validate(DeliveryRecordForm newForm) throws FormValidationException{
		ErrorList errorList = new ErrorList();
		
		errorList.addIfNotNull(checkFormat("patientID", newForm.getPatientID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkFormat("childbirthVisitID", newForm.getChildbirthVisitID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkFormat("deliveryDateTime", newForm.getDeliveryDateTime(), ValidationFormat.DATETIMESTAMP, false));
		errorList.addIfNotNull(checkFormat("deliveryMethod", newForm.getDeliveryMethod(), ValidationFormat.PREFERREDCHILDBIRTHMETHOD, false));
		errorList.addIfNotNull(checkFormat("childFirstName", newForm.getChildFirstName(), ValidationFormat.NAME, false));
		errorList.addIfNotNull(checkFormat("childLastName", newForm.getChildLastName(), ValidationFormat.NAME, false));
		
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}