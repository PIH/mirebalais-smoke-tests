package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.InPatientList;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;

public class InPatientTest extends BasicMirebalaisSmokeTest {

	private HeaderPage headerPage;
	private InPatientList ipl;
	
	@Before
    public void setUp() {
		initBasicPageObjects();
		headerPage = new HeaderPage(driver);
		ipl  = new InPatientList(driver);
	}
	
	@Test
	public void admitPatientTransferInsideHospitalAndFilterWard() throws Exception {
		loginPage.logInAsAdmin();
		
		createPatient();
		startVisit();
		
		String admissionPlace = patientDashboard.addConsultNoteWithAdmission();		
		assertThat(patientDashboard.countEncouters(PatientDashboard.CONSULTATION), is(1));
		assertThat(patientDashboard.countEncouters(PatientDashboard.ADMISSION), is(1));
		
		assurePlaces(admissionPlace, admissionPlace);
		
		appDashboard.findPatientById(patientIdentifier);
		
		String transferPlace = patientDashboard.addConsultNoteWithTransfer();		
		assertThat(patientDashboard.countEncouters(PatientDashboard.CONSULTATION), is(2));
		assertThat(patientDashboard.countEncouters(PatientDashboard.TRANSFER), is(1));
		
		assurePlaces(admissionPlace, transferPlace);
		/*
		ipl.filterBy(transferPlace);
		assertThat(ipl.isListFilteredBy(transferPlace), is(true));
		*/
		headerPage.logOut();
	}
	
	private void assurePlaces(String firstAdmitted, String currentWard) throws Exception {
		appDashboard.openInPatientApp();
		
		assertThat(ipl.getCurrentWard(patientIdentifier), is(currentWard));
		assertThat(ipl.getFirstAdmitted(patientIdentifier), is(firstAdmitted));
	}

}
