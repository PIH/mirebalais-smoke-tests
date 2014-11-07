package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AppointmentRequestsPage extends AbstractPageObject{

    public AppointmentRequestsPage(WebDriver driver) {
        super(driver);
    }

    public Boolean containsRequestFor(Patient patient) {
        return containsRequestFor(patient.getName());
    }

    public Boolean containsRequestFor(String patientName) {
        return  driver.getPageSource().contains(patientName);
    }

    public void cancelLastRequest() {
        List<WebElement> cancelIcons = driver.findElements(By.className("delete-item"));
        clickOn(cancelIcons.get(cancelIcons.size() - 1));
        clickOn(By.className("confirm"));
    }



}
