package org.openmrs.module.mirebalais.smoke;

import org.apache.commons.lang.SystemUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientRegistrationDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openmrs.module.mirebalais.smoke.pageobjects.SmokeTestProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public abstract class BasicMirebalaisSmokeTest {

    protected SmokeTestProperties properties = new SmokeTestProperties();
    protected static LoginPage loginPage;
    protected AppDashboard appDashboard;
    protected Registration registration;
    protected PatientRegistrationDashboard patientRegistrationDashboard;
    protected PatientDashboard patientDashboard;
    protected Patient testPatient;
	
	protected static ChromeDriver driver;

    @BeforeClass
    public static void startWebDriver() {
        setupChromeDriver();
    	driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
    	driver.get(new SmokeTestProperties().getWebAppUrl());
    }

	@AfterClass
    public static void stopWebDriver() {
        driver.quit();
    }
	
	protected void initBasicPageObjects() {
		loginPage = new LoginPage(driver);
		registration = new Registration(driver);
		patientRegistrationDashboard = new PatientRegistrationDashboard(driver);
		patientDashboard = new PatientDashboard(driver);
		appDashboard = new AppDashboard(driver);
	}
	
	protected void createPatient() {
		appDashboard.openPatientRegistrationApp();
		registration.goThruRegistrationProcessWithoutPrintingCard(); 
		testPatient = new Patient(patientRegistrationDashboard.getIdentifier(), patientRegistrationDashboard.getName());
	}
	
	protected void startVisit() throws Exception {
		appDashboard.findPatientById(testPatient.getIdentifier());
		patientDashboard.startVisit();
		 
		Wait<WebDriver> wait = new WebDriverWait(driver, 5);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.cssSelector("div.status-container")).isDisplayed();
			}
		});
	}
	
	private static void setupChromeDriver() {
        URL resource = null;
        ClassLoader classLoader = BasicMirebalaisSmokeTest.class.getClassLoader();

        if(SystemUtils.IS_OS_MAC_OSX) {
            resource = classLoader.getResource("chromedriver/mac/chromedriver");
        } else if(SystemUtils.IS_OS_LINUX) {
            resource = classLoader.getResource("chromedriver/linux/chromedriver");
        } else if(SystemUtils.IS_OS_WINDOWS) {
            resource = classLoader.getResource("chromedriver/windows/chromedriver.exe");
        }
        System.setProperty("webdriver.chrome.driver", resource.getPath());

    }

}