package org.openmrs.module.mirebalais.smoke.suite;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openmrs.module.mirebalais.smoke.ActiveVisitsTest;
import org.openmrs.module.mirebalais.smoke.AdmissionDischargeTransferRetroTest;
import org.openmrs.module.mirebalais.smoke.AdmissionDischargeTransferTest;
import org.openmrs.module.mirebalais.smoke.ArchivesRoomFlowTest;
import org.openmrs.module.mirebalais.smoke.BasicMirebalaisSmokeTest;
import org.openmrs.module.mirebalais.smoke.CaptureVitalsTest;
import org.openmrs.module.mirebalais.smoke.CheckInTest;
import org.openmrs.module.mirebalais.smoke.ConsultNoteTest;
import org.openmrs.module.mirebalais.smoke.DailyAppointmentsTest;
import org.openmrs.module.mirebalais.smoke.DeathCertificateTest;
import org.openmrs.module.mirebalais.smoke.DispensingTest;
import org.openmrs.module.mirebalais.smoke.EmergencyCheckinTest;
import org.openmrs.module.mirebalais.smoke.GenerateDossierAtCheckinTest;
import org.openmrs.module.mirebalais.smoke.HeaderTest;
import org.openmrs.module.mirebalais.smoke.InPatientTest;
import org.openmrs.module.mirebalais.smoke.LegacyRegistrationFlowTest;
import org.openmrs.module.mirebalais.smoke.ManageAppointmentsTest;
import org.openmrs.module.mirebalais.smoke.ManageServiceTypesTest;
import org.openmrs.module.mirebalais.smoke.MasterPatientIndexTest;
import org.openmrs.module.mirebalais.smoke.MergePatientTest;
import org.openmrs.module.mirebalais.smoke.MultipleSubmitTest;
import org.openmrs.module.mirebalais.smoke.NonCodedDiagnosesTest;
import org.openmrs.module.mirebalais.smoke.OrdersTest;
import org.openmrs.module.mirebalais.smoke.PatientSearchTest;
import org.openmrs.module.mirebalais.smoke.RequestAppointmentsTest;
import org.openmrs.module.mirebalais.smoke.RetroConsultNoteTest;
import org.openmrs.module.mirebalais.smoke.RetroVisitTest;
import org.openmrs.module.mirebalais.smoke.UserAdminTest;
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
                        DeathCertificateTest.class,
                        DispensingTest.class,
                        EmergencyCheckinTest.class,
                        GenerateDossierAtCheckinTest.class,
                        HeaderTest.class,
                        InPatientTest.class,
                        LegacyRegistrationFlowTest.class,
                        ManageAppointmentsTest.class,
                        ManageServiceTypesTest.class,
                        MasterPatientIndexTest.class,
                        MergePatientTest.class,
                        MultipleSubmitTest.class,
                        CheckInTest.class,
                        NonCodedDiagnosesTest.class,
                        OrdersTest.class,
                        PatientSearchTest.class,
                        RequestAppointmentsTest.class,
                        LegacyRegistrationFlowTest.class,
                        RetroConsultNoteTest.class,
                        RetroVisitTest.class,
                        UserAdminTest.class } )
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
