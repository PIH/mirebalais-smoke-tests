package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

@Ignore
public class LoginTest extends BasicMirebalaisSmokeTest{

    private LoginPage loginPage;

    @Before
    public void setUp() {
    	driver = new ChromeDriver();
		driver.get(properties.getWebAppUrl());

		loginPage = new LoginPage(driver);
    }

	@Test
    public void testLogin() {
    	loginPage.logIn("admin", "Admin123");
    	
    	assertTrue(driver.findElement(By.tagName("body")).getText().contains("Welcome to the Mirebalais EMR."));
    }
	
}
