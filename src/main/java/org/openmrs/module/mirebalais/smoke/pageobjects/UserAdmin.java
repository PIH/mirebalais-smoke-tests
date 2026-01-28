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

import org.openmrs.module.mirebalais.smoke.helper.UserDatabaseHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

// This is obviously only usable by Haiti/Mirebalais
public class UserAdmin extends AbstractPageObject {
	
	private static final String PHYSICIAN_ROLE = "physician";
	
	private static final String ARCHIVIST_CLERK_ROLE = "archivistClerk";
	
	private static final String SCHEDULE_MANAGER_ROLE = "scheduleManager";
	
	private static final String SYS_ADMIN_ROLE = "sysAdmin";
    private final String CREOLE = "ht";

    private SysAdminPage adminPage;
	
	private String[] PROVIDER_TYPES = { "Teknisyen Laboratwa", "Enfimyè (RN)", "Administratè Jeneral",
	        "Teknisyen Radyoloji", "Famasis", "Anestezist" };

    public UserAdmin(WebDriver driver) {
		super(driver);
		adminPage = new SysAdminPage(driver);
	}
	
	public void createPhysicianAccount(String firstName, String lastName, String username, String password) throws Exception {
		createA(PHYSICIAN_ROLE, firstName, lastName, username, password, CREOLE);
	}
	
	public void createScheduleManagerAccount(String firstName, String lastName, String username, String password) throws Exception {
		createA(SCHEDULE_MANAGER_ROLE, firstName, lastName, username, password, CREOLE);
	}
	
	public void createArchivistClerkAccount(String firstName, String lastName, String username, String password) throws Exception {
		createA(ARCHIVIST_CLERK_ROLE, firstName, lastName, username, password, CREOLE);
	}
	
	public void createSysAdminAccount(String firstName, String lastName, String username, String password, String language) throws Exception {
		createA(SYS_ADMIN_ROLE, firstName, lastName, username, password, language);
	}
	
	private void createA(String role, String firstName, String lastName, String username, String password, String language) throws Exception {
		createAccount(firstName, lastName, username, password);
		getRightRole(role).click();
		chooseProviderType();
		chooseLanguage(language);
		clickOnSave();
        UserDatabaseHandler.addUserForDelete(username);
		openAccount(username);
		setupTwoFactorAuthenticationWithSecretQuestion(password);
	}

	public void openAccount(String username) {
		driver.findElement(By.name("nameOrIdentifier")).sendKeys(username);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText(username)).click();
	}

	public void setupTwoFactorAuthenticationWithSecretQuestion(String password) throws Exception {
		driver.findElement(By.cssSelector(".action-section li:nth-of-type(4) a")).click();
		driver.findElement(By.id("option-secret")).click();
		driver.findElement(By.id("next-button")).click();
		driver.findElement(By.id("question")).sendKeys("Re-enter password");
		driver.findElement(By.id("answer")).sendKeys(password);
		driver.findElement(By.id("confirmAnswer")).sendKeys(password);
		try {
			driver.findElement(By.name("password")).sendKeys(password);
		}
		catch (Exception ignore) {}
		driver.findElement(By.id("save-button")).click();
	}
	
	private void chooseProviderType() {
		Select select = new Select(driver.findElement(By.name("providerRole")));
		select.selectByVisibleText(drawProviderType());
	}
	
	private String drawProviderType() {
		return PROVIDER_TYPES[(int) (Math.random() * PROVIDER_TYPES.length)];
	}
	
	private void chooseLanguage(String language) {
		Select select = new Select(driver.findElement(By.name("defaultLocale")));
        select.selectByValue(language);
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
		driver.findElement(By.id("createUserAccountButton")).click();
		driver.findElement(By.name("username")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("confirmPassword")).sendKeys(password);
		driver.findElement(By.name("passwordChangeRequired")).click();
	}
	
	private void clickOnSave() {
		driver.findElement(By.id("save-button")).click();
	}

    private WebElement getRightRole(String role) {
		List<WebElement> options = driver.findElements(By.name("capabilities"));
		for (WebElement element : options) {
			if (element.getAttribute("value").contains(role)) {
                scrollIntoView(element);
                return element;
			}
		}
		throw new ElementNotVisibleException("No element with name 'capabilities' where value contains role '" + role + "'");
	}
	
}
