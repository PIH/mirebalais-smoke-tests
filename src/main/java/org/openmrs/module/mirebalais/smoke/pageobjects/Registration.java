package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;

public class Registration {

	private WebDriver driver;
	private Wait<WebDriver> wait;
	
	private static final String[] FIRST_NAMES = {"Alexandre", "Cosmin", "Darius", "Ellen", "Émerson", "Evan", "Fernando", "Mário", "Mark", "Neil", "Renee"}; 
	private static final String[] LAST_NAMES = {"Barbosa", "Ioan", "Jazayeri", "Ball", "Hernandez", "Waters", "Freire", "Areias", "Goodrich", "Craven", "Orser"}; 
	
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
	
	public void registerSpecificGuy(String firstName, String lastName) {
		clickOnSearchByNameButton();
		enterFirstAndLastName(firstName, lastName);
		enterSexData();
		enterDateOfBirthData();
		enterAddressLandmarkData();
		enterPatientLocality();
		enterPhoneData();
		confirmData();
		driver.findElement(By.id("printNo")).click();
		driver.findElement(By.id("checkmark-yellow")).click();
	}
	
	public void openSimilarityWindow(String firstName, String lastName) {
		clickOnSearchByNameButton();
		enterFirstAndLastName(firstName, lastName);
		
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return 	webDriver.findElement(By.id("confirmExistingPatientDiv")).isDisplayed() &&
						! (webDriver.findElement(By.id("confirmExistingPatientDiv")).getText().contains("searching"));
			}
		});
		driver.findElement(By.id("confirmExistingPatientDiv")).click();
		
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.id("confirmExistingPatientModalDiv")).isDisplayed();
			}
		});
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
		driver.findElement(By.id("possibleLocalityField")).sendKeys("Mirebalais");
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

	private void enterFirstAndLastName(String firstName, String lastName) {
		driver.findElement(By.id("patientInputLastName")).sendKeys(lastName);
		clickNext();
		
		driver.findElement(By.id("patientInputFirstName")).sendKeys(firstName);
		clickNext();
	}
	
	private void enterFirstAndLastName() {
		enterFirstAndLastName(getFirstName(), getLastName());
	}

	private String getFirstName() {
		return FIRST_NAMES[(int)(Math.random() * FIRST_NAMES.length)];
	}

	private String getLastName() {
		return LAST_NAMES[(int)(Math.random() * LAST_NAMES.length)];
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

	public void finishesRegistration() {
		driver.findElement(By.id("cancelBtn")).click();
		enterSexData();
		enterDateOfBirthData();
		enterAddressLandmarkData();
		enterPatientLocality();
		enterPhoneData();
		confirmData();
		driver.findElement(By.id("printNo")).click();
		driver.findElement(By.id("checkmark-yellow")).click();
	}
	
}
