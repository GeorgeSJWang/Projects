package edu.ncsu.csc.itrust.unit.dao.ultrasound;

import junit.framework.TestCase;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.sql.rowset.serial.SerialBlob;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import edu.ncsu.csc.itrust.model.old.dao.mysql.UltrasoundDAO;
import edu.ncsu.csc.itrust.model.old.beans.UltrasoundBean;
import edu.ncsu.csc.itrust.model.old.beans.FetusBean;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class UltrasoundDAOTest extends TestCase {
	UltrasoundDAO ultrasoundDAO = TestDAOFactory.getTestInstance().getUltrasoundDAO();
	TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.uc94();
	}
	
	@Test
	public void testAddUltrasound() throws Exception {
		UltrasoundBean u = new UltrasoundBean();
		u.setPatientID(1);
		InputStream iS = new ByteArrayInputStream("ultrasound".getBytes());
		u.setInputStream(iS);
		List<FetusBean> l = new ArrayList<>();
		u.setFetus(l);
		u.setImageType("image/png");
		u.setCreated_on(new Date(0));
		
		ultrasoundDAO.addRecord(u);
		
		List<UltrasoundBean> ubList = ultrasoundDAO.getAllUltrasounds(1);
		assertEquals(1, ubList.size());
		assertEquals("image/png", ubList.get(0).getImageType());
		
		UltrasoundBean uResult = ultrasoundDAO.getUltrasound(ubList.get(0).getRecordID());
		assertEquals("image/png", uResult.getImageType());
		assertEquals(1, uResult.getPatientID());
	}
	
	@Test
	public void testAddFetus() throws Exception {
		long recordID = 0;
		long patientID = 1;
		long ultrasoundID = 2;
		Date createdOn = new Date();
		float CRL = 1.0f;
		float BPD = 2.0f;
		float HC = 3.0f;
		float FL = 4.0f;
		float OFD = 5.0f;
		float AC = 6.0f;
		float HL = 7.0f;
		float EFW = 7.0f;
		
		FetusBean f = new FetusBean();
		f.setRecordID(recordID);
		f.setPatientID(patientID);
		f.setUltrasoundID(ultrasoundID);
		f.setCreated_on(createdOn);
		f.setCRL(CRL);
		f.setBPD(BPD);
		f.setHC(HC);
		f.setFL(FL);
		f.setOFD(OFD);
		f.setAC(AC);
		f.setHL(HL);
		f.setEFW(EFW);
		
		ultrasoundDAO.addFetus(f);
		
		List<FetusBean> fList = ultrasoundDAO.getAllFetus(1);
		assertEquals(1, fList.size());
		assertEquals(4.0f, fList.get(0).getFL());
		assertEquals(1.0f, fList.get(0).getCRL());
	}
	
	@Test
	public void testGetImageType() throws Exception{
		UltrasoundBean u = new UltrasoundBean();
		u.setPatientID(1);
		InputStream iS = new ByteArrayInputStream("ultrasound".getBytes());
		u.setInputStream(iS);
		List<FetusBean> l = new ArrayList<>();
		u.setFetus(l);
		u.setImageType("image/png");
		u.setCreated_on(new Date(0));
		
		long uid = ultrasoundDAO.addRecord(u);
		
		String imageType = ultrasoundDAO.getImageType(uid);
		assertEquals("image/png", imageType);
	}
	
	@Test
	public void testGetImage() throws Exception{
		UltrasoundBean u = new UltrasoundBean();
		u.setPatientID(1);
		InputStream iS = new ByteArrayInputStream("ultrasound".getBytes());
		u.setInputStream(iS);
		List<FetusBean> l = new ArrayList<>();
		u.setFetus(l);
		u.setImageType("image/png");
		u.setCreated_on(new Date(0));
		
		long uid = ultrasoundDAO.addRecord(u);
		
		InputStream image = ultrasoundDAO.getImageData(uid);
		assertEquals(true, !image.equals(null));
	}
}
