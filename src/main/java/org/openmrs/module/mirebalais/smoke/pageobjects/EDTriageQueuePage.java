package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class EDTriageQueuePage {
    private WebDriver driver;

    public EDTriageQueuePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean lookForPatient(Patient patient) {
        List<WebElement> elems = driver.findElements(
                    By.xpath("//td/a[contains(text(), '" + patient.getIdentifier() + "')]"));
        boolean found = elems.size() > 0 ? true : false;
        return(found);
    }
}
