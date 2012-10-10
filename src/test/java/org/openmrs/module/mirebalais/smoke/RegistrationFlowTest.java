package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertTrue;

import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class RegistrationFlowTest  {

    static WebDriver driver;
    static Wait<WebDriver> wait;
    
    private LoginPage loginPage;
    private IdentificationSteps identificationSteps;
    private Registration registration;
    
    
    @Before
    public void setUp() {
    	driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 30);
		driver.get("http://bamboo.pih-emr.org:8080/mirebalais");
    
		loginPage = new LoginPage(driver);
		identificationSteps = new IdentificationSteps(driver, wait);
		registration = new Registration(driver, wait);
    }
    
    @After
    public void tearDown() {
    	driver.close();
    }
     
    @Test
    public void testRegistratePacientWithoutPrinting() {
    	loginPage.logIn("admin", "Admin123");
    	identificationSteps.setLocationAndChooseRegisterTask();
    	registration.goThruRegistrationProcessWithoutPrintingCard();
    	
    	assertTrue(driver.findElement(By.tagName("body")).getText().contains("Patient dashboard"));
    }
    
    @Test
    @Ignore // until we define what's going on with the printer functionality
    public void testRegistratePacientAndPrintingCard() {
    	loginPage.logIn("admin", "Admin123");
    	identificationSteps.setLocationAndChooseRegisterTask();
    	registration.goThruRegistrationProcessPrintingCard();
    	
    	assertTrue(driver.findElement(By.tagName("body")).getText().contains("Patient dashboard"));
    	// TODO: make sure it printed
    }
}