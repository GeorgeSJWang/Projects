package edu.ncsu.csc.itrust.unit.bean;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.easymock.classextension.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.ObstetricsVisitLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ObstetricsVisitLoaderTest extends TestCase {
	private IMocksControl ctrl;
	private ResultSet rs;
	private ObstetricsVisitLoader load;
	private DAOFactory factory;

	@Override
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new ObstetricsVisitLoader();
		factory = TestDAOFactory.getTestInstance();
	}

	@Test
	public void testLoadList() throws SQLException {
		List<ObstetricsVisitBean> l = load.loadList(rs);
		assertEquals(0, l.size());
	}
	
	@Test
	public void testLoadSingle() {

		try {
			// this just sets the value for a method call (kinda hard coding I
			// assume)
			expect(rs.getInt("ID")).andReturn(1234321);
			expect(rs.getInt("PatientID")).andReturn(4321234);
			expect(rs.getTimestamp("scheduledDate")).andReturn(new java.sql.Timestamp(new Date(0).getTime()));
			expect(rs.getTimestamp("createdDate")).andReturn(new java.sql.Timestamp(new Date(0).getTime()));
			expect(rs.getString("numWeeks")).andReturn("17");
			expect(rs.getFloat("weight")).andReturn((float)150.0);
			expect(rs.getString("bloodPressure")).andReturn("120/50");
			expect(rs.getInt("FHR")).andReturn(60);
			expect(rs.getInt("numChildren")).andReturn(4);
			expect(rs.getBoolean("LLP")).andReturn(false);
			ctrl.replay();

			ObstetricsVisitBean r = load.loadSingle(rs);
			assertEquals(1234321, r.getID());
			assertEquals(4321234, r.getPatientID());
			assertEquals(new java.sql.Timestamp(new Date(0).getTime()), r.getScheduledDate());
			assertEquals(new java.sql.Timestamp(new Date(0).getTime()), r.getCreatedDate());
			assertEquals("17", r.getNumWeeks());
			assertEquals((float)150.0, r.getWeight());
			assertEquals("120/50", r.getBloodPressure());
			assertEquals(60, r.getFHR());
			assertEquals(4, r.getNumChildren());
			assertEquals((Boolean)false, r.getLLP());

		} catch (SQLException e) {
			// TODO
		}
	}
	
	@Test
	public void testLoadParameters() {
		try {
			ObstetricsVisitBean ob = new ObstetricsVisitBean();
			ob.setPatientID(1);
			ob.setScheduledDate(new Timestamp(new Date(0).getTime()));
			ob.setCreatedDate(new Timestamp(new Date(0).getTime()));
			ob.setNumWeeks("17");
			ob.setWeight((float)150);
			ob.setBloodPressure("120/50");
			ob.setFHR(60);
			ob.setNumChildren(4);
			ob.setLLP((Boolean)false);
			
			Connection conn = factory.getConnection();
			
			PreparedStatement stmt = load.loadParameters(conn.prepareStatement(
					"INSERT INTO obstetricsVisit (patientID, scheduledDate, createdDate, numWeeks, weight,"
					+ " bloodPressure, FHR, numChildren, LLP) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"),
					ob);
			
			String strSTMT = "INSERT INTO obstetricsVisit (patientID, scheduledDate, createdDate, numWeeks, weight, bloodPressure, FHR, numChildren, LLP) "
					+ "VALUES (1, '1969-12-31 18:00:00', '1969-12-31 18:00:00', '17', 150.0, '120/50', 60, 4, 0)";
			
			boolean contains = (stmt.toString()).contains(strSTMT);
			
			assertEquals(true, contains);
					
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testLoadParametersUpdate() {
		try {
			ObstetricsVisitBean ob = new ObstetricsVisitBean();
			ob.setPatientID(1);
			ob.setScheduledDate(new Timestamp(new Date(0).getTime()));
			ob.setCreatedDate(new Timestamp(new Date(0).getTime()));
			ob.setNumWeeks("17");
			ob.setWeight((float)150);
			ob.setBloodPressure("120/50");
			ob.setFHR(60);
			ob.setNumChildren(4);
			ob.setLLP((Boolean)false);
			
			Connection conn = factory.getConnection();
			
			PreparedStatement stmt = load.loadParametersUpdate(conn.prepareStatement(
					"UPDATE obstetricsVisit SET patientID=?, scheduledDate=?, createdDate=?, numWeeks=?, weight=?,"
							+ " bloodPressure=?, FHR=?, numChildren=?, LLP=? WHERE ID=?"),
					ob);
			
			String strSTMT = "UPDATE obstetricsVisit SET patientID=1, scheduledDate='1969-12-31 18:00:00', createdDate='1969-12-31 18:00:00', "
					+ "numWeeks='17', weight=150.0, bloodPressure='120/50', FHR=60, numChildren=4, LLP=0 WHERE ID=0";
			
			boolean contains = (stmt.toString()).contains(strSTMT);
			
			assertEquals(true, contains);
					
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
}