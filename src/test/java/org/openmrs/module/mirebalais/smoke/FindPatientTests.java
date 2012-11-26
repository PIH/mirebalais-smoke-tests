package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.FindPatient;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;

@Ignore
public class FindPatientTests extends BasicMirebalaisSmokeTest {

    private LoginPage loginPage;
    private Registration registration;
    private FindPatient findPatient;

    @Before
    protected void setUp() {
		driver.get(properties.getWebAppUrl());

		loginPage = new LoginPage(driver);
		findPatient = new FindPatient(driver);
    }

	@Test
	@Ignore
	public void test() {
		fail("Not yet implemented");
	}

}
