package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;

public class Registration extends AbstractPatientRegistrationPageObject {

    private static final String[] FIRST_NAMES = {"Alexandre", "Cosmin", "Darius", "Ellen", "Émerson", "Evan", "Fernando", "Mário", "Mark", "Neil", "Renee"};
	private static final String[] LAST_NAMES = {"Barbosa", "Ioan", "Jazayeri", "Ball", "Hernandez", "Waters", "Freire", "Areias", "Goodrich", "Craven", "Orser"}; 
	
	public Registration(WebDriver driver, Wait<WebDriver> wait) {
        super(driver, wait);
    }
	
	public void goThruRegistrationProcessWithoutPrintingCard() {
		registerPatient();
        chooseNotToPrintIdCard();
    }
	
	public void goThruRegistrationProcessPrintingCard() {
		registerPatient();
        chooseToPrintIdCard();
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
        chooseNotToPrintIdCard();
        clickYellowCheckMark();
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

    private void enterFirstAndLastName() {
		enterFirstAndLastName(getFirstName(), getLastName());
	}

	private String getFirstName() {
		return FIRST_NAMES[(int)(Math.random() * FIRST_NAMES.length)];
	}

	private String getLastName() {
		return LAST_NAMES[(int)(Math.random() * LAST_NAMES.length)];
	}

    public void finishesRegistration() {
		driver.findElement(By.id("cancelBtn")).click();
		enterSexData();
		enterDateOfBirthData();
		enterAddressLandmarkData();
		enterPatientLocality();
		enterPhoneData();
		confirmData();
        chooseNotToPrintIdCard();
        clickYellowCheckMark();
    }
	
}
