package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.FindPatient;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

@Ignore
public class FindPatientTests {

	private static WebDriver driver;
    private static Wait<WebDriver> wait;
    
    private LoginPage loginPage;
    private IdentificationSteps identificationSteps;
    private FindPatient findPatient;
    
	@Before
    public void setUp() {
    	driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 30);
		driver.get("http://bamboo.pih-emr.org:8080/mirebalais");
    
		loginPage = new LoginPage(driver);
		identificationSteps = new IdentificationSteps(driver, wait);
		findPatient = new FindPatient(driver, wait);
    }
    
    @After
    public void tearDown() {
    	driver.close();
    }
    
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
