package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VitalsApp extends AbstractPageObject {

    private static final String SEARCH_PATIENT_FIELD_ID = "patient-search";
	private static final By CONFIRM_PATIENT_BUTTON = By.className("icon-arrow-right");
	private static final By HEIGHT_INCHES_FIELD = By.id("height_inches");
	private static final By SEARCH_PATIENT_FIELD = By.id(SEARCH_PATIENT_FIELD_ID);
	private static final By WEIGHT_INCHES_FIELD = By.id("weight_lbs");
	
	public VitalsApp(WebDriver driver) {
		super(driver);
	}

	public void enterPatientIdentifier(String patientID) {
		setClearTextToField(SEARCH_PATIENT_FIELD_ID, patientID);
	}

	public void confirmPatient() {
		clickOn(CONFIRM_PATIENT_BUTTON);
	}

	public void enterVitals() {
        hitEnterOnInchesField();
        setClearTextToFieldThruSpan("height_cm", "15");
        hitEnterOnLbsField();
		setClearTextToFieldThruSpan("weight_kg", "50");
		hitEnterOnBMI();
        hitEnterOnFahrenheitField();
		setClearTextToFieldThruSpan("temperature_c", "36");
		setClearTextToFieldThruSpan("heart_rate", "50");
		setClearTextToFieldThruSpan("respiratory_rate", "50");
		setClearTextToFieldThruSpan("bp_systolic", "120");
		setClearTextToFieldThruSpan("bp_diastolic", "80");
		setClearTextToFieldThruSpan("o2_sat", "50");
		
		driver.findElement(By.id("confirmationQuestion")).findElement(By.className("confirm")).click();
	}

	private void hitEnterOnBMI() {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("document.getElementById('hidden-calculated-bmi').setAttribute('type', 'text');");
		driver.findElement(By.id("hidden-calculated-bmi")).sendKeys(Keys.RETURN);
	}

    private void hitEnterOnFahrenheitField() {
        driver.findElement(By.id("temperature_f")).sendKeys(Keys.RETURN);
    }

    private void hitEnterOnLbsField() {
        setClearTextToField(WEIGHT_INCHES_FIELD, "");
    }

    private void hitEnterOnInchesField() {
        driver.findElement(HEIGHT_INCHES_FIELD).sendKeys(Keys.RETURN);
    }

	public void captureVitalsForPatient(String identifier) {
		enterPatientIdentifier(identifier);
		confirmPatient();
		enterVitals();
	}
	
	public boolean isSearchPatientDisplayed() {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(SEARCH_PATIENT_FIELD));
            return true;
        } catch (Exception e) {
        	e.printStackTrace();
        	return false;
        }
	}
	
}