package org.openmrs.module.mirebalais.smoke.pageobjects;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DispensingApp extends AbstractPageObject {

    By activePrescriptionsSelector = By.xpath("//*[contains(text(), 'Active prescriptions')]");
    By allPrescriptionsSelector = By.xpath("//*[contains(text(), 'All prescriptions')]");
    By filterByLocationSelector = By.xpath("//input[@placeholder = 'Filter by location']");
    By searchByPatientIDOrNameSelector = By.xpath("//input[@placeholder = 'Search by patient ID or name']");
    By createdColumnHeader = By.xpath("//div[contains(text(), 'Created')]");
    By patientNameColumnHeader = By.xpath("//div[contains(text(), 'Patient name')]");
    By prescriberColumnHeader = By.xpath("//div[contains(text(), 'Prescriber')]");
    By locationColumnHeader = By.xpath("//div[contains(text(), 'Location')]");
    By drugsColumnHeader = By.xpath("//div[contains(text(), 'Drugs')]");
    By lastDispenserColumnHeader = By.xpath("//div[contains(text(), 'Last dispenser')]");
    By statusColumnHeader = By.xpath("//div[contains(text(), 'Status')]");


    public DispensingApp(WebDriver driver) {
        super(driver);
    }

    public boolean isActivePrescriptionsTabPresent() {
        wait15seconds.until(presenceOfElementLocated(activePrescriptionsSelector));
        return driver.findElement(activePrescriptionsSelector) != null;
    }

    public boolean isAllPrescriptionsTabPresent() {
        wait15seconds.until(presenceOfElementLocated(allPrescriptionsSelector));
        return driver.findElement(allPrescriptionsSelector) != null;
    }

    public boolean isFilterByLocationInputPresent() {
        wait15seconds.until(presenceOfElementLocated(filterByLocationSelector));
        return driver.findElement(filterByLocationSelector) != null;
    }

    public boolean isSearchByPatientIDOrNameInputPresent() {
        wait15seconds.until(presenceOfElementLocated(searchByPatientIDOrNameSelector));
        return driver.findElement(searchByPatientIDOrNameSelector) != null;
    }

    public boolean isCreatedColumnHeaderPresent() {
        wait15seconds.until(presenceOfElementLocated(createdColumnHeader));
        return driver.findElement(createdColumnHeader) != null;
    }

    public boolean isPatientNameColumnHeaderPresent() {
        wait15seconds.until(presenceOfElementLocated(patientNameColumnHeader));
        return driver.findElement(patientNameColumnHeader) != null;
    }

    public boolean isPrescriberColumnHeaderPresent() {
        wait15seconds.until(presenceOfElementLocated(prescriberColumnHeader));
        return driver.findElement(prescriberColumnHeader) != null;
    }

    public boolean isLocationColumnHeaderPresent() {
        wait15seconds.until(presenceOfElementLocated(locationColumnHeader));
        return driver.findElement(locationColumnHeader) != null;
    }

    public boolean isDrugsColumnHeaderPresent() {
        wait15seconds.until(presenceOfElementLocated(drugsColumnHeader));
        return driver.findElement(drugsColumnHeader) != null;
    }

    public boolean isLastDispenserColumnHeaderPresent() {
        wait15seconds.until(presenceOfElementLocated(lastDispenserColumnHeader));
        return driver.findElement(lastDispenserColumnHeader) != null;
    }

    public boolean isStatusColumnHeaderPresent() {
        wait15seconds.until(presenceOfElementLocated(statusColumnHeader));
        return driver.findElement(statusColumnHeader) != null;
    }

    public void clickActivePrescriptionsTab() {
        driver.findElement(activePrescriptionsSelector).click();
    }

    public void clickAllPrescriptionsTab() {
        driver.findElement(allPrescriptionsSelector).click();
    }

    public void clickOnFilterByLocationInput() {
        driver.findElement(filterByLocationSelector).click();
    }

    public void clickOnSearchByPatientIdOrNameInput() {
        driver.findElement(searchByPatientIDOrNameSelector).click();
    }

}
