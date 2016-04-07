package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppointmentRequestsPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppointmentSchedulingApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.RequestAppointmentPage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RequestAppointmentsTest extends DbTest {

    @Test
    public void requestAppointment() throws Exception {

        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
        initBasicPageObjects();
        
        AppDashboard appDashboard = new AppDashboard(driver);
        AppointmentSchedulingApp appointmentSchedulingApp = new AppointmentSchedulingApp(driver);
        AppointmentRequestsPage appointmentRequestsPage = new AppointmentRequestsPage(driver);
        RequestAppointmentPage requestAppointmentPage = new RequestAppointmentPage(driver);

        logInAsAdmin();

        appDashboard.goToClinicianFacingDashboard(testPatient.getId());
        clinicianDashboard.openRequestAppointmentForm();
        requestAppointmentPage.requestAppointment();

        header.home();

        appDashboard.openAppointmentSchedulingApp();
        appointmentSchedulingApp.openAppointmentRequestsApp();

        // make sure the patient is in the list
        assertTrue(appointmentRequestsPage.containsRequestFor(testPatient));

        appointmentRequestsPage.cancelLastRequest();

        // hack, stall
        Thread.sleep(3000);

        // patient should be removed from list
        assertFalse(appointmentRequestsPage.containsRequestFor(testPatient));

    }

}
