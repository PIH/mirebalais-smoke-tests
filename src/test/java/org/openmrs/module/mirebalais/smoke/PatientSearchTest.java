package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PatientSearchTest extends DbTest {


    @Test
    public void loadPatientDashboardByPatientIdentifier() throws Exception {

        login();

        appDashboard.findPatientByIdentifier(adultTestPatient.getIdentifier());

        assertTrue(clinicianDashboard.isOpenForPatient(adultTestPatient));

    }

    @Test
    public void loadPatientDashboardByPatientName() throws Exception {
        login();
        appDashboard.findPatientByName(adultTestPatient);
        assertTrue(clinicianDashboard.isOpenForPatient(adultTestPatient));
    }

}

