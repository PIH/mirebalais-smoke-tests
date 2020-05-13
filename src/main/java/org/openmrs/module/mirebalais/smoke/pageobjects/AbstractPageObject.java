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

import org.openmrs.module.mirebalais.smoke.helper.SmokeTestProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Function;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public abstract class AbstractPageObject {

    protected SmokeTestProperties properties = new SmokeTestProperties();

    protected WebDriver driver;
    public WebDriverWait wait5seconds;
    public WebDriverWait wait15seconds;
    public WebDriverWait wait2minutes;
    private String baseServerUrl;

    public AbstractPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait5seconds = new WebDriverWait(driver, 5);
        this.wait15seconds = new WebDriverWait(driver,15);
        this.wait2minutes = new WebDriverWait(driver, 120);

        setBaseServerUrl();
    }

    protected void gotoPage(String addressSufix) {
        driver.get(baseServerUrl + addressSufix);
    }

    private void setBaseServerUrl() {
        String serverUrl = System.getProperty("baseServerUrl");
        this.baseServerUrl = (serverUrl == null || serverUrl.isEmpty() ? properties.getWebAppUrl() : serverUrl);
    }
    
    public void clickNext() {
        clickOn(By.id("right-arrow-yellow"));
    }
    
    public void clickYellowCheckMark() {
    	clickOn(By.id("checkmark-yellow"));
    }
    
    public void setTextToField(By element, String text) {
        wait5seconds.until(visibilityOfElementLocated(element));  // TODO hack until chromedriver bug is fixed
        setText(driver.findElement(element), text);
    }
    
    public void setTextToField(String textFieldId, String text) {
        setTextToField(By.id(textFieldId), text);
    }
    
    public void setClearTextToFieldThruSpan(String spanId, String text) {
    	setText(findTextFieldInsideSpan(spanId), text);
	}

    public void hitTabKey() {
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
    }

    public void hitTabKey(By elementId) {
        driver.findElement(elementId).sendKeys(Keys.TAB);
    }

    public void hitEnterKey() {
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);
    }


    public void hitEnterKey(By elementId) {
        driver.findElement(elementId).sendKeys(Keys.ENTER);
    }

    private void setText(WebElement element, String text) {
    	element.clear();
		element.sendKeys(text);
        element.sendKeys(Keys.RETURN);
    }
    
    private WebElement findTextFieldInsideSpan(String spanId) {
    	return driver.findElement(By.id(spanId)).findElement(By.tagName("input"));
    }
    
    public String format(String patientName) {
		int index = patientName.indexOf(" ");
		return new StringBuffer()
					.append(patientName.substring(index).trim())
					.append(", ")
					.append(patientName.substring(0,index).trim())
					.toString();
	}
    
    public void findPatientById(String patientIdentifier, By idField) throws Exception {
		WebElement element = driver.findElement(idField);
		element.sendKeys(patientIdentifier);
		element.sendKeys(Keys.RETURN);
	}
    
    private void clickOnTheRightPatient(String patientIdentifier) throws Exception {
	    clickOnOptionLookingForText(patientIdentifier, By.cssSelector("li.ui-menu-item"));
	}
    
    public void clickOnOptionLookingForText(String text, By by) throws Exception {
		getOptionBasedOnText(text,by).click();
	}
    
    public WebElement getOptionBasedOnText(String text, By elementIdentifier) throws Exception {
		List<WebElement> options = driver.findElements(elementIdentifier);
		for (WebElement option : options) {
	        if(option.getText().contains(text)) {
	            return option;
	        }
	    } 
		throw new Exception("Option not found");
    }

    public void clickOnRandomOption(By elementIdentifier) {
    	getRandomOption(elementIdentifier).click();
    }

    public WebElement getRandomOption(By elementIdentifier) {
    	List<WebElement> options = driver.findElements(elementIdentifier);
    	return options.get((int)(Math.random() * options.size()));
    }

    public void clickOn(WebElement element) {
        // Tricky ways to try to get deterministic click behavior in Selenium
        //   see https://stackoverflow.com/questions/31725033/selenium-click-not-always-working/31725102#31725102
        // Simple
//        element.click();
        // Trick #2
//        Actions actions = new Actions(driver);
//        actions.moveToElement(element).click().build().perform();
        // Trick #3
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
        // Trick #4 (from comments)
//        element.sendKeys(Keys.ENTER);
    }

    public void clickOn(By byClause) {
        // see https://stackoverflow.com/questions/31725033/selenium-click-not-always-working/31725102#31725102
        // Trick #1
        WebElement element = wait15seconds.until(ExpectedConditions.elementToBeClickable(byClause));
        clickOn(element);
	}

    public <V> V clickUntil(WebElement element, Function<? super WebDriver, V> isTrue) {
        return clickUntil(element, isTrue, 15);
    }

	public <V> V clickUntil(WebElement element, Function<? super WebDriver, V> isTrue, int timeout) {
        long startMillis = System.currentTimeMillis();
        int attempt = 0;
        while (true) {
            attempt++;
            try {
                switch (attempt % 4) {
                    case 0:
                        Actions actions = new Actions(driver);
                        actions.moveToElement(element).click().build().perform();
                    case 1:
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        js.executeScript("arguments[0].click();", element);
                    case 2:
                        element.click();
                    case 3:
                        element.sendKeys(Keys.ENTER);
                }
            } catch (StaleElementReferenceException | ElementNotInteractableException e) {}
            try {
                return (new WebDriverWait(driver, 1)).until(isTrue);
            } catch (TimeoutException e) {
                if ((System.currentTimeMillis() - startMillis) / 1000 > timeout) {
                    throw new TimeoutException(
                            String.format("Kept clicking for %s seconds and never succeeded", timeout),
                            e);
                }
            }
        }
    }

    public void clickOnFirst(By elementId) {
        List<WebElement> elements = driver.findElements(elementId);
        clickOn(elements.get(0));
    }

    public void clickOnLast(By elementId) {
        List<WebElement> elements = driver.findElements(elementId);
        clickOn(elements.get(elements.size()-1));
    }

    public void hoverOn(By elementId) {
        Actions builder = new Actions(driver);
        Actions hover = builder.moveToElement(driver.findElement(elementId));
        hover.perform();
    }

    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void selectFromDropdown(String id, int elementPosition) {
        findSelectOptions(By.id(id)).get(elementPosition).click();
    }

    public void selectFromDropdown(By element, int elementPosition) {
        findSelectOptions(element).get(elementPosition).click();
    }

    public List<WebElement> findSelectOptions(By element) {
        return driver.findElement(element).findElements(By.tagName("option"));
    }

    protected WebElement findOptionByText(String text, WebElement selectElement) {
        for (WebElement option : selectElement.findElements(By.tagName("option"))) {
            if (option.getText().contains(text)) {
                return option;
            }
        }
        return null;
    }

}
