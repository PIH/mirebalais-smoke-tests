package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import org.openmrs.module.mirebalais.smoke.pageobjects.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BaseHtmlForm extends AbstractPageObject {

    private final By providerDropDown = By.cssSelector("#who select");

    private final By locationDropDown = By.cssSelector("#where select");

    private final By options = By.tagName("option");

    private String location;

    private String provider;

    public BaseHtmlForm(WebDriver driver) {
        super(driver);
    }

    public void choosePrimaryDiagnosis(String primaryDiagnosis) {
        setClearTextToField("diagnosis-search", primaryDiagnosis);
        driver.findElement(By.cssSelector("strong.matched-name")).click();
    }


    public void editPrimaryDiagnosis(String primaryDiagnosis) throws Exception {
        removePrimaryDiagnosis();
        choosePrimaryDiagnosis(primaryDiagnosis);
    }

    public void confirmData() {
        clickOn(By.cssSelector("#buttons .confirm"));
    }

    public boolean submitButtonIsEnabled() {
        return !driver.findElement(By.cssSelector("#buttons .confirm")).getAttribute("class").contains("disabled");
    }

    public void removePrimaryDiagnosis() {
        clickOn(By.cssSelector("#display-encounter-diagnoses-container .delete-item"));
    }


    public void selectProvider(int providerOption) {
        WebElement provider = driver.findElement(providerDropDown).findElements(options).get(providerOption);
        provider.click();

        this.provider = provider.getText();
    }

    public void selectProvider() {
        selectProvider(1);
    }

    public void selectLocation(int locationOption) {
        WebElement location = driver.findElement(locationDropDown).findElements(options).get(locationOption);
        location.click();

        this.location = location.getText();
    }

    public void selectLocation() {
        selectLocation(1);
    }

    public String getLocation() {
        return location;
    }

    public String getProvider() {
        return provider;
    }
}
