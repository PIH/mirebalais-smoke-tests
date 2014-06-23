/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ConsultNoteForm extends BaseHtmlForm {

    // TODO key on something other than text
	public static final String ADMISSION = "Admisyon";
	public static final String DEATH = "Mouri";
	public static final String DISCHARGE = "Egzeyat";
	public static final String TRANSFER = "Transfè anndan lopital la";

    private By locationsForTransferWithinHospital = By.cssSelector("#transferInternalLocation option");
    private By locationsForAdmission = By.cssSelector("#admissionLocation option");

    public ConsultNoteForm(WebDriver driver) {
		super(driver);
	}

    public void fillFormWithDischarge(String primaryDiagnosis) throws Exception {
        fillFormWithBasicInfo(primaryDiagnosis, DISCHARGE);
    }

    public void fillFormWithDeath(String primaryDiagnosis) throws Exception {
        choosePrimaryDiagnosis(primaryDiagnosis);
        chooseDisposition(DEATH);
        WebElement dateField = driver.findElement(By.cssSelector("#dateOfDeath input[type=text]"));
        dateField.sendKeys("20 Jun 2014");
        dateField.click();
        dateField.sendKeys(Keys.RETURN);
        confirmData();
	}
	
	public String fillFormWithAdmissionAndReturnLocation(String primaryDiagnosis, int locationNumbered) throws Exception {
        return fillFormAndReturnPlace(primaryDiagnosis, ADMISSION, locationsForAdmission, locationNumbered);
	}

	public String fillFormWithTransferAndReturnLocation(String primaryDiagnosis, int locationNumbered) throws Exception {
        return fillFormAndReturnPlace(primaryDiagnosis, TRANSFER, locationsForTransferWithinHospital, locationNumbered);
	}

    protected void fillFormWithBasicInfo(String primaryDiagnosis, String disposition) throws Exception {

        assertThat(submitButtonIsEnabled(),is(false));

        choosePrimaryDiagnosis(primaryDiagnosis);
        assertThat(submitButtonIsEnabled(),is(false));

        chooseDisposition(disposition);
        // for real-time consult note, submit button should not be enabled until diagnosis and disposition have been selected
        assertThat(submitButtonIsEnabled(),is(true));

        confirmData();
    }

    protected String fillFormAndReturnPlace(String primaryDiagnosis, String disposition, By dropdownOptionsLocator, int locationNumber) throws Exception  {
		choosePrimaryDiagnosis(primaryDiagnosis);
		chooseDisposition(disposition);
        wait5seconds.until(visibilityOfElementLocated(dropdownOptionsLocator));
        WebElement location = driver.findElements(dropdownOptionsLocator).get(locationNumber);
        String locationText = location.getText();
        location.click();
        confirmData();
        return locationText;
	}
	
	protected String chooseOption(By placeCombo, WebElement location) {
        wait5seconds.until(visibilityOfElementLocated(placeCombo));
        location.click();
		return location.getText();
	}

	protected void chooseDisposition(String dispositionText) throws Exception {
        Select dispositions = new Select(driver.findElement(By.cssSelector("span[id^='disposition'] select:nth-of-type(1)")));  // find the first select that is child of the span whose id starts with "disposition"
        dispositions.selectByVisibleText(dispositionText);
    }

}
