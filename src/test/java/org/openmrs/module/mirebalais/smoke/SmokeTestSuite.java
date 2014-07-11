package org.openmrs.module.mirebalais.smoke;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openmrs.module.mirebalais.smoke.helper.SmokeTestDriver;
import org.openqa.selenium.WebDriver;

@RunWith(Suite.class)
@Suite.SuiteClasses( {  ActiveVisitsTest.class,
                        AdmissionDischargeTransferRetroTest.class,
                        AdmissionDischargeTransferTest.class,
                        ArchivesRoomFlowTest.class,
                        CaptureVitalsTest.class,
                        ConsultNoteTest.class,
                        DailyAppointmentsTest.class,
                        DispensingTest.class,
                        EmergencyCheckinTest.class,
                        GenerateDossierAtCheckinTest.class,
                        HeaderTest.class,
                        InPatientTest.class,
                        ManageAppointmentsTest.class,
                        ManageServiceTypesTest.class,
                        MasterPatientIndexTest.class,
                        MergePatientTest.class,
                        MultipleSubmitTest.class,
                        NewCheckInTest.class,
                        NonCodedDiagnosesTest.class,
                        OrdersTest.class,
                        PatientSearchTest.class,
                        RegistrationFlowTest.class,
                        RetroConsultNoteTest.class,
                        RetroVisitTest.class,
                        UserAdminTest.class} )
public class SmokeTestSuite {

    private static WebDriver driver;

    @BeforeClass
    public static void startWebDriver() {
        driver = new SmokeTestDriver().getDriver();
    }

    @AfterClass
    public static void stopWebDriver() {
        driver.quit();
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
