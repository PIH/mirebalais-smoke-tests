package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;

public class DashboardAppsOrderingTest extends BasicMirebalaisSmokeTest {
	
	private AppDashboard dashboardPO;
    private static LoginPage loginPage;

    @Before
    public void setup() {
    	loginPage = new LoginPage(driver);
        loginPage.logIn("admin", "Admin123");
        dashboardPO = new AppDashboard(driver);
    }

    @Test
    public void appsAreCorrectlyOrdered() {
        List<String> appsNames = dashboardPO.getAppsNames();
        assertThat(appsNames, contains(
                "Archives", "Retrospective Checkin", "Patient Registration and Check-In", "Find a Patient",
                "System Administration", "Active Visits", "(Legacy) OpenMRS Administration"));
    }
}
