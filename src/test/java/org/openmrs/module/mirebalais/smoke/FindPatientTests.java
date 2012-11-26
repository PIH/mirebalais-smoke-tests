package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.FindPatient;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;

@Ignore
public class FindPatientTests extends BasicMirebalaisSmokeTest {

    private LoginPage loginPage;
    private IdentificationSteps identificationSteps;
    private Registration registration;
    private FindPatient findPatient;

    @Before
    protected void setUp() {
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
