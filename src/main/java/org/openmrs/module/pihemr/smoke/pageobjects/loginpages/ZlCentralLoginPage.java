package org.openmrs.module.pihemr.smoke.pageobjects.loginpages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        By facilityOption = By.xpath("//ul[contains(@class, 'visit-location-select')]//*[contains(text(), '" + FACILITY_NAME + "')]");
        try {
            new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(facilityOption));
        }
        catch (TimeoutException e) {
            return; // facility-selection list never appeared, so this user doesn't need to pick one
        }
        List<WebElement> facilityOptions = driver.findElements(facilityOption);
        if (!facilityOptions.isEmpty()) {
            facilityOptions.get(0).click();
        }
    }
}

