package org.openmrs.module.mirebalais.smoke;

import org.dbunit.dataset.DataSetException;
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

import java.sql.SQLException;

import static org.apache.commons.lang3.StringUtils.replaceChars;
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
	public void setUp() throws SQLException, DataSetException {
		initBasicPageObjects();
		userAdmin = new UserAdmin(driver);
		header = new HeaderPage(driver);
		myAccountApp = new MyAccountApp(driver);
		
		username = createUser();
		
		login();
		appDashboard.openSysAdminApp();
	}
	
	@Test
	public void createUserWithClinicalRoleAndUserChangesOwnPassword() throws Exception {

        userAdmin.createClinicalAccount(NameGenerator.getUserFirstName(), NameGenerator.getUserLastName(), username,
		    DEFAULT_PASSWORD);
		logOutAndLogInWithNewUser(username);
		
		appDashboard.openMyAccountApp();
		myAccountApp.openChangePassword();
		myAccountApp.changePassword(DEFAULT_PASSWORD, DEFAULT_PASSWORD);
		logOutAndLogInWithNewUser(username);
		
		assertThat(appDashboard.isActiveVisitsAppPresented(), is(true));

        turnOffImplicitWaits(); // once we've found one element, assume that all are present
		assertThat(appDashboard.isCaptureVitalsAppPresented(), is(true));
		
		assertThat(appDashboard.isSystemAdministrationAppPresented(), is(false));
		assertThat(appDashboard.isPatientRegistrationAppPresented(), is(false));
		assertThat(appDashboard.isArchivesRoomAppPresented(), is(false));
		assertThat(appDashboard.isReportsAppPresented(), is(true));
		
		assertThat(appDashboard.isStartHospitalVisitAppPresented(), is(false));
		assertThat(appDashboard.isStartClinicVisitAppPresented(), is(false));
		assertThat(appDashboard.isEditPatientAppPresented(), is(true));
		
		assertThat(appDashboard.isLegacyAppPresented(), is(false));
        turnOnImplicitWait();
	}
	
	@Test
	public void createUserWithRadiologyRole() throws Exception {
		userAdmin.createRadiologyAccount(NameGenerator.getUserFirstName(), NameGenerator.getUserLastName(), username,
		    DEFAULT_PASSWORD);

		logOutAndLogInWithNewUser(username);
		
		assertThat(appDashboard.isActiveVisitsAppPresented(), is(true));

        turnOffImplicitWaits(); // once we've found one element, assume that all are present
		assertThat(appDashboard.isCaptureVitalsAppPresented(), is(false));
		
		assertThat(appDashboard.isSystemAdministrationAppPresented(), is(false));
		assertThat(appDashboard.isPatientRegistrationAppPresented(), is(false));
		assertThat(appDashboard.isArchivesRoomAppPresented(), is(false));
		assertThat(appDashboard.isReportsAppPresented(), is(true));
		
		assertThat(appDashboard.isStartHospitalVisitAppPresented(), is(false));
		assertThat(appDashboard.isStartClinicVisitAppPresented(), is(false));
		assertThat(appDashboard.isEditPatientAppPresented(), is(true));
		
		assertThat(appDashboard.isLegacyAppPresented(), is(false));
        turnOnImplicitWait();
	}
	
	@Test
	public void createUserWithDataArchivesRole() throws Exception {
		userAdmin.createDataArchivesAccount(NameGenerator.getUserFirstName(), NameGenerator.getUserLastName(), username,
		    DEFAULT_PASSWORD);

		logOutAndLogInWithNewUser(username);
		
		assertThat(appDashboard.isActiveVisitsAppPresented(), is(true));

        turnOffImplicitWaits(); // once we've found one element, assume that all are present
		assertThat(appDashboard.isCaptureVitalsAppPresented(), is(false));
		
		assertThat(appDashboard.isSystemAdministrationAppPresented(), is(false));
		assertThat(appDashboard.isPatientRegistrationAppPresented(), is(true));
		assertThat(appDashboard.isArchivesRoomAppPresented(), is(true));
		assertThat(appDashboard.isReportsAppPresented(), is(true));
		
		assertThat(appDashboard.isStartHospitalVisitAppPresented(), is(true));
		assertThat(appDashboard.isStartClinicVisitAppPresented(), is(true));
		assertThat(appDashboard.isEditPatientAppPresented(), is(true));
		
		assertThat(appDashboard.isLegacyAppPresented(), is(false));
        turnOnImplicitWait();
	}
	
	@Test
	public void createUserWithSysAdminRoleWithEnglishAsDesiredLanguage() throws Exception {
		userAdmin.createSysAdminAccount(NameGenerator.getUserFirstName(), NameGenerator.getUserLastName(), username,
		    DEFAULT_PASSWORD, "en");

		logOutAndLogInWithNewUser(username);
		
		assertThat(appDashboard.isActiveVisitsAppPresented(), is(true));

        turnOffImplicitWaits();
		assertThat(appDashboard.isCaptureVitalsAppPresented(), is(true));
		
		assertThat(appDashboard.isSystemAdministrationAppPresented(), is(true));
		assertThat(appDashboard.isPatientRegistrationAppPresented(), is(true));
		assertThat(appDashboard.isArchivesRoomAppPresented(), is(true));
		assertThat(appDashboard.isReportsAppPresented(), is(true));
		
		assertThat(appDashboard.isStartHospitalVisitAppPresented(), is(true));
		assertThat(appDashboard.isStartClinicVisitAppPresented(), is(true));
		assertThat(appDashboard.isEditPatientAppPresented(), is(true));
		
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
		Thread.sleep(5000);
        header.logOut();
		loginPage.logIn(username, DEFAULT_PASSWORD);
	}
	
}
