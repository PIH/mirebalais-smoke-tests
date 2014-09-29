package org.openmrs.module.mirebalais.smoke;

import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.LacollineIntegrationFlow;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MasterPatientIndexTest extends BasicMirebalaisSmokeTest {
	
	private static final String DEFAULT_NAME = "test";

    // TODO find a new server for this to work off of

	@Test
    @Ignore
	public void searchForAPatientOnLacollineServer() {
		initBasicPageObjects();
        LacollineIntegrationFlow lacollineIntegrationFlow = new LacollineIntegrationFlow(driver);

        login();
		
		appDashboard.openMasterPatientIndexApp();
		lacollineIntegrationFlow.searchByName(DEFAULT_NAME);
		
		assertThat(lacollineIntegrationFlow.isImportButtonPresented(), is(true));
	}
	
}
