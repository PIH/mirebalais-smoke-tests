package org.openmrs.module.mirebalais.smoke;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.apploader.CustomAppLoaderConstants;
import org.openmrs.module.mirebalais.smoke.helper.NameGenerator;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.MyAccountApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.SysAdminPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.UserAdmin;
import org.openqa.selenium.By;

import static org.apache.commons.lang.StringUtils.replaceChars;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserAdminTest extends DbTest {
	
	private static final String DEFAULT_PASSWORD = "Teste123";
	
	private UserAdmin userAdmin;
	
	private HeaderPage header;
	
	private MyAccountApp myAccountApp;
	
	private String username;
	
	@Before
	public void setUp() throws Exception {
		initBasicPageObjects();
		userAdmin = new UserAdmin(driver);
		header = new HeaderPage(driver);
		myAccountApp = new MyAccountApp(driver);
		
		username = createUser();
		
		login();
		appDashboard.openSysAdminApp();
	}
	
	@Test
	public void createUserWithPhysicianRoleAndUserChangesOwnPassword() throws Exception {

        userAdmin.createPhysicianAccount(NameGenerator.getUserFirstName(), NameGenerator.getUserLastName(), username,
                DEFAULT_PASSWORD);
		logOutAndLogInWithNewUser(username);
		
		appDashboard.openMyAccountApp();
		myAccountApp.openChangePassword();
		String newPassword = "something else!";
		myAccountApp.changePassword(DEFAULT_PASSWORD, newPassword);
		logOutAndLogInWithNewUser(username, newPassword);
		
		assertThat(appDashboard.isActiveVisitsAppPresented(), is(true));

        turnOffImplicitWaits(); // once we've found one element, assume that all are present
		assertThat(appDashboard.isUhmCaptureVitalsAppPresented(), is(true));
        assertThat(appDashboard.isAwaitingAdmissionAppPresented(), is(true));
        assertThat(appDashboard.isInpatientsAppPresented(), is(true));

        assertThat(appDashboard.isSchedulingAppPresented(), is(false));
		assertThat(appDashboard.isSystemAdministrationAppPresented(), is(false));
		assertThat(appDashboard.isPatientRegistrationAppPresented(), is(false));
		assertThat(appDashboard.isArchivesRoomAppPresented(), is(false));
		assertThat(appDashboard.isReportsAppPresented(), is(false));
		assertThat(appDashboard.isStartClinicVisitAppPresented(), is(false));
		assertThat(appDashboard.isLegacyAppPresented(), is(false));
        turnOnImplicitWait();
	}
	
	@Test
	public void createUserWithScheduleManagerRole() throws Exception {
		userAdmin.createScheduleManagerAccount(NameGenerator.getUserFirstName(), NameGenerator.getUserLastName(), username,
                DEFAULT_PASSWORD);

		logOutAndLogInWithNewUser(username);

        assertThat(appDashboard.isActiveVisitsAppPresented(), is(true));

        turnOffImplicitWaits(); // once we've found one element, assume that all are present
        assertThat(appDashboard.isAwaitingAdmissionAppPresented(), is(true));
        assertThat(appDashboard.isInpatientsAppPresented(), is(true));
        assertThat(appDashboard.isPatientRegistrationAppPresented(), is(true));
        assertThat(appDashboard.isReportsAppPresented(), is(true));
        assertThat(appDashboard.isSchedulingAppPresented(), is(true));

        assertThat(appDashboard.isStartClinicVisitAppPresented(), is(false));
        assertThat(appDashboard.isUhmCaptureVitalsAppPresented(), is(false));
        assertThat(appDashboard.isSystemAdministrationAppPresented(), is(false));
        assertThat(appDashboard.isArchivesRoomAppPresented(), is(false));
        assertThat(appDashboard.isLegacyAppPresented(), is(false));
        turnOnImplicitWait();
	}
	
	@Test
	public void createUserWithArchivistClerkRole() throws Exception {
		userAdmin.createArchivistClerkAccount(NameGenerator.getUserFirstName(), NameGenerator.getUserLastName(), username,
                DEFAULT_PASSWORD);

		logOutAndLogInWithNewUser(username);

        assertThat(appDashboard.isArchivesRoomAppPresented(), is(true));
        turnOffImplicitWaits(); // once we've found one element, assume that all are present
        assertThat(appDashboard.isPatientRegistrationAppPresented(), is(true));
        assertThat(appDashboard.isStartClinicVisitAppPresented(), is(true));
		assertThat(appDashboard.isReportsAppPresented(), is(true));

        assertThat(appDashboard.isSchedulingAppPresented(), is(false));
		assertThat(appDashboard.isUhmCaptureVitalsAppPresented(), is(false));
        assertThat(appDashboard.isActiveVisitsAppPresented(), is(false));
		assertThat(appDashboard.isSystemAdministrationAppPresented(), is(false));
		assertThat(appDashboard.isLegacyAppPresented(), is(false));
        assertThat(appDashboard.isAwaitingAdmissionAppPresented(), is(false));
        assertThat(appDashboard.isInpatientsAppPresented(), is(false));
        turnOnImplicitWait();
	}
	
	@Test
	public void createUserWithSysAdminRoleWithEnglishAsDesiredLanguage() throws Exception {
		userAdmin.createSysAdminAccount(NameGenerator.getUserFirstName(), NameGenerator.getUserLastName(), username,
		    DEFAULT_PASSWORD, "en");

		logOutAndLogInWithNewUser(username);

        assertThat(appDashboard.isSchedulingAppPresented(), is(true));
        turnOffImplicitWaits(); // once we've found one element, assume that all are present
        assertThat(appDashboard.isActiveVisitsAppPresented(), is(true));
        assertThat(appDashboard.isSystemAdministrationAppPresented(), is(true));
        assertThat(appDashboard.isReportsAppPresented(), is(true));
        assertThat(appDashboard.isInpatientsAppPresented(), is(true));
        assertThat(appDashboard.isArchivesRoomAppPresented(), is(true));
        assertThat(appDashboard.isPatientRegistrationAppPresented(), is(true));
        assertThat(appDashboard.isStartClinicVisitAppPresented(), is(true));
        assertThat(appDashboard.isUhmCaptureVitalsAppPresented(), is(true));

        assertThat(appDashboard.isLegacyAppPresented(), is(false));
        turnOnImplicitWait();
		
		appDashboard.openSysAdminApp();
        // confirm localized in English
		String text = driver.findElement(By.id(replaceChars(CustomAppLoaderConstants.Apps.MANAGE_ACCOUNTS, ".", "-") + SysAdminPage.SYSTEM_ADMINISTRATION_APP_LINK_SUFFIX)).getText();
		assertFalse(text.contains("Jere Kont"));
		assertTrue(text.contains("Manage Accounts"));
		
	}
	
	@After
	public void tearDown() {
		header.logOut();
	}
	
	private String createUser() {
		return new String("user" + System.currentTimeMillis());
	}

	private void logOutAndLogInWithNewUser(String username) throws InterruptedException {
		logOutAndLogInWithNewUser(username, DEFAULT_PASSWORD);
	}

	private void logOutAndLogInWithNewUser(String username, String password) throws InterruptedException {
		Thread.sleep(5000);
        header.logOut();
		loginPage.logIn(username, password);
	}
	
}
