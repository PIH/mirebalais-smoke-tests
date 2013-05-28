package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.LacollineIntegrationFlow;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;

public class MasterPatientIndexTest extends BasicMirebalaisSmokeTest {

	private LacollineIntegrationFlow lacollineIntegrationFlow;
	
	private static final String DEFAULT_NAME = "alex";
    
	@Before
	public void setUp() {
        loginPage = new LoginPage(driver);
        appDashboard = new AppDashboard(driver);
        lacollineIntegrationFlow = new LacollineIntegrationFlow(driver);
	}
    
	@Test
	public void searchForAPatientOnLacollineServer() {
        loginPage.logInAsAdmin();
                
        appDashboard.openMasterPatientIndexApp();
        lacollineIntegrationFlow.searchByName(DEFAULT_NAME);
        
        assertThat(lacollineIntegrationFlow.isImportButtonPresented(), is(true));
	}

}
