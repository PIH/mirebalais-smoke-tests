package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
		
		assertTrue(confirmRadiologyOrderInPatientDashboard());
	}

	//TODO: for now it's simple. sometime in the future this will be probably clickable so we can change behavior
	private boolean confirmRadiologyOrderInPatientDashboard() {
		List<WebElement> elements = driver.findElement(By.id("encountersList")).findElements(By.tagName("strong"));
		System.out.println("Tamanhao= " +  elements.size());
		for(WebElement element: elements) {
			if (element.getText().contains("Radiology Order")) {
				return true;
			}
			System.out.println(element.getText());
		}
		return false;
	}
}

