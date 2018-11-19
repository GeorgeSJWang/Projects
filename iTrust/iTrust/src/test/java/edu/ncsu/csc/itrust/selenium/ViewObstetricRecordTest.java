package edu.ncsu.csc.itrust.selenium;


import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ViewObstetricRecordTest extends iTrustSeleniumTest{
	
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
	 * Just a sample test
	 * Please ignore this test
	 * @throws Exception
	 */
	@Test
	public void testSample() throws Exception{
		WebDriver wd = login("9000000003","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		wd.findElement(By.linkText("Patient Information")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		
		// Fill in search form
		
		//wd.findElement(By.id("searchBox")).sendKeys("1");
		
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		
		/*
		wd.findElement(By.name("FIRST_NAME")).clear();
		wd.findElement(By.name("FIRST_NAME")).sendKeys("Random");
		wd.findElement(By.name("LAST_NAME")).clear();
		wd.findElement(By.name("FIRST_NAME")).sendKeys("Person");
		wd.findElement(By.cssSelector("input[type='submit']")).click();
		*/
		
		//wd.findElement(By.xpath("//input[@value='1']")).submit();
		//wd.findElements(By.tagName("input")).get(1).click();
		assertEquals("iTrust - Edit Patient", wd.getTitle());
		
		WebElement tableElem = wd.findElements(By.tagName("table")).get(1);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		
		WebElement row = rowsOnTable.next();
		assertTrue(row.getText().contains("Patient Information"));

		row = rowsOnTable.next();
		assertTrue(row.getText().contains("First Name:"));
		assertTrue(row.findElement(By.tagName("input")).toString().contains("Random"));
		
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Last Name:"));

	}
	
	
	/**
	 * Tests the ability for the HCP that is logged in to view a patients Obstetrics information if that obstetric information exists
	 * if any exists
	 * @throws Exception
	 */
	
	@Test
	public void testViewObstetricsRecords() throws Exception{
		// Log in as HCP3
		WebDriver wd = login("9000000003","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		wd.findElement(By.linkText("Patient Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		
		// Select a patient and view the patient's obstetrics records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
		
		// Get the obstetrics records table
		WebElement tableElem = wd.findElements(By.tagName("table")).get(1);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		
		// Check the title and fields of the table
		WebElement row = rowsOnTable.next();
		assertTrue(row.getText().contains("Obstetrics Records"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("ID"));
		assertTrue(row.getText().contains("Patient ID"));
		assertTrue(row.getText().contains("Creation Date"));
		assertTrue(row.getText().contains("Action"));
		
		// Check the first record in table
		/*
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("1"));
		assertTrue(row.getText().contains("1"));
		assertTrue(row.getText().contains("10/30/2018"));
		*/
		
		// Click on an existing record and enter the view page
		tableElem.findElements(By.id("viewButton")).get(0).click();
		assertEquals("iTrust - View an Obstetrics Record", wd.getTitle());
		
		// Get the obtetrics record table
		tableElem = wd.findElements(By.tagName("table")).get(0);
		tableData = tableElem.findElements(By.tagName("tr"));
		rowsOnTable = tableData.iterator();
		
		// Check the title and fields of the obtetrics record table
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Obstetrics Record"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Estimated Due Date"));
		assertTrue(row.getText().contains("Num Weeks Pregnant"));
		assertTrue(row.getText().contains("Report Created On"));
		
		// Check for a obtetrics record
		/*
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("07/08/2019"));
		assertTrue(row.getText().contains("4"));
		*/
		
		// Get the prior pregnancies table
		tableElem = wd.findElements(By.tagName("table")).get(2);
		tableData = tableElem.findElements(By.tagName("tr"));
		rowsOnTable = tableData.iterator();
				
		// Check the title and fields of the prior pregnancies table
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Prior Pregnancies"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Year of Conception"));
		assertTrue(row.getText().contains("Weeks Pregnant"));
		assertTrue(row.getText().contains("Hours of Labor"));
		assertTrue(row.getText().contains("Weight Gain"));
		assertTrue(row.getText().contains("Delivery Type"));
		assertTrue(row.getText().contains("Num Children"));
		
		// Check for a prior pregnancy record
		/*
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("2011"));
		assertTrue(row.getText().contains("41"));
		assertTrue(row.getText().contains("14"));
		assertTrue(row.getText().contains("10"));
		assertTrue(row.getText().contains("caesarean section"));
		assertTrue(row.getText().contains("2"));
		*/
		
	}
	
	/**
	 * View Obstetrics information for patient that is not eligible
	 * @throws Exception
	 */
	public void testViewObstetricsNotEligible() throws Exception{
		// Login as HCP3
		WebDriver wd = login("9000000003","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		wd.findElement(By.linkText("Patient Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		
		// Select Patient who is not eligible
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("21");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
		
		// The page should show error message for not eligible patient
		assertTrue(wd.findElement(By.id("ViewObRecordError")).getText().contains("The patient is not eligible for obstetric care."));
		
		// Click on "no" button, prompted to search a patient again
		wd.findElement(By.id("CancelEligible")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
	}
	
	/**
	 * Change the patient’s eligibility for patient that is not eligible
	 * @throws Exception
	 */
	public void testViewObstetricsSetEligible() throws Exception{
		// Login as HCP3
		WebDriver wd = login("9000000003","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
		
		wd.findElement(By.linkText("Patient Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
		
		// Select Patient who is not eligible
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("21");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
		
		// There should be no Obstetrics Record
		assertTrue(wd.findElement(By.id("ViewObRecordError")).getText().contains("The patient is not eligible for obstetric care."));
		
		// Click on "yes" button, change the patient’s eligibility
		wd.findElement(By.id("SetEligible")).click();
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
		
		// Get the obstetrics Records Table
		WebElement tableElem = wd.findElements(By.tagName("table")).get(1);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		
		// Check the name and fields of the table
		WebElement row = rowsOnTable.next();
		assertTrue(row.getText().contains("Obstetrics Records"));
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("ID"));
		assertTrue(row.getText().contains("Patient ID"));
		assertTrue(row.getText().contains("Creation Date"));
		assertTrue(row.getText().contains("Action"));
	}

}
