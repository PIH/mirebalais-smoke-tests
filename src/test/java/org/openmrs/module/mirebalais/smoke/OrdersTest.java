package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openqa.selenium.By;
@Ignore
public class OrdersTest extends BasicMirebalaisSmokeTest {

	private LoginPage loginPage;
    private AppDashboard appDashboard;
    private PatientDashboard patientDashboard;
    
    private static final String STUDY_1 = "Chest, 1 view (X-ray)";
    private static final String STUDY_2 = "Elbow - Left, 2 views (X-ray)";
    
    @Before
	public void setUp() {
		loginPage = new LoginPage(driver);
		appDashboard = new AppDashboard(driver);
		patientDashboard = new PatientDashboard(driver);
	}
    
	@Test
	public void orderSingleXRay() {
		loginPage.logInAsAdmin();
		appDashboard.openActiveVisitsApp();
		
		//TODO: create an user to do this instead of using this from other test
		driver.findElement(By.linkText("UNKNOWN UNKNOWN")).click();
		patientDashboard.orderXRay(STUDY_1, STUDY_2);
		
		assertTrue(patientDashboard.hasOrder(STUDY_1));
	}

	

}

