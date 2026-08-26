package org.openmrs.module.mirebalais.smoke.suite;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openmrs.module.mirebalais.smoke.ActiveVisitsTest;
import org.openmrs.module.mirebalais.smoke.AdmissionDischargeTransferRetroTest;
import org.openmrs.module.mirebalais.smoke.AdmissionDischargeTransferTest;
import org.openmrs.module.mirebalais.smoke.ArchivesRoomFlowTest;
import org.openmrs.module.mirebalais.smoke.BasicSmokeTest;
import org.openmrs.module.mirebalais.smoke.CaptureVitalsZlCentralTest;
import org.openmrs.module.mirebalais.smoke.CheckInZlCentralTest;
import org.openmrs.module.mirebalais.smoke.ConsultNoteTest;
import org.openmrs.module.mirebalais.smoke.DailyAppointmentsTest;
import org.openmrs.module.mirebalais.smoke.EDNoteTest;
import org.openmrs.module.mirebalais.smoke.EDTriageTest;
import org.openmrs.module.mirebalais.smoke.GenerateDossierAtCheckinTest;
import org.openmrs.module.mirebalais.smoke.HeaderTest;
import org.openmrs.module.mirebalais.smoke.InPatientTest;
import org.openmrs.module.mirebalais.smoke.ManageAppointmentsTest;
import org.openmrs.module.mirebalais.smoke.ManageServiceTypesTest;
import org.openmrs.module.mirebalais.smoke.MergePatientTest;
import org.openmrs.module.mirebalais.smoke.MultipleSubmitTest;
import org.openmrs.module.mirebalais.smoke.NonCodedDiagnosesTest;
import org.openmrs.module.mirebalais.smoke.PatientRegistrationZlCentralFlowTest;
import org.openmrs.module.mirebalais.smoke.PatientSearchTest;
import org.openmrs.module.mirebalais.smoke.RadiologyOrdersTest;
import org.openmrs.module.mirebalais.smoke.RequestAppointmentsTest;
import org.openmrs.module.mirebalais.smoke.RetroConsultNoteTest;
import org.openmrs.module.mirebalais.smoke.RetroVisitTest;
import org.openmrs.module.mirebalais.smoke.UserAdminTest;
import org.openmrs.module.mirebalais.smoke.VisitNoteZlCentralTest;
import org.openmrs.module.mirebalais.smoke.helper.SmokeTestDriver;
import org.openqa.selenium.WebDriver;

@RunWith(Suite.class)
@Suite.SuiteClasses( {  ActiveVisitsTest.class,
                        AdmissionDischargeTransferRetroTest.class,
                        AdmissionDischargeTransferTest.class,
                        ArchivesRoomFlowTest.class,
                        CaptureVitalsZlCentralTest.class,
                        CheckInZlCentralTest.class,
                        ConsultNoteTest.class,
                        DailyAppointmentsTest.class,
                        EDNoteTest.class,
                        EDTriageTest.class,
                        GenerateDossierAtCheckinTest.class,
                        HeaderTest.class,
                        InPatientTest.class,
                        ManageAppointmentsTest.class,
                        ManageServiceTypesTest.class,
                        MergePatientTest.class,
                        MultipleSubmitTest.class,
                        NonCodedDiagnosesTest.class,
                        RadiologyOrdersTest.class,
                        PatientRegistrationZlCentralFlowTest.class,
                        PatientSearchTest.class,
                        RequestAppointmentsTest.class,
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
