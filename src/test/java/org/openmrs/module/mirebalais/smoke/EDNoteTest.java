package org.openmrs.module.mirebalais.smoke;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class EDNoteTest extends DbTest {

    private static final String PRIMARY_DIAGNOSIS = "IGU";
    private static final String EDITED_PRIMARY_DIAGNOSIS = "Asthme";

    @BeforeClass
    public static void prepare() throws Exception {
        logInAsPhysicianUser("Ijans");
    }

    @Before
    public void setUp() throws Exception {
        Patient testPatient = PatientDatabaseHandler.insertAdultTestPatient();
        adminPage.updateLuceneIndex();
        initBasicPageObjects();

        appDashboard.goToClinicianFacingDashboard(testPatient.getId());
        clinicianDashboard.startVisit();
    }

    @Test
    public void addEDNote() throws Exception {
        visitNote.addEDNote(PRIMARY_DIAGNOSIS);

        assertThat(visitNote.countEncountersOfType(VisitNote.CONSULTATION_CREOLE_NAME), is(1));
    }

    @Test
    public void editEDNote() throws Exception {

        visitNote.addEDNote(PRIMARY_DIAGNOSIS);
        new WebDriverWait(driver, 5).until(visibilityOfElementLocated(VisitNote.consultNoteSectionHeader));
        visitNote.editExistingEDNote(EDITED_PRIMARY_DIAGNOSIS);

        assertThat(visitNote.countEncountersOfType(VisitNote.CONSULTATION_CREOLE_NAME), is(1));

        //visitNote.viewConsultationDetails();
        new WebDriverWait(driver, 5).until(visibilityOfElementLocated(VisitNote.consultNoteSectionHeader));
        assertThat(visitNote.containsText(EDITED_PRIMARY_DIAGNOSIS), is(true));
        assertThat(visitNote.containsText(PRIMARY_DIAGNOSIS), is(false));
    }
}
