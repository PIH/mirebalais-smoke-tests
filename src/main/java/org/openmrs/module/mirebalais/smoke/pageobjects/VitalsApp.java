package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class VitalsApp extends AbstractPageObject {

	public static final String SEARCH_PATIENT_FIELD_ID = "patient-search";

	private static final By CONFIRM_PATIENT_BUTTON = By.className("icon-arrow-right");
	private static final By HEIGHT_INCHES_FIELD = By.id("height_inches");
	private static final By WEIGHT_INCHES_FIELD = By.id("weight_lbs");
    private static final By LOCATION_FIELD = By.cssSelector("#encounterLocation select");

	public VitalsApp(WebDriver driver) {
		super(driver);
	}

	public void enterPatientIdentifier(String patientID) {
		setTextToField(SEARCH_PATIENT_FIELD_ID, patientID);
	}

	public void confirmPatient() {
		clickOn(CONFIRM_PATIENT_BUTTON);
	}

	public void enterVitals() {
	    hitEnterOnLocationField();
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
        setClearTextToFieldThruSpan("chief_complaint", "headache");
		driver.findElement(By.id("confirmationQuestion")).findElement(By.className("confirm")).click();
	}

	public void enterVitalsForInfant() {
        hitEnterOnLocationField();
		hitEnterOnInchesField();
		setClearTextToFieldThruSpan("height_cm", "15");
		hitEnterOnLbsField();
		setClearTextToFieldThruSpan("weight_kg", "50");
		hitEnterOnFahrenheitField();
		setClearTextToFieldThruSpan("temperature_c", "36");
		setClearTextToFieldThruSpan("heart_rate", "50");
		setClearTextToFieldThruSpan("respiratory_rate", "50");
		setClearTextToFieldThruSpan("bp_systolic", "120");
		setClearTextToFieldThruSpan("bp_diastolic", "80");
		setClearTextToFieldThruSpan("o2_sat", "50");
		hitEnterOnCmField();
		setClearTextToFieldThruSpan("muac_mm", "100");
		setClearTextToFieldThruSpan("head_cm", "100");
		hitEnterOnCalculatedRatio();
		setClearTextToFieldThruSpan("chief_complaint", "headache");
		driver.findElement(By.id("confirmationQuestion")).findElement(By.className("confirm")).click();
	}

	private void hitEnterOnBMI() {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("document.getElementById('hidden-calculated-bmi').setAttribute('type', 'text');");
		driver.findElement(By.id("hidden-calculated-bmi")).sendKeys(Keys.RETURN);
	}

	private void hitEnterOnCalculatedRatio() {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("document.getElementById('hidden-calculated-ratio').setAttribute('type', 'text');");
		driver.findElement(By.id("hidden-calculated-ratio")).sendKeys(Keys.RETURN);
	}

    private void hitEnterOnFahrenheitField() {
        driver.findElement(By.id("temperature_f")).sendKeys(Keys.RETURN);
    }

    private void hitEnterOnLbsField() {
        setTextToField(WEIGHT_INCHES_FIELD, "");
    }

    private void hitEnterOnInchesField() {
        driver.findElement(HEIGHT_INCHES_FIELD).sendKeys(Keys.RETURN);
    }

    private void hitEnterOnCmField() { driver.findElement(By.id("muac_cm")).sendKeys(Keys.RETURN); }

    private void hitEnterOnLocationField() {
        driver.findElement(LOCATION_FIELD).sendKeys(Keys.RETURN);
    }
}
