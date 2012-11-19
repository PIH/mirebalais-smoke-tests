package org.openmrs.module.mirebalais.smoke.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UserAdmin extends AbstractPageObject {

	public UserAdmin(WebDriver driver) {
		super(driver);
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
		driver.findElement(By.linkText("Manage Accounts")).click();
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
