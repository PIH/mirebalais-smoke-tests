package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DailyAppointments extends AbstractPageObject {

    private static final By FILTER_DATE = By.cssSelector("#filter-date");
    private static final By FILTER_LOCATION = By.cssSelector("#filter-location");

    public DailyAppointments(WebDriver driver) {
        super(driver);
    }

    public boolean isDateFilterDefined() {
        return ( (driver.findElement(FILTER_DATE) != null) && driver.findElement(FILTER_DATE).isDisplayed()  ? true : false);
    }

    public Boolean isLocationFilterDefined() {
        return ( (driver.findElement(FILTER_LOCATION) != null) && driver.findElement(FILTER_LOCATION).isDisplayed() ? true : false);
    }
}
