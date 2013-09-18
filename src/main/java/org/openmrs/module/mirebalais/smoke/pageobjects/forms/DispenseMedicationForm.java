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

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.openmrs.module.mirebalais.smoke.pageobjects.AbstractPageObject;
import org.openmrs.module.mirebalais.smoke.pageobjects.selects.DurationUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class DispenseMedicationForm extends AbstractPageObject {
	
	private By frequencyTextInput = cssSelector("#frequency1 input[type=text]");
	
	private By doseTextInput = cssSelector("#dose1 input[type=text]");
	
	private By doseUnitTextInput = cssSelector("#doseUnit1 input[type=text]");
	
	private By durationTextInput = cssSelector("#duration1 input[type=text]");
	
	private By durationUnitDropDown = cssSelector("#durationUnit1 select");
	
	private By amountTextInput = cssSelector("#amount1 input[type=text]");
	
	private By medicationNameAutocompleteInput = cssSelector("#name1 input.ui-autocomplete-input");
	
	private By firstAutocompleteResult = By.className("ui-menu-item");
	
	private ExpectedCondition<WebElement> firstResultIsDisplayed = visibilityOfElementLocated(firstAutocompleteResult);
	
	public DispenseMedicationForm(WebDriver driver) {
		super(driver);
	}
	
	public void fillFirstMedication(String name, String frequency, String dose, String doseUnit, String duration,
	        DurationUnit durationUnit, String amount) throws InterruptedException {
		
		autocompleteMedicationWith(name);
		
		driver.findElement(frequencyTextInput).sendKeys(frequency);
		driver.findElement(doseTextInput).sendKeys(dose);
		driver.findElement(doseUnitTextInput).sendKeys(doseUnit);
		driver.findElement(durationTextInput).sendKeys(duration);
		driver.findElement(durationUnitDropDown).findElements(By.tagName("option")).get(durationUnit.getIndex()).click();
		driver.findElement(amountTextInput).sendKeys(amount);
	}
	
	public void submit() {
		driver.findElement(By.className("submitButton")).click();
	}
	
	private void autocompleteMedicationWith(String name) throws InterruptedException {
		WebElement autocomplete = driver.findElement(medicationNameAutocompleteInput);
		
		autocomplete.sendKeys(name);
		wait5seconds.until(firstResultIsDisplayed);
		autocomplete.sendKeys(Keys.DOWN);
		autocomplete.sendKeys(Keys.ENTER);
	}
	
}
