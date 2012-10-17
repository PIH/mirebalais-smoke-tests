package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;

public class Registration {

	private WebDriver driver;
	private Wait<WebDriver> wait;
    
	public Registration(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
	}
	
	public void goThruRegistrationProcessWithoutPrintingCard() {
		registerPatient();
		
		driver.findElement(By.id("printNo")).click();
	}
	
	public void goThruRegistrationProcessPrintingCard() {
		registerPatient();
		
		driver.findElement(By.id("printYes")).click();
	}
	
	private void registerPatient() {
		clickOnSearchByNameButton();
		enterFirstAndLastName();
		enterSexData();
		enterDateOfBirthData();
		enterAddressLandmarkData();
		enterPatientLocality();
		enterPhoneData();
		confirmData();
	}

	private void clickOnSearchByNameButton() {
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.id("searchByNameBtn")).isDisplayed();
			}
		});
		driver.findElement(By.id("searchByNameBtn")).click();
	}
	
	private void confirmData() {
		//TODD: change this structure to assert something?
		clickCheckMark();
	}
	
	private void enterPhoneData() {
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.id("patientInputPhoneNumber")).isDisplayed();
			}
		});
		driver.findElement(By.id("patientInputPhoneNumber")).sendKeys("11111111");
		clickNext();
	}

	private void enterPatientLocality() {
		driver.findElement(By.id("possibleLocalityField")).sendKeys("mirebal");
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.cssSelector("tr.addressFieldRow.evenRow > td")) != null;
			}
		});
		driver.findElement(By.cssSelector("tr.addressFieldRow.evenRow > td")).click();
		clickNext();
		
		driver.findElement(By.cssSelector("table#confirmPossibleLocalityModalList.searchTableList > tbody > tr.addressRow > td")).click();
	}

	private void enterAddressLandmarkData() {
		driver.findElement(By.id("addressLandmarkField")).sendKeys("mirebalais");
		clickNext();
	}

	private void enterSexData() {
		// TODO: explicitly select male/female
		clickNext();
	}

	private void enterFirstAndLastName() {
		driver.findElement(By.id("patientInputLastName")).sendKeys("Silva");
		clickNext();
		
		driver.findElement(By.id("patientInputFirstName")).sendKeys("Pedro");
		clickNext();
	}

	private void enterDateOfBirthData() {
		driver.findElement(By.id("day")).sendKeys("1");
		driver.findElement(By.id("ui-active-menuitem")).click();
		driver.findElement(By.id("year")).sendKeys("1970");
		clickNext();
	}
    
	private void clickNext() {
		driver.findElement(By.id("right-arrow-yellow")).click();
	}
    
	private void clickCheckMark() {
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.id("checkmark-yellow")).isDisplayed();
			}
		});
		driver.findElement(By.id("checkmark-yellow")).click();
	}
	
}
