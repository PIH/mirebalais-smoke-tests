package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyAccountApp extends AbstractPageObject{

	private static final By CHANGE_PASSWORD = By.cssSelector("#tasks i.icon-book");
	private static final By SAVE_BUTTON = By.id("save-button");
	
	
	public MyAccountApp(WebDriver driver) {
		super(driver);
	}

	public void openChangePassword() {
		clickOn(CHANGE_PASSWORD);
	}
	
	public void changePassword(String oldPassword, String newPassword) {
		setClearTextToField("oldPassword", oldPassword);
		setClearTextToField("newPassword", newPassword);
		setClearTextToField("confirmPassword", newPassword);
		clickOn(SAVE_BUTTON);
	}
}
