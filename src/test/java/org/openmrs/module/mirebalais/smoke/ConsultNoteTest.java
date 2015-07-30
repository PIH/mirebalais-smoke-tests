package org.openmrs.module.mirebalais.smoke;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.DeathCertificateFormPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConsultNoteTest extends DbTest {

    private static final String PRIMARY_DIAGNOSIS = "IGU";
    private static final String EDITED_PRIMARY_DIAGNOSIS = "Asthme";

    private DeathCertificateFormPage deathCertificateForm;

	@BeforeClass
	public static void prepare() throws Exception {
        logInAsPhysicianUser();
    }
	
	@Before
	public void setUp() throws Exception {
		Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
		initBasicPageObjects();
        deathCertificateForm = new DeathCertificateFormPage(driver);

        appDashboard.goToPatientPage(testPatient.getId());
		patientDashboard.startVisit();
	}
	
	@Test
	public void addConsultationToAVisitWithoutCheckin() throws Exception {
		patientDashboard.addConsultNoteWithAdmissionToLocation(PRIMARY_DIAGNOSIS, 2);
		
		assertThat(patientDashboard.countEncountersOfType(PatientDashboard.CONSULTATION_CREOLE_NAME), is(1));
	}
	
	@Test
	public void addConsultationNoteWithDeathAsDispositionDoesNotCloseVisit() throws Exception {
		patientDashboard.addConsultNoteWithDeath(PRIMARY_DIAGNOSIS);
        deathCertificateForm.waitToLoad();
        deathCertificateForm.cancel();

		assertThat(clinicianDashboard.isDead(), is(true));
		assertThat(clinicianDashboard.hasActiveVisit(), is(true));
	}


    @Test
    public void editConsultationNote() throws Exception {

        patientDashboard.addConsultNoteWithAdmissionToLocation(PRIMARY_DIAGNOSIS, 2);
        patientDashboard.editExistingConsultNote(EDITED_PRIMARY_DIAGNOSIS);

        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.CONSULTATION_CREOLE_NAME), is(1));

        patientDashboard.viewConsultationDetails();
        assertThat(patientDashboard.containsText(EDITED_PRIMARY_DIAGNOSIS), is(true));
        assertThat(patientDashboard.containsText(PRIMARY_DIAGNOSIS), is(false));
    }

}
