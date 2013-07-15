package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.mirebalais.smoke.helper.NameGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nullable;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Registration extends AbstractPageObject {

	public Registration(WebDriver driver) {
        super(driver);
    }
	
	public void goThruRegistrationProcessWithoutPrintingCard() {
		registerPatient();
        chooseNotToPrintIdCard();
    }
	
	public void goThruRegistrationProcessPrintingCard() {
		registerPatient();
        chooseToPrintIdCard();
        wait5seconds.until(visibilityOfElementLocated(By.id("scanPatientIdentifier")));
    }

    public String registerSpecificGuyWithoutPrintingCard(String name) {
    	clickOnSearchByNameButton();
        clickNext();
        enterFirstAndLastName(name);
		enterSexData();
		enterDateOfBirthData();
		enterAddressLandmarkData();
		enterPatientLocality();
		enterPhoneData();
		confirmData();
        chooseNotToPrintIdCard();
        return driver.findElement(By.id("patientPreferredId")).getText();
    }

	private void registerPatient() {
		clickOnSearchByNameButton();
        clickNext();
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
		enterFirstAndLastName(NameGenerator.getPatientName());
	}

    protected void enterPatientLocality() {
        driver.findElement(By.id("possibleLocalityField")).sendKeys("Mirebalais");
        wait5seconds.until(invisibilityOfElementLocated(By.id("loadingGraph")));

        driver.findElement(By.cssSelector("tr.addressFieldRow.evenRow > td")).click();
        clickNext();

        driver.findElement(By.cssSelector("table#confirmPossibleLocalityModalList.searchTableList > tbody > tr.addressRow > td")).click();
    }

    protected void enterSexData() {
        By byRadioMaleId = By.id("rdioM");
        driver.findElement(byRadioMaleId).click();
        clickNext();

        wait5seconds.until(invisibilityOfElementLocated(byRadioMaleId));
    }
    
    protected void enterDateOfBirthData() {
    	Integer day = 1 + (int)(Math.random() * 28);
    	Integer year = 1930 + (int)(Math.random() * 71);
        driver.findElement(By.id("day")).sendKeys(day.toString());
        driver.findElement(By.id("ui-active-menuitem")).click();
        driver.findElement(By.id("year")).sendKeys(year.toString());
        clickNext();
    }

    protected void enterAddressLandmarkData() {
		driver.findElement(By.id("addressLandmarkField")).sendKeys("mirebalais");
		clickNext();
	}

    protected void enterPhoneData() {
        wait5seconds.until(ExpectedConditions.visibilityOfElementLocated(By.id("patientInputPhoneNumber")));
        driver.findElement(By.id("patientInputPhoneNumber")).sendKeys("11111111");
        clickNext();
    }

    protected void confirmData() {
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

    protected void enterFirstAndLastName(String name) {
    	int spaceIndex = name.indexOf(' '); 
        enterLastName(name.substring(spaceIndex).trim());
        enterFirstName(name.substring(0, spaceIndex).trim());
	}

    protected void chooseNotToPrintIdCard() {
        driver.findElement(By.id("printNo")).click();
    }

    protected void chooseToPrintIdCard() {
        driver.findElement(By.id("printYes")).click();
    }
}
