package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Registration extends AbstractPageObject {

    private static final String[] FIRST_NAMES = {"Alexandre", "Cosmin", "Darius", "Ellen", "Émerson", "Evan", "Fernando", "Mário", "Mark", "Neil", "Renee"};
	private static final String[] LAST_NAMES = {"Barbosa", "Ioan", "Jazayeri", "Ball", "Hernandez", "Waters", "Freire", "Areias", "Goodrich", "Craven", "Orser"}; 
	private Wait<WebDriver> wait;
	
	public Registration(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 5);
    }
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
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
		driver.findElement(By.id("searchByNameBtn")).click();
	}

    private void enterFirstAndLastName() {
		enterFirstAndLastName(getFirstName(), getLastName());
	}

	public String getFirstName() {
		return FIRST_NAMES[(int)(Math.random() * FIRST_NAMES.length)];
	}

	public String getLastName() {
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
	
    

    protected void enterPatientLocality() {
        driver.findElement(By.id("possibleLocalityField")).sendKeys("Mirebalais");
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                return !webDriver.findElement(By.id("loadingGraph")).isDisplayed();
            }
        });
        driver.findElement(By.cssSelector("tr.addressFieldRow.evenRow > td")).click();
        clickNext();

        driver.findElement(By.cssSelector("table#confirmPossibleLocalityModalList.searchTableList > tbody > tr.addressRow > td")).click();
    }

    protected void enterSexData() {
        driver.findElement(By.id("rdioM")).click();
        clickNext();
    }

    protected void enterDateOfBirthData() {
        driver.findElement(By.id("day")).sendKeys("1");
        driver.findElement(By.id("ui-active-menuitem")).click();
        driver.findElement(By.id("year")).sendKeys("1970");
        clickNext();
    }

    protected void enterAddressLandmarkData() {
		driver.findElement(By.id("addressLandmarkField")).sendKeys("mirebalais");
		clickNext();
	}

    protected void enterPhoneData() {
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.id("patientInputPhoneNumber")).isDisplayed();
			}
		});
		driver.findElement(By.id("patientInputPhoneNumber")).sendKeys("11111111");
        clickNext();
    }

    protected void confirmData() {
        //TODD: change this structure to assert something?
        clickCheckMark();
    }

    private void clickCheckMark() {
		driver.findElement(By.id("checkmark-yellow")).click();
	}

    protected void enterFirstName(String firstName) {
        driver.findElement(By.id("patientInputFirstName")).sendKeys(firstName);
        clickNext();
    }

    protected void enterLastName(String lastName) {
        driver.findElement(By.id("patientInputLastName")).sendKeys(lastName);
        clickNext();
    }

    protected void enterFirstAndLastName(String firstName, String lastName) {
        enterLastName(lastName);
        enterFirstName(firstName);
	}

    protected void chooseNotToPrintIdCard() {
        driver.findElement(By.id("printNo")).click();
    }

    protected void chooseToPrintIdCard() {
        driver.findElement(By.id("printYes")).click();
    }
	
}
