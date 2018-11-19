package edu.ncsu.csc.itrust.selenium;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddObstetricRecordTest extends iTrustSeleniumTest{
	
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
	 * Tests the ability for the OB/GYN HCP that is logged in to add a patients Obstetrics information
	 * @throws Exception
	 */
	@Test
	public void testAddObstetricsRecords() throws Exception{
		// Log in as OBHCP0
		WebDriver wd = login("9000000012","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
				
		wd.findElement(By.linkText("Patient Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
				
		// Select a Patient and view the Patient's Obstetrics Records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
				
		// Click on the add button which has the id "addButton"
		wd.findElement(By.id("addButton")).click();
		assertEquals("iTrust - Add an Obstetrics Record", wd.getTitle());
				
		// Get the add prior pregnancy form
		WebElement form = wd.findElement(By.id("addPregnancyForm"));
		
		// Create a new prior pregnancy
		form.findElement(By.name("YOC")).sendKeys("2012");
		form.findElement(By.name("num_weeks_pregnant")).sendKeys("40");
		form.findElement(By.name("num_hours_labor")).sendKeys("20");
		form.findElement(By.name("weight_gain")).sendKeys("10.0");
		form.findElement(By.name("delivery_type")).sendKeys("vaginal delivery");
		form.findElement(By.name("num_children")).sendKeys("2");
		form.submit();
		
		// Verify the new prior pregnancy was saved
		WebElement tableElem = wd.findElement(By.id("AddOBViewPreg"));
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		WebElement row = rowsOnTable.next();
		assertTrue(row.getText().contains("Prior Pregnancies"));
		row = rowsOnTable.next();
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("2012"));
		assertTrue(row.getText().contains("40"));
		assertTrue(row.getText().contains("20"));
		assertTrue(row.getText().contains("10.0"));
		assertTrue(row.getText().contains("vaginal delivery"));
		assertTrue(row.getText().contains("2"));
		
		// Get the date of today
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		
		// Get the add obstetrics record form
		form = wd.findElement(By.id("addObRecordForm"));
		
		// Check if the date field is filled in automatically
		assertTrue(form.findElement(By.name("created_on")).toString().contains(dateFormat.format(date)));
		
		// Create a new obstetrics record
		form.findElement(By.name("LMP")).sendKeys("08/05/2018");
		form.submit();
		
		// Check if the record is successfully added
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("New Obstetrics Record successfully added!"));
		
		// Go back to the obstetrics records page
		wd.findElement(By.id("backtoview")).click();
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
		
		tableElem = wd.findElements(By.tagName("table")).get(1);
		tableElem.findElements(By.id("viewButton")).get(0).click();
		assertEquals("iTrust - View an Obstetrics Record", wd.getTitle());
				
		// Verify the new obstetrics record was saved
		tableElem = wd.findElements(By.tagName("table")).get(0);
		tableData = tableElem.findElements(By.tagName("tr"));
		rowsOnTable = tableData.iterator();
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("Obstetrics Record"));
		row = rowsOnTable.next();
		row = rowsOnTable.next();
		// Check EDD
		assertTrue(row.getText().contains("05/12/2019"));
		// Check Num Weeks Pregnant
		assertTrue(row.getText().contains("12"));		
	}
	
	/**
	 * Tests the inability for the normal HCP that is logged in to add a patients Obstetrics information 
	 * @throws Exception
	 */
	@Test
	public void testFailAddObstetricsRecords() throws Exception{
		// Log in as HCP3
		WebDriver wd = login("9000000003","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
				
		wd.findElement(By.linkText("Patient Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
				
		// Select a Patient and view the Patient's Obstetrics Records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
				
		// Normal HCP should not find the "addButton"
		assertTrue(wd.findElements(By.id("addButton")).size() < 1);
		
	}
	
	/**
	 * Tests if error message is printed when enter blank input
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBlankObstetricsInput() throws Exception{
		// Log in as OBHCP0
		WebDriver wd = login("9000000012","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
				
		wd.findElement(By.linkText("Patient Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
				
		// Select a Patient and view the Patient's Obstetrics Records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
				
		// Click on the add button which has the id "addButton"
		wd.findElement(By.id("addButton")).click();
		assertEquals("iTrust - Add an Obstetrics Record", wd.getTitle());
		
		// Enter blank LMP
		WebElement form = wd.findElement(By.id("addObRecordForm"));
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter blank YearOfConception
		form = wd.findElement(By.id("addPregnancyForm"));
		form.findElement(By.name("num_weeks_pregnant")).sendKeys("40");
		form.findElement(By.name("num_hours_labor")).sendKeys("25");
		form.findElement(By.name("weight_gain")).sendKeys("10");
		form.findElement(By.name("delivery_type")).sendKeys("vaginal delivery");
		form.findElement(By.name("num_children")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter blank num_weeks_pregnant
		form = wd.findElement(By.id("addPregnancyForm"));
		form.findElement(By.name("YOC")).sendKeys("2012");
		form.findElement(By.name("num_hours_labor")).sendKeys("25");
		form.findElement(By.name("weight_gain")).sendKeys("10");
		form.findElement(By.name("delivery_type")).sendKeys("vaginal delivery");
		form.findElement(By.name("num_children")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter blank num_hours_labor
		form = wd.findElement(By.id("addPregnancyForm"));
		form.findElement(By.name("YOC")).sendKeys("2012");
		form.findElement(By.name("num_weeks_pregnant")).sendKeys("40");
		form.findElement(By.name("weight_gain")).sendKeys("10");
		form.findElement(By.name("delivery_type")).sendKeys("vaginal delivery");
		form.findElement(By.name("num_children")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter blank weight_gain
		form = wd.findElement(By.id("addPregnancyForm"));
		form.findElement(By.name("YOC")).sendKeys("2012");
		form.findElement(By.name("num_weeks_pregnant")).sendKeys("40");
		form.findElement(By.name("num_hours_labor")).sendKeys("25");
		form.findElement(By.name("delivery_type")).sendKeys("vaginal delivery");
		form.findElement(By.name("num_children")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter blank delivery_type
		form = wd.findElement(By.id("addPregnancyForm"));
		form.findElement(By.name("YOC")).sendKeys("2012");
		form.findElement(By.name("num_weeks_pregnant")).sendKeys("40");
		form.findElement(By.name("num_hours_labor")).sendKeys("25");
		form.findElement(By.name("weight_gain")).sendKeys("10");
		form.findElement(By.name("num_children")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter blank num_children
		form = wd.findElement(By.id("addPregnancyForm"));
		form.findElement(By.name("YOC")).sendKeys("2012");
		form.findElement(By.name("num_weeks_pregnant")).sendKeys("40");
		form.findElement(By.name("num_hours_labor")).sendKeys("25");
		form.findElement(By.name("weight_gain")).sendKeys("10");
		form.findElement(By.name("delivery_type")).sendKeys("vaginal delivery");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));		
	}
	
	/**
	 * Tests invalid input.
	 * Case 1: LMP 123
	 * Case 2: YearOfConception 992
	 * Case 3: num_weeks_pregnant 370
	 * Case 4: num_hours_labor 200
	 * Case 5: num_children 20
	 * Valid case: LMP 08/05/2018, YearOfConception 1992, num_weeks_pregnant 37, num_hours_labor 20, num_children 2
	 * @throws Exception
	 */
	@Test
	public void testBadObstetricsInput() throws Exception{
		// Log in as OBHCP0
		WebDriver wd = login("9000000012","pw");
		assertEquals("iTrust - HCP Home", wd.getTitle());
				
		wd.findElement(By.linkText("Patient Records")).click();
		assertEquals("iTrust - Please Select a Patient", wd.getTitle());
				
		// Select a Patient and view the Patient's Obstetrics Records 
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		assertEquals("iTrust - View Obstetrics Records", wd.getTitle());
				
		// Click on the add button which has the id "addButton"
		wd.findElement(By.id("addButton")).click();
		assertEquals("iTrust - Add an Obstetrics Record", wd.getTitle());
		
		// Enter bad LMP
		WebElement form = wd.findElement(By.id("addObRecordForm"));
		form.findElement(By.name("LMP")).sendKeys("123");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter bad YearOfConception
		form = wd.findElement(By.id("addPregnancyForm"));
		form.findElement(By.name("YOC")).sendKeys("992");
		form.findElement(By.name("num_weeks_pregnant")).sendKeys("37");
		form.findElement(By.name("num_hours_labor")).sendKeys("20");
		form.findElement(By.name("weight_gain")).sendKeys("10");
		form.findElement(By.name("delivery_type")).sendKeys("vaginal delivery");
		form.findElement(By.name("num_children")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter bad num_weeks_pregnant
		form = wd.findElement(By.id("addPregnancyForm"));
		form.findElement(By.name("YOC")).sendKeys("1992");
		form.findElement(By.name("num_weeks_pregnant")).sendKeys("370");
		form.findElement(By.name("num_hours_labor")).sendKeys("20");
		form.findElement(By.name("weight_gain")).sendKeys("10");
		form.findElement(By.name("delivery_type")).sendKeys("vaginal delivery");
		form.findElement(By.name("num_children")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		// Enter bad num_hours_labor
		form = wd.findElement(By.id("addPregnancyForm"));
		form.findElement(By.name("YOC")).sendKeys("1992");
		form.findElement(By.name("num_weeks_pregnant")).sendKeys("37");
		form.findElement(By.name("num_hours_labor")).sendKeys("200");
		form.findElement(By.name("weight_gain")).sendKeys("10");
		form.findElement(By.name("delivery_type")).sendKeys("vaginal delivery");
		form.findElement(By.name("num_children")).sendKeys("2");
		form.submit();
		assertTrue(wd.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
	}

}
