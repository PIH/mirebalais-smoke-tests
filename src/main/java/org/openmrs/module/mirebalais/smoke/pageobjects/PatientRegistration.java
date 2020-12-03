package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class PatientRegistration extends AbstractPageObject {

    private static final By SEARCH_RESULTS_TABLE = By.id("patient-search-results-table");

    public enum Gender {MALE, FEMALE}

    public PatientRegistration(WebDriver driver) {
        super(driver);
    }

    public void registerPatient(String givenName, String familyName, String nickname, Gender gender, Integer birthDay, Integer birthMonth,
                                Integer birthYear, String mothersFirstName, String birthplace, String addressSearchValue, String phoneNumber, Integer insuranceName, String insuranceNumber, String otherInsurance,
                                Integer martialStatus, Integer occupation, Integer religion, String contact, String relationship, String contactAddress,
                                Boolean addressUsesHierarchy, String contactPhoneNumber,
                                Boolean automaticallyEnterIdentifier, Boolean relationshipsEnabled, Boolean biometricsEnabled, Integer printIdCard,
                                Boolean additionalIdentifiersEnabled,
                                By successElement) throws Exception{

        wait15seconds.until(visibilityOfElementLocated(By.id("checkbox-enable-registration-date")));
        keepCurrentRegistrationDate();
        if (biometricsEnabled) {
            skipBiometricsSection();
        }
        enterPatientName(familyName, givenName, nickname);
        enterGender(gender);
        enterBirthDate(birthDay, birthMonth, birthYear);
        enterMothersFirstName(mothersFirstName);
        enterPersonAddressViaShortcut(addressSearchValue);
        enterPhoneNumber(phoneNumber);
        selectInsuranceName(insuranceName);
        enterInsuranceNumberAsFreeText(insuranceNumber);
        enterOtherInsuranceNameAsFreeText(otherInsurance);

        if (addressUsesHierarchy) {
            enterBirthplaceViaShortcut(birthplace);
        }
        else {
            enterBirthplaceAsFreeText(birthplace);
        }

        selectMaritalStatus(martialStatus);
        selectOccupation(occupation);
        selectReligion(religion);
        if (relationshipsEnabled) {
            skipRelationshipSection();
        }

        if (StringUtils.isNotBlank(contact)) {
            enterContactPerson(contact);
            enterContactRelationship(relationship);

            if (addressUsesHierarchy) {
                enterContactAddressViaShortcut(contactAddress);
            }
            else {
                enterContactAddressAsFreeText(contact);
            }

            enterContactPhoneNumber(contactPhoneNumber);
        }
        automaticallyEnterIdentifier(automaticallyEnterIdentifier);

        if (additionalIdentifiersEnabled) {
            enterAdditionalIdentifiers();
        }

        printIdCard(printIdCard);

        confirm(successElement);
    }

    public void keepCurrentRegistrationDate() {
        hitEnterKey(By.id("checkbox-enable-registration-date"));
    }

    public void skipBiometricsSection() {
        List<WebElement> fingerprintSections = driver.findElements(By.className("fingerprints-field"));
        if (fingerprintSections != null) {
            for (WebElement section : fingerprintSections) {
                hitEnterKey();
            }
        }
    }

    public void enterPatientName(String familyName, String givenName, String nickname) {
        setTextToField(By.name("familyName"), familyName);
        setTextToField(By.name("givenName"), givenName);
        setTextToField(By.name("middleName"), nickname);

    }

    public void enterGender(Gender gender) throws Exception {
        driver.findElement(By.name("gender")).findElements(By.tagName("option")).get((gender.equals(Gender.MALE) ? 0 : 1)).click();
        hitEnterKey(By.name("gender"));
    }

    public void enterBirthDate(Integer day, Integer month, Integer year) {

        setTextToField(By.name("birthdateDay"), day.toString());

        // TODO: revert https://github.com/PIH/mirebalais-smoke-tests/commit/62837a40a4461529c45cf78645aac65a032d2c92
        // TODO: after this chrome bug is fixed: https://code.google.com/p/chromium/issues/detail?id=513768
        WebElement birthDateMonth = driver.findElement(By.name("birthdateMonth"));
        new Select(birthDateMonth).selectByIndex(month);
        birthDateMonth.sendKeys(Keys.TAB);

        setTextToField(By.name("birthdateYear"), year.toString());
    }

    public void enterMothersFirstName(String mothersFirstName) {
        if (StringUtils.isNotBlank(mothersFirstName)) {
            setTextToField(By.name("mothersFirstName"), mothersFirstName);
        }
    }

    public void enterPersonAddressViaShortcut(String searchValue) {
        enterAddressViaShortcut(searchValue,0);  // index=0 because person address is first address in the registration form
    }

    public void enterAddressViaShortcut(String searchValue, int index) {

        // hack to hopefully add a delay when opening in edit mode
        if (index == 0) {
            wait5seconds.until(visibilityOfElementLocated(By.className("address-hierarchy-shortcut")));
        }

        WebElement searchBox = driver.findElements(By.className("address-hierarchy-shortcut")).get(index);
        searchBox.sendKeys(searchValue);
        wait5seconds.until(visibilityOfElementLocated(By.partialLinkText(searchValue)));
        searchBox.sendKeys(Keys.ENTER);
        searchBox.sendKeys(Keys.ENTER);
    }

    public void enterPhoneNumber(String number) {
        setTextToField(By.name("phoneNumber"), number);
    }

    public void enterBirthplaceViaShortcut(String searchValue) {
        if (StringUtils.isNotBlank(searchValue)) {
            enterAddressViaShortcut(searchValue, 1);  // index=1 because place of birth is the second address in the registration form
        }
    }

    public void enterBirthplaceAsFreeText(String birthplace) {
        if (StringUtils.isNotBlank(birthplace)) {
            setTextToField(By.name("obs.PIH:PLACE OF BIRTH"), birthplace);
        }
    }

    public void selectMaritalStatus(Integer option) {
        if (option != null) {
            selectFromDropdown(By.name("obs.PIH:CIVIL STATUS"), option);
            hitEnterKey(By.name("obs.PIH:CIVIL STATUS"));
        }
    }

    public void selectOccupation(Integer option) {
        if (option != null) {
            selectFromDropdown(By.name("obs.PIH:Occupation"), option);
            hitEnterKey(By.name("obs.PIH:Occupation"));
        }
    }

    public void skipRelationshipSection() {
        // TODO might want to implement this, but would depend on having another patient being set up
        hitTabKey(By.name("relationship_type"));
        hitTabKey(By.name("relationship_type"));

    }

    public void enterContactPerson(String contact) {
        if (StringUtils.isNotEmpty(contact)) {
            setTextToField(By.name("obsgroup.PIH:PATIENT CONTACTS CONSTRUCT.obs.PIH:NAMES AND FIRSTNAMES OF CONTACT"), contact);
        }
    }

    public void enterContactRelationship(String relationship) {
        if (StringUtils.isNotEmpty(relationship)) {
            setTextToField(By.name("obsgroup.PIH:PATIENT CONTACTS CONSTRUCT.obs.PIH:RELATIONSHIPS OF CONTACT"), relationship);
        }
    }

    public void enterContactAddressViaShortcut(String searchValue) {
        if (StringUtils.isNotEmpty(searchValue)) {
            enterAddressViaShortcut(searchValue, 2);  // index=2 because contact is the third address in the registration form
        }
    }

    public void enterContactAddressAsFreeText(String searchValue) {
        if (StringUtils.isNotEmpty(searchValue)) {
            setTextToField(By.name("obsgroup.PIH:PATIENT CONTACTS CONSTRUCT.obs.PIH:ADDRESS OF PATIENT CONTACT"), searchValue);
            hitTabKey(By.name("obsgroup.PIH:PATIENT CONTACTS CONSTRUCT.obs.PIH:ADDRESS OF PATIENT CONTACT"));
        }
    }

    public void enterContactPhoneNumber(String phoneNumber) {
        if (StringUtils.isNotEmpty(phoneNumber)) {
            setTextToField(By.name("obsgroup.PIH:PATIENT CONTACTS CONSTRUCT.obs.PIH:TELEPHONE NUMBER OF CONTACT"), phoneNumber);
        }
    }

    public void selectReligion(Integer option) {
        if (option != null) {
            selectFromDropdown(By.name("obs.PIH:Religion"), option);
            hitEnterKey(By.name("obs.PIH:Religion"));
        }
    }

    public void selectInsuranceName(Integer option) {
        if (option != null) {
            selectFromDropdown(By.name("obsgroup.PIH:Insurance CONSTRUCT.obs.PIH:Haiti insurance company name"), option);
            hitEnterKey(By.name("obsgroup.PIH:Insurance CONSTRUCT.obs.PIH:Haiti insurance company name"));
        }
    }

    public void enterInsuranceNumberAsFreeText(String insuranceNumber) {
        if (StringUtils.isNotBlank(insuranceNumber)) {
            setTextToField(By.name("obsgroup.PIH:Insurance CONSTRUCT.obs.PIH:Insurance policy number"), insuranceNumber);
        }
    }

    public void enterOtherInsuranceNameAsFreeText(String otherInsurance) {
        if (StringUtils.isNotBlank(otherInsurance)) {
            setTextToField(By.name("obsgroup.PIH:Insurance CONSTRUCT.obs.PIH:Insurance company name (text)"), otherInsurance);
        }
    }

    public void automaticallyEnterIdentifier(Boolean automaticallyEnterIdentifier) {
        if (automaticallyEnterIdentifier != null && automaticallyEnterIdentifier) {
            driver.findElement(By.id("checkbox-autogenerate-identifier")).sendKeys(Keys.ENTER);
        }
        else {
            // handle manual entry case
        }
    }

    public void enterAdditionalIdentifiers() {
        // TODO: actually test adding the identifiers instead of just skipping
        hitTabKey();
        hitTabKey();
        hitTabKey();
        hitTabKey();
    }

    public void printIdCard(Integer option) {
        if (option != null && option != 0) {
            selectFromDropdown(By.name("obs.PIH:ID Card Printing Requested"), option);
            hitEnterKey(By.name("obs.PIH:ID Card Printing Requested"));
        }
    }

    public void manuallyEnterIdentifier(String identifier) {
        driver.findElement(By.id("checkbox-autogenerate-identifier")).sendKeys(Keys.SPACE);
        setTextToField(By.id("patient-identifier"), identifier);
    }

    public void confirm(By successElement) {
        hitEnterKey(By.className("submitButton"));
        wait15seconds.until(visibilityOfElementLocated(successElement));  // wait for reload of the landing page
    }

    public void editExistingPatient(Patient patient, String givenName, String familyName,
                                    String nickname, Gender gender, Integer birthDay, Integer birthMonth,
                                    Integer birthYear, String mothersFirstName, String addressSearchValue,
                                    String contact, String relationship,
                                    Boolean placeOfBirthAndContactAddressUseHierarchy,
                                    String phoneNumber) throws Exception {

        // find the existing patient
        findExistingPatient(patient);
        editDemographics(familyName, givenName,  nickname, gender, birthDay, birthMonth, birthYear, mothersFirstName);
        editContactInfo(addressSearchValue, phoneNumber);
        editRegistration();
        //editSocial(addressSearchValue, religion, placeOfBirthAndContactAddressUseHierarchy);

        if (StringUtils.isNotEmpty(contact)) {
            editContactPerson(contact, relationship, addressSearchValue, placeOfBirthAndContactAddressUseHierarchy);
        }
    }

    public void findExistingPatient(Patient patient) {

        setTextToField(By.id("patient-search"), patient.getName());

        // patient should be in results list
        WebElement searchResults = driver.findElement(SEARCH_RESULTS_TABLE);
        searchResults.findElement(By.xpath("/*//*[contains(text(), '" + patient.getFamily_name() + ", "
                + patient.getGiven_name() + "')]")).click();


    }

    public void editDemographics(String familyName, String givenName, String nickname, Gender gender, Integer birthDay,
                                 Integer birthMonth, Integer birthYear, String mothersFirstName)  throws Exception {
        clickOn(By.id("demographics-edit-link"));
        enterPatientName(familyName, givenName, nickname);
        enterGender(gender);
        enterBirthDate(birthDay, birthMonth, birthYear);
        hitTabKey(By.id("birthdateEstimated-field"));
        enterMothersFirstName(mothersFirstName);
        confirm(By.id("demographics-edit-link")); // edit-link is success element to confirm back on edit page
    }

    public void editContactInfo(String searchValue, String phoneNumber) {
        clickOn(By.id("contactInfo-edit-link"));
        enterPersonAddressViaShortcut(searchValue);
        enterPhoneNumber(phoneNumber);
        confirm(By.id("demographics-edit-link")); // edit-link is success element to confirm back on edit page
    }

    public void editRegistration() {
        clickOn(By.cssSelector("#coreapps-mostRecentRegistrationSummary .edit-action"));
        // just edit location  and provider for now to keep it simple--should verify that the edit page opens, can be updated, and saves
        selectFromDropdown(By.cssSelector("#patientRegistration select:nth-of-type(1)"), 1);  // kind of a hack, should add ID to encounterProviderAndRole tag
        selectFromDropdown(By.cssSelector("#encounterLocationField select"), 1);
        confirm(By.id("demographics-edit-link")); // edit-link is success element to confirm back on edit page
    }

    public void editSocial(String addressSearchValue, Integer religion, Boolean useHierarchyForPlaceOfBirth) {
        clickOn(By.cssSelector("#coreapps-mostRecentRegistrationSocial .edit-action"));

        if (useHierarchyForPlaceOfBirth) {
            enterAddressViaShortcut(addressSearchValue, 0);
        }
        else {
            setTextToField(By.cssSelector("#placeOfBirth input"), addressSearchValue);
        }

        selectFromDropdown(By.cssSelector("#civilStatus"), 4);
        selectFromDropdown(By.cssSelector("#occupation"), 4);

        if (religion != null) {
            selectFromDropdown(By.cssSelector("#religion"), religion);
        }

        confirm(By.id("demographics-edit-link")); // edit-link is success element to confirm back on edit page
    }

    public void editContactPerson(String contact, String relationship, String addressSearchValue, Boolean userHierarchyForContactPersonAddress) {
        clickOn(By.cssSelector("#coreapps-mostRecentRegistrationContact .edit-action"));

        setTextToField(By.cssSelector("#contactName input"), contact);
        setTextToField(By.cssSelector("#contactRelationship input"), relationship);

        if (userHierarchyForContactPersonAddress) {
            enterAddressViaShortcut(addressSearchValue, 0);
        }
        else {
            setTextToField(By.cssSelector("#contactAddress textarea"), addressSearchValue);
        }

        setTextToField(By.cssSelector("#contactPhoneNumber input"), "555-1212");

        confirm(By.id("demographics-edit-link")); // edit-link is success element to confirm back on edit page
    }

}
