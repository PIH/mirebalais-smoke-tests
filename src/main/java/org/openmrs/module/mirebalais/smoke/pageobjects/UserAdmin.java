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

import java.util.List;

public class UserAdmin extends AbstractPageObject {
	
	private SysAdminPage adminPage;
	
	public UserAdmin(WebDriver driver) {
		super(driver);
		adminPage = new SysAdminPage(driver);
	}

	public boolean isAccountCreatedSuccesfully() {
		return driver.findElement(By.id("info-message")).getText().contains("Saved account successfully");
	}
	
	public void createClinicalAccount(String firstName, String lastName, String username, String password) {
		createAccount(firstName, lastName, username, password);
		chooseClinicalRole();
	    clickOnSave();
	}
	
	public void createRadiologyAccount(String firstName, String lastName, String username, String password) {
		createAccount(firstName, lastName, username, password);
		chooseRadiologyRole();
	    clickOnSave();
	}

	public void createDataArchivesAccount(String firstName, String lastName, String username, String password) {
		createAccount(firstName, lastName, username, password);
		chooseDataArchivesRole();
	    clickOnSave();
	}
	
	public void createSysAdminAccount(String firstName, String lastName, String username, String password) {
		createAccount(firstName, lastName, username, password);
		chooseSysAdminRole();
	    clickOnSave();
	}
	
	private void createAccount(String firstName, String lastName, String username, String password) {
		fillBasicInfo(firstName, lastName);
		fillUserAccountDetails(username, password);
	}
	
	private void fillBasicInfo(String firstName, String lastName) {
		adminPage.openManageAccounts();
		driver.findElement(By.id("create-account-button")).click();
		driver.findElement(By.name("givenName")).sendKeys(firstName);
		driver.findElement(By.name("familyName")).sendKeys(lastName);
	}
	
	private void fillUserAccountDetails(String username, String password) {
		driver.findElement(By.xpath("/html/body/div/div[2]/form/fieldset[2]/div[2]/input")).click();
		
		driver.findElement(By.name("username")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("confirmPassword")).sendKeys(password);

		WebElement select = driver.findElement(By.name("privilegeLevel"));
	    List<WebElement> options = select.findElements(By.tagName("option"));
	    for (WebElement option : options) {
	        if("Privilege Level: Full".equals(option.getText()))
	            option.click();
	    }
	}
	
	private void clickOnSave() {
		driver.findElement(By.xpath("/html/body/div/div[2]/form/input[3]")).click();
	}
	
	private void chooseClinicalRole() {
		driver.findElement(By.id("clinical")).click();
	}
	
	private void chooseDataArchivesRole() {
		driver.findElement(By.id("dataArchives")).click();
	}
	
	private void chooseRadiologyRole() {
		driver.findElement(By.id("radiology")).click();
	}
	
	private void chooseSysAdminRole() {
		driver.findElement(By.id("sysAdmin")).click();
	}
	
}
