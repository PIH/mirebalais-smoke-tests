package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class RegistrationFlowTest extends BasicMirebalaisSmokeTest {

    private LoginPage loginPage;
    private IdentificationSteps identificationSteps;
    private Registration registration;
    private AppDashboard appDashboard;
    
    @Before
    public void setUp() {
		loginPage = new LoginPage(driver);
		identificationSteps = new IdentificationSteps(driver);
		registration = new Registration(driver);
		appDashboard = new AppDashboard(driver);
    }

    @Test
    public void registerPatientdPrintingCard() {
    	loginPage.logIn("admin", "Admin123");
    	
    	appDashboard.openPatientRegistrationApp();
		identificationSteps.setLocationAndChooseRegisterTask();
    	registration.goThruRegistrationProcessPrintingCard();

    	Wait<WebDriver> wait = new WebDriverWait(driver, 2);
    	wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.id("scanPatientIdentifier")).isDisplayed();
			}
		});
    	assertTrue(driver.findElement(By.tagName("body")).getText().contains("Please scan ID card to proceed..."));
    	// TODO: make sure it printed
    }
}
