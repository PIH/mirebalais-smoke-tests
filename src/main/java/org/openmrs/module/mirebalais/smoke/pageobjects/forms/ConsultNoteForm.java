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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ConsultNoteForm extends BaseHtmlForm {

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
        Thread.sleep(1000); // give the dx widget time to be hidden, for some reason there seems to be an error at times,  where the widget blocks entering the date of death
        chooseDisposition(DEATH);
        WebElement dateField = driver.findElement(By.cssSelector("#dateOfDeath input[type=text]"));
        dateField.sendKeys("20 Jun 2014");
        dateField.click();
        dateField.sendKeys(Keys.RETURN);
        confirmData();
	}
	
	public void fillFormWithAdmission(String primaryDiagnosis, String nonCodedDiagnosis, String locationName) throws Exception {
        fillFormAndReturnPlace(primaryDiagnosis, nonCodedDiagnosis, ADMISSION, locationsForAdmission, locationName);
	}

	public void fillFormWithTransfer(String primaryDiagnosis, String nonCodedDiagnosis, String locationName) throws Exception {
        fillFormAndReturnPlace(primaryDiagnosis, nonCodedDiagnosis, TRANSFER, locationsForTransferWithinHospital, locationName);
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

    protected void fillFormAndReturnPlace(String primaryDiagnosis, String nonCodedDiagnosis, String disposition, By dropdownOptionsLocator, String locationText) throws Exception  {
        choosePrimaryDiagnosis(primaryDiagnosis);
        chooseNonCodedDiagnosis(nonCodedDiagnosis);
        chooseDisposition(disposition);
        wait5seconds.until(visibilityOfElementLocated(dropdownOptionsLocator));
        WebElement location = driver.findElements(dropdownOptionsLocator).stream().filter(new Predicate<WebElement>() {
            @Override
            public boolean test(WebElement webElement) {
                return webElement.getText().equalsIgnoreCase(locationText);
            }
        }).collect(Collectors.toList()).get(0);
        location.click();
        confirmData();
    }
	
	protected String chooseOption(By placeCombo, WebElement location) {
        wait5seconds.until(visibilityOfElementLocated(placeCombo));
        location.click();
		return location.getText();
	}


}
