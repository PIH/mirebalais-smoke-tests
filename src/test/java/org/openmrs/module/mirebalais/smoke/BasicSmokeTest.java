package org.openmrs.module.mirebalais.smoke;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openmrs.module.mirebalais.smoke.helper.SmokeTestDriver;
import org.openmrs.module.mirebalais.smoke.helper.SmokeTestProperties;
import org.openmrs.module.mirebalais.smoke.helper.UserDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.AdminPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.ClinicianDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.MirebalaisLoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.TermsAndConditionsPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class BasicSmokeTest {

	protected static LoginPage loginPage;

    protected static TermsAndConditionsPage termsAndConditionsPage;

	protected static HeaderPage header;

	protected static AdminPage adminPage;

	protected static WebDriver driver;

    protected AppDashboard appDashboard;

    protected VisitNote visitNote;

    protected ClinicianDashboard clinicianDashboard;

    protected static boolean createdOwnDriver;

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

	@BeforeClass
	public static void getWebDriver() {

        // when running as a suite, MirebalaisSmokeTestSuite should inject the driver into the context
        // if not running as a suite, we create our own driver (and flag createdOwnDriver as true so that we know we need to do teardown)
        if (driver == null) {
            System.out.println("Initializing new Chrome Driver");
            driver = new SmokeTestDriver().getDriver();
            createdOwnDriver = true;
        }
        else {
            createdOwnDriver = false;
        }
	}

	@Before
    public void initPageObjects() {
        log("Initializing page objects");
        header = new HeaderPage(driver);
        adminPage = new AdminPage(driver);
        loginPage = getLoginPage();
        termsAndConditionsPage = new TermsAndConditionsPage(driver);
        visitNote = new VisitNote(driver);
        appDashboard = new AppDashboard(driver);
        clinicianDashboard = new ClinicianDashboard(driver);
    }

    // most test uses Mirebalais Login Page, must be specifically overridden by non-Mirebalais stesps
    protected LoginPage getLoginPage() { return new MirebalaisLoginPage(driver); }

    @After
    public void teardown() throws Exception {
        log("Logging out");
        logout();
    }

    @AfterClass
    public static void after() throws Exception {
        System.out.println("Deleting test users and logging out if necessary");
        try {
            UserDatabaseHandler.deleteAllTestUsers();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("tear down failed", e);
        }

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
            System.out.println("Quitting Created Chrome Driver");
            driver.quit();
        }
    }

	protected static void logInAsPhysicianUser() throws Exception {
		loginPage.logInAsPhysicianUser();
        termsAndConditionsPage.acceptTermsIfPresent();
		header.home();
	}

    protected static void logInAsPhysicianUser(String location) throws Exception {
        loginPage.logInAsPhysicianUser(location);
        termsAndConditionsPage.acceptTermsIfPresent();
        header.home();
    }

	protected static void logInAsPharmacyManagerUser() throws Exception {
        loginPage.logInAsPharmacyManagerUser();
        termsAndConditionsPage.acceptTermsIfPresent();
        header.home();
	}

    protected static void logInAsArchivist() throws Exception{
        loginPage.logInAsArchivistUser();
        termsAndConditionsPage.acceptTermsIfPresent();
        header.home();
    }

    protected static void logInAsAdmin() throws Exception {
        loginPage.logInAsAdmin();
        termsAndConditionsPage.acceptTermsIfPresent();
        header.home();
    }

    protected static void logInAsAdmin(String location) throws Exception {
        loginPage.logInAsAdmin(location);
        termsAndConditionsPage.acceptTermsIfPresent();
        header.home();
    }

    protected void login() throws Exception {
        loginPage.logInAsAdmin();
        termsAndConditionsPage.acceptTermsIfPresent();
        header.home();
    }

    protected void home() {
	    header.home();
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
        BasicSmokeTest.driver = driver;
    }

    public static void setHeader(HeaderPage header) {
        BasicSmokeTest.header = header;
    }

    public void log(String message) {
        System.out.println(new Date() + " - " + getClass().getSimpleName() + ": " + message);
    }
}
