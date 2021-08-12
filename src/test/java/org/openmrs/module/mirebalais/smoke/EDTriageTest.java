package org.openmrs.module.mirebalais.smoke;

import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.EDTriageEditPatientPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.EDTriageQueuePage;

import static org.junit.Assert.assertTrue;

public class EDTriageTest extends DbTest {

    // TODO what do we need to do to fix this?

    @Test
    @Ignore
    public void addPatientToQueue() throws Exception {
        String chiefComplaint = "bruises";

        // login to "Ijans" location
        logInAsAdmin("Ijans");

        // open ED Triage app
        appDashboard.openEDTriageApp();

        // search for patient
        appDashboard.findPatientByIdentifier(adultTestPatient.getIdentifier());

        // enter chief complaint for patient and save
        EDTriageEditPatientPage editPage = new EDTriageEditPatientPage(driver);
        editPage.enterChiefComplaintAndSave(chiefComplaint);

        // open ED Triage Queue app
        appDashboard.openEDTriageQueueApp();

        // confirm that patient is in the queue
        // TODO: check complaint also!
        EDTriageQueuePage queuePage = new EDTriageQueuePage(driver);
        boolean patientExists = queuePage.lookForPatient(adultTestPatient);
        assertTrue(patientExists);
    }
}
