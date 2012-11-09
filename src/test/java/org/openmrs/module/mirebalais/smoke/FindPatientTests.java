package org.openmrs.module.mirebalais.smoke;

import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.FindPatient;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;

import static org.junit.Assert.fail;

@Ignore
public class FindPatientTests extends BasicMirebalaisSmokeTest {

    private LoginPage loginPage;
    private IdentificationSteps identificationSteps;
    private Registration registration;
    private FindPatient findPatient;

    @Override
    protected void specificSetUp() {
		driver.get(properties.getWebAppUrl());

		loginPage = new LoginPage(driver);
		identificationSteps = new IdentificationSteps(driver);
		findPatient = new FindPatient(driver);
    }

	@Test
	@Ignore
	public void test() {
		fail("Not yet implemented");
	}

}
