package edu.ncsu.csc.itrust.model.old.beans.loaders;

import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundBean;

/**
 * A loader for UltrasoundBean.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class UltrasoundLoader implements BeanLoader<UltrasoundBean> {
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * loadList
	 * @param rs rs
	 * @throws SQLException
	 */
	@Override
	public List<UltrasoundBean> loadList(ResultSet rs) throws SQLException {
		List<UltrasoundBean> list = new ArrayList<UltrasoundBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	private void loadCommon(ResultSet rs, UltrasoundBean ub) throws SQLException{
		ub.setRecordID(rs.getLong("ID"));
		ub.setPatientID(rs.getLong("PatientID"));
		ub.setCreated_on(rs.getDate("created_on"));
		Blob imageBlob = rs.getBlob("Image");
		ub.setInputStream(imageBlob.getBinaryStream(1, imageBlob.length()));
		ub.setImageType(rs.getString("ImageType"));
	}
	
	/**
	 * loadSingle
	 * @param rs rs
	 * @return p
	 * @throws SQLException
	 */
	@Override
	public UltrasoundBean loadSingle(ResultSet rs) throws SQLException {
		UltrasoundBean ub = new UltrasoundBean();
		loadCommon(rs, ub);
		return ub;
	}

	/**
	 * loadParameters
	 * @throws SQLException
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, UltrasoundBean ub) throws SQLException {
		int i = 1;
		ps.setLong(i++, ub.getPatientID());
		Date date = null;
		try {
			date = new java.sql.Date(DATE_FORMAT.parse(ub.getCreated_on()).getTime());
		} catch (ParseException e) {
			//TODO
		}
		ps.setDate(i++, date);
		ps.setBlob(i++, ub.getInputStream());
		ps.setString(i++, ub.getImageType());

		return ps;
	}

}