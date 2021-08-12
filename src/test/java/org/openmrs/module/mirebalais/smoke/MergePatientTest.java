package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.MergeFlow;
import org.openmrs.module.mirebalais.smoke.pageobjects.SysAdminPage;

import static org.junit.Assert.assertTrue;

public class MergePatientTest extends DbTest {

	@Test
	public void mergePatientsByName() throws Exception {

		SysAdminPage sysAdminPage = new SysAdminPage(driver);
		MergeFlow mergeFlow = new MergeFlow(driver);

		login();

		appDashboard.openSysAdminApp();
		sysAdminPage.openMergePatients();

		mergeFlow.setPatientsToMerge(adultTestPatient.getIdentifier(), anotherAdultTestPatient.getIdentifier());

		assertTrue(visitNote.verifyIfSuccessfulMessageIsDisplayed());
	}

}
