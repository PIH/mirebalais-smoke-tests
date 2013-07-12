package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.InPatientList;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InPatientTest extends DbTest {

    private InPatientList inPatientList;
	
	@Before
	public void setUp() throws Exception {
        super.setUp();
		initBasicPageObjects();
		inPatientList = new InPatientList(driver);
    }
	
	@Test
	public void admitPatientTransferInsideHospitalAndFilterWard() throws Exception {
		login();

		appDashboard.goToPatientPage(testPatient.getId());
		patientDashboard.startVisit();

        String admissionPlace = patientDashboard.addConsultNoteWithAdmission();

		assertThat(patientDashboard.countEncouters(PatientDashboard.CONSULTATION), is(1));
		assertThat(patientDashboard.countEncouters(PatientDashboard.ADMISSION), is(1));
		assertFirstAdmittedAndCurrentWardAre(admissionPlace, admissionPlace);

		appDashboard.goToPatientPage(testPatient.getId());

		String transferPlace = patientDashboard.addConsultNoteWithTransfer();

		assertThat(patientDashboard.countEncouters(PatientDashboard.CONSULTATION), is(2));
		assertThat(patientDashboard.countEncouters(PatientDashboard.TRANSFER), is(1));
		assertFirstAdmittedAndCurrentWardAre(admissionPlace, transferPlace);

		inPatientList.filterBy(transferPlace);

        assertThat(inPatientList.isListFilteredBy(transferPlace), is(true));
	}

    private void assertFirstAdmittedAndCurrentWardAre(String firstAdmitted, String currentWard) throws Exception {
		appDashboard.openInPatientApp();
		
		assertThat(inPatientList.getCurrentWard(testPatient.getIdentifier()), is(currentWard));
		assertThat(inPatientList.getFirstAdmitted(testPatient.getIdentifier()), is(firstAdmitted));
	}
}
