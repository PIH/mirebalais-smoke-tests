package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;

public class FindPatientTests extends BasicMirebalaisSmokeTest {

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
	public void test() {
		fail("Not yet implemented");
	}

}
