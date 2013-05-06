package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;

public class LoginTest extends BasicMirebalaisSmokeTest {

    private LoginPage loginPage;
    private HeaderPage headerPage;
    
    private static final String NEW_LOCATION = "Ijans";

    @Before
    public void setUp() {
		loginPage = new LoginPage(driver);
		headerPage = new HeaderPage(driver);
    }

	@Test
    public void loginAndChangeLocation() {
		loginPage.logInAsAdmin();
		headerPage.changeLocation(NEW_LOCATION);
		assertThat(headerPage.getLocation().contains(NEW_LOCATION), is(true));
    }
	
}
