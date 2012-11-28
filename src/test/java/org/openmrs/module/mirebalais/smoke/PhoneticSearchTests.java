package org.openmrs.module.mirebalais.smoke;


import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.CleanUpTests;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;

@Ignore
public class PhoneticSearchTests extends BasicMirebalaisSmokeTest {

    private static LoginPage loginPage;
    private Registration registration;
    
    private String patientOne = "June Marken";
	private String patientTwo = "Jayne Marconi";
    
    public void specificSetUp() {
		registration = new Registration(driver);
    }


    @BeforeClass
    public static void setUpEnvironment() {
    	loginPage = new LoginPage(driver);
    	loginPage.logIn("admin", "Admin123");
    }

    
    @Test
    public void findsAMatch() {
    	
    	registration.registerSpecificGuyWithoutPrintingCard(patientTwo);
    	registration.openSimilarityWindow(patientOne);
    	
    	assertTrue(driver.findElement(By.id("confirmExistingPatientModalDiv")).getText().contains(patientOne));
    	assertTrue(driver.findElement(By.className("confirmExistingPatientModalList")).getText().contains(patientTwo));
    }
    
    
    @After
    public void tearDown(){
        try{
            driver.navigate().to(properties.getWebAppUrl() +  "/mirebalais/module/patientregistration/workflow/patientRegistrationTask.form");
        } catch (WebDriverException e) {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
    }

    @AfterClass
    public static void deleteData() throws SQLException {
        CleanUpTests cleanUpTests = new CleanUpTests();
        cleanUpTests.deletePatientsWithNameAs("June","Marken");
        cleanUpTests.deletePatientsWithNameAs("June","Mark");
        cleanUpTests.deletePatientsWithNameAs("Jayne","Marconi");
		cleanUpTests.closeConnection();
		stopWebDriver();
    }
     
}
