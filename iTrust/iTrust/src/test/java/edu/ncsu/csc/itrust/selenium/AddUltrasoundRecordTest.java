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

public class AddUltrasoundRecordTest extends iTrustSeleniumTest {
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
	 * Tests the ability for the HCP that is logged in to add a patients ultrasound record
	 * @throws Exception
	 */
	@Test
	public void testaddUltrasoundRecord() throws Exception{
		// Log in as ObHCP
		WebDriver wd = login("9000000012","pw");
		wd.findElement(By.linkText("View Ultrasound Records")).click();
		
		// Select a patient and view the patient's ultrasound records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Ultrasound Records", wd.getTitle());
		
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
		
		//top table
		tableElem = wd.findElements(By.id("topTable")).get(0);
		tableData = tableElem.findElements(By.tagName("tr"));
		rowsOnTable = tableData.iterator();
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Ultrasound Record"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("ID"));
		assertTrue(row.getText().contains("Patient ID"));
		assertTrue(row.getText().contains("Record Created On"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("1"));
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		assertTrue(row.getText().contains(dateFormat.format(date)));
	}
	
	
}
