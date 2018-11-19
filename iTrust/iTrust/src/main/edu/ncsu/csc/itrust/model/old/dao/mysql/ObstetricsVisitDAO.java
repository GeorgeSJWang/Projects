package edu.ncsu.csc.itrust.model.old.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.ObstetricsVisitLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;

/**
 * Used for managing all static information related to an obstetrics visit record. 
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
public class ObstetricsVisitDAO {
	private DAOFactory factory;
	private ObstetricsVisitLoader obstetricsVisitLoader;

	/**
	 * The typical constructor.
	 * 
	 * @param factory
	 *            The {@link DAOFactory} associated with this DAO, which is used
	 *            for obtaining SQL connections, etc.
	 */
	public ObstetricsVisitDAO(DAOFactory factory) {
		this.factory = factory;
		this.obstetricsVisitLoader = new ObstetricsVisitLoader();
	}

	/**
	 * Returns the obstetrics visit information for a given ID
	 * 
	 * @param vid
	 *            The obstetrics visit ID of the obstetrics visit record to retrieve.
	 * @return An ObstetricsVisitBean.
	 * @throws DBException
	 */
	public ObstetricsVisitBean getObstetricsVisit(long vid) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM obstetricsVisit WHERE ID = ?");
				) {
			ps.setLong(1, vid);
			ResultSet rs = ps.executeQuery();
			ObstetricsVisitBean obstetricVisit = rs.next() ? obstetricsVisitLoader.loadSingle(rs) : null;
			rs.close();
			
			return obstetricVisit;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	/**
	 * Lists every obstetrics visit record for a certain patient
	 * 
	 * @return A java.util.List of ObstetricsVisit Beans representing the records.
	 * @throws DBException
	 */
	public List<ObstetricsVisitBean> getAllObstetricsVisits(long pid) throws DBException {
		try (
				Connection conn = factory.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM obstetricsVisit WHERE patientID = ? "
						+ "ORDER BY createdDate DESC");
				) {
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			List<ObstetricsVisitBean> obstetricsVisits = obstetricsVisitLoader.loadList(rs);
			rs.close();
					
			return obstetricsVisits;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * Lists every obstetrics visit record for a certain patient sorted by scheduled date
	 * 
	 * @return A java.util.List of ObstetricsVisit Beans representing the records.
	 * @throws DBException
	 */
	public List<ObstetricsVisitBean> getSortedObstetricsVisits(long pid) throws DBException {
		try (
				Connection conn = factory.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM obstetricsVisit WHERE patientID = ? "
						+ "ORDER BY scheduledDate DESC");
				) {
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			List<ObstetricsVisitBean> obstetricsVisits = obstetricsVisitLoader.loadList(rs);
			rs.close();
					
			return obstetricsVisits;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * Add a obstetrics visit
	 * 
	 * @param newVisit
	 *            The ObstetricsVisit bean representing the new information
	 * @throws DBException
	 */
	public void addObstetricsVisit(ObstetricsVisitBean newVisit) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = obstetricsVisitLoader.loadParameters(conn.prepareStatement(
						"INSERT INTO obstetricsVisit (patientID, scheduledDate, createdDate, numWeeks, weight,"
						+ " bloodPressure, FHR, numChildren, LLP) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"),
						newVisit)) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * update a obstetrics visit
	 * 
	 * @param newVisit
	 *            The ObstetricsVisit bean representing the new information
	 * @throws DBException
	 */
	public void updateObstetricsVisit(ObstetricsVisitBean newVisit) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = obstetricsVisitLoader.loadParametersUpdate(conn.prepareStatement(
						"UPDATE obstetricsVisit SET patientID=?, scheduledDate=?, createdDate=?, numWeeks=?, weight=?,"
						+ " bloodPressure=?, FHR=?, numChildren=?, LLP=? WHERE ID=?"),
						newVisit)) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	

}
