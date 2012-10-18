package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertTrue;

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
    public void findsAMatch() {
    	loginPage.logIn("admin", "Admin123");
    	identificationSteps.setLocationAndChooseRegisterTask();
    	registration.registerSpecificGuy("Jayne", "Marconi");
    	registration.openSimilarityWindow("June", "Marken");
    	
    	assertTrue(driver.findElement(By.id("confirmExistingPatientModalDiv")).getText().contains("June Marken"));
    	assertTrue(driver.findElement(By.className("confirmExistingPatientModalList")).getText().contains("Jayne Marconi"));
    }

}
