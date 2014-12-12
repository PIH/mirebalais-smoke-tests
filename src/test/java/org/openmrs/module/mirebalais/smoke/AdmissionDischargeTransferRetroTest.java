package org.openmrs.module.mirebalais.smoke;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class AdmissionDischargeTransferRetroTest extends DbTest {

    private final String anemia = "D64.9";

    private final String malaria = "B54";


    @BeforeClass
    public void prepare() throws Exception {
        logInAsAdmin();
    }

    @Before
    public void setUp() throws Exception {
        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
        initBasicPageObjects();

        appDashboard.goToPatientPage(testPatient.getId());
        patientDashboard.startVisit();
    }

    @Test
    public void shouldEnterAndEditAnAdmissionNoteAsAdminUser() throws Exception {

        patientDashboard.addAdmissionNoteAsAdminUser(malaria);
        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.ADMISSION_CREOLE_NAME), is(1));

        String previousProvider = patientDashboard.providerForFirstEncounter();
        String previousLocation = patientDashboard.locationForFirstEncounter();
        patientDashboard.editExistingAdmissionNote(anemia, 3, 4);

        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.ADMISSION_CREOLE_NAME), is(1));

        String currentProvider = patientDashboard.getAdmissionNoteForm().getProvider();
        String currentLocation = patientDashboard.getAdmissionNoteForm().getLocation();

        assertThat(currentProvider, not(previousProvider));
        assertThat(currentLocation, not(previousLocation));

    }


}
