package org.openmrs.module.mirebalais.smoke;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    public void loginAndChangeLocation() throws Exception {
		loginPage.logInAsAdmin();
		headerPage.changeLocation(NEW_LOCATION);
		
		Wait<WebDriver> wait = new WebDriverWait(driver, 10);
    	wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				return headerPage.getLocation().contains(NEW_LOCATION);
			}
		});
    }
	
}
