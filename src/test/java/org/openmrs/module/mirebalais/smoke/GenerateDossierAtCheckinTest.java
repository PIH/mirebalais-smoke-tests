package org.openmrs.module.mirebalais.smoke;

import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.flows.CheckInPatientFlow;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.ArchivesRoomApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.ClinicianDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.MirebalaisLoginPage;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

// ignore until we get the mock printer running again
@Ignore
public class GenerateDossierAtCheckinTest extends DbTest {
	
	@Test
	public void shouldCreateDossierLocallyAtCheckinWhenDossierIsMissing() throws Exception {
		Patient testPatient = PatientDatabaseHandler.insertAdultTestPatient();

		// TODO fix this to use standard log-in page methods that take care of hitting the button that update lucene?
		new MirebalaisLoginPage(driver).logIn("admin", "Admin123", "Chimyoterapi");
		adminPage.updateLuceneIndex();

		AppDashboard dashboard = new AppDashboard(driver);
		dashboard.openCheckinApp();
		
		ClinicianDashboard clinicianDashboard = new ClinicianDashboard(driver);

		// check the patient in, create dossier
		CheckInPatientFlow checkInPatientFlow = new CheckInPatientFlow(driver);
		checkInPatientFlow.checkInAndCreateLocalDossierFor(testPatient.getIdentifier());

		checkInPatientFlow.findPatientAndSelectContinue(testPatient.getIdentifier());

		assertThat(clinicianDashboard.getDossierNumber().matches("A\\d{6}"), is(true));

		// check in again, request dossier
		checkInPatientFlow.checkIn();

		dashboard.openArchivesRoomApp();
		ArchivesRoomApp archives = new ArchivesRoomApp(driver);
		archives.goToPullTab();
		
		assertThat(archives.isPatientInList(testPatient.getIdentifier(), "pull_requests_table"), is(true));
	}
}
