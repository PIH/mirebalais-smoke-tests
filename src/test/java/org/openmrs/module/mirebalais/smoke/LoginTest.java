package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@Ignore
public class LoginTest {

	static WebDriver driver;
    
    private LoginPage loginPage;
    
    @Before
    public void setUp() {
    	driver = new FirefoxDriver();
		driver.get("http://bamboo.pih-emr.org:8080/mirebalais");
    
		loginPage = new LoginPage(driver);
    }
    
    @After
    public void tearDown() {
    	driver.close();
    }
    
    
	@Test
    @Ignore
    public void testLogin() {
    	loginPage.logIn("admin", "Admin123");
    	
    	assertTrue(driver.findElement(By.tagName("body")).getText().contains("Welcome to the Mirebalais EMR."));
    }
	
}
