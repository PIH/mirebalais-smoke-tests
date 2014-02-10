package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class RetroConsultNoteForm extends ConsultNoteForm {

    private final By providerDropDown = By.cssSelector("#who select");

    private final By locationDropDown = By.cssSelector("#where select");

    private final By options = By.tagName("option");

    public RetroConsultNoteForm(WebDriver driver) {
        super(driver);
    }

    protected void fillFormWithBasicInfo(String primaryDiagnosis, String disposition) throws Exception {

        assertThat(subbmitButtonIsEnabled(),is(false));

        choosePrimaryDiagnosis(primaryDiagnosis);
        assertThat(subbmitButtonIsEnabled(),is(false));

        chooseProvider();
        assertThat(subbmitButtonIsEnabled(),is(false));

        chooseLocation();
        assertThat(subbmitButtonIsEnabled(),is(false));   // aren't allowed to submit until disposition selected

        chooseDisposition(disposition);
        confirmData();
    }

    protected String fillFormAndReturnPlace(String primaryDiagnosis, String disposition, By dropdownOptionsLocator, int locationNumber) throws Exception  {
        chooseProvider();
        chooseLocation();
        choosePrimaryDiagnosis(primaryDiagnosis);
        chooseDisposition(disposition);
        wait5seconds.until(visibilityOfElementLocated(dropdownOptionsLocator));
        WebElement location = driver.findElements(dropdownOptionsLocator).get(locationNumber);
        String locationText = location.getText();
        location.click();
        confirmData();
        return locationText;
    }

    private void chooseProvider() {
        WebElement provider = driver.findElement(providerDropDown).findElements(options).get(1);
        provider.click();
    }

    private void chooseLocation() {
        WebElement location = driver.findElement(locationDropDown).findElements(options).get(1);
        location.click();
    }
}
