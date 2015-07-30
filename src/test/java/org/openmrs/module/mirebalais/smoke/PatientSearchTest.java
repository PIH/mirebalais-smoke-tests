package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;

import static org.junit.Assert.assertTrue;

public class PatientSearchTest extends DbTest {


    @Test
    public void loadPatientDashboardByPatientIdentifier() throws Exception {

        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
        initBasicPageObjects();
        login();

        appDashboard.findPatientByIdentifier(testPatient.getIdentifier());

        assertTrue(clinicianDashboard.isOpenForPatient(testPatient));

        logout();
    }

    @Test
    public void loadPatientDashboardByPatientName() throws Exception {

        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
        initBasicPageObjects();
        login();

        appDashboard.findPatientByName(testPatient);

        assertTrue(clinicianDashboard.isOpenForPatient(testPatient));

        logout();
    }

}

