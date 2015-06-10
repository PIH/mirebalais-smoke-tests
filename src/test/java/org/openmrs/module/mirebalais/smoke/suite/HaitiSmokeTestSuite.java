package org.openmrs.module.mirebalais.smoke.suite;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openmrs.module.mirebalais.smoke.BasicMirebalaisSmokeTest;
import org.openmrs.module.mirebalais.smoke.TempTest;
import org.openmrs.module.mirebalais.smoke.helper.SmokeTestDriver;
import org.openqa.selenium.WebDriver;

@RunWith(Suite.class)
@Suite.SuiteClasses({TempTest.class})
public class HaitiSmokeTestSuite {

    private static WebDriver driver;

    @BeforeClass
    public static void startWebDriver() {
        driver = new SmokeTestDriver().getDriver();
        BasicMirebalaisSmokeTest.setDriver(driver);
    }

    @AfterClass
    public static void stopWebDriver() {
        driver.quit();
    }

    public static WebDriver getDriver() {
        return driver;
    }

}
