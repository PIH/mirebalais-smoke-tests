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

    public void registerPatient(String givenName, String familyName, String nickname, Gender gender, Integer birthDay, Integer birthMonth,
                                Integer birthYear, String mothersFirstName, String birthplace, String addressSearchValue, String phoneNumber,
                                int martialStatus, String occupation, int religion, String contact, String relationship, String contactAddress, String contactPhoneNumber,
                                int printIdCard) throws Exception{

        wait15seconds.until(visibilityOfElementLocated(By.id("checkbox-enable-registration-date")));
        keepCurrentRegistrationDate();
        enterPatientName(familyName, givenName, nickname);
        enterGender(gender);
        enterBirthDate(birthDay, birthMonth, birthYear);
        enterMothersFirstName(mothersFirstName);
        enterBirthplace(birthplace);
        enterAddressViaShortcut(addressSearchValue);
        enterTelephoneNumber(phoneNumber);
        selectMartialStatus(martialStatus);
        enterOccupation(occupation);
        selectReligion(religion);
        enterContactPerson(contact);
        enterContactRelationship(relationship);
        enterContactAddress(contactAddress);
        enterContactPhoneNumber(contactPhoneNumber);
        automaticallyEnterIdentifier();
        printIdCard(printIdCard);
        confirm();
    }

    private void enterContactAddress(String contactAddress) {
        setTextToField(By.name("obsgroup.PIH:PATIENT CONTACTS CONSTRUCT.obs.PIH:ADDRESS OF PATIENT CONTACT"), contactAddress);
        hitTabKey(By.name("obsgroup.PIH:PATIENT CONTACTS CONSTRUCT.obs.PIH:ADDRESS OF PATIENT CONTACT"));  // because this is a text area, need to tab, not enter
    }

    public void keepCurrentRegistrationDate() {
        hitEnterKey(By.id("checkbox-enable-registration-date"));
    }

    public void enterPatientName(String familyName, String givenName, String nickname) {
        setTextToField(By.name("familyName"), familyName);
        setTextToField(By.name("givenName"), givenName);
        setTextToField(By.name("middleName"), nickname);

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

    public void enterMothersFirstName(String mothersFirstName) {
        setTextToField(By.name("mothersFirstName"), mothersFirstName);
    }

    public void enterBirthplace(String birthplace) {
        setTextToField(By.name("birthplace"), birthplace);
        hitTabKey(By.name("birthplace"));  // because this is a text area, need to tab, not enter
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

    public void selectMartialStatus(int option) {
        selectFromDropdown(By.name("obs.PIH:CIVIL STATUS"), option);
        hitEnterKey(By.name("obs.PIH:CIVIL STATUS"));
    }

    public void enterOccupation(String occupation) {
        setTextToField(By.name("obs.PIH:2452"), occupation);
    }

    public void enterContactPerson(String contact) {
        setTextToField(By.name("obsgroup.PIH:PATIENT CONTACTS CONSTRUCT.obs.PIH:NAMES AND FIRSTNAMES OF CONTACT"), contact);
    }

    public void enterContactRelationship(String relationship) {
        setTextToField(By.name("obsgroup.PIH:PATIENT CONTACTS CONSTRUCT.obs.PIH:RELATIONSHIPS OF CONTACT"), relationship);
    }

    public void enterContactPhoneNumber(String phoneNumber) {
        setTextToField(By.name("obsgroup.PIH:PATIENT CONTACTS CONSTRUCT.obs.PIH:TELEPHONE NUMBER OF CONTACT"), phoneNumber);
    }

    public void selectReligion(int option) {
        selectFromDropdown(By.name("obs.PIH:Religion"), option);
        hitEnterKey(By.name("obs.PIH:Religion"));
    }

    public void automaticallyEnterIdentifier() {
        driver.findElement(By.id("checkbox-autogenerate-identifier")).sendKeys(Keys.ENTER);
    }

    public void printIdCard(int option) {
        selectFromDropdown(By.name("obs.PIH:ID Card Printing Requested"), option);
        hitEnterKey(By.name("obs.PIH:ID Card Printing Requested"));
    }

    public void manuallyEnterIdentifier(String identifier) {
        driver.findElement(By.id("checkbox-autogenerate-identifier")).sendKeys(Keys.SPACE);
        setTextToField(By.id("patient-identifier"), identifier);
    }

    public void confirm() {
        hitEnterKey(By.className("submitButton"));
        wait5seconds.until(visibilityOfElementLocated(By.id("register-patient-button")));  // wait for reload of the landing page
    }



}
