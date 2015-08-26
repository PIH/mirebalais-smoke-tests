package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.Keys.RETURN;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class CheckInFormPage extends AbstractPageObject {
	
	private static final String CONFIRM_TEXT = "Konfime";

    public static final By SEARCH_FIELD = By.id("patient-search");

	public CheckInFormPage(WebDriver driver) {
		super(driver);
	}

    public void enterInfo() {
        selectFirstOptionFor("typeOfVisit");
        selectSecondOptionFor("paymentAmount");
        findInputInsideSpan("receiptNumber").sendKeys("receipt #" + Keys.RETURN);
        selectNotToPrintWristbandIfQuestionPresent();

        WebElement confirmButton = driver.findElement(By.id("confirmationQuestion")).findElement(By.className("confirm"));
        confirmButton.click();

        new WebDriverWait(driver, 20).until(stalenessOf(confirmButton));
    }

    public void enterInfoWithMultipleEnterKeystrokesOnSubmit()  {
        selectFirstOptionFor("typeOfVisit");
        selectSecondOptionFor("paymentAmount");
        findInputInsideSpan("receiptNumber").sendKeys("receipt #" + Keys.RETURN);
        selectNotToPrintWristbandIfQuestionPresent();

        WebElement confirmButton = driver.findElement(By.id("confirmationQuestion")).findElement(By.className("confirm"));
        confirmButton.sendKeys(RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,
                RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,
                RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN,RETURN);

    }

	public void enterInfoFillingTheFormTwice(String patientIdentifier) throws Exception {
		findPatient(patientIdentifier);
		clickOnCheckIn();
        selectFirstOptionFor("typeOfVisit");
        selectSecondOptionFor("paymentAmount");
        findInputInsideSpan("receiptNumber").sendKeys("receipt #" + Keys.RETURN);
        selectNotToPrintWristbandIfQuestionPresent();
        clickOnNoButton();
        selectSecondOptionFor("typeOfVisit");
        selectThirdOptionFor("paymentAmount");
        findInputInsideSpan("receiptNumber").sendKeys("receipt #" + Keys.RETURN);
        selectNotToPrintWristbandIfQuestionPresent();
		confirmData();
		confirmPopup();
	}

	private void findPatient(String patientIdentifier) throws Exception {
		super.findPatientById(patientIdentifier, SEARCH_FIELD);
	}

	private void clickOnCheckIn() {
		clickOn(By.id("pih.checkin.registrationAction"));
	}

	private void confirmData() {
		clickOn(By.cssSelector("#confirmationQuestion .confirm"));
	}

    private void confirmDataForScheduleAppointment() {
        clickOn(By.cssSelector("#confirmationQuestion .confirm"));
    }
	
	private void clickOnNoButton() {
		clickOnConfirmationTab();
		clickOn(By.cssSelector("#confirmationQuestion input.cancel"));
	}
	
	private void clickOnConfirmationTab() {
		List<WebElement> elements = driver.findElements(By.cssSelector("#formBreadcrumb span"));
		for (WebElement element : elements) {
	        if(element.getText().contains(CONFIRM_TEXT)) {
	        	element.click();
	        }
	    }
	}
	
	private void confirmPopup() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.id("create-paper-record-dialog")));
        clickOn(By.cssSelector("#create-paper-record-dialog button"));
	}

    // TODO: revert https://github.com/PIH/mirebalais-smoke-tests/commit/e9ab41b02f4c263362b3627cd9e9b3cde951bd4f
    // TODO: after this chrome bug is fixed: https://code.google.com/p/chromium/issues/detail?id=513768
    private void selectOptionFor(String spanId, int option) {
        findSelectInsideSpan(spanId).selectByIndex(option);
    }

    private void selectFirstOptionFor(String spanId) {
        selectOptionFor(spanId, 1);
    }

    private void selectSecondOptionFor(String spanId) {
        selectOptionFor(spanId, 2);
    }

    private void selectThirdOptionFor(String spanId) {
        selectOptionFor(spanId, 3);
    }

    private Select findSelectInsideSpan(String spanId) {
        return new Select(driver.findElement(By.id(spanId)).findElement(By.tagName("select")));
    }

    private WebElement findInputInsideSpan(String spanId) {
        return driver.findElement(By.cssSelector("#" + spanId + " input"));

    }

	public boolean isPatientSearchDisplayed() {
		return driver.findElement(SEARCH_FIELD).isDisplayed();
	}

    private void selectNotToPrintWristbandIfQuestionPresent() {
        try {
            driver.findElement(By.id("print-wristband-question"));
            selectSecondOptionFor("print-wristband-question");
        }
        catch (NoSuchElementException e) {
            // ignore if question not present
        }
    }

}	