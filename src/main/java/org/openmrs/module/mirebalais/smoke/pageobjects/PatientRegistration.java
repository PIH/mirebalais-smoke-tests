package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class PatientRegistration extends AbstractPageObject {

    public enum Gender {MALE, FEMALE}

    public PatientRegistration(WebDriver driver) {
        super(driver);
    }

    public void registerPatient(String givenName, String familyName, Gender gender, Integer birthDay, Integer birthMonth,
                                Integer birthYear, String addressSearchValue, String phoneNumber) throws Exception{
        keepCurrentRegistrationDate();
        enterPatientName(givenName, familyName);
        enterGender(gender);
        enterBirthDate(birthDay, birthMonth, birthYear);
        enterAddressViaShortcut(addressSearchValue);
        enterTelephoneNumber(phoneNumber);
        automaticallyEnterIdentifier();
        confirm();
    }

    public void keepCurrentRegistrationDate() {
        hitEnterKey(By.id("checkbox-enable-registration-date"));
    }

    public void enterPatientName(String givenName, String familyName) {
        setTextToField(By.name("givenName"), givenName);
        setTextToField(By.name("familyName"), familyName);
    }

    public void enterGender(Gender gender) throws Exception {
        driver.findElement(By.name("gender")).findElements(By.tagName("option")).get((gender.equals(Gender.MALE) ? 1 : 2)).click();
        hitEnterKey(By.name("gender"));
    }

    public void enterBirthDate(Integer day, Integer month, Integer year) {

        setTextToField(By.name("birthdateDay"), day.toString());

        WebElement birthDateMonth = driver.findElement(By.name("birthdateMonth"));
        for (int i = 0; i < month; i++) {
            birthDateMonth.sendKeys(Keys.ARROW_DOWN);
        }
        birthDateMonth.sendKeys(Keys.TAB);

        setTextToField(By.name("birthdateYear"), year.toString());
    }

    public void enterAddressViaShortcut(String searchValue) {
        // use the shortcut to enter Cange
        driver.findElement(By.className("address-hierarchy-shortcut")).sendKeys(searchValue);
        wait5seconds.until(visibilityOfElementLocated(By.className("ui-menu-item")));
        hitEnterKey(By.className("address-hierarchy-shortcut"));
        hitEnterKey(By.className("address-hierarchy-shortcut"));
    }

    public void enterTelephoneNumber(String number) {
        setTextToField(By.name("phoneNumber"), number);
    }

    public void automaticallyEnterIdentifier() {
        driver.findElement(By.id("checkbox-autogenerate-identifier")).sendKeys(Keys.ENTER);
    }

    public void manuallyEnterIdentifier(String identifier) {
        driver.findElement(By.id("checkbox-autogenerate-identifier")).sendKeys(Keys.SPACE);
        setTextToField(By.id("patient-identifier"), identifier);
    }

    public void confirm() {
        hitEnterKey(By.className("submitButton"));
        hitEnterKey(By.className("confirm")); // the duplicate patient pop-up
        wait5seconds.until(visibilityOfElementLocated(By.id("checkbox-enable-registration-date")));  // wait for reload
    }



}
