package edu.ncsu.csc.itrust.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.PregnancyForm;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.validate.ObstetricsValidator;
import edu.ncsu.csc.itrust.model.old.validate.PatientValidator;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO;


/**
 * View a patient's obstetrics record used by viewObstetricsRecords.jsp
 * 
 * 
 */
public class AddObstetricsAction extends PatientBaseAction {
	private ObstetricsValidator validator = new ObstetricsValidator();
	private PatientDAO patientDAO;
	private ObstetricsDAO obstetricsDAO;
	private AuthDAO authDAO;
	private long loggedInMID;

	/**
	 * The super class validates the patient id
	 * 
	 * @param factory The DAOFactory used to create the DAOs for this action.
	 * @param loggedInMID The MID of the user who is authorizing this action.
	 * @param pidString The MID of the patient being edited.
	 * @throws ITrustException
	 */
	public AddObstetricsAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
		super(factory, pidString);
		this.patientDAO = factory.getPatientDAO();
		this.authDAO = factory.getAuthDAO();
		this.obstetricsDAO = factory.getObstetricsDAO();
		this.loggedInMID = loggedInMID;
	}
	
	/**
	 * Returns a PatientBean for the patient
	 * 
	 * @return the PatientBean
	 * @throws DBException
	 */
	public PatientBean getPatient() throws DBException {
		return patientDAO.getPatient(this.getPid());
	}
	
	/**
	 * Update ObstetricsBean object and save its information
	 * 
	 * @param newRecord, PatientID, LMP, created_on
	 * @throws ITrustException
	 * @throws FormValidationException
	 */
	public long addRecord(ObstetricsBean newRecord, String PatientID, String LMP, String created_on) throws ITrustException, FormValidationException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		ObstetricsForm form = new ObstetricsForm(PatientID, LMP, created_on);
		validator.validate(form);
		
		// set ObstetricsBean manually after validation
		newRecord.setPatientID(Integer.parseInt(PatientID));
		try {
			newRecord.setLMP(sdf.parse(LMP));
			newRecord.setCreated_on(sdf.parse(created_on));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	    long diffInMillies = Math.abs(newRecord.getCreated_onAsDate().getTime() - newRecord.getLMPAsDate().getTime());
	    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		newRecord.setNumber_of_weeks_pregnant((int)diff/7);
		return obstetricsDAO.addRecord(newRecord);
	}
	
	/**
	 * Update PregnancyBean object and save its information
	 * 
	 * @param newPregnancy, PatientID, YOC, num_weeks_pregnant,
			 num_hours_labor, weight_gain, delivery_type, num_children, Date_delivery
	 * @throws ITrustException
	 * @throws FormValidationException
	 */
	public long addPregnancy(PregnancyBean newPregnancy, String PatientID, String YOC, String num_weeks_pregnant,
			String num_hours_labor, String weight_gain, String delivery_type, String num_children, String Date_delivery) throws ITrustException, FormValidationException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		PregnancyForm form = new PregnancyForm(PatientID, YOC, num_weeks_pregnant, num_hours_labor, weight_gain, 
				delivery_type, num_children, Date_delivery);
		validator.validatePregnancy(form);
		
		// set PregnancyBean manually after validation
		newPregnancy.setPatientID(Integer.parseInt(PatientID));
		try {
			newPregnancy.setDate(sdf.parse(Date_delivery));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newPregnancy.setNum_weeks_pregnant(Integer.parseInt(num_weeks_pregnant));
		newPregnancy.setNum_hours_labor(Integer.parseInt(num_hours_labor));
		newPregnancy.setDelivery_type(delivery_type);
		newPregnancy.setYOC(Integer.parseInt(YOC));
		newPregnancy.setWeight_gain(Float.parseFloat(weight_gain));
		newPregnancy.setNum_children(Integer.parseInt(num_children));
		
		return obstetricsDAO.addPregnancy(newPregnancy);
	}
	
	
	/**
	 * Return an obstetrics record that oid represents
	 * 
	 * @param oid The id of the obstetrics record we are looking for.
	 * @return an ObstetricsBean
	 * @throws ITrustException
	 */
	public ObstetricsBean getObstetricsRecord(long oid) throws ITrustException {
		return obstetricsDAO.getObstetrics(oid);
	}
}
