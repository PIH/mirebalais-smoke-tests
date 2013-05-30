package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EmergencyCheckin extends AbstractPageObject {

	private Registration registration;
	
    public EmergencyCheckin(WebDriver driver) {
        super(driver);
        registration = new Registration(driver);
    }

    public void checkinMaleUnindentifiedPatient() {
        driver.findElement(By.id("registerJdBtn")).click();
        registration.enterSexData();
    }

}
