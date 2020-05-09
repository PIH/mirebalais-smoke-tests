package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppointmentSchedulingApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.DailyAppointments;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DailyAppointmentsTest extends DbTest{

    //@Test
    public void schedulerAdminLaunchDailyAppointments() throws Exception {
        AppDashboard appDashboard = new AppDashboard(driver);
        AppointmentSchedulingApp appointmentSchedulingApp = new AppointmentSchedulingApp(driver);
        DailyAppointments dailyAppointments = new DailyAppointments(driver);

        logInAsAdmin();
        appDashboard.openAppointmentSchedulingApp();
        appointmentSchedulingApp.openDailyAppointmentsApp();

        assertThat(dailyAppointments.isDateFilterDefined(), is(true));
        assertThat(dailyAppointments.isLocationFilterDefined(), is(true));
    }

}
