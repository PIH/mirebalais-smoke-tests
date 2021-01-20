package org.openmrs.module.mirebalais.smoke.suite;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openmrs.module.mirebalais.smoke.*;
import org.openmrs.module.mirebalais.smoke.helper.SmokeTestDriver;
import org.openqa.selenium.WebDriver;

@RunWith(Suite.class)
@Suite.SuiteClasses( { /* ActiveVisitsTest.class,
                        AdmissionDischargeTransferRetroTest.class,
                        AdmissionDischargeTransferTest.class,
                        ArchivesRoomFlowTest.class,
                        CaptureVitalsMirebalaisTest.class,
                        CheckInMirebalaisTest.class,
                        ConsultNoteTest.class,
                        DailyAppointmentsTest.class,
                        DeathCertificateTest.class,
                        DispensingTest.class,
                        EDNoteTest.class,
                        EDTriageTest.class,
                        GenerateDossierAtCheckinTest.class,
                        HeaderTest.class,
                        InPatientTest.class,
                        ManageAppointmentsTest.class,
                        ManageServiceTypesTest.class,
                        MasterPatientIndexTest.class,
                        MergePatientTest.class,
                        MultipleSubmitTest.class,
                        NonCodedDiagnosesTest.class,
                        RadiologyOrdersTest.class,
                        PatientRegistrationMirebalaisFlowTest.class,*/
                       // PatientSearchTest.class,
                       // RequestAppointmentsTest.class,
                       // RetroConsultNoteTest.class,
                        RetroVisitTest.class,
                       // UserAdminTest.class,
                     //   VisitNoteMirebalaisTest.class
                    } )
public class MirebalaisSmokeTestSuite {

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
