package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;


public class PhoneticSearchTests extends BasicMirebalaisSmokeTest {

    private static LoginPage loginPage;
    private static IdentificationSteps identificationSteps;
    private Registration registration;
    
    public void specificSetUp() {
		
		registration = new Registration(driver, wait);
    }

    
    @BeforeClass
    public static void setUpEnvironment() {
    	loginPage = new LoginPage(driver);
		identificationSteps = new IdentificationSteps(driver, wait);
		
    	loginPage.logIn("admin", "Admin123");
    	identificationSteps.setLocationAndChooseRegisterTask();
    }

    
    @Test
    public void findsAMatch() {
    	registration.registerSpecificGuy("Jayne", "Marconi");
    	registration.openSimilarityWindow("June", "Marken");
    	
    	assertTrue(driver.findElement(By.id("confirmExistingPatientModalDiv")).getText().contains("June Marken"));
    	assertTrue(driver.findElement(By.className("confirmExistingPatientModalList")).getText().contains("Jayne Marconi"));
    }
    
    
    @Test
    public void doesNotfindAMatch() {
    	registration.registerSpecificGuy("June", "Marken");
    	registration.openSimilarityWindow("Jayne", "Marconi");

    	assertTrue(driver.findElement(By.id("confirmExistingPatientModalDiv")).getText().contains("Jayne Marconi"));
    	assertFalse(driver.findElement(By.className("confirmExistingPatientModalList")).getText().contains("June Marken"));
    }

    
    @After
    public void byeBye() {
    	registration.finishesRegistration();
    }
    
    
    
    @Test
    public void cleanUpData() {
    	driver.get("http://bamboo.pih-emr.org:8080/mirebalais/admin");
    	
    	//voidPatient("June Marken");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    	voidPatient("Jayne Marconi");
    }


	private void voidPatient(String patientName) {
		driver.findElement(By.linkText("Manage Patients")).click();
    	wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.id("inputNode")).isDisplayed();
			}
		});
    	driver.findElement(By.id("inputNode")).sendKeys(patientName);
    	
    	wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.id("openmrsSearchTable")).isDisplayed();
			}
		});
    	driver.findElement(By.className("odd")).click();
    	
    	wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.name("names[0].voided")).isDisplayed();
			}
		});
    	driver.findElement(By.name("names[0].voided")).click();
    	
    	wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.xpath("//*[@id=\"content\"]/form[2]/fieldset/input[2]")).isDisplayed();
			}
		});
    	driver.findElement(By.xpath("//*[@id=\"content\"]/form[2]/fieldset/input[2]")).click();
	}
     
}
