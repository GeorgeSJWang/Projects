package edu.ncsu.csc.itrust.model.old.beans.loaders;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;

/**
 * A loader for PregnancyBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class ObstetricsLoader implements BeanLoader<ObstetricsBean> {
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * loadList
	 * @param rs rs
	 * @throws SQLException
	 */
	@Override
	public List<ObstetricsBean> loadList(ResultSet rs) throws SQLException {
		List<ObstetricsBean> list = new ArrayList<ObstetricsBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	private void loadCommon(ResultSet rs, ObstetricsBean p) throws SQLException{
		p.setID(rs.getInt("ID"));
		p.setPatientID(rs.getInt("PatientID"));
		p.setCreated_on(rs.getDate("Created_on"));
		p.setLMP(rs.getDate("LMP"));
		p.setNumber_of_weeks_pregnant(rs.getInt("Number_of_weeks_pregnant"));
	}
	
	/**
	 * loadSingle
	 * @param rs rs
	 * @return p
	 * @throws SQLException
	 */
	@Override
	public ObstetricsBean loadSingle(ResultSet rs) throws SQLException {
		ObstetricsBean p = new ObstetricsBean();
		loadCommon(rs, p);
		return p;
	}

	/**
	 * loadParameters
	 * @throws SQLException
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, ObstetricsBean p) throws SQLException {
		int i = 1;
		//ps.setInt(i++, p.getID());
		ps.setInt(i++, p.getPatientID());
		Date date = null;
		try {
			date = new java.sql.Date(DATE_FORMAT.parse(p.getLMP())
				.getTime());
		} catch (ParseException e) {
			//TODO
		}
		ps.setDate(i++, date);
		ps.setInt(i++, p.getNumber_of_weeks_pregnant());
		date = null;
		try {
			date = new java.sql.Date(DATE_FORMAT.parse(p.getCreated_on())
				.getTime());
		} catch (ParseException e) {
			//TODO
		}
		ps.setDate(i++, date);
		
		return ps;
	}
}