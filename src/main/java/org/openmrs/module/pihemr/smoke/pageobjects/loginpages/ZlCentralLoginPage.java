package org.openmrs.module.pihemr.smoke.pageobjects.loginpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ZlCentralLoginPage extends LoginPage {

    private static final String FACILITY_NAME = "Mirebalais";

    public ZlCentralLoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getLocale() {
        return "ht";
    }

    @Override
    public String getDefaultLocationName() {
        return "Klinik Ekstèn Jeneral";
    }

    // this server shows a facility-selection list (e.g. "Cange" vs "Mirebalais") before revealing
    // the location-selection section, but only when a user has access to more than one facility
    @Override
    protected void selectFacilityIfNeeded() {
        List<WebElement> facilityOptions = driver.findElements(
                By.xpath("//ul[contains(@class, 'visit-location-select')]//*[contains(text(), '" + FACILITY_NAME + "')]"));
        if (!facilityOptions.isEmpty()) {
            facilityOptions.get(0).click();
        }
    }
}

