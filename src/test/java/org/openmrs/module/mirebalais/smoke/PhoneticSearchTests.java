package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PhoneticSearchTests {

	private static WebDriver driver;
    private static Wait<WebDriver> wait;
    
    private LoginPage loginPage;
    private Registration registration;
    
	@Before
    public void setUp() {
    	driver = new FirefoxDriver();
    	
    	wait = new WebDriverWait(driver, 30);
		driver.get("http://mareias.pih-emr.org:8080/mirebalais");
		
		loginPage = new LoginPage(driver);
		registration = new Registration(driver, wait);
    }

    @After
    public void tearDown() {
    	driver.close();
    }
    
    @Test
    @Ignore
    public void registersPatientWithoutPrintingCard() {
    	loginPage.logIn("admin", "Admin123");
    	//identificationSteps.setLocationAndChooseRegisterTask();
    	registration.goThruRegistrationProcessWithoutPrintingCard();
    	
    	assertTrue(driver.findElement(By.tagName("body")).getText().contains("Patient dashboard"));
    }

}
