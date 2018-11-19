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

import edu.ncsu.csc.itrust.model.old.beans.PregnancyBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.PregnancyLoader;
import junit.framework.TestCase;

public class PregnancyLoaderTest extends TestCase {
	private IMocksControl ctrl;
	private ResultSet rs;
	private PregnancyLoader load;

	@Override
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new PregnancyLoader();
	}

	@Test
	public void testLoadList() throws SQLException {
		List<PregnancyBean> l = load.loadList(rs);
		assertEquals(0, l.size());
	}
	
	@Test
	public void testLoadSingle() {

		try {
			expect(rs.getInt("ID")).andReturn(1234321);
			expect(rs.getInt("PatientID")).andReturn(4321234);
			expect(rs.getDate("Date_delivery")).andReturn(new java.sql.Date(new Date(0).getTime()));
			expect(rs.getInt("YOC")).andReturn(7);
			expect(rs.getInt("Num_weeks_pregnant")).andReturn(17);
			expect(rs.getInt("Num_hours_labor")).andReturn(17);
			expect(rs.getString("Delivery_type")).andReturn("c-section");
			expect(rs.getFloat("weight_gain")).andReturn((float) 7.17);
			expect(rs.getInt("num_children")).andReturn(2);
			ctrl.replay();

			PregnancyBean r = load.loadSingle(rs);
			assertEquals(1234321, r.getID());
			assertEquals(4321234, r.getPatientID());
			assertEquals(new java.sql.Date(new Date(0).getTime()), r.getDateAsDate());
			assertEquals(7, r.getYOC());
			assertEquals(17, r.getNum_weeks_pregnant());
			assertEquals(17, r.getNum_hours_labor());
			assertEquals("c-section", r.getDelivery_type());
			assertEquals((float)7.17, r.getWeight_gain());
			assertEquals(2, r.getNum_children());

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