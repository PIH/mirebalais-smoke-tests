/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;

public abstract class AbstractPatientRegistrationPageObject extends AbstractPageObject {

    public AbstractPatientRegistrationPageObject(WebDriver driver, Wait<WebDriver> wait) {
        super(driver, wait);
    }

    @Override
    public void initialize() {
        // by default, do nothing
    }

    protected void clickNext() {
        driver.findElement(By.id("right-arrow-yellow")).click();
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
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.id("checkmark-yellow")).isDisplayed();
			}
		});
		driver.findElement(By.id("checkmark-yellow")).click();
	}

    protected void clickYellowCheckMark() {
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

    protected void enterReceiptNumber(String receiptNumber) {
        driver.findElement(By.id("receiptInput")).sendKeys(receiptNumber);
        clickNext();
    }

    protected void choosePaymentAmount(String paymentAmountElementId) {
        driver.findElement(By.id(paymentAmountElementId)).click();
    }

    protected void chooseVisitReason(String visitReasonElementId) {
        driver.findElement(By.id(visitReasonElementId)).click();
    }
}
