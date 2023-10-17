package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.DispensingApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.SierraLeoneLoginPage;

/**
 * Smoke tests for the Dispensing ESM
 */
public class MedicationDispensingTest extends DbTest {

    // note: targeted for testing on Gladi, using Sierra Leone login page
    @Override
    protected LoginPage getLoginPage() { return new SierraLeoneLoginPage(driver); }
    @Test
    public void shouldLaunchDispensingApp() throws Exception {
        AppDashboard appDashboard = new AppDashboard(driver);
        DispensingApp dispensingApp = new DispensingApp(driver);

        logInAsAdmin("Wellbody Clinic");
        appDashboard.openMedicationDispensingApp();

        // just test that the expected elements are visible and clickable
        assertThat(dispensingApp.isAllPrescriptionsTabPresent(), is(true));
        dispensingApp.clickAllPrescriptionsTab();
        assertThat(dispensingApp.isActivePrescriptionsTabPresent(), is(true));
        dispensingApp.clickActivePrescriptionsTab();

        assertThat(dispensingApp.isFilterByLocationInputPresent(), is(true));
        dispensingApp.clickOnFilterByLocationInput();

        assertThat(dispensingApp.isSearchByPatientIDOrNameInputPresent(), is(true));
        dispensingApp.clickOnSearchByPatientIdOrNameInput();

        assertThat(dispensingApp.isCreatedColumnHeaderPresent(), is(true));
        assertThat(dispensingApp.isPatientNameColumnHeaderPresent(), is(true));
        assertThat(dispensingApp.isPrescriberColumnHeaderPresent(), is(true));
        assertThat(dispensingApp.isLocationColumnHeaderPresent(), is(true));
        assertThat(dispensingApp.isDrugsColumnHeaderPresent(), is(true));
        assertThat(dispensingApp.isLastDispenserColumnHeaderPresent(), is(true));
        assertThat(dispensingApp.isStatusColumnHeaderPresent(), is(true));
    }



}
