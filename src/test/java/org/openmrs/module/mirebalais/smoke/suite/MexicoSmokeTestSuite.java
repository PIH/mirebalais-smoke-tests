package org.openmrs.module.mirebalais.smoke.suite;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openmrs.module.mirebalais.smoke.BasicSmokeTest;
import org.openmrs.module.mirebalais.smoke.PatientRegistrationMexicoFlowTest;
import org.openmrs.module.mirebalais.smoke.helper.SmokeTestDriver;
import org.openqa.selenium.WebDriver;

@RunWith(Suite.class)
@Suite.SuiteClasses({PatientRegistrationMexicoFlowTest.class})
public class MexicoSmokeTestSuite {

    private static WebDriver driver;

    @BeforeClass
    public static void startWebDriver() {
        driver = new SmokeTestDriver().getDriver();
        BasicSmokeTest.setDriver(driver);
    }

    @AfterClass
    public static void stopWebDriver() {
        driver.quit();
    }

    public static WebDriver getDriver() {
        return driver;
    }

}
