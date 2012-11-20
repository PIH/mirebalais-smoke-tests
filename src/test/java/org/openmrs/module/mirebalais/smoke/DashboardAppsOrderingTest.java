package org.openmrs.module.mirebalais.smoke;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class DashboardAppsOrderingTest extends BasicMirebalaisSmokeTest {

    private AppDashboard dashboardPO;
    private static LoginPage loginPage;

    @BeforeClass
    public static void setUpEnvironment() {
        loginPage = new LoginPage(driver);
        loginPage.logIn("admin", "Admin123");
    }

    @Override
    protected void specificSetUp() {
        dashboardPO = new AppDashboard(driver);
    }

    @Test
    public void appsAreCorrectlyOrdered() {
        List<String> appsNames = dashboardPO.getAppsNames();
        assertThat(appsNames, contains(
                "Archives", "Patient Registration and Check-In", "Find a Patient",
                "System Administration", "Active Visits", "(Legacy) OpenMRS Administration"));
    }
}
