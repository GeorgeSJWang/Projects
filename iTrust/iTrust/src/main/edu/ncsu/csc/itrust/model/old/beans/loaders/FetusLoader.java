package edu.ncsu.csc.itrust.model.old.beans.loaders;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.model.old.beans.FetusBean;

/**
 * A loader for FeatusBean.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class FetusLoader implements BeanLoader<FetusBean> {
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * loadList
	 * @param rs rs
	 * @throws SQLException
	 */
	@Override
	public List<FetusBean> loadList(ResultSet rs) throws SQLException {
		List<FetusBean> list = new ArrayList<FetusBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	private void loadCommon(ResultSet rs, FetusBean fb) throws SQLException{
		fb.setRecordID(rs.getLong("ID"));
		fb.setPatientID(rs.getLong("PatientID"));
		fb.setUltrasoundID(rs.getLong("UltrasoundID"));
		fb.setCreated_on(rs.getDate("created_on"));
		fb.setCRL(rs.getFloat("CRL"));
		fb.setBPD(rs.getFloat("BPD"));
		fb.setHC(rs.getFloat("HC"));
		fb.setFL(rs.getFloat("FL"));
		fb.setOFD(rs.getFloat("OFD"));
		fb.setAC(rs.getFloat("AC"));
		fb.setHL(rs.getFloat("HL"));
		fb.setEFW(rs.getFloat("EFW"));
	}
	
	/**
	 * loadSingle
	 * @param rs rs
	 * @return p
	 * @throws SQLException
	 */
	@Override
	public FetusBean loadSingle(ResultSet rs) throws SQLException {
		FetusBean fb = new FetusBean();
		loadCommon(rs, fb);
		return fb;
	}

	/**
	 * loadParameters
	 * @throws SQLException
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, FetusBean fb) throws SQLException {
		int i = 1;
		ps.setLong(i++, fb.getPatientID());
		ps.setLong(i++, fb.getUltrasoundID());
		Date date = null;
		try {
			date = new java.sql.Date(DATE_FORMAT.parse(fb.getCreated_on()).getTime());
		} catch (ParseException e) {
			//TODO
		}
		ps.setDate(i++, date);
		ps.setFloat(i++, fb.getCRL());
		ps.setFloat(i++, fb.getBPD());
		ps.setFloat(i++, fb.getHC());
		ps.setFloat(i++, fb.getFL());
		ps.setFloat(i++, fb.getOFD());
		ps.setFloat(i++, fb.getAC());
		ps.setFloat(i++, fb.getHL());
		ps.setFloat(i++, fb.getEFW());		
		return ps;
	}
}