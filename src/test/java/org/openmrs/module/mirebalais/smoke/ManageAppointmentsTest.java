package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppointmentSchedulingApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.ManageAppointments;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ManageAppointmentsTest extends DbTest {

    @Test
    public void sysAdminShouldLaunchManageAppointmentsAndFindAppointmentType() throws Exception {
        AppDashboard appDashboard = new AppDashboard(driver);
        AppointmentSchedulingApp appointmentSchedulingApp = new AppointmentSchedulingApp(driver);
        ManageAppointments manageAppointments = new ManageAppointments(driver);
        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();

        logInAsAdmin();
        appDashboard.openAppointmentSchedulingApp();
        appointmentSchedulingApp.openManageAppointmentsApp();

        manageAppointments.enterPatientIdentifier(testPatient.getIdentifier());

        assertThat(manageAppointments.isSelectServiceTypeDefined(), is(true));
        assertThat(manageAppointments.isDateRangePickerDefined(), is(true));

    }
}
