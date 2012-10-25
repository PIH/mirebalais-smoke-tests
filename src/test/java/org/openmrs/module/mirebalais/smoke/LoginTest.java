package org.openmrs.module.mirebalais.smoke;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;


import static org.junit.Assert.assertTrue;

@Ignore
public class LoginTest extends BasicMirebalaisSmokeTest{

    private LoginPage loginPage;

    @Before
    public void setUp() {
    	driver = new ChromeDriver();
    }

    @Override
    protected void specificSetUp() {
		driver.get("http://bamboo.pih-emr.org:8080/mirebalais");

		loginPage = new LoginPage(driver);
    }

	@Test
    public void testLogin() {
    	loginPage.logIn("admin", "Admin123");
    	
    	assertTrue(driver.findElement(By.tagName("body")).getText().contains("Welcome to the Mirebalais EMR."));
    }
	
}
