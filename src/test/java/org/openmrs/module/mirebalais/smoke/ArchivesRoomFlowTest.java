package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.ArchivesRoomApp;

public class ArchivesRoomFlowTest extends BasicMirebalaisSmokeTest {

	private String patientName;
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
		appDashboard.openPatientRegistrationApp();
		registration.goThruRegistrationProcessWithoutPrintingCard(); 
		patientIdentifier = patientRegistrationDashboard.getIdentifier();
		patientName = patientRegistrationDashboard.getName();

		appDashboard.findPatientById(patientIdentifier);
		patientDashboard.requestRecord();
		 
		appDashboard.openArchivesRoomApp();
		
		try {
			dossieNumber = archivesRoomApp.createRecord(patientIdentifier, patientName);
			archivesRoomApp.sendDossie(dossieNumber);
			archivesRoomApp.returnRecord(dossieNumber);
				
			appDashboard.findPatientById(patientIdentifier);
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