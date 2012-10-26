package org.openmrs.module.mirebalais.smoke;

import java.net.URL;

import org.apache.commons.lang.SystemUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasicMirebalaisSmokeTest {

	protected static ChromeDriver driver;
    protected static Wait<WebDriver> wait;

    @BeforeClass
    public static void startWebDriver() {
        setupChromeDriver();
    	driver = new ChromeDriver();
    	wait = new WebDriverWait(driver, 30);
    	driver.get("http://bamboo.pih-emr.org:8080/mirebalais");
    }

	@Before
	public void setUp() {
		specificSetUp();
	}
	
	protected abstract void specificSetUp();
	
	@AfterClass
    public static void stopWebDriver() {
        driver.quit();
    }
	
	private static void setupChromeDriver() {
        URL resource = null;
        ClassLoader classLoader = BasicMirebalaisSmokeTest.class.getClassLoader();

        if(SystemUtils.IS_OS_MAC_OSX) {
            resource = classLoader.getResource("chromedriver/mac/chromedriver");
        } else if(SystemUtils.IS_OS_LINUX) {
            resource = classLoader.getResource("chromedriver/linux/chromedriver");
        }
        System.setProperty("webdriver.chrome.driver", resource.getPath());

    }


}