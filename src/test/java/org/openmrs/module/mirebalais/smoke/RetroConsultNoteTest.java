package org.openmrs.module.mirebalais.smoke;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RetroConsultNoteTest extends DbTest {

    private static final String PRIMARY_DIAGNOSIS = "IGU";
    private static final String EDITED_PRIMARY_DIAGNOSIS = "Asthme";

    @BeforeClass
    public static void prepare() throws Exception {
        logInAsAdmin();
    }

    @Before
    public void setUp() throws Exception {
        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
        initBasicPageObjects();

        appDashboard.goToPatientPage(testPatient.getId());
    }

    @Test
    public void addConsultationToAnActiveVisit() throws Exception {
        patientDashboard.startVisit();
        patientDashboard.addRetroConsultNoteWithDischarge(PRIMARY_DIAGNOSIS);
        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.CONSULTATION_CREOLE_NAME), is(1));
    }

    @Test
    public void addConsultationToARetroVisit() throws Exception {
        patientDashboard.addRetroVisit();
        patientDashboard.addRetroConsultNoteWithDischarge(PRIMARY_DIAGNOSIS);
        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.CONSULTATION_CREOLE_NAME), is(1));
    }

    @Test
    public void editRetroConsultationNote() throws Exception {

        patientDashboard.addRetroVisit();
        patientDashboard.addRetroConsultNoteWithDischarge(PRIMARY_DIAGNOSIS);
        patientDashboard.editExistingConsultNote(EDITED_PRIMARY_DIAGNOSIS);

        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.CONSULTATION_CREOLE_NAME), is(1));

        patientDashboard.viewConsultationDetails();
        assertThat(patientDashboard.containsText(EDITED_PRIMARY_DIAGNOSIS), is(true));
        assertThat(patientDashboard.containsText(PRIMARY_DIAGNOSIS), is(false));
    }

}
