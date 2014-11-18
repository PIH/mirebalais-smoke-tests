package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Implements common methods for interacting with the question-per-screen Simple Form UI by keyboard
 */
public abstract class AbstractSimpleFormUiPageObject extends AbstractPageObject {

    public AbstractSimpleFormUiPageObject(WebDriver driver) {
        super(driver);
    }


    public void pressEnter() {
        WebElement field = driver.switchTo().activeElement();
        field.sendKeys(Keys.RETURN);
    }

    /**
     * Sends keys, followed by Return, to the active element
     * @param keys
     */
    public void enterFieldByKeyboard(String keys) {
        WebElement field = driver.switchTo().activeElement();
        field.sendKeys(keys);
        field.sendKeys(Keys.RETURN);
    }

    /**
     * For each element in keysForFields, sends keys followed by Return, to the active element. (The Return after each
     * field will go to the next field.)
     * @param keysForFields
     */
    public void enterFieldsByKeyboard(String... keysForFields) {
        for (String keys : keysForFields) {
            enterFieldByKeyboard(keys);
        }
    }

    /**
     * Used for autocomplete widgets that need time to load their results
     * @param ms number of milliseconds to wait after typing keys, before pressing Enter
     * @param keys
     */
    public void enterFieldByKeyboardWithWait(long ms, String keys) {
        WebElement field = driver.switchTo().activeElement();
        field.sendKeys(keys);
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        field.sendKeys(Keys.RETURN);
    }

    /**
     * Used for autocomplete widgets that need time to load their results
     * @param ms number of milliseconds to wait after typing keys, before pressing Enter
     * @param keysForFields
     */
    public void enterFieldsByKeyboardWithWait(long ms, String... keysForFields) {
        for (String keys : keysForFields) {
            enterFieldByKeyboardWithWait(ms, keys);
        }
    }
}
