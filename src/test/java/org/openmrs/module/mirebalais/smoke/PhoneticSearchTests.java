package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.By;


public class PhoneticSearchTests extends BasicMirebalaisSmokeTest {

    private static LoginPage loginPage;
    private static IdentificationSteps identificationSteps;
    private Registration registration;
    
    public void specificSetUp() {
		
		registration = new Registration(driver, wait);
    }

    
    @BeforeClass
    public static void setUpEnvironment() {
    	loginPage = new LoginPage(driver);
		identificationSteps = new IdentificationSteps(driver, wait);
		
    	loginPage.logIn("admin", "Admin123");
    	identificationSteps.setLocationAndChooseRegisterTask();
    }

    
    @Test
    public void findsAMatch() {
    	registration.registerSpecificGuy("Jayne", "Marconi");
    	registration.openSimilarityWindow("June", "Marken");
    	
    	assertTrue(driver.findElement(By.id("confirmExistingPatientModalDiv")).getText().contains("June Marken"));
    	assertTrue(driver.findElement(By.className("confirmExistingPatientModalList")).getText().contains("Jayne Marconi"));
    	
    	registration.finishesRegistration();
    }
    
    
    @Test
    public void doesNotfindAMatch() {
    	registration.registerSpecificGuy("June", "Marken");
    	registration.openSimilarityWindow("Jayne", "Marconi");

    	assertTrue(driver.findElement(By.id("confirmExistingPatientModalDiv")).getText().contains("Jayne Marconi"));
    	assertFalse(driver.findElement(By.className("confirmExistingPatientModalList")).getText().contains("June Marken"));
    	
    	registration.finishesRegistration();
    }

}
