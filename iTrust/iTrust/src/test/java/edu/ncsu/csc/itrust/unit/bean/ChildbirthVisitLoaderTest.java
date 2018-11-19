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

import edu.ncsu.csc.itrust.model.old.beans.ChildbirthVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.ObstetricsVisitBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.ChildbirthVisitLoader;
import edu.ncsu.csc.itrust.model.old.beans.loaders.ObstetricsVisitLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ChildbirthVisitLoaderTest extends TestCase {
	private IMocksControl ctrl;
	private ResultSet rs;
	private ChildbirthVisitLoader load;
	private DAOFactory factory;

	@Override
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new ChildbirthVisitLoader();
		factory = TestDAOFactory.getTestInstance();
	}

	@Test
	public void testLoadList() throws SQLException {
		List<ChildbirthVisitBean> l = load.loadList(rs);
		assertEquals(0, l.size());
	}
	
	@Test
	public void testLoadSingle() {

		try {
			// this just sets the value for a method call (kinda hard coding I
			// assume)
			expect(rs.getInt("ID")).andReturn(1234321);
			expect(rs.getInt("PatientID")).andReturn(4321234);
			expect(rs.getString("PreferredChildbirthMethod")).andReturn("test");
			expect(rs.getString("Drugs")).andReturn("drug");
			expect(rs.getTimestamp("ScheduledDate")).andReturn(new java.sql.Timestamp(new Date(0).getTime()));
			expect(rs.getBoolean("PreScheduled")).andReturn(true);
			ctrl.replay();

			ChildbirthVisitBean r = load.loadSingle(rs);
			assertEquals(1234321, r.getVisitID());
			assertEquals(4321234, r.getPatientID());
			assertEquals("test", r.getPreferredChildbirthMethod());
			assertEquals("drug", r.getDrugs());
			assertEquals(new java.sql.Timestamp(new Date(0).getTime()), r.getScheduledDate());
			assertEquals(true, r.isPreScheduled());

		} catch (SQLException e) {
			// TODO
		}
	}
	
	@Test
	public void testLoadParameters() {
		try {
			ChildbirthVisitBean cbvb = new ChildbirthVisitBean();
			cbvb.setPatientID(1);	
			cbvb.setPreferredChildbirthMethod("test");
			cbvb.setDrugs("drug");
			cbvb.setScheduledDate(new Timestamp(new Date(0).getTime()));
			cbvb.setPreScheduled(true);
			
			Connection conn = factory.getConnection();
			
			PreparedStatement stmt = load.loadParameters(conn.prepareStatement(
					"INSERT INTO childbirthvisit (PatientID, PreferredChildbirthMethod, Drugs, "
							+ "ScheduledDate, PreScheduled) VALUES (?, ?, ?, ?, ?)"),
					cbvb);
			
			String strSTMT = "INSERT INTO childbirthvisit (PatientID, PreferredChildbirthMethod, Drugs, "
					+ "ScheduledDate, PreScheduled) VALUES (1, 'test', 'drug', '1969-12-31', 1)";
			boolean contains = (stmt.toString()).contains(strSTMT);
			
			assertEquals(true, contains);
					
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testLoadParametersUpdate() {
		try {
			ChildbirthVisitBean cbvb = new ChildbirthVisitBean();
			cbvb.setVisitID(1);
			cbvb.setPatientID(1);	
			cbvb.setPreferredChildbirthMethod("test");
			cbvb.setDrugs("drug");
			cbvb.setScheduledDate(new Timestamp(new Date(0).getTime()));
			cbvb.setPreScheduled(true);
			
			Connection conn = factory.getConnection();
			
			PreparedStatement stmt = load.loadParametersUpdate(conn.prepareStatement(
					"UPDATE childbirthvisit SET PatientID=?, PreferredChildbirthMethod=?,"
							+ "Drugs=?, ScheduledDate=?, PreScheduled=? WHERE ID=?"),
					cbvb);
			String strSTMT = "UPDATE childbirthvisit SET PatientID=1, PreferredChildbirthMethod='test',"
					+ "Drugs='drug', ScheduledDate='1969-12-31', PreScheduled=1 WHERE ID=1";
			boolean contains = (stmt.toString()).contains(strSTMT);
			
			assertEquals(true, contains);
					
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
}