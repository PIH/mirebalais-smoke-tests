package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.flows.CheckInPatientFlow;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.ArchivesRoomApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;

import static junit.framework.Assert.assertTrue;

public class GenerateDossierAtCheckinTest extends DbTest {

    @Test
    public void shouldCreateDossierLocallyAtCheckinWhenDossierIsMissing() throws Exception {
        new LoginPage(driver).logIn("admin", "Admin123", 10);

        AppDashboard dashboard = new AppDashboard(driver);
        dashboard.openCheckinApp();

        PatientDashboard patientDashboard = new PatientDashboard(driver);

        CheckInPatientFlow checkInPatientFlow = new CheckInPatientFlow(driver);
        checkInPatientFlow.checkInAndCreateLocalDossierFor("TESTIDTEST");
        checkInPatientFlow.confirmPatient("TESTIDTEST");

        String dossieNumber = patientDashboard.getDossierNumber();
        assertTrue(dossieNumber.matches("A\\d{6}"));

        checkInPatientFlow.checkIn();

        dashboard.openArchivesRoomApp();
        ArchivesRoomApp archives = new ArchivesRoomApp(driver);
        archives.goToPullTab();
        assertTrue(archives.isPatientInList("TESTIDTEST", "pull_requests_table"));
    }

}
