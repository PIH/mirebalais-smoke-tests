package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.MergeFlow;
import org.openmrs.module.mirebalais.smoke.pageobjects.SysAdminPage;

public class MergePatientTest extends BasicMirebalaisSmokeTest {

	private SysAdminPage sysAdminPage;
	private MergeFlow mergeFlow;
	
	private String patientNameOne = "Bruce Wayne";
	private String patientNameTwo = "Clark Kent";

    private String patientIdOne = "";
    private String patientIdTwo = "";
    

    @Before
    public void setUp() {
    	initBasicPageObjects();
		sysAdminPage = new SysAdminPage(driver);
		mergeFlow = new MergeFlow(driver);

        loginPage.logInAsAdmin();

        appDashboard.openPatientRegistrationApp();
        patientIdOne = registration.registerSpecificGuyWithoutPrintingCard(patientNameOne);

        appDashboard.openPatientRegistrationApp();
		patientIdTwo = registration.registerSpecificGuyWithoutPrintingCard(patientNameTwo);
	}
	
	@Test
	public void mergePatientsByName() {
		appDashboard.openSysAdminApp();
		sysAdminPage.openManagePatientRecords();
		
		mergeFlow.setPatientsToMerge(patientIdOne, patientIdTwo);
		
		assertTrue(patientDashboard.verifyIfSuccessfulMessageIsDisplayed());
	}

}
