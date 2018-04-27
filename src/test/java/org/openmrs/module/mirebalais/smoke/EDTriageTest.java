package org.openmrs.module.mirebalais.smoke;

import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.EDTriageEditPatientPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.EDTriageQueuePage;

import static org.junit.Assert.assertTrue;

public class EDTriageTest extends DbTest {

    @Test
    public void addPatientToQueue() throws Exception {
        String chiefComplaint = "bruises";

        // create patient
        Patient testPatient = PatientDatabaseHandler.insertAdultTestPatient();

        // login to "Ijans" location
        logInAsAdmin("Ijans");

        // open ED Triage app
        initBasicPageObjects();
        appDashboard.openEDTriageApp();

        // search for patient
        appDashboard.findPatientByIdentifier(testPatient.getIdentifier());

        // enter chief complaint for patient and save
        EDTriageEditPatientPage editPage = new EDTriageEditPatientPage(driver);
        editPage.enterChiefComplaintAndSave(chiefComplaint);

        // open ED Triage Queue app
        appDashboard.openEDTriageQueueApp();

        // confirm that patient is in the queue
        // TODO: check complaint also!
        EDTriageQueuePage queuePage = new EDTriageQueuePage(driver);
        boolean patientExists = queuePage.lookForPatient(testPatient);
        assertTrue(patientExists);
    }
}
