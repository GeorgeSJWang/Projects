package edu.ncsu.csc.itrust.model.old.beans.loaders;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;

/**
 * A loader for ObstetricsVisitBean.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class ObstetricsVisitLoader implements BeanLoader<ObstetricsVisitBean> {
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * loadList
	 * @param rs rs
	 * @throws SQLException
	 */
	@Override
	public List<ObstetricsVisitBean> loadList(ResultSet rs) throws SQLException {
		List<ObstetricsVisitBean> list = new ArrayList<ObstetricsVisitBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	private void loadCommon(ResultSet rs, ObstetricsVisitBean p) throws SQLException{
		p.setID(rs.getInt("ID"));
		p.setPatientID(rs.getInt("PatientID"));
		p.setScheduledDate(rs.getTimestamp("scheduledDate"));
		p.setCreatedDate(rs.getTimestamp("createdDate"));
		p.setNumWeeks(rs.getString("numWeeks"));
		p.setWeight(rs.getFloat("weight"));
		p.setBloodPressure(rs.getString("bloodPressure"));
		p.setFHR(rs.getInt("FHR"));
		p.setNumChildren(rs.getInt("numChildren"));
		p.setLLP(rs.getBoolean("LLP"));
	}
	
	/**
	 * loadSingle
	 * @param rs rs
	 * @return p
	 * @throws SQLException
	 */
	@Override
	public ObstetricsVisitBean loadSingle(ResultSet rs) throws SQLException {
		ObstetricsVisitBean p = new ObstetricsVisitBean();
		loadCommon(rs, p);
		return p;
	}

	/**
	 * loadParameters
	 * @throws SQLException
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, ObstetricsVisitBean p) throws SQLException {
		int i = 1;
		//ps.setInt(i++, p.getID());
		ps.setLong(i++, p.getPatientID());
		ps.setTimestamp(i++, p.getScheduledDate());
		ps.setTimestamp(i++, p.getCreatedDate());
		ps.setString(i++, p.getNumWeeks());
		ps.setFloat(i++, p.getWeight());
		ps.setString(i++, p.getBloodPressure());
		ps.setInt(i++, p.getFHR());
		ps.setInt(i++, p.getNumChildren());
		ps.setBoolean(i++, p.getLLP());
		return ps;
	}
	
	/**
	 * loadParameters for update
	 * @throws SQLException
	 */
	public PreparedStatement loadParametersUpdate(PreparedStatement ps, ObstetricsVisitBean p) throws SQLException {
		int i = 1;
		//ps.setInt(i++, p.getID());
		ps.setLong(i++, p.getPatientID());
		ps.setTimestamp(i++, p.getScheduledDate());
		ps.setTimestamp(i++, p.getCreatedDate());
		ps.setString(i++, p.getNumWeeks());
		ps.setFloat(i++, p.getWeight());
		ps.setString(i++, p.getBloodPressure());
		ps.setInt(i++, p.getFHR());
		ps.setInt(i++, p.getNumChildren());
		ps.setBoolean(i++, p.getLLP());
		ps.setLong(i++, p.getID());
		return ps;
	}
}