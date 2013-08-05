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

import org.openmrs.module.mirebalais.smoke.pageobjects.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ConsultNoteForm extends AbstractPageObject {

	public static final String ADMISSION = "admitToHospital";
	public static final String DEATH = "markPatientDead";
	public static final String DISCHARGE = "discharge";
	public static final String TRANSFER = "transferWithinHospital";

    private By locationsForTransferWithinHospital = By.cssSelector("#transferWithinHospital-field option");
    private By locationsForAdmission = By.cssSelector("#admitToHospital-field option");

    public ConsultNoteForm(WebDriver driver) {
		super(driver);
	}

	public void fillFormWithDischarge(String primaryDiagnosis) throws Exception {
		fillFormWithBasicInfo(primaryDiagnosis, DISCHARGE);
	}
	
	public void fillFormWithDeath(String primaryDiagnosis) throws Exception {
		fillFormWithBasicInfo(primaryDiagnosis, DEATH);
	}
	
	public String fillFormWithAdmissionAndReturnLocation(String primaryDiagnosis, int locationNumbered) throws Exception {
        return fillFormAndReturnPlace(primaryDiagnosis, ADMISSION, locationsForAdmission, locationNumbered);
	}

	public String fillFormWithTransferAndReturnLocation(String primaryDiagnosis, int locationNumbered) throws Exception {
        return fillFormAndReturnPlace(primaryDiagnosis, TRANSFER, locationsForTransferWithinHospital, locationNumbered);
	}

    public void editPrimaryDiagnosis(String primaryDiagnosis) throws Exception {
        removePrimaryDiagnosis();
        choosePrimaryDiagnosis(primaryDiagnosis);
        confirmData();
    }

	protected void fillFormWithBasicInfo(String primaryDiagnosis, String disposition) throws Exception {
		choosePrimaryDiagnosis(primaryDiagnosis);
		chooseDisposition(disposition);
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

	protected void choosePrimaryDiagnosis(String primaryDiagnosis) {
		setClearTextToField("diagnosis-search", primaryDiagnosis);
		driver.findElement(By.cssSelector("strong.matched-name")).click();
	}
	
	protected void chooseDisposition(String dispositionValue) throws Exception {
        Select dispositions = new Select(driver.findElement(By.id("disposition-field")));
        dispositions.selectByValue(dispositionValue);
    }

    protected void removePrimaryDiagnosis() {
        clickOn(By.cssSelector("#display-encounter-diagnoses-container .delete-item"));
    }

    protected void confirmData() {
		clickOn(By.cssSelector("#buttons .confirm"));
	}

}
