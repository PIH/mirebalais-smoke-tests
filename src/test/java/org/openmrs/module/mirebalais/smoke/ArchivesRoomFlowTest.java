package org.openmrs.module.mirebalais.smoke;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.ArchivesRoomApp;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArchivesRoomFlowTest extends DbTest {
	private ArchivesRoomApp archivesRoomApp;
	
	@Before
    public void setUp() throws Exception {
        super.setUp();

		initBasicPageObjects();
		archivesRoomApp = new ArchivesRoomApp(driver);
	}

	@Test
	public void requestRecord() throws Exception {
		loginPage.logInAsAdmin();

		appDashboard.findPatientById(testPatient.getIdentifier());
		patientDashboard.requestRecord();
		 
		appDashboard.openArchivesRoomApp();

        String dossieNumber = archivesRoomApp.createRecord(testPatient.getIdentifier(), testPatient.getName());
        archivesRoomApp.sendDossie(dossieNumber);
        archivesRoomApp.returnRecord(dossieNumber);

        appDashboard.findPatientById(testPatient.getIdentifier());

		assertThat(patientDashboard.getDossieNumber(), is(dossieNumber));
		assertThat(patientDashboard.canRequestRecord(), is(true));
	}
	
	@After
	public void cleanAllRequests() {
		appDashboard.openArchivesRoomApp();
		archivesRoomApp.clearRequestList();
	}
		
}