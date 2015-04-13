package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.flows.CheckInPatientFlow;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.ArchivesRoomApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.MirebalaisLoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GenerateDossierAtCheckinTest extends DbTest {
	
	@Test
	public void shouldCreateDossierLocallyAtCheckinWhenDossierIsMissing() throws Exception {
		Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
		
		new MirebalaisLoginPage(driver).logIn("admin", "Admin123", "Chimyoterapi");
		
		AppDashboard dashboard = new AppDashboard(driver);
		dashboard.openCheckinApp();
		
		PatientDashboard patientDashboard = new PatientDashboard(driver);
		
		CheckInPatientFlow checkInPatientFlow = new CheckInPatientFlow(driver);
		checkInPatientFlow.checkInAndCreateLocalDossierFor(testPatient.getIdentifier());
		checkInPatientFlow.confirmPatient(testPatient.getIdentifier());
		
		assertThat(patientDashboard.getDossierNumber().matches("A\\d{6}"), is(true));
		
		checkInPatientFlow.checkIn();
		
		dashboard.openArchivesRoomApp();
		ArchivesRoomApp archives = new ArchivesRoomApp(driver);
		archives.goToPullTab();
		
		assertThat(archives.isPatientInList(testPatient.getIdentifier(), "pull_requests_table"), is(true));
	}
}
