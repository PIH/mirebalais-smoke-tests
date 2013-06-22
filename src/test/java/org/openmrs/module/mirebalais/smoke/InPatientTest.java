package org.openmrs.module.mirebalais.smoke;

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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
		
		appDashboard.findPatientById(testPatient.getIdentifier());
		
		String transferPlace = patientDashboard.addConsultNoteWithTransfer();		
		assertThat(patientDashboard.countEncouters(PatientDashboard.CONSULTATION), is(2));
		assertThat(patientDashboard.countEncouters(PatientDashboard.TRANSFER), is(1));
		
		assurePlaces(admissionPlace, transferPlace);
		
		int oldSize = getPatientCount();
		ipl.filterBy(transferPlace);
		waitForTableToUpdate(oldSize);
		assertThat(ipl.isListFilteredBy(transferPlace), is(true));
		
		headerPage.logOut();
	}
	
	private void assurePlaces(String firstAdmitted, String currentWard) throws Exception {
		appDashboard.openInPatientApp();
		
		assertThat(ipl.getCurrentWard(testPatient.getIdentifier()), is(currentWard));
		assertThat(ipl.getFirstAdmitted(testPatient.getIdentifier()), is(firstAdmitted));
	}

	private void waitForTableToUpdate(final int oldSize) {
		Wait<WebDriver> wait = new WebDriverWait(driver, 20);
    	wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				return (getPatientCount() < oldSize);
			}
		});
	}
	
	private int getPatientCount() {
		try {
			return ipl.getPatientCount();
		} catch (StaleElementReferenceException e) {
			return Integer.MAX_VALUE;
		}
	}
}
