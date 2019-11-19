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
import org.openqa.selenium.WebElement;

public class HeaderPage extends AbstractPageObject {

	public HeaderPage(WebDriver driver) {
		super(driver);
	}

	public void logOut() {
        // hack, just go to the logout url since the we are having so much trouble with the toast message overlapping the logout button
        driver.get(properties.getWebAppUrl() + "/appui/header/logout.action?successUrl=openmrs");
    }

    public void home() {
        // hack, just go to the logout url since the we are having trouble with the toast message overlapping the home button
        driver.get(properties.getWebAppUrl() + "/index.htm");
    }

	public By location() {
		return By.cssSelector("li.change-location span");
	}

	public String changeLocationTo(int listElement) throws Exception {
		clickOnLocationMenu();
        WebElement location = driver.findElements(By.cssSelector("ul.select li")).get(listElement - 1);
        String locationName = location.getAttribute("textContent");
        select(location);
        return locationName;
    }

    private void select(WebElement location) {
        location.click();
    }

    private void clickOnLocationMenu() {
		driver.findElement(By.className("icon-map-marker")).click();
	}

}
