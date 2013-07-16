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

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ConsultNoteForm extends AbstractPageObject {

	private static final String PRIMARY_DIAGNOSIS = "IGU";
	
	public static final String ADMISSION = "admitToHospital";
	public static final String DEATH = "markPatientDead";
	public static final String DISCHARGE = "discharge";
	public static final String TRANSFER = "transferWithinHospital";

    private By locationsForTransferWithinHospital = By.cssSelector("#transferWithinHospital-field option");
    private By locationsForAdmission = By.cssSelector("#admitToHospital-field option");

    public ConsultNoteForm(WebDriver driver) {
		super(driver);
	}

	public void fillFormWithDischarge() throws Exception {
		fillFormWithBasicInfo(DISCHARGE);
	}
	
	public void fillFormWithDeath() throws Exception {
		fillFormWithBasicInfo(DEATH);
	}
	
	public String fillFormWithAdmissionAndReturnLocation(int locationNumbered) throws Exception {
        return fillFormAndReturnPlace(ADMISSION, locationsForAdmission, locationNumbered);
	}

	public String fillFormWithTransferAndReturnLocation(int locationNumbered) throws Exception {
        return fillFormAndReturnPlace(TRANSFER, locationsForTransferWithinHospital, locationNumbered);
	}

	protected void fillFormWithBasicInfo(String disposition) throws Exception {
		choosePrimaryDiagnosis();
		chooseDisposition(disposition);
		confirmData();
	}
	
	protected String fillFormAndReturnPlace(String disposition, By dropdownOptionsLocator, int locationNumber) throws Exception  {
		choosePrimaryDiagnosis();
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
	
	protected void choosePrimaryDiagnosis() {
		setClearTextToField("diagnosis-search", PRIMARY_DIAGNOSIS);
		driver.findElement(By.cssSelector("strong.matched-name")).click();
	}
	
	protected void chooseDisposition(String disposition) throws Exception {
        driver.findElement(By.cssSelector("#disposition-field option[value=" + disposition + "]")).click();
	}
	
	protected void confirmData() {
		clickOn(By.cssSelector("#buttons .confirm"));
	}

}
