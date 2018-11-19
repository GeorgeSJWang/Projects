package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
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
import edu.ncsu.csc.itrust.logger.TransactionLogger;
import edu.ncsu.csc.itrust.model.old.beans.ApptBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.ObstetricsVisitForm;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ApptDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import edu.ncsu.csc.itrust.model.old.validate.ApptBeanValidator;
import edu.ncsu.csc.itrust.model.old.validate.ObstetricsValidator;
import edu.ncsu.csc.itrust.model.old.validate.ObstetricsVisitValidator;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsVisitDAO;


/**
 * Add a patient's obstetrics visit used by addObstetricsVisit.jsp
 * 
 * 
 */
public class AddObstetricsVisitAction extends PatientBaseAction {
	private ObstetricsVisitValidator validator = new ObstetricsVisitValidator();
	private ApptBeanValidator appValidator = new ApptBeanValidator();
	private PatientDAO patientDAO;
	private ObstetricsDAO obstetricsDAO;
	private ApptDAO apptDAO;
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
	public AddObstetricsVisitAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
		super(factory, pidString);
		this.patientDAO = factory.getPatientDAO();
		this.authDAO = factory.getAuthDAO();
		this.obstetricsDAO = factory.getObstetricsDAO();
		this.obstetricsVisitDAO = factory.getObstetricsVisitDAO();
		this.apptDAO = factory.getApptDAO();
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
	
	public void addVisit(ObstetricsVisitBean newVisit, String patientID, String scheduledDate, String createdDate, String weight, 
				String bloodPressure, String FHR, String numChildren, String LLP) throws ITrustException, FormValidationException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		ObstetricsVisitForm form = new ObstetricsVisitForm(patientID, scheduledDate, createdDate, weight, 
				bloodPressure, FHR, numChildren, LLP);
		validator.validate(form);
		
		// set ObstetricsVisitBean manually after validation
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
		obstetricsVisitDAO.addObstetricsVisit(newVisit);
		
		// add an appointment
		
		ApptBean appt = new ApptBean();
		try {
			appt.setDate(new Timestamp(sdf.parse(scheduledDate).getTime()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		appt.setHcp(loggedInMID);
		appt.setPatient(Long.parseLong(patientID));
		appt.setApptType("General Checkup");
		appt.setComment("Auto Generated Schedule.");
		appValidator.validate(appt);
		try {
			apptDAO.scheduleAppt(appt);
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
	}
}
