package edu.ncsu.csc.itrust.model.old.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.PregnancyLoader;
import edu.ncsu.csc.itrust.model.old.beans.loaders.ObstetricsLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;

/**
 * Used for managing all static information related to an obstetrics record. 
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be
 * reflections of the database, that is, one DAO per table in the database (most
 * of the time). For more complex sets of queries, extra DAOs are added. DAOs
 * can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than
 * a factory. All DAOs should be accessed by DAOFactory (@see
 * {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 */
public class ObstetricsDAO {
	private DAOFactory factory;
	private PregnancyLoader pregnancyLoader;
	private ObstetricsLoader obstetricsLoader;

	/**
	 * The typical constructor.
	 * 
	 * @param factory
	 *            The {@link DAOFactory} associated with this DAO, which is used
	 *            for obtaining SQL connections, etc.
	 */
	public ObstetricsDAO(DAOFactory factory) {
		this.factory = factory;
		this.pregnancyLoader = new PregnancyLoader();
		this.obstetricsLoader = new ObstetricsLoader();
	}


	/**
	 * Adds an empty patient to the table, returns the new MID
	 * 
	 * @return The MID of the patient as a long.
	 * @throws DBException
	 */
	public long addEmptyPatient() throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement ps = conn.prepareStatement("INSERT INTO patients(MID) VALUES(NULL)")) {
			ps.executeUpdate();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	/**
	 * Returns the pregnancy and obstetrics information for a given obstetrics ID
	 * 
	 * @param oid
	 *            The obstetrics ID of the obstetrics record to retrieve.
	 * @return An ObstetricsBean.
	 * @throws DBException
	 */
	public ObstetricsBean getObstetrics(long oid) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM obstetricsrecords WHERE ID = ?")
				;
				) {
			ps.setLong(1, oid);
			ResultSet rs = ps.executeQuery();
			ObstetricsBean obstetric = rs.next() ? obstetricsLoader.loadSingle(rs) : null;
			rs.close();
			long pid = obstetric.getPatientID();
			
			PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM pregnancyrecords WHERE PatientID = ? ORDER BY Date_delivery DESC");
			ps1.setLong(1, pid);
			ResultSet rs1 = ps1.executeQuery();
			//List<PregnancyBean> pregnancies = rs1.next() ? pregnancyLoader.loadList(rs1) : null;
			List<PregnancyBean> pregnancies = pregnancyLoader.loadList(rs1);
			rs1.close();
			
			obstetric.setPregnancies(pregnancies);
			
			return obstetric;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	/**
	 * Lists every obstetrics record for a certain patient
	 * 
	 * @return A java.util.List of Obstetrics Beans representing the records.
	 * @throws DBException
	 */
	public List<ObstetricsBean> getAllObstetrics(long pid) throws DBException {
		try (
				Connection conn = factory.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM obstetricsrecords WHERE PatientID = ? ORDER BY created_on DESC")
				;
				) {
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			//List<ObstetricsBean> obstetrics = rs.next() ? obstetricsLoader.loadList(rs) : null;
			List<ObstetricsBean> obstetrics = obstetricsLoader.loadList(rs);
			rs.close();
			
			PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM pregnancyrecords WHERE PatientID = ? ORDER BY Date_delivery DESC");
			ps1.setLong(1, pid);
			ResultSet rs1 = ps1.executeQuery();
			//List<PregnancyBean> pregnancies = rs1.next() ? pregnancyLoader.loadList(rs1) : null;
			List<PregnancyBean> pregnancies = pregnancyLoader.loadList(rs1);
			rs1.close();
			for (int i = 0; i < obstetrics.size(); i++) {
				obstetrics.get(i).setPregnancies(pregnancies);
			}
			
			
			return obstetrics;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	public List<PregnancyBean> getAllPregnancy(long pid) throws DBException {
		try (
				Connection conn = factory.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM pregnancyrecords WHERE PatientID = ? ORDER BY Date_delivery DESC");
				) {
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			List<PregnancyBean> pregnancies = pregnancyLoader.loadList(rs);
			rs.close();
			
			return pregnancies;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	/**
	 * Add a obstetric record
	 * 
	 * @param newRecord
	 *            The Obstetrics bean representing the new information
	 * @throws DBException
	 */
	public long addRecord(ObstetricsBean newRecord) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = obstetricsLoader.loadParameters(conn.prepareStatement(
						"INSERT INTO obstetricsrecords (PatientID, LMP, number_of_weeks_pregnant, created_on)"
								+ "VALUES (?, ?, ?, ?)"),
						newRecord)) {
			stmt.executeUpdate();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * Add a pregnancy record
	 * 
	 * @param newRecord
	 *            The pregnancy bean representing the new information
	 * @throws DBException
	 */
	public long addPregnancy(PregnancyBean newPregnancy) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = pregnancyLoader.loadParameters(conn.prepareStatement(
						"INSERT INTO pregnancyrecords (PatientID, YOC, num_weeks_pregnant, num_hours_labor, weight_gain, "
						+ "delivery_type, num_children, Date_delivery)"
								+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"),
						newPregnancy)) {
			stmt.executeUpdate();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

}
