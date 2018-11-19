package edu.ncsu.csc.itrust.action;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.FetusBean;
import edu.ncsu.csc.itrust.model.old.beans.forms.UltrasoundForm;
import edu.ncsu.csc.itrust.model.old.beans.forms.FetusForm;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.validate.FetusValidator;
import edu.ncsu.csc.itrust.model.old.validate.UltrasoundValidator;
import edu.ncsu.csc.itrust.model.old.dao.mysql.UltrasoundDAO;

/**
 * Add a patient's ultrasound record used by addUltrasound.jsp
 */
public class AddUltrasoundAction extends PatientBaseAction {

	private UltrasoundValidator uValidator = new UltrasoundValidator();
	private FetusValidator fValidator = new FetusValidator();
	private PatientDAO patientDAO;
	private UltrasoundDAO ultrasoundDAO;
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
	public AddUltrasoundAction(DAOFactory factory, long loggedInMID, String pidString) throws ITrustException {
		super(factory, pidString);
		this.patientDAO = factory.getPatientDAO();
		this.authDAO = factory.getAuthDAO();
		this.ultrasoundDAO = factory.getUltrasoundDAO();
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
	 * Update UltrasoundBean object and save its information
	 * 
	 * @param newRecord, PatientID, created_on, image
	 * @throws ITrustException
	 * @throws FormValidationException
	 */
	public long addRecord(UltrasoundBean newRecord, String PatientID, InputStream image, String imageType, String created_on) throws ITrustException, FormValidationException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		UltrasoundForm form = new UltrasoundForm(PatientID, created_on, imageType);
		uValidator.validate(form);
		
		// set UltrasoundBean manually after validation
		newRecord.setPatientID(Long.parseLong(PatientID));
		newRecord.setInputStream(image);
		newRecord.setImageType(imageType);
		try {
			newRecord.setCreated_on(sdf.parse(created_on));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return ultrasoundDAO.addRecord(newRecord);
	}
	
	/**
	 * Update FetusBean object and save its information
	 * 
	 * @param newFetus, patientID, ultrasoundID, created_on, CRL, BPD, HC, FL, OFD, AC, HL, EFW
	 * @throws ITrustException
	 * @throws FormValidationException
	 */
	public long addFetus(FetusBean newFetus, String patientID, String ultrasoundID, String created_on, String CRL, String BPD,
			String HC, String FL, String OFD, String AC, String HL, String EFW) throws ITrustException, FormValidationException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		FetusForm form = new FetusForm(patientID, ultrasoundID, created_on, CRL, BPD, HC, FL, OFD, AC, HL, EFW);
		fValidator.validate(form);
		
		// set FetusBean manually after validation
		newFetus.setPatientID(Long.parseLong(patientID));
		newFetus.setUltrasoundID(Long.parseLong(ultrasoundID));
		try {
			newFetus.setCreated_on(sdf.parse(created_on));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newFetus.setCRL(Float.parseFloat(CRL));
		newFetus.setBPD(Float.parseFloat(BPD));
		newFetus.setHC(Float.parseFloat(HC));
		newFetus.setFL(Float.parseFloat(FL));
		newFetus.setOFD(Float.parseFloat(OFD));
		newFetus.setAC(Float.parseFloat(AC));
		newFetus.setHL(Float.parseFloat(HL));
		newFetus.setEFW(Float.parseFloat(EFW));
				
		return ultrasoundDAO.addFetus(newFetus);
	}
	
	
	/**
	 * Return an ultrasound record that uid represents
	 * 
	 * @param uid The id of the ultrasound record we are looking for.
	 * @return an UltrasoundBean
	 * @throws ITrustException
	 */
	public UltrasoundBean getUltrasoundRecord(long uid) throws ITrustException {
		return ultrasoundDAO.getUltrasound(uid);
	}
	
}