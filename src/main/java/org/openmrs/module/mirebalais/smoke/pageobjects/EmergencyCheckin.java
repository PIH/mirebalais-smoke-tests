package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;

public class EmergencyCheckin extends AbstractPatientRegistrationPageObject {

    public EmergencyCheckin(WebDriver driver, Wait<WebDriver> wait) {
        super(driver, wait);
    }

    @Override
    public void initialize() {
        login("admin", "Admin123");
        gotoPage("module/patientregistration/workflow/selectLocationAndService.form");
        driver.findElement(By.xpath("//*[@id='locationDiv']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[1]/td[1]")).click();
        driver.findElement(By.xpath("//*[@id='taskDiv']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td[1]")).click();

    }

    public void checkinMaleUnindentifiedPatient() {
        driver.findElement(By.id("registerJdBtn")).click();

        enterSexData();
    }

    public void registerCreateByName(String lastName, String firstName) {
        driver.findElement(By.id("searchByNameBtn")).click();

        enterFirstAndLastName(firstName, lastName);
        enterSexData();
        enterDateOfBirthData();

        enterAddressLandmarkData();
        enterPatientLocality();

        enterPhoneData();
        confirmData();

        chooseNotToPrintIdCard();

        // current date for encounter
        clickNext();

        chooseVisitReason("visitReasonStatus242"); // Standard Outpatient Visit
        choosePaymentAmount("paymentAmountStatus50"); // 50 gourdes
        enterReceiptNumber("ABC123");

        clickYellowCheckMark();
    }

}
