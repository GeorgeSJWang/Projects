package edu.ncsu.csc.itrust.action;

import java.sql.Timestamp;
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
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsVisitForm;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.validate.ObstetricsValidator;
import edu.ncsu.csc.itrust.model.old.validate.ObstetricsVisitValidator;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsVisitDAO;


/**
 * Edit a patient's obstetrics visit used by editObstetricsVisit.jsp
 * 
 * 
 */
public class EditObstetricsVisitAction extends PatientBaseAction {
	private ObstetricsVisitValidator validator = new ObstetricsVisitValidator();
	private PatientDAO patientDAO;
	private ObstetricsDAO obstetricsDAO;
	private ObstetricsVisitDAO obstetricsVisitDAO;
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
	public EditObstetricsVisitAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
		super(factory, pidString);
		this.patientDAO = factory.getPatientDAO();
		this.authDAO = factory.getAuthDAO();
		this.obstetricsDAO = factory.getObstetricsDAO();
		this.obstetricsVisitDAO = factory.getObstetricsVisitDAO();
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
	 * Return an obstetrics vsiit that vid represents
	 * 
	 * @param vid The id of the obstetrics visit we are looking for.
	 * @return an ObstetricsVisitBean
	 * @throws ITrustException
	 */
	public ObstetricsVisitBean getObstetricsVisit(long vid) throws ITrustException {
		return obstetricsVisitDAO.getObstetricsVisit(vid);
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
	 * Return true if the patient is an obstetrics patient
	 * 
	 * @param pid The id of the patient we are checking.
	 * @return a Boolean
	 * @throws ITrustException
	 */
	public Boolean isObstericsPatient(long pid) throws ITrustException {
		List<ObstetricsBean> oblist = getAllObstetrics(pid);
		if (oblist.size() != 0){
			
			/* Get current date */
			Date now = new Date();
			
			long diffInMillies = Math.abs(now.getTime() - oblist.get(0).getLMPAsDate().getTime());
		    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			if (((int)diff/7) < 49){
				return true;
			}
		}
		return false;
	}
	
	public void editVisit(ObstetricsVisitBean newVisit, String ID, String patientID, String scheduledDate, String createdDate,
			String weight, String bloodPressure, String FHR, String numChildren, String LLP) throws ITrustException, FormValidationException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		ObstetricsVisitForm form = new ObstetricsVisitForm(patientID, scheduledDate, createdDate, weight, 
				bloodPressure, FHR, numChildren, LLP);
		validator.validate(form);
		// set ObstetricsVisitBean manually after validation
		newVisit.setID(Integer.parseInt(ID));
		newVisit.setPatientID(Integer.parseInt(patientID));
		try {
			newVisit.setScheduledDate(new Timestamp(sdf.parse(scheduledDate).getTime()));
			newVisit.setCreatedDate(new Timestamp(sdf.parse(createdDate).getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		newVisit.setWeight(Float.parseFloat(weight));
		newVisit.setBloodPressure(bloodPressure);
		newVisit.setFHR(Integer.parseInt(FHR));
		newVisit.setNumChildren(Integer.parseInt(numChildren));
		newVisit.setLLP(LLP != null);
		
		List<ObstetricsBean> oblist = getAllObstetrics(newVisit.getPatientID());
		
		/* Calculate weeks pregnant */		
		long diffInMillies;
		try {
			diffInMillies = Math.abs(sdf.parse(scheduledDate).getTime() - oblist.get(0).getLMPAsDate().getTime());
			long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		    newVisit.setNumWeeks(Integer.toString((int)diff/7)+"-"+Integer.toString((int)diff%7));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		obstetricsVisitDAO.updateObstetricsVisit(newVisit);
	}
}
