package edu.ncsu.csc.itrust.model.old.validate;

import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.old.beans.forms.FetusForm;

/**
 * Validates a fetus form
 */

public class FetusValidator extends BeanValidator<FetusForm> {
	/**
	 * The default constructor.
	 */
	public FetusValidator() {
	}

	
	/* FORMAT is dummy version for now */
	@Override
	public void validate(FetusForm newForm) throws FormValidationException{
		ErrorList errorList = new ErrorList();
		
		errorList.addIfNotNull(checkFormat("PatientID", newForm.getPatientID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkFormat("UltrasoundID", newForm.getUltrasoundID(), ValidationFormat.MID, false));
		errorList.addIfNotNull(checkFormat("created_on", newForm.getCreated_on(), ValidationFormat.DATETIMESTAMP, false));
		errorList.addIfNotNull(checkFormat("CRL", newForm.getCRL(), ValidationFormat.HEIGHT, false));
		errorList.addIfNotNull(checkFormat("BPD", newForm.getBPD(), ValidationFormat.HEIGHT, false));
		errorList.addIfNotNull(checkFormat("HC", newForm.getHC(), ValidationFormat.HEIGHT, false));
		errorList.addIfNotNull(checkFormat("FL", newForm.getFL(), ValidationFormat.HEIGHT, false));
		errorList.addIfNotNull(checkFormat("OFD", newForm.getOFD(), ValidationFormat.HEIGHT, false));
		errorList.addIfNotNull(checkFormat("AC", newForm.getAC(), ValidationFormat.HEIGHT, false));
		errorList.addIfNotNull(checkFormat("HL", newForm.getHL(), ValidationFormat.HEIGHT, false));
		errorList.addIfNotNull(checkFormat("EFW", newForm.getEFW(), ValidationFormat.WEIGHT, false));
		
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}