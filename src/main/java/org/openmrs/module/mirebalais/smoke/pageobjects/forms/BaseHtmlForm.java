package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.module.mirebalais.smoke.pageobjects.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public abstract class BaseHtmlForm extends AbstractPageObject {

    // TODO key on something other than text
    public static final String ADMISSION = "Admisyon";
    public static final String DEATH = "Mouri";
    public static final String DISCHARGE = "Egzeyat";
    public static final String TRANSFER = "Transfè anndan lopital la";

    private final By providerDropDown = By.cssSelector("#who select");

    private final By locationDropDown = By.cssSelector("#where select");

    private final By options = By.tagName("option");

    private String location;

    private String provider;

    public BaseHtmlForm(WebDriver driver) {
        super(driver);
    }

    public void choosePrimaryDiagnosis(String primaryDiagnosis) {
        setTextToField("diagnosis-search", primaryDiagnosis);
        driver.findElement(By.cssSelector("strong.matched-name")).click();
    }


    public void editPrimaryDiagnosis(String primaryDiagnosis) throws Exception {
        removePrimaryDiagnosis();
        choosePrimaryDiagnosis(primaryDiagnosis);
    }

    public void chooseNonCodedDiagnosis(String nonCodedDiagnosis) {
        if (StringUtils.isNotBlank(nonCodedDiagnosis)) {
            clickOn(By.cssSelector("#non-coded-diagnosis-toggle > input"));
            setTextToField(By.cssSelector("#non-coded-diagnosis > textarea"), nonCodedDiagnosis);
        }
    }

    public void confirmData() {
        List<WebElement>  elements = driver.findElements(By.cssSelector("#buttons .confirm"));

        for (WebElement element : elements) {
            if (!element.getAttribute("id").equals("next")) {
                clickOn(element);
                break;
            }
        }

      //  clickOn(elements.get(elements.size() -1 ));  // our visit notes have "next" and "confirm" buttons--hack to make sure we click "next", not confirm
    }

    public boolean submitButtonIsEnabled() {
        return !driver.findElement(By.cssSelector("#buttons .confirm")).getAttribute("class").contains("disabled");
    }

    public void removePrimaryDiagnosis() {
        clickOn(By.cssSelector("#display-encounter-diagnoses-container .delete-item"));
    }

    protected void chooseDisposition(String dispositionText) throws Exception {
        Select dispositions = new Select(driver.findElement(By.cssSelector("span[id^='disposition'] select:nth-of-type(1)")));  // find the first select that is child of the span whose id starts with "disposition"
        dispositions.selectByVisibleText(dispositionText);
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
