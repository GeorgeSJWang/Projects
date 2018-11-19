package edu.ncsu.csc.itrust.unit.bean;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.easymock.classextension.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.model.old.beans.ObstetricsBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.ObstetricsLoader;
import junit.framework.TestCase;

public class ObstetricsLoaderTest extends TestCase {
	private IMocksControl ctrl;
	private ResultSet rs;
	private ObstetricsLoader load;

	@Override
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new ObstetricsLoader();
	}

	@Test
	public void testLoadList() throws SQLException {
		List<ObstetricsBean> l = load.loadList(rs);
		assertEquals(0, l.size());
	}
	
	@Test
	public void testLoadSingle() {

		try {
			// this just sets the value for a method call (kinda hard coding I
			// assume)
			expect(rs.getInt("ID")).andReturn(1234321);
			expect(rs.getInt("PatientID")).andReturn(4321234);
			expect(rs.getDate("Created_on")).andReturn(new java.sql.Date(new Date(0).getTime()));
			expect(rs.getDate("LMP")).andReturn(new java.sql.Date(new Date(0).getTime()));
			expect(rs.getInt("Number_of_weeks_pregnant")).andReturn(17);
			ctrl.replay();

			ObstetricsBean r = load.loadSingle(rs);
			assertEquals(1234321, r.getID());
			assertEquals(4321234, r.getPatientID());
			assertEquals(new java.sql.Date(new Date(0).getTime()), r.getCreated_onAsDate());
			assertEquals(new java.sql.Date(new Date(0).getTime()), r.getLMPAsDate());
			assertEquals(17, r.getNumber_of_weeks_pregnant());

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