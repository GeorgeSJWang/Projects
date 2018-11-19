package edu.ncsu.csc.itrust.selenium;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ViewObstetricVisitTest extends iTrustSeleniumTest {

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
	 * Tests the ability for the HCP that is logged in to view a patients Obstetrics Visits
	 * @throws Exception
	 */
	
	@Test
	public void testViewObstetricsVisits() throws Exception{
		// Log in as HCP3
		WebDriver wd = login("9000000012","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		wd.findElement(By.linkText("View Obstetrics Office Visit")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		
		// Select a patient and view the patient's obstetrics visits records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Obstetrics Office Visits", wd.getTitle());
		
		// Get the obstetrics visits table
		WebElement tableElem = wd.findElements(By.tagName("table")).get(1);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		
		// Check the title and fields of the table
		WebElement row = rowsOnTable.next();
		assertTrue(row.getText().contains("Obstetrics Office Visits"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("ID"));
		assertTrue(row.getText().contains("Scheduled Date"));
		assertTrue(row.getText().contains("Weeks Pregnant"));
		assertTrue(row.getText().contains("Weight(pounds)"));
		assertTrue(row.getText().contains("Blood Pressure"));
		assertTrue(row.getText().contains("Fetal heart rate"));
		assertTrue(row.getText().contains("Num Children"));
		assertTrue(row.getText().contains("Low Lying Placenta"));
		assertTrue(row.getText().contains("Creation Date"));
		assertTrue(row.getText().contains("Action"));		
	}
	
}
