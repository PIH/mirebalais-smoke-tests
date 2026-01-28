package org.openmrs.module.mirebalais.smoke.pageobjects.loginpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SecretQuestionLoginPage {

	protected WebDriver driver;

	public SecretQuestionLoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void enterSecretQuestion(String secretQuestionAnswer) {
		driver.findElement(By.id("answer")).sendKeys(secretQuestionAnswer);
		driver.findElement(By.id("login-button")).click();
	}
}
