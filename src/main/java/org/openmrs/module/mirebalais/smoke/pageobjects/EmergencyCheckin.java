package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EmergencyCheckin extends AbstractPageObject {

    private AppDashboard appDashboard;
	private Registration registration;
	
    public EmergencyCheckin(WebDriver driver) {
        super(driver);
        appDashboard = new AppDashboard(driver);
        registration = new Registration(driver);
    }

  

    public void checkinMaleUnindentifiedPatient() {
        appDashboard.openStartHospitalVisitApp();
    	
        driver.findElement(By.id("registerJdBtn")).click();

        registration.enterSexData();
    }


}
