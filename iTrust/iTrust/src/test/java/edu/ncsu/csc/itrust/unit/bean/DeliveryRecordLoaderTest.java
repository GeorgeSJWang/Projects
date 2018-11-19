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

import edu.ncsu.csc.itrust.model.old.beans.DeliveryRecordBean;
import edu.ncsu.csc.itrust.model.old.beans.loaders.DeliveryRecordLoader;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.model.old.enums.Gender;
import junit.framework.TestCase;

public class DeliveryRecordLoaderTest extends TestCase {
	private IMocksControl ctrl;
	private ResultSet rs;
	private DeliveryRecordLoader loader;
	private DAOFactory factory;

	@Override 
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		loader = new DeliveryRecordLoader();
		factory = TestDAOFactory.getTestInstance();
		
	}

	@Test
	public void testLoadList() throws SQLException {
		List<DeliveryRecordBean> drbList = loader.loadList(rs);
		assertEquals(0, drbList.size());
		
	}
	
	@Test
	public void testLoadSingle() {

		try {
			expect(rs.getInt("ID")).andReturn(7);
			expect(rs.getInt("PatientID")).andReturn(17);
			expect(rs.getInt("ChildbirthVisitID")).andReturn(117);
			expect(rs.getInt("ChildID")).andReturn(17);
			expect(rs.getString("Gender")).andReturn("Male");
			expect(rs.getTimestamp("DeliveryDateTime")).andReturn(new java.sql.Timestamp(new Date(0).getTime()));
			expect(rs.getString("DeliveryMethod")).andReturn("caesarean section");
			expect(rs.getBoolean("IsEstimated")).andReturn(true);
			ctrl.replay();

			DeliveryRecordBean drb = loader.loadSingle(rs);
			assertEquals(7, drb.getID());
			assertEquals(17, drb.getPatientID());
			assertEquals(117, drb.getChildbirthVisitID());
			assertEquals(17, drb.getChildID());
			assertEquals(Gender.Male, drb.getGender());
			assertEquals(new java.sql.Timestamp(new Date(0).getTime()), drb.getDeliveryDateTime());
			assertEquals("caesarean section", drb.getDeliveryMethod());
			assertEquals(true, drb.getIsEstimated());
		} catch (SQLException e) {
			// TODO
		}
	}
	
	@Test
	public void testLoadParameters() {
		try {
			DeliveryRecordBean drb = new DeliveryRecordBean();
			drb.setPatientID(1);
			drb.setChildbirthVisitID(17);
			drb.setChildID(7);
			drb.setGender(Gender.Female);
			drb.setDeliveryDateTime(new Timestamp(new Date(0).getTime()));
			drb.setDeliveryMethod("caesarean section");
			drb.setIsEstimated(true);
						
			Connection conn = factory.getConnection();
			
			PreparedStatement stmt = loader.loadParameters(conn.prepareStatement(
					"INSERT INTO deliveryrecords (PatientID, ChildbirthVisitID, ChildID, Gender, "
					+ "DeliveryDateTime, DeliveryMethod, IsEstimated VALUES (?, ?, ?, ?, ?, ?, ?)"),
					drb);

			String strSTMT = "INSERT INTO deliveryrecords (PatientID, ChildbirthVisitID, ChildID, Gender, "
					+ "DeliveryDateTime, DeliveryMethod, IsEstimated VALUES (1, 17, 7, 'Female', "
					+ "'1969-12-31 18:00:00', 'caesarean section', 1)";
			boolean contains = (stmt.toString()).contains(strSTMT);
			assertEquals(true, contains);
					
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testLoadParametersUpdate() {
		try {
			DeliveryRecordBean drb = new DeliveryRecordBean();
			drb.setID(11);
			drb.setPatientID(1);
			drb.setChildbirthVisitID(17);
			drb.setChildID(17);
			drb.setGender(Gender.Female);
			drb.setDeliveryDateTime(new Timestamp(new Date(0).getTime()));
			drb.setDeliveryMethod("caesarean section");
			drb.setIsEstimated(true);
			
			Connection conn = factory.getConnection();
			
			PreparedStatement stmt = loader.loadParametersUpdate(conn.prepareStatement(
					"UPDATE deliveryrecords SET PatientID=?, ChildbirthVisitID=?, ChildID=?, Gender=?, "
							+ "DeliveryDateTime=?, DeliveryMethod=?, IsEstimated=? WHERE ID=?"),
					drb);
			
			String strSTMT = "UPDATE deliveryrecords SET PatientID=1, ChildbirthVisitID=17, ChildID=17, "
					+ "Gender='Female', DeliveryDateTime='1969-12-31 18:00:00', "
					+ "DeliveryMethod='caesarean section', IsEstimated=1 WHERE ID=11";
			boolean contains = (stmt.toString()).contains(strSTMT);
			assertEquals(true, contains);
					
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
}
