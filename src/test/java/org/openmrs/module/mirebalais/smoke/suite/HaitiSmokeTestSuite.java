package org.openmrs.module.mirebalais.smoke.suite;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openmrs.module.mirebalais.smoke.BasicMirebalaisSmokeTest;
import org.openmrs.module.mirebalais.smoke.CaptureVitalsHSNTest;
import org.openmrs.module.mirebalais.smoke.CheckInTest;
import org.openmrs.module.mirebalais.smoke.PatientRegistrationHSNFlowTest;
import org.openmrs.module.mirebalais.smoke.helper.SmokeTestDriver;
import org.openqa.selenium.WebDriver;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        //CaptureVitalsTest.class,
        CaptureVitalsHSNTest.class,
        CheckInTest.class,
        PatientRegistrationHSNFlowTest.class,
        //VisitNoteTest.class  // this has been disable since we don't currently have them turned on on HSN
                    })
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
