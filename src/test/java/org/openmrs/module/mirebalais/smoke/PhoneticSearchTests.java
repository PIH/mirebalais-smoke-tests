package org.openmrs.module.mirebalais.smoke;

import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.By;

import static org.junit.Assert.assertTrue;

@Ignore
public class PhoneticSearchTests extends BasicMirebalaisSmokeTest {

    private LoginPage loginPage;
    private Registration registration;
    
    @Override
    protected void specificSetUp() {
		driver.get("http://mareias.pih-emr.org:8080/mirebalais");

		loginPage = new LoginPage(driver);
		registration = new Registration(driver, wait);
    }

    @Test
    public void registersPatientWithoutPrintingCard() {
    	loginPage.logIn("admin", "Admin123");
    	//identificationSteps.setLocationAndChooseRegisterTask();
    	registration.goThruRegistrationProcessWithoutPrintingCard();
    	
    	assertTrue(driver.findElement(By.tagName("body")).getText().contains("Patient dashboard"));
    }

}
