package edu.ncsu.csc.itrust.model.old.dao.mysql;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.FetusBean;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.FetusLoader;
import edu.ncsu.csc.itrust.model.old.beans.loaders.UltrasoundLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;

/**
 * Used for managing all static information related to an ultrasound record. 
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
public class UltrasoundDAO {
	private DAOFactory factory;
	private FetusLoader fetusLoader;
	private UltrasoundLoader ultrasoundLoader;

	/**
	 * The typical constructor.
	 * 
	 * @param factory
	 *            The {@link DAOFactory} associated with this DAO, which is used
	 *            for obtaining SQL connections, etc.
	 */
	public UltrasoundDAO(DAOFactory factory) {
		this.factory = factory;
		this.fetusLoader = new FetusLoader();
		this.ultrasoundLoader = new UltrasoundLoader();
	}

	/**
	 * Returns the fetus and ultrasound information for a given ultrasound ID
	 * 
	 * @param uid
	 *            The ultrasound ID of the ultrasound record to retrieve.
	 * @return An UltrasoundBean.
	 * @throws DBException
	 */
	public UltrasoundBean getUltrasound(long uid) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM ultrasoundrecords WHERE ID = ?")
				;
				) {
			ps.setLong(1, uid);
			ResultSet rs = ps.executeQuery();
			UltrasoundBean ultrasound = rs.next() ? ultrasoundLoader.loadSingle(rs) : null;
			rs.close();
			
			PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM fetusrecords WHERE UltrasoundID = ? ORDER BY created_on DESC");
			ps1.setLong(1, uid);
			ResultSet rs1 = ps1.executeQuery();
			List<FetusBean> fetus = fetusLoader.loadList(rs1);
			rs1.close();
			
			ultrasound.setFetus(fetus);
		
			return ultrasound;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	/**
	 * Lists every ultrasound record for a certain patient
	 * 
	 * @return A java.util.List of UltrasoundBean representing the records.
	 * @throws DBException
	 */
	public List<UltrasoundBean> getAllUltrasounds(long pid) throws DBException {
		try (
				Connection conn = factory.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM ultrasoundrecords WHERE PatientID = ? ORDER BY created_on DESC")
				;
				) {
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			List<UltrasoundBean> ultrasound = ultrasoundLoader.loadList(rs);
			rs.close();
			
			PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM fetusrecords WHERE PatientID = ? ORDER BY created_on DESC");
			ps1.setLong(1, pid);
			ResultSet rs1 = ps1.executeQuery();
			List<FetusBean> fetus = fetusLoader.loadList(rs1);
			rs1.close();
			for (int i = 0; i < ultrasound.size(); i++) {
				ultrasound.get(i).setFetus(fetus);
			}
			
			return ultrasound;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * Returns the ImageData
	 * 
	 * @param uid
	 * @return ImageData
	 * @throws DBException
	 */
	public InputStream getImageData(long uid) throws DBException {
		try (
				Connection conn = factory.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM ultrasoundrecords WHERE ID = ?");
				) {

			ps.setLong(1, uid);
			ResultSet rs = ps.executeQuery();
			rs.next();
			InputStream sImage;
			sImage = rs.getBinaryStream("Image");
			rs.close();
			return sImage;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * Returns the ImageType
	 * 
	 * @param uid
	 * @return Type of Image
	 * @throws DBException
	 */
	public String getImageType(long uid) throws DBException {
		try (
				Connection conn = factory.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM ultrasoundrecords WHERE ID = ?");
				) {

			ps.setLong(1, uid);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String imageType;
			imageType = rs.getString("ImageType");
			rs.close();
			return imageType;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	
	/**
	 * Lists every fetus record for a certain patient
	 * 
	 * @return A java.util.List of FetusBean representing the records.
	 * @throws DBException
	 */
	public List<FetusBean> getAllFetus(long pid) throws DBException {
		try (
				Connection conn = factory.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM fetusrecords WHERE PatientID = ? ORDER BY created_on DESC");
				) {
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			List<FetusBean> fetus = fetusLoader.loadList(rs);
			rs.close();
			
			return fetus;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * Adds an ultrasound record
	 * 
	 * @param newRecord
	 *            The UltrasoundBean representing the new information
	 * @throws DBException
	 */
	public long addRecord(UltrasoundBean newRecord) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = ultrasoundLoader.loadParameters(conn.prepareStatement(
						"INSERT INTO ultrasoundrecords (PatientID, created_on, Image, ImageType)"
								+ "VALUES (?, ?, ?, ?)"),
						newRecord)) {
			stmt.executeUpdate();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
	
	/**
	 * Add an fetus record
	 * 
	 * @param newRecord
	 *            The fetus bean representing the new information
	 * @throws DBException
	 */
	public long addFetus(FetusBean newFetus) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = fetusLoader.loadParameters(conn.prepareStatement(
						"INSERT INTO fetusrecords (PatientID, UltrasoundID, created_on, CRL, BPD, HC, FL, OFD, AC, HL, EFW)"
								+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
						newFetus)) {
			stmt.executeUpdate();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

}