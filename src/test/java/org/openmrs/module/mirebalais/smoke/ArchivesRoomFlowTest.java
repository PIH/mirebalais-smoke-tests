package org.openmrs.module.mirebalais.smoke;

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

        String dossierNumber = archivesRoomApp.createRecord(testPatient.getIdentifier());
        archivesRoomApp.sendDossier(dossierNumber);
        archivesRoomApp.returnRecord(dossierNumber);

        appDashboard.findPatientById(testPatient.getIdentifier());

		assertThat(patientDashboard.getDossierNumber(), is(dossierNumber));
		assertThat(patientDashboard.canRequestRecord(), is(true));
	}

}