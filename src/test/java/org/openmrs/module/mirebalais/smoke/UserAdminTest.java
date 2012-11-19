package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.UserAdmin;

public class UserAdminTest extends BasicMirebalaisSmokeTest {

    private LoginPage loginPage;
    private AppDashboard appDashboard;
    private UserAdmin userAdmin;
    private String username;
	private static final String password = "Teste123";
    
    @Override
    public void specificSetUp() {
		loginPage = new LoginPage(driver);
		appDashboard = new AppDashboard(driver);
		userAdmin = new UserAdmin(driver);
    }

    @Test
    public void createUserWithClinicalRole() throws InterruptedException {
    	username = createUser();
    	
		loginPage.logIn("admin", "Admin123");
    	appDashboard.openSysAdminApp();
    	userAdmin.createClinicalAccount("Test", "User", username, password);
    	
    	assertTrue(userAdmin.isAccountCreatedSuccesfully());
    	
    	logOutAndLogInWithNewUser();
    	
    	assertTrue(appDashboard.isActiveVisitsAppPresented());
    	assertTrue(appDashboard.isFindAPatientAppPresented());
    	assertFalse(appDashboard.isSystemAdministrationAppPresented());
    	assertFalse(appDashboard.isPatientRegistrationAppPresented());
    	assertFalse(appDashboard.isArchivesRoomAppPresented());
	}
    
    @Test
    public void createUserWithRadiologyRole() throws InterruptedException {
    	username = createUser();
    	
		loginPage.logIn("admin", "Admin123");
		appDashboard.openSysAdminApp();
    	userAdmin.createRadiologyAccount("Test", "User", username, password);
    	
    	assertTrue(userAdmin.isAccountCreatedSuccesfully());
    	
    	logOutAndLogInWithNewUser();
    	
    	assertTrue(appDashboard.isActiveVisitsAppPresented());
    	assertTrue(appDashboard.isFindAPatientAppPresented());
    	assertFalse(appDashboard.isSystemAdministrationAppPresented());
    	assertFalse(appDashboard.isPatientRegistrationAppPresented());
    	assertFalse(appDashboard.isArchivesRoomAppPresented());
	}
    
    @Test
    public void createUserWithDataArchivesRole() throws InterruptedException {
    	username = createUser();
    	
		loginPage.logIn("admin", "Admin123");
		appDashboard.openSysAdminApp();
    	userAdmin.createDataArchivesAccount("Test", "User", username, password);
    	
    	assertTrue(userAdmin.isAccountCreatedSuccesfully());
    	
    	logOutAndLogInWithNewUser();
    	
    	assertTrue(appDashboard.isActiveVisitsAppPresented());
    	assertTrue(appDashboard.isFindAPatientAppPresented());
    	assertFalse(appDashboard.isSystemAdministrationAppPresented());
    	assertTrue(appDashboard.isPatientRegistrationAppPresented());
    	assertTrue(appDashboard.isArchivesRoomAppPresented());
	}
    
    @Test
    public void createUserWithSysAdminRole() throws InterruptedException {
    	username = createUser();
    	
		loginPage.logIn("admin", "Admin123");
    	
    	assertTrue(appDashboard.isActiveVisitsAppPresented());
    	assertTrue(appDashboard.isFindAPatientAppPresented());
    	assertTrue(appDashboard.isSystemAdministrationAppPresented());
    	assertTrue(appDashboard.isPatientRegistrationAppPresented());
    	assertTrue(appDashboard.isArchivesRoomAppPresented());
    	
    	appDashboard.openSysAdminApp();
    	userAdmin.createSysAdminAccount("Test", "User", username, password);
    	
    	assertTrue(userAdmin.isAccountCreatedSuccesfully());
    	
    	logOutAndLogInWithNewUser();
    	
    	assertTrue(appDashboard.isActiveVisitsAppPresented());
    	assertTrue(appDashboard.isFindAPatientAppPresented());
    	assertTrue(appDashboard.isSystemAdministrationAppPresented());
    	assertTrue(appDashboard.isPatientRegistrationAppPresented());
    	assertTrue(appDashboard.isArchivesRoomAppPresented());
	}
   
    @After
    public void tearDown() {
    	loginPage.logOut();
    }
    
    /* TODO: atualizar para:
     * 1) apagar o usuário criado
     * 2) ter os usuários com papeis diferentes pré-determinados e só garantir que está mostrando os corretos
     */
    private String createUser() {
    	return new String("user" + System.currentTimeMillis());
    }

       
    private void logOutAndLogInWithNewUser() {
    	loginPage.logOut();
    	loginPage.logIn(username, password);
    }
}
