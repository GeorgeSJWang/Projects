package edu.ncsu.csc.itrust.unit.bean;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.easymock.classextension.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.model.old.beans.FetusBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.FetusLoader;
import edu.ncsu.csc.itrust.model.old.beans.loaders.ObstetricsLoader;
import junit.framework.TestCase;

public class FetusLoaderTest extends TestCase {
	private IMocksControl ctrl;
	private ResultSet rs;
	private FetusLoader load;

	@Override
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new FetusLoader();
	}

	@Test
	public void testLoadList() throws SQLException {
		List<FetusBean> l = load.loadList(rs);
		assertEquals(0, l.size());
	}
	
	@Test
	public void testLoadSingle() {

		try {
			expect(rs.getLong("ID")).andReturn(0L);
			expect(rs.getLong("PatientID")).andReturn(1L);
			expect(rs.getLong("UltrasoundID")).andReturn(2L);
			expect(rs.getDate("created_on")).andReturn(new java.sql.Date(new Date(0).getTime()));
			expect(rs.getFloat("CRL")).andReturn(1.0f);
			expect(rs.getFloat("BPD")).andReturn(2.0f);
			expect(rs.getFloat("HC")).andReturn(3.0f);
			expect(rs.getFloat("FL")).andReturn(4.0f);
			expect(rs.getFloat("OFD")).andReturn(5.0f);
			expect(rs.getFloat("AC")).andReturn(6.0f);
			expect(rs.getFloat("HL")).andReturn(7.0f);
			expect(rs.getFloat("EFW")).andReturn(8.0f);
			ctrl.replay();

			FetusBean f = load.loadSingle(rs);
			assertEquals(0, f.getRecordID());
			assertEquals(1, f.getPatientID());
			assertEquals(2, f.getUltrasoundID());
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			assertEquals(dateFormat.format(new java.sql.Date(new Date(0).getTime())), f.getCreated_on());
			assertEquals(1.0f, f.getCRL());
			assertEquals(2.0f, f.getBPD());
			assertEquals(3.0f, f.getHC());
			assertEquals(4.0f, f.getFL());
			assertEquals(5.0f, f.getOFD());
			assertEquals(6.0f, f.getAC());
			assertEquals(7.0f, f.getHL());
			assertEquals(8.0f, f.getEFW());

		} catch (SQLException e) {
			// TODO
		}
	}
	
	@Test
	public void testLoadParameters() {
		try {
			load.loadParameters(null, null);
			fail();
		} catch (Exception e) {
		}
	}
}