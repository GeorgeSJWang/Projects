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

public class EditObstetricVisitTest extends iTrustSeleniumTest {
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
	public void testEditObstetricsVisits() throws Exception{
		// Log in as ObHCP
		WebDriver wd = login("9000000012","pw");
		
		// got to add an ob visit 
		wd.findElement(By.linkText("Document Obstetrics Office Visit")).click();
		wd.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		wd.findElement(By.id("mainForm")).submit();
		
		// Get the obstetrics visits table
		WebElement tableElem = wd.findElements(By.tagName("table")).get(0);
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		WebElement row = rowsOnTable.next();
		assertTrue(row.getText().contains("New Obstetrics Visit"));
		
		// Get the add ob visit form
		WebElement form = wd.findElement(By.id("addObVisitForm"));
				
		// Create a new ob visit
		form.findElement(By.name("scheduledDate")).sendKeys("11/03/2019 15:05");
		form.findElement(By.name("weight")).sendKeys("155.0");
		form.findElement(By.name("bloodPressure")).sendKeys("100/110");
		form.findElement(By.name("FHR")).sendKeys("10");
		form.findElement(By.name("numChildren")).sendKeys("2");
		form.submit();
		
		// verify the ob visit was saved
		assertEquals("iTrust - View Obstetrics Office Visits", wd.getTitle());
		tableElem = wd.findElement(By.id("OBlist"));
		tableData = tableElem.findElements(By.tagName("tr"));
		rowsOnTable = tableData.iterator();
		row = rowsOnTable.next();
		row = rowsOnTable.next();
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("11/03/2019 15:05"));
		assertTrue(row.getText().contains("155.0"));
		assertTrue(row.getText().contains("100/110"));
		assertTrue(row.getText().contains("10"));
		assertTrue(row.getText().contains("2"));
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		assertTrue(row.getText().contains(dateFormat.format(date)));
		
		//edit the ob record 
		wd.findElement(By.id("editButton")).click();
		assertEquals("iTrust - Edit an Obstetrics Visit", wd.getTitle());
		form = wd.findElement(By.id("editObVisitForm"));
		form.findElement(By.name("scheduledDate")).clear();
		form.findElement(By.name("scheduledDate")).sendKeys("11/03/2020 15:05");
		form.findElement(By.name("weight")).clear();
		form.findElement(By.name("weight")).sendKeys("160.0");
		form.findElement(By.name("bloodPressure")).clear();
		form.findElement(By.name("bloodPressure")).sendKeys("110/120");
		form.findElement(By.name("FHR")).clear();
		form.findElement(By.name("FHR")).sendKeys("20");
		form.findElement(By.name("LLP")).click();
		form.findElement(By.name("numChildren")).clear();
		form.findElement(By.name("numChildren")).sendKeys("3");
		form.submit();
		
		// verify the ob visit was saved
		assertEquals("iTrust - View Obstetrics Office Visits", wd.getTitle());
		tableElem = wd.findElement(By.id("OBlist"));
		tableData = tableElem.findElements(By.tagName("tr"));
		rowsOnTable = tableData.iterator();
		row = rowsOnTable.next();
		row = rowsOnTable.next();
		row = rowsOnTable.next();
		assertTrue(row.getText().contains("11/03/2020 15:05"));
		assertTrue(row.getText().contains("160.0"));
		assertTrue(row.getText().contains("110/120"));
		assertTrue(row.getText().contains("20"));
		assertTrue(row.getText().contains("true"));
		assertTrue(row.getText().contains("3"));
		assertTrue(row.getText().contains(dateFormat.format(date)));
	}
}
