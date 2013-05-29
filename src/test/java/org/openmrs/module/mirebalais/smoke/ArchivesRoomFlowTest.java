package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.ArchivesRoomApp;

public class ArchivesRoomFlowTest extends BasicMirebalaisSmokeTest {

	private ArchivesRoomApp archivesRoomApp;
	
	
	@Before
    public void setUp() {
		initBasicPageObjects();
		archivesRoomApp = new ArchivesRoomApp(driver);
	}

	@Test
	public void requestRecord() throws Exception{
		String dossieNumber = null;
		
		loginPage.logInAsAdmin();
		createPatient();

		appDashboard.findPatientById(testPatient.getIdentifier());
		patientDashboard.requestRecord();
		 
		appDashboard.openArchivesRoomApp();
		
		try {
			dossieNumber = archivesRoomApp.createRecord(testPatient.getIdentifier(), testPatient.getName());
			archivesRoomApp.sendDossie(dossieNumber);
			archivesRoomApp.returnRecord(dossieNumber);
				
			appDashboard.findPatientById(testPatient.getIdentifier());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		assertThat(patientDashboard.getDossieNumber(), is(dossieNumber));
		assertThat(patientDashboard.canRequestRecord(), is(true));
	}
	
	@After
	public void cleanAllRequests() {
		appDashboard.openArchivesRoomApp();
		archivesRoomApp.clearRequestList();
	}
		
}