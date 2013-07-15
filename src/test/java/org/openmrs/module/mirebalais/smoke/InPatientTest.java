package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.InPatientList;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;

public class InPatientTest extends DbTest {
	
	private InPatientList inPatientList;

	@Test
	public void admitPatientTransferInsideHospitalAndFilterWard() throws Exception {
		Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
		initBasicPageObjects();

		inPatientList = new InPatientList(driver);
		
		login();
		
		appDashboard.goToPatientPage(testPatient.getId());
		patientDashboard.startVisit();
		
		String admissionPlace = patientDashboard.addConsultNoteWithAdmission();
		
		assertThat(patientDashboard.countEncoutersOfType(PatientDashboard.CONSULTATION), is(1));
		assertThat(patientDashboard.countEncoutersOfType(PatientDashboard.ADMISSION), is(1));
		assertFirstAdmittedAndCurrentWardAre(testPatient.getIdentifier(), admissionPlace, admissionPlace);
		
		appDashboard.goToPatientPage(testPatient.getId());
		
		String transferPlace = patientDashboard.addConsultNoteWithTransfer();
		
		assertThat(patientDashboard.countEncoutersOfType(PatientDashboard.CONSULTATION), is(2));
		assertThat(patientDashboard.countEncoutersOfType(PatientDashboard.TRANSFER), is(1));
		assertFirstAdmittedAndCurrentWardAre(testPatient.getIdentifier(), admissionPlace, transferPlace);
		
		inPatientList.filterBy(transferPlace);
		
		assertThat(inPatientList.isListFilteredBy(transferPlace), is(true));
	}
	
	private void assertFirstAdmittedAndCurrentWardAre(String identifier, String firstAdmitted, String currentWard)
	        throws Exception {
		appDashboard.openInPatientApp();
		
		assertThat(inPatientList.getCurrentWard(identifier), is(currentWard));
		assertThat(inPatientList.getFirstAdmitted(identifier), is(firstAdmitted));
	}
}
