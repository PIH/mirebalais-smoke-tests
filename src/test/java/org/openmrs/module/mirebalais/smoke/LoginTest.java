package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openqa.selenium.By;
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
		
		Wait<WebDriver> wait = new WebDriverWait(driver, 2);
    	wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.className("icon-caret-down")).isDisplayed();
			}
		});
		assertThat(headerPage.getLocation().contains(NEW_LOCATION), is(true));
    }
	
}
