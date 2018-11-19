package edu.ncsu.csc.itrust.selenium;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ViewChildbirthVisitTest extends iTrustSeleniumTest {

//	public ViewObstetricVisitTest() {
//		// TODO Auto-generated constructor stub
//	}
	
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
	 * Tests the ability for the HCP that is logged in to view a patients Childbirth Visits
	 * @throws Exception
	 */
	
	@Test
	public void testViewChildbirthVisits() throws Exception{
		// Log in as HCP3
		WebDriver wd = login("9000000012","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		wd.findElement(By.linkText("View Childbirth Office Visit")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		
		// Select a patient and view the patient's childbirth visits records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Childbirth Office Visits", wd.getTitle());
		
		// Get the childbirth visits table
		WebElement tableElem = wd.findElements(By.tagName("table")).get(1);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		
		// Check the title and fields of the table
		WebElement row = rowsOnTable.next();
		assertTrue(row.getText().contains("Childbirth Office Visits"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("ID"));
		assertTrue(row.getText().contains("Preferred Childbirth Method"));
		assertTrue(row.getText().contains("Drugs"));
		assertTrue(row.getText().contains("Scheduled Date"));
		assertTrue(row.getText().contains("Prescheduled"));
		assertTrue(row.getText().contains("Action"));		
	}
	
}
