package edu.ncsu.csc.itrust.selenium;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddFetusInformationTest extends iTrustSeleniumTest {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		// Turn off htmlunit warnings
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	}

	/**
	 * Tests the ability for the HCP that is logged in to add a fetus record to a pregnancy record
	 * @throws Exception
	 */
	@Test
	public void testaddFetusRecord() throws Exception{
		// Log in as ObHCP
		WebDriver wd = login("9000000012","pw");
		wd.findElement(By.linkText("View Ultrasound Records")).click();
		
		// Select a patient and view the patient's ultrasound records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		
		// Get the ultrasoundList table
		WebElement tableElem = wd.findElements(By.tagName("table")).get(1);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		
		// Check the title of the table
		WebElement row = rowsOnTable.next();
		assertTrue(row.getText().contains("Ultrasound Records"));
		
		//click on add record
		wd.findElement(By.id("addButton")).click();
		assertEquals("iTrust - Add an Ultrasound Record", wd.getTitle());
		
		// Get the add ultrasound form
		WebElement form = wd.findElement(By.id("addUltrasoundRecordForm"));
		
		// upload image
		WebElement element = wd.findElement(By.name("photo"));
		String absolutePath = new File("iTrust/WebRoot/image/colorwheel.jpg").getAbsolutePath();
		element.sendKeys(absolutePath);
		form.submit();
				
		// verify the ultrasound record was saved
		assertEquals("iTrust - View an Ultrasound Record", wd.getTitle());
		
		//click on add record
		wd.findElement(By.id("addButton")).click();
		assertEquals("iTrust - Add a Fetus Information", wd.getTitle());
		
		// addFetusForm
		form = wd.findElement(By.id("addFetusForm"));
		form.findElement(By.name("CRL")).sendKeys("11.1");
		form.findElement(By.name("BPD")).sendKeys("22.2");
		form.findElement(By.name("HC")).sendKeys("33.3");
		form.findElement(By.name("FL")).sendKeys("44.4");
		form.findElement(By.name("OFD")).sendKeys("55.5");
		form.findElement(By.name("AC")).sendKeys("66.6");
		form.findElement(By.name("HL")).sendKeys("77.7");
		form.findElement(By.name("EFW")).sendKeys("88.8");
		form.submit();
		
		// add a second fetus 
		wd.findElement(By.id("addButton")).click();
		assertEquals("iTrust - Add a Fetus Information", wd.getTitle());
		form = wd.findElement(By.id("addFetusForm"));
		form.findElement(By.name("CRL")).sendKeys("20.0");
		form.findElement(By.name("BPD")).sendKeys("20.0");
		form.findElement(By.name("HC")).sendKeys("20.0");
		form.findElement(By.name("FL")).sendKeys("20.0");
		form.findElement(By.name("OFD")).sendKeys("20.0");
		form.findElement(By.name("AC")).sendKeys("20.0");
		form.findElement(By.name("HL")).sendKeys("20.0");
		form.findElement(By.name("EFW")).sendKeys("20.0");
		form.submit();
		
		// verify the fetus record was saved
		assertEquals("iTrust - View an Ultrasound Record", wd.getTitle());
		
		//top table
//		tableData = wd.findElements(By.id("ViewPreg"));
		tableElem = wd.findElements(By.id("ViewPreg")).get(0);
		tableData = tableElem.findElements(By.tagName("tr"));
		rowsOnTable = tableData.iterator();
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Fetus Information"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Crown Rump Length"));
		assertTrue(row.getText().contains("Biparietal Diameter"));
		assertTrue(row.getText().contains("Head Circumference"));
		assertTrue(row.getText().contains("Femur Length"));
		assertTrue(row.getText().contains("Occipitofrontal Diameter"));
		assertTrue(row.getText().contains("Abdominal Circumference"));
		assertTrue(row.getText().contains("Humerus Length"));
		assertTrue(row.getText().contains("Estimated Fetal Weight"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("11.1"));
		assertTrue(row.getText().contains("22.2"));
		assertTrue(row.getText().contains("33.3"));
		assertTrue(row.getText().contains("44.4"));
		assertTrue(row.getText().contains("55.5"));
		assertTrue(row.getText().contains("66.6"));
		assertTrue(row.getText().contains("77.7"));
		assertTrue(row.getText().contains("88.8"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("20.0"));
	}
}
