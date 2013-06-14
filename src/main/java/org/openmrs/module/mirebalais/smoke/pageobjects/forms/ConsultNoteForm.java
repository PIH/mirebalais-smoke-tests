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

import org.openmrs.module.mirebalais.smoke.helper.Waiter;
import org.openmrs.module.mirebalais.smoke.pageobjects.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ConsultNoteForm extends AbstractPageObject {

	private static final String PRIMARY_DIAGNOSIS = "IGU";
	
	public static final String ADMISSION = "Admisyon";
	public static final String DEATH = "Mouri";
	public static final String DISCHARGE = "Soti";
	public static final String TRANSFER = "Transfè anndan";
	
	public ConsultNoteForm(WebDriver driver) {
		super(driver);
	}

	public void fillFormWithDischarge() throws Exception {
		fillFormWithBasicInfo(DISCHARGE);
	}
	
	public void fillFormWithDeath() throws Exception {
		fillFormWithBasicInfo(DEATH);
	}
	
	public String fillFormWithAdmissionAndReturnPlace() throws Exception {
		return fillFormAndReturnPlace(ADMISSION, By.cssSelector("#admitToHospital-field option"));
	}

	public String fillFormWithTransferAndReturnPlace() throws Exception {
		return fillFormAndReturnPlace(TRANSFER, By.cssSelector("#transferWithinHospital-field option"));
	}

	protected void fillFormWithBasicInfo(String disposition) throws Exception {
		choosePrimaryDiagnosis();
		chooseDisposition(disposition);
		confirmData();
	}
	
	protected String fillFormAndReturnPlace(String disposition, By placeCombo) throws Exception  {
		choosePrimaryDiagnosis();
		chooseDisposition(disposition);
		String dischargePlace = chooseOption(placeCombo);
		confirmData();
		return dischargePlace;
	}
	
	protected String chooseOption(By placeCombo) {
    	Waiter.waitForElementToDisplay(placeCombo, 10, driver);
		WebElement option = getRandomOptionExcludingFirst(placeCombo);
		option.click();
		return option.getText();
	}
	
	protected void choosePrimaryDiagnosis() {
		setClearTextToField("diagnosis-search", PRIMARY_DIAGNOSIS);
		driver.findElement(By.cssSelector("strong.matched-name")).click();
	}
	
	protected void chooseDisposition(String disposition) throws Exception {
		clickOnOptionLookingForText(disposition, By.cssSelector("#dispositions option"));	
	}
	
	protected void confirmData() {
		clickOn(By.cssSelector("#buttons .confirm"));
	}

}
