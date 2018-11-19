package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO;


/**
 * View a patient's obstetrics record used by viewObstetricsRecords.jsp
 * 
 * 
 */
public class ViewObstetricsAction extends PatientBaseAction {
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
	public ViewObstetricsAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
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
	 * Takes the information out of the PatientBean param and updates the patient's information
	 * 
	 * @param p
	 *            the new patient information
	 * @throws ITrustException
	 * @throws FormValidationException
	 */
	public void updateInformation(PatientBean p) throws ITrustException, FormValidationException {
		p.setMID(pid); // for security reasons
		patientDAO.editPatient(p, loggedInMID);
	}
	
	/**
	 * Return a list of obstetrics record that pid represents
	 * 
	 * @param pid The id of the patient we are looking for.
	 * @return a list of ObstetricsBean
	 * @throws ITrustException
	 */
	public List<ObstetricsBean> getAllObstetrics(long pid) throws ITrustException {
		return obstetricsDAO.getAllObstetrics(pid);
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
