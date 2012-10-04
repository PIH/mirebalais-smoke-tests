package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IdentificationStepsTest {

	static WebDriver driver;
	static Wait<WebDriver> wait;
	
    private LoginPage loginPage;
    private IdentificationSteps identificationSteps;
    
    @Before
    public void setUp() {
    	driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 30);
		driver.get("http://bamboo.pih-emr.org:8080/mirebalais");
    
		loginPage = new LoginPage(driver);
		identificationSteps = new IdentificationSteps(driver, wait);
    }
    
    @After
    public void tearDown() {
    	driver.close();
    }
    
	@Test
    @Ignore
    public void testNavigateUntilFirstRegistrationPage() {
    	loginPage.logIn("admin", "Admin123");
    	identificationSteps.setLocationAndChooseRegisterTask();
    	
    	assertNotNull(driver.findElement(By.id("patientInputLastName")));
    }
	
}
