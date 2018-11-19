package edu.ncsu.csc.itrust.action;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.ChildbirthVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.ChildbirthVisitDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;

/**
 * View a patient's childbirth visit used by viewChildbirthVisit.jsp
 * 
 * 
 */
public class ViewChildbirthVisitAction extends PatientBaseAction {
	private PatientDAO patientDAO;
	private ChildbirthVisitDAO childbirthDAO;
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
	public ViewChildbirthVisitAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
		super(factory, pidString);
		this.patientDAO = factory.getPatientDAO();
		this.authDAO = factory.getAuthDAO();
		this.childbirthDAO = factory.getChildbirthVisitDAO();
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
	 * Return a list of childbirth visits that pid represents
	 * 
	 * @param pid The id of the patient we are looking for.
	 * @return a list of ChildbirthVisitBean
	 * @throws ITrustException
	 */
	public List<ChildbirthVisitBean> getAllChildbirthVisits(long pid) throws ITrustException {
		return childbirthDAO.getAllChildbirthVisits(pid);
	}

	
	/**
	 * Return a childbirth visit that vid represents
	 * 
	 * @param vid The id of the childbirth visit we are looking for.
	 * @return a ChildbirthVisitBean
	 * @throws ITrustException
	 */
	public ChildbirthVisitBean getChildbirthVisit(long vid) throws ITrustException {
		return childbirthDAO.getChildbirthVisit(vid);
	}
	
	
}
