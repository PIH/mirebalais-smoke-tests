package org.openmrs.module.pihemr.smoke.suite;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openmrs.module.pihemr.smoke.ActiveVisitsTest;
import org.openmrs.module.pihemr.smoke.AdmissionDischargeTransferRetroTest;
import org.openmrs.module.pihemr.smoke.AdmissionDischargeTransferTest;
import org.openmrs.module.pihemr.smoke.BasicSmokeTest;
import org.openmrs.module.pihemr.smoke.CaptureVitalsZlCentralTest;
import org.openmrs.module.pihemr.smoke.CheckInTest;
import org.openmrs.module.pihemr.smoke.ConsultNoteTest;
import org.openmrs.module.pihemr.smoke.EDNoteTest;
import org.openmrs.module.pihemr.smoke.EDTriageTest;
import org.openmrs.module.pihemr.smoke.HeaderTest;
import org.openmrs.module.pihemr.smoke.InPatientTest;
import org.openmrs.module.pihemr.smoke.MergePatientTest;
import org.openmrs.module.pihemr.smoke.MultipleSubmitTest;
import org.openmrs.module.pihemr.smoke.NonCodedDiagnosesTest;
import org.openmrs.module.pihemr.smoke.PatientRegistrationZlCentralFlowTest;
import org.openmrs.module.pihemr.smoke.PatientSearchTest;
import org.openmrs.module.pihemr.smoke.RadiologyOrdersTest;
import org.openmrs.module.pihemr.smoke.RetroConsultNoteTest;
import org.openmrs.module.pihemr.smoke.RetroVisitTest;
import org.openmrs.module.pihemr.smoke.UserAdminTest;
import org.openmrs.module.pihemr.smoke.VisitNoteZlCentralTest;
import org.openmrs.module.pihemr.smoke.helper.SmokeTestDriver;
import org.openqa.selenium.WebDriver;

@RunWith(Suite.class)
@Suite.SuiteClasses( {  ActiveVisitsTest.class,
                        AdmissionDischargeTransferRetroTest.class,
                        AdmissionDischargeTransferTest.class,
                        CaptureVitalsZlCentralTest.class,
                        CheckInTest.class,
                        ConsultNoteTest.class,
                        EDNoteTest.class,
                        EDTriageTest.class,
                        HeaderTest.class,
                        InPatientTest.class,
                        MergePatientTest.class,
                        MultipleSubmitTest.class,
                        NonCodedDiagnosesTest.class,
                        RadiologyOrdersTest.class,
                        PatientRegistrationZlCentralFlowTest.class,
                        PatientSearchTest.class,
                        RetroConsultNoteTest.class,
                        RetroVisitTest.class,
                        UserAdminTest.class,
                        VisitNoteZlCentralTest.class
                    } )
public class ZlCentralSmokeTestSuite {

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
