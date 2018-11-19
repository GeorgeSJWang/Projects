package edu.ncsu.csc.itrust.selenium;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ViewUltrasoundRecordsTest extends iTrustSeleniumTest {
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
	 * Tests the ability for the HCP that is logged in to view a patients ultrasound records
	 * @throws Exception
	 */
	@Test
	public void testViewUltrasound() throws Exception{
		// Log in as ObHCP
		WebDriver wd = login("9000000012","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		wd.findElement(By.linkText("View Ultrasound Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		
		// Select a patient and view the patient's ultrasound records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Ultrasound Records", wd.getTitle());
		
		// Get the ultrasound table
		WebElement tableElem = wd.findElements(By.tagName("table")).get(1);
		//ultrasoundList
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		
		// Check the title and fields of the table
		WebElement row = rowsOnTable.next();
		assertTrue(row.getText().contains("Ultrasound Records"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("ID"));
		assertTrue(row.getText().contains("Patient ID"));
		assertTrue(row.getText().contains("Creation Date"));
		assertTrue(row.getText().contains("Action"));		
	}
	
	/**
	 * Tests the ability for the HCP that is logged in to view a patients ultrasound records
	 * @throws Exception
	 */
	@Test
	public void testViewMultipleUltrasounds() throws Exception{
		// Log in as ObHCP
		WebDriver wd = login("9000000012","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		wd.findElement(By.linkText("View Ultrasound Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		
		// Select a patient and view the patient's ultrasound records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Ultrasound Records", wd.getTitle());
		
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
		
		// go back to view ultrasounds
		wd.findElement(By.linkText("View Ultrasound Records")).click();
		
		//add another record
		wd.findElement(By.id("addButton")).click();
		assertEquals("iTrust - Add an Ultrasound Record", wd.getTitle());
		// Get the add ultrasound form
		form = wd.findElement(By.id("addUltrasoundRecordForm"));
		// upload image
		element = wd.findElement(By.name("photo"));
		absolutePath = new File("iTrust/WebRoot/image/colorwheel.jpg").getAbsolutePath();
		element.sendKeys(absolutePath);
		form.submit();
		// verify the ultrasound record was saved
		assertEquals("iTrust - View an Ultrasound Record", wd.getTitle());
		
		// go back to view ultrasounds
		wd.findElement(By.linkText("View Ultrasound Records")).click();
		
		// Get the ultrasound table
		WebElement tableElem = wd.findElements(By.tagName("table")).get(1);
		//ultrasoundList
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		
		// Check the title and fields of the table
		WebElement row = rowsOnTable.next();
		assertTrue(row.getText().contains("Ultrasound Records"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("ID"));
		assertTrue(row.getText().contains("Patient ID"));
		assertTrue(row.getText().contains("Creation Date"));
		assertTrue(row.getText().contains("Action"));		
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("1"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("1"));
	}
}
