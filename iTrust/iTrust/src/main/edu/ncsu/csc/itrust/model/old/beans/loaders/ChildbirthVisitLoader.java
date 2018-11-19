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

import edu.ncsu.csc.itrust.model.old.beans.ChildbirthVisitBean;

/**
 * A loader for ChildbirthVisitBean.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class ChildbirthVisitLoader implements BeanLoader<ChildbirthVisitBean> {
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * loadList
	 * @param rs rs
	 * @throws SQLException
	 */
	@Override
	public List<ChildbirthVisitBean> loadList(ResultSet rs) throws SQLException {
		List<ChildbirthVisitBean> list = new ArrayList<ChildbirthVisitBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	private void loadCommon(ResultSet rs, ChildbirthVisitBean p) throws SQLException{
		p.setVisitID(rs.getInt("ID"));
		p.setPatientID(rs.getInt("PatientID"));
		p.setPreferredChildbirthMethod(rs.getString("PreferredChildbirthMethod"));
		p.setDrugs(rs.getString("Drugs"));
		p.setScheduledDate(rs.getTimestamp("ScheduledDate"));
		p.setPreScheduled(rs.getBoolean("PreScheduled"));
	}
	
	/**
	 * loadSingle
	 * @param rs rs
	 * @return p
	 * @throws SQLException
	 */
	@Override
	public ChildbirthVisitBean loadSingle(ResultSet rs) throws SQLException {
		ChildbirthVisitBean p = new ChildbirthVisitBean();
		loadCommon(rs, p);
		return p;
	}

	/**
	 * loadParameters
	 * @throws SQLException
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, ChildbirthVisitBean p) throws SQLException {
		int i = 1;
		//ps.setInt(i++, p.getID());
		ps.setLong(i++, p.getPatientID());
		ps.setString(i++, p.getPreferredChildbirthMethod());
		ps.setString(i++, p.getDrugs());
		Date date = null;
		date = new java.sql.Date(p.getScheduledDate().getTime());
		ps.setDate(i++, date);
		ps.setBoolean(i++, p.isPreScheduled());
		return ps;
	}
	
	/**
	 * loadParameters for update
	 * @throws SQLException
	 */
	public PreparedStatement loadParametersUpdate(PreparedStatement ps, ChildbirthVisitBean p) throws SQLException {
		int i = 1;
		ps.setLong(i++, p.getPatientID());
		ps.setString(i++, p.getPreferredChildbirthMethod());
		ps.setString(i++, p.getDrugs());
		Date date = null;
		date = new java.sql.Date(p.getScheduledDate().getTime());
		ps.setDate(i++, date);
		ps.setBoolean(i++, p.isPreScheduled());
		ps.setLong(i++, p.getVisitID());
		return ps;
	}
}