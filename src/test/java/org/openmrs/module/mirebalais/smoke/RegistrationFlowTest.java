package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationFlowTest extends BasicMirebalaisSmokeTest {

    private Registration registration;
    
    private final static String SCAN_MESSAGE = "Tanpri skane kat idantifikasyon kontinye...";
    
    @Before
    public void setUp() {
		loginPage = new LoginPage(driver);
		registration = new Registration(driver);
		appDashboard = new AppDashboard(driver);
    }

    @Test
    public void registerPatientdPrintingCard() {
    	loginPage.logInAsAdmin();
    	
    	appDashboard.openPatientRegistrationApp();
    	registration.goThruRegistrationProcessPrintingCard();

    	Wait<WebDriver> wait = new WebDriverWait(driver, 2);
    	wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.id("scanPatientIdentifier")).isDisplayed();
			}
		});
    	assertTrue(driver.findElement(By.tagName("body")).getText().contains(SCAN_MESSAGE));
    }
}
