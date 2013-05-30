package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.helper.Waiter;
import org.openqa.selenium.By;

public class RegistrationFlowTest extends BasicMirebalaisSmokeTest {

    private final static String SCAN_MESSAGE = "Tanpri skane kat idantifikasyon kontinye...";
    
    @Before
    public void setUp() {
    	initBasicPageObjects();
    }

    @Test
    public void registerPatientdPrintingCard() {
    	loginPage.logInAsAdmin();
    	
    	appDashboard.openPatientRegistrationApp();
    	registration.goThruRegistrationProcessPrintingCard();

    	Waiter.waitForElementToDisplay(By.id("scanPatientIdentifier"), 2, driver);
    	assertTrue(driver.findElement(By.tagName("body")).getText().contains(SCAN_MESSAGE));
    }
}
