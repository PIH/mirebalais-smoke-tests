package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.By;

public class PhoneticSearchTests extends BasicMirebalaisSmokeTest {

    private LoginPage loginPage;
    private IdentificationSteps identificationSteps;
    private Registration registration;
    
    public void specificSetUp() {
		loginPage = new LoginPage(driver);
		identificationSteps = new IdentificationSteps(driver, wait);
		registration = new Registration(driver, wait);
    }
    
    @Test
    @Ignore
    public void registersPatientWithoutPrintingCard() {
    	loginPage.logIn("admin", "Admin123");
    	identificationSteps.setLocationAndChooseRegisterTask();
    	registration.registerSpecificGuy("Louis", "Marko");
    	registration.openSimilarityWindow();
    	
    	assertTrue(driver.findElement(By.tagName("body")).getText().contains("Louis Marken"));
    }

}
