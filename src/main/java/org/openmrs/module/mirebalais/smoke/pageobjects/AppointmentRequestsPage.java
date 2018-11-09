package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class AppointmentRequestsPage extends AbstractPageObject{

    public AppointmentRequestsPage(WebDriver driver) {
        super(driver);
    }

    public Boolean containsRequestFor(Patient patient) {
        return containsRequestFor(patient.getNameLastNameFirst());
    }

    public Boolean containsRequestFor(String patientName) {
        try {
            wait5seconds.until(ExpectedConditions.textToBePresentInElement(By.cssSelector("body"), patientName));
        }
        catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public void cancelLastRequest() {
        List<WebElement> cancelIcons = driver.findElements(By.className("delete-item"));
        clickOn(cancelIcons.get(cancelIcons.size() - 1));
        clickOn(By.className("confirm"));
    }



}
