package org.openmrs.module.mirebalais.smoke;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasicMirebalaisSmokeTest {

	protected static WebDriver driver;
    protected static Wait<WebDriver> wait;
    
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver","chromedriver");
    	driver = new ChromeDriver();
    	
    	wait = new WebDriverWait(driver, 30);
		driver.get("http://bamboo.pih-emr.org:8080/mirebalais");
		
		specificSetUp();
	}

	protected abstract void specificSetUp();
	
	@After
    public void tearDown() {
    	driver.close();
    }
	
}