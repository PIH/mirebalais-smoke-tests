package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EmergencyCheckin extends AbstractPageObject {

	private LegacyRegistration legacyRegistration;
	
    public EmergencyCheckin(WebDriver driver) {
        super(driver);
        legacyRegistration = new LegacyRegistration(driver);
    }

    public void checkinMaleUnindentifiedPatient() {
        driver.findElement(By.id("registerJdBtn")).click();
        legacyRegistration.enterSexData();
    }

}
