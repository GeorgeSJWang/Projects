package edu.ncsu.csc.itrust.model.old.beans.loaders;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;

/**
 * A loader for PregnancyBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class PregnancyLoader implements BeanLoader<PregnancyBean> {
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * loadList
	 * @param rs rs
	 * @throws SQLException
	 */
	@Override
	public List<PregnancyBean> loadList(ResultSet rs) throws SQLException {
		List<PregnancyBean> list = new ArrayList<PregnancyBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	private void loadCommon(ResultSet rs, PregnancyBean p) throws SQLException{
		p.setID(rs.getInt("ID"));
		p.setPatientID(rs.getInt("PatientID"));
		p.setDate(rs.getDate("Date_delivery"));
		p.setYOC(rs.getInt("YOC"));
		p.setNum_weeks_pregnant(rs.getInt("Num_weeks_pregnant"));
		p.setNum_hours_labor(rs.getInt("Num_hours_labor"));
		p.setDelivery_type(rs.getString("Delivery_type"));
		p.setWeight_gain(rs.getFloat("weight_gain"));
		p.setNum_children(rs.getInt("num_children"));
	}
	
	/**
	 * loadSingle
	 * @param rs rs
	 * @return p
	 * @throws SQLException
	 */
	@Override
	public PregnancyBean loadSingle(ResultSet rs) throws SQLException {
		PregnancyBean p = new PregnancyBean();
		loadCommon(rs, p);
		return p;
	}

	/**
	 * loadParameters
	 * @throws SQLException
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, PregnancyBean p) throws SQLException {
		int i = 1;
		ps.setInt(i++, p.getPatientID());
		ps.setInt(i++, p.getYOC());
		ps.setInt(i++, p.getNum_weeks_pregnant());
		ps.setInt(i++, p.getNum_hours_labor());
		ps.setFloat(i++, p.getWeight_gain());
		ps.setString(i++, p.getDelivery_type());
		ps.setInt(i++, p.getNum_children());
		Date date = null;
		try {
			date = new java.sql.Date(DATE_FORMAT.parse(p.getDate())
				.getTime());
		} catch (ParseException e) {
			//TODO
		}
		ps.setDate(i++, date);
		
		return ps;
	}
}