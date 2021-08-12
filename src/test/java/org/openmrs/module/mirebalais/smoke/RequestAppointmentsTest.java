package org.openmrs.module.mirebalais.smoke;

import org.junit.After;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.helper.AppointmentTypeDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.helper.NameGenerator;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppointmentRequestsPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppointmentSchedulingApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.RequestAppointmentPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.ServiceTypeApp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RequestAppointmentsTest extends DbTest {

    @Test
    public void requestAppointment() throws Exception {
        AppDashboard appDashboard = new AppDashboard(driver);
        AppointmentSchedulingApp appointmentSchedulingApp = new AppointmentSchedulingApp(driver);
        AppointmentRequestsPage appointmentRequestsPage = new AppointmentRequestsPage(driver);
        RequestAppointmentPage requestAppointmentPage = new RequestAppointmentPage(driver);
        ServiceTypeApp serviceTypeApp = new ServiceTypeApp(driver);

        logInAsAdmin();

        appDashboard.openAppointmentSchedulingApp();
        appointmentSchedulingApp.openManageAppointmentTypesApp();

        String serviceTypeName = NameGenerator.getServiceTypeName();
        serviceTypeApp.openNewServiceType();
        serviceTypeApp.createServiceType(serviceTypeName, "20", "Description");

        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());
        clinicianDashboard.openRequestAppointmentForm();
        requestAppointmentPage.requestAppointment(serviceTypeName);

        header.home();

        appDashboard.openAppointmentSchedulingApp();
        appointmentSchedulingApp.openAppointmentRequestsApp();

        // make sure the patient is in the list
        assertTrue(appointmentRequestsPage.containsRequestFor(adultTestPatient));

        appointmentRequestsPage.cancelLastRequest();

        // hack, stall
        Thread.sleep(3000);

        // patient should be removed from list
        assertFalse(appointmentRequestsPage.containsRequestFor(adultTestPatient));

    }

    @After
    public void deleteAppointmentTypeTestData() throws Exception {
        try {
            AppointmentTypeDatabaseHandler.deleteAppointmentTypes();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("tear down failed", e);
        }
    }

}
