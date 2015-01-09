package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PatientRegistration extends AbstractPageObject {

    public enum Gender {MALE, FEMALE}

    public PatientRegistration(WebDriver driver) {
        super(driver);
    }

    public void registerPatient(String givenName, String familyName, Gender gender, Integer birthDay, Integer birthMonth, Integer birthYear, String phoneNumber) throws Exception{
        //keepCurrentRegistrationDate();
        enterPatientName(givenName, familyName);
        enterGender(gender);
        enterBirthDate(birthDay,birthMonth, birthYear);
        //skipAddressFields();  // TODO implement for real once we add hierarchy support
        //enterTelephoneNumber(phoneNumber);
        //confirm();
    }

    public void keepCurrentRegistrationDate() {
        hitTabKey(By.id("registrationDateDay-field"));
        hitTabKey(By.id("registrationDateMonth-field"));
        hitTabKey(By.id("registrationDateYear-field"));
    }

    public void enterPatientName(String givenName, String familyName) {
        setTextToField(By.name("givenName"), givenName);
        setTextToField(By.name("familyName"), familyName);
    }

    public void enterGender(Gender gender) throws Exception {
        // TODO depends on test running in Creole
        clickOnOptionLookingForText((gender.equals(Gender.FEMALE) ? "Fi" : "Gason"), By.name("gender"));
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

    public void skipAddressFields() {
        hitTabKey(By.id("address2"));
        hitTabKey(By.id("address1"));
        hitTabKey(By.id("address3"));
        hitTabKey(By.id("cityVillage"));
        hitTabKey(By.id("stateProvince"));
        hitTabKey(By.id("country"));
    }

    public void enterTelephoneNumber(String number) {
        setTextToField(By.name("phoneNumber"), number);
    }

    public void confirm() {
        hitEnterKey(By.className("submitButton"));
    }



}
