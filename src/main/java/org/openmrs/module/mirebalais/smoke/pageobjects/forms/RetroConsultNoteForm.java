package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RetroConsultNoteForm extends ConsultNoteForm {

    public RetroConsultNoteForm(WebDriver driver) {
        super(driver);
    }

    protected void fillFormWithBasicInfo(String primaryDiagnosis, String disposition) throws Exception {

        assertThat(submitButtonIsEnabled(),is(false));

        choosePrimaryDiagnosis(primaryDiagnosis);
        assertThat(submitButtonIsEnabled(),is(false));

        selectProvider();
        assertThat(submitButtonIsEnabled(),is(false));

        selectLocation();
        assertThat(submitButtonIsEnabled(),is(false));   // aren't allowed to submit until disposition selected

        chooseDisposition(disposition);
        confirmData();
    }

    protected String fillFormAndReturnPlace(String primaryDiagnosis, String disposition, By dropdownOptionsLocator, int locationNumber) throws Exception  {
        selectProvider();
        selectLocation();
        choosePrimaryDiagnosis(primaryDiagnosis);
        chooseDisposition(disposition);
        wait5seconds.until(visibilityOfElementLocated(dropdownOptionsLocator));
        WebElement location = driver.findElements(dropdownOptionsLocator).get(locationNumber);
        String locationText = location.getText();
        location.click();
        confirmData();
        return locationText;
    }
}
