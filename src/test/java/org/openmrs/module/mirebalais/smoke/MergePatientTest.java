package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.MergeFlow;
import org.openmrs.module.mirebalais.smoke.pageobjects.SysAdminPage;

import static org.junit.Assert.assertTrue;

public class MergePatientTest extends DbTest {
	
	@Test
	public void mergePatientsByName() throws Exception {
		initBasicPageObjects();
		SysAdminPage sysAdminPage = new SysAdminPage(driver);
		MergeFlow mergeFlow = new MergeFlow(driver);
		
		login();
		
		Patient firstPatient = PatientDatabaseHandler.insertAdultTestPatient();
		Patient secondPatient = PatientDatabaseHandler.insertAdultTestPatient();
		
		appDashboard.openSysAdminApp();
		sysAdminPage.openMergePatients();
		
		mergeFlow.setPatientsToMerge(firstPatient.getIdentifier(), secondPatient.getIdentifier());
		
		assertTrue(visitNote.verifyIfSuccessfulMessageIsDisplayed());
	}
	
}
