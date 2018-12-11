package org.openmrs.module.mirebalais.smoke;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.DeathCertificateFormPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ConsultNoteTest extends DbTest {

    private static final String PRIMARY_DIAGNOSIS = "IGU";
    private static final String EDITED_PRIMARY_DIAGNOSIS = "Asthme";

    private DeathCertificateFormPage deathCertificateForm;

	@BeforeClass
	public static void prepare() throws Exception {
        logInAsPhysicianUser("Klinik Ekstèn");
    }
	
	@Before
	public void setUp() throws Exception {
		Patient testPatient = PatientDatabaseHandler.insertAdultTestPatient();
		adminPage.updateLuceneIndex();
		initBasicPageObjects();
        deathCertificateForm = new DeathCertificateFormPage(driver);

        appDashboard.goToClinicianFacingDashboard(testPatient.getId());
		clinicianDashboard.startVisit();
	}
	
	@Test
	public void addConsultationToAVisitWithoutCheckin() throws Exception {
		visitNote.addConsultNoteWithAdmissionToLocation(PRIMARY_DIAGNOSIS, 2);
		
		assertThat(visitNote.countEncountersOfType(VisitNote.CONSULTATION_CREOLE_NAME), is(1));
	}
	
	@Test
	public void addConsultationNoteWithDeathAsDispositionDoesNotCloseVisit() throws Exception {
		visitNote.addConsultNoteWithDeath(PRIMARY_DIAGNOSIS);
        deathCertificateForm.waitToLoad();
        deathCertificateForm.cancel();

		assertThat(clinicianDashboard.isDead(), is(true));
		assertThat(clinicianDashboard.hasActiveVisit(), is(true));
	}


    @Test
    public void editConsultationNote() throws Exception {

        visitNote.addConsultNoteWithAdmissionToLocation(PRIMARY_DIAGNOSIS, 2);
        visitNote.editExistingConsultNote(EDITED_PRIMARY_DIAGNOSIS);

        assertThat(visitNote.countEncountersOfType(VisitNote.CONSULTATION_CREOLE_NAME), is(1));

        //visitNote.viewConsultationDetails();
        new WebDriverWait(driver, 5).until(visibilityOfElementLocated(VisitNote.consultNoteSectionHeader));
        assertThat(visitNote.containsText(EDITED_PRIMARY_DIAGNOSIS), is(true));
        assertThat(visitNote.containsText(PRIMARY_DIAGNOSIS), is(false));
    }

}
