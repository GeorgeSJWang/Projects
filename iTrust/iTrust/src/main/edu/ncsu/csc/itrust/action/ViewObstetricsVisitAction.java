package edu.ncsu.csc.itrust.action;

import java.text.DateFormat;
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
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ObstetricsVisitDAO;
import edu.ncsu.csc.itrust.model.old.enums.BloodType;


/**
 * View a patient's obstetrics visit used by viewObstetricsVisit.jsp
 * 
 * 
 */
public class ViewObstetricsVisitAction extends PatientBaseAction {
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
	public ViewObstetricsVisitAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
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
	 * Return a list of obstetrics visits that pid represents
	 * 
	 * @param pid The id of the patient we are looking for.
	 * @return a list of ObstetricsVisitBean
	 * @throws ITrustException
	 */
	public List<ObstetricsVisitBean> getAllObstetricsVisits(long pid) throws ITrustException {
		return obstetricsVisitDAO.getAllObstetricsVisits(pid);
	}
	
	/**
	 * Return a list of obstetrics visits that pid represents sorted by scheduled dates
	 * 
	 * @param pid The id of the patient we are looking for.
	 * @return a list of ObstetricsVisitBean
	 * @throws ITrustException
	 */
	public List<ObstetricsVisitBean> getSortedObstetricsVisits(long pid) throws ITrustException {
		return obstetricsVisitDAO.getSortedObstetricsVisits(pid);
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
	
	/**
	 * Return true if the patient meets the condition for RH shot
	 * 
	 * @param pid - the id of the patient we are checking.
	 * @return a Boolean
	 * @throws ITrustException
	 */
	public Boolean needRHShot(long pid) throws ITrustException {
		PatientBean pb = patientDAO.getPatient(pid);
		String bloodType = pb.getBloodType().getName();
		
		List<ObstetricsVisitBean> OVBs = obstetricsVisitDAO.getSortedObstetricsVisits(pid);
		int numWeeks = 0;

		if(OVBs != null && OVBs.size() > 0) {
			numWeeks = Integer.parseInt(OVBs.get(0).getNumWeeks().split("-")[0]);
		}
		
		// checking of RH shot history needs to wait for UC96 [S4]
		// as of now (UC94), assume patient never receives one
		// will come back after UC 96
		if (bloodType.charAt(bloodType.length()-1) == '-' && numWeeks > 28)
			return true;
		
		return false;
	}
	
}
