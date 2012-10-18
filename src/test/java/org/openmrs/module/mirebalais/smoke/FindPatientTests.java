package org.openmrs.module.mirebalais.smoke;

import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.FindPatient;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;

import static org.junit.Assert.fail;

@Ignore
public class FindPatientTests extends BasicMirebalaisSmokeTest {

    private LoginPage loginPage;
    private IdentificationSteps identificationSteps;
    private FindPatient findPatient;

    @Override
    protected void specificSetUp() {
		driver.get("http://bamboo.pih-emr.org:8080/mirebalais");

		loginPage = new LoginPage(driver);
		identificationSteps = new IdentificationSteps(driver, wait);
		findPatient = new FindPatient(driver, wait);
    }

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
