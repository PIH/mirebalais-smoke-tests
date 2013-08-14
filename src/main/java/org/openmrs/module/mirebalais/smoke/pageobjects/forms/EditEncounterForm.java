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

public class EditEncounterForm extends AbstractPageObject {
	
	private final By providerDropDown = By.cssSelector("#provider select");
	
	private final By locationDropDown = By.cssSelector("#location select");
	
	private final By options = By.tagName("option");
	
	private String provider;
	
	private String location;
	
	public EditEncounterForm(WebDriver driver) {
		super(driver);
		wait5seconds.until(visibilityOfElementLocated(providerDropDown));
	}
	
	public void selectProvider(int providerOption) {
		WebElement provider = driver.findElement(providerDropDown).findElements(options).get(providerOption);
		provider.click();
		
		this.provider = provider.getText();
	}
	
	public void selectLocation(int locationOption) {
		WebElement location = driver.findElement(locationDropDown).findElements(options).get(locationOption);
		location.click();
		
		this.location = location.getText();
	}
	
	public SelectedProviderAndLocation selectedProviderAndLocation() {
		return new SelectedProviderAndLocation(provider, location);
	}
	
	public class SelectedProviderAndLocation {
		
		private final String location;
		
		private final String provider;
		
		SelectedProviderAndLocation(String provider, String location) {
			this.provider = provider;
			this.location = location;
		}
		
		public String provider() {
			return provider;
		}
		
		public String location() {
			return location;
		}
	}
}
