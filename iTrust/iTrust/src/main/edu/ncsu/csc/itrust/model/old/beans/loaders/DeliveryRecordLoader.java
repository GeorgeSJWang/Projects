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

import edu.ncsu.csc.itrust.model.old.beans.DeliveryRecordBean;

/**
 * A loader for DeliveryRecordBean.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class DeliveryRecordLoader implements BeanLoader<DeliveryRecordBean> {
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm");

	/**
	 * loadList
	 * @param rs rs
	 * @throws SQLException
	 */
	@Override
	public List<DeliveryRecordBean> loadList(ResultSet rs) throws SQLException {
		List<DeliveryRecordBean> list = new ArrayList<DeliveryRecordBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	private void loadCommon(ResultSet rs, DeliveryRecordBean d) throws SQLException{
		d.setID(rs.getInt("ID"));
		d.setPatientID(rs.getInt("PatientID"));
		d.setChildbirthVisitID(rs.getInt("ChildbirthVisitID"));
		d.setChildID(rs.getInt("ChildID"));
		d.setGenderStr(rs.getString("Gender"));
		d.setDeliveryDateTime(rs.getTimestamp("DeliveryDateTime"));
		d.setDeliveryMethod(rs.getString("DeliveryMethod"));
		d.setIsEstimated(rs.getBoolean("IsEstimated"));
	}
	
	/**
	 * loadSingle
	 * @param rs rs
	 * @return d
	 * @throws SQLException
	 */
	@Override
	public DeliveryRecordBean loadSingle(ResultSet rs) throws SQLException {
		DeliveryRecordBean d = new DeliveryRecordBean();
		loadCommon(rs, d);
		return d;
	}

	/**
	 * loadParameters
	 * @throws SQLException
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, DeliveryRecordBean d) throws SQLException {
		int i = 1;
		//ps.setInt(i++, d.getID());
		ps.setLong(i++, d.getPatientID());
		ps.setLong(i++, d.getChildbirthVisitID());
		ps.setLong(i++, d.getChildID());
		ps.setString(i++, d.getGender().getName());
		Timestamp date = null;
		try {
			date = new java.sql.Timestamp(DATE_FORMAT.parse(d.getDeliveryDateTimeString()).getTime());
		} catch (ParseException e) {
			//TODO
		}
		ps.setTimestamp(i++, date);
		ps.setString(i++, d.getDeliveryMethod());
		ps.setBoolean(i++, d.getIsEstimated());
		return ps;
	}
	
	/**
	 * loadParameters for update
	 * @throws SQLException
	 */
	public PreparedStatement loadParametersUpdate(PreparedStatement ps, DeliveryRecordBean d) throws SQLException {
		int i = 1;
		ps.setLong(i++, d.getPatientID());
		ps.setLong(i++, d.getChildbirthVisitID());
		ps.setLong(i++, d.getChildID());
		ps.setString(i++, d.getGender().getName());
		Timestamp date = null;
		try {
			date = new java.sql.Timestamp(DATE_FORMAT.parse(d.getDeliveryDateTimeString()).getTime());
		} catch (ParseException e) {
			//TODO
		}
		ps.setTimestamp(i++, date);
		ps.setString(i++, d.getDeliveryMethod());
		ps.setBoolean(i++, d.getIsEstimated());
		ps.setLong(i++, d.getID());
		return ps;
	}
}