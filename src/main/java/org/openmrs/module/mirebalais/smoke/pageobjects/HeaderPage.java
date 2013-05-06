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
package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderPage extends AbstractPageObject {

	public HeaderPage(WebDriver driver) {
		super(driver);
	}

	public void logOut() {
		driver.findElement(By.className("logout")).click();
	}
	
	public String getLocation() {
		return driver.findElement(By.cssSelector("li.change-location span")).getText();
	}
	
	public void changeLocation(String location) throws Exception {
		clickOnLocationMenu();
    	chooseLocation(location);
	}
	
	private void clickOnLocationMenu() {
		driver.findElement(By.className("icon-map-marker")).click();
	}
	
	private void chooseLocation(String location) throws Exception {
		clickOnOptionLookingForText(location, By.cssSelector("ul.select li"));
	}
	
}
