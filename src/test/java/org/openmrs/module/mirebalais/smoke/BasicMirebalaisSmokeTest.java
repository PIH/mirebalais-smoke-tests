package org.openmrs.module.mirebalais.smoke;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openmrs.module.mirebalais.smoke.helper.SmokeTestDriver;
import org.openmrs.module.mirebalais.smoke.helper.SmokeTestProperties;
import org.openmrs.module.mirebalais.smoke.pageobjects.AdminPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.ClinicianDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.LegacyPatientRegistrationDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.LegacyRegistration;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.MirebalaisLoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public abstract class BasicMirebalaisSmokeTest {

    protected SmokeTestProperties properties = new SmokeTestProperties();

	protected static LoginPage loginPage;
	
	protected static HeaderPage header;

	protected static AdminPage adminPage;
	
	protected static WebDriver driver;
	
	@Rule
	public TestRule testWatcher = new TestWatcher() {
		
		@Override
		public void failed(Throwable t, Description test) {
			File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(tempFile, new File("screenshots/" + test.getDisplayName() + ".png"));
			}
			catch (IOException e) {}
		}
	};
	
	protected AppDashboard appDashboard;
	
	protected LegacyRegistration legacyRegistration;
	
	protected LegacyPatientRegistrationDashboard patientRegistrationDashboard;
	
	protected VisitNote visitNote;

    protected ClinicianDashboard clinicianDashboard;

    protected static boolean createdOwnDriver;
	
	@BeforeClass
	public static void getWebDriver() {

        // when running as a suite, MirebalaisSmokeTestSuite should inject the driver into the context
        // if not running as a suite, we create our own driver (and flag createdOwnDriver as true so that we know we need to do teardown)
        if (driver == null) {
            driver = new SmokeTestDriver().getDriver();
            createdOwnDriver = true;
        }
        else {
            createdOwnDriver = false;
        }

        if (header == null) {
            header = new HeaderPage(driver);
        }

        if (loginPage == null) {
            loginPage = new MirebalaisLoginPage(driver);
        }

        if (adminPage == null) {
            adminPage = new AdminPage(driver);
        }
	}

    @AfterClass
    public static void after() throws Exception {

        if (header == null) {
            header = new HeaderPage(driver);
        }

        // back to home page
        header.home();

        // log out if necessary
        try {
            header.logOut();
        }
        catch (TimeoutException ex) {
            // do nothing, assume we are already logged out
        }

        if (createdOwnDriver) {
            driver.quit();
        }
    }
	
	protected static void logInAsPhysicianUser() throws Exception {
		loginPage.logInAsPhysicianUser();
		adminPage.updateLuceneIndex();
		header.home();
	}

    protected static void logInAsPhysicianUser(String location) throws Exception {
        loginPage.logInAsPhysicianUser(location);
        adminPage.updateLuceneIndex();
        header.home();
    }
	
	protected static void logInAsPharmacyManagerUser() throws Exception {
        loginPage.logInAsPharmacyManagerUser();
        adminPage.updateLuceneIndex();
        header.home();
	}

    protected static void logInAsArchivist() throws Exception{
        loginPage.logInAsArchivistUser();
        adminPage.updateLuceneIndex();
        header.home();
    }

    protected static void logInAsAdmin() throws Exception {
        loginPage.logInAsAdmin();
        adminPage.updateLuceneIndex();
        header.home();
    }

    protected static void logInAsAdmin(String location) throws Exception {
        loginPage.logInAsAdmin(location);
        adminPage.updateLuceneIndex();
        header.home();
    }

    protected void login() throws Exception{
        loginPage.logInAsAdmin();
        adminPage.updateLuceneIndex();
        header.home();
    }

    protected void setLoginPageObject(LoginPage loginPage) {
        this.loginPage = loginPage;
	}


    protected void initBasicPageObjects() {
        legacyRegistration = new LegacyRegistration(driver);
        patientRegistrationDashboard = new LegacyPatientRegistrationDashboard(driver);
        visitNote = new VisitNote(driver);
        appDashboard = new AppDashboard(driver);
        clinicianDashboard = new ClinicianDashboard(driver);
    }

	protected void logout() {
		new HeaderPage(driver).logOut();
	}

    protected void turnOffImplicitWaits() {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    protected void turnOnImplicitWait() {
        driver.manage().timeouts().implicitlyWait(SmokeTestProperties.IMPLICIT_WAIT_TIME, TimeUnit.SECONDS);
    }

    public static void setDriver(WebDriver driver) {
        BasicMirebalaisSmokeTest.driver = driver;
    }

    public static void setLoginPage(LoginPage loginPage) {
        BasicMirebalaisSmokeTest.loginPage = loginPage;
    }

    public static void setHeader(HeaderPage header) {
        BasicMirebalaisSmokeTest.header = header;
    }
}
