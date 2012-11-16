package org.openmrs.module.mirebalais.smoke.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UserAdmin extends AbstractPageObject {

	public UserAdmin(WebDriver driver) {
		super(driver);
	}


	public void createAccount(String firstName, String lastName, String username, String password) {
		driver.findElement(By.linkText("Create Account")).click();
		driver.findElement(By.name("givenName")).sendKeys(firstName);
		driver.findElement(By.name("familyName")).sendKeys(lastName);
		
		// clicar em user account details
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
	    
	    driver.findElement(By.xpath("//*[@id='capabilities']")).click();

	    // clicar em save button
	    driver.findElement(By.xpath("/html/body/div/div[2]/form/input[3]")).click();
	}

	public boolean isAccountCreatedSuccesfully() {
		return driver.findElement(By.id("info-message")).getText().contains("Saved account successfully");
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

}
