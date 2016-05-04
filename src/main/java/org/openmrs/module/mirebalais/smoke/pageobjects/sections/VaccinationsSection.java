package org.openmrs.module.mirebalais.smoke.pageobjects.sections;

import org.openmrs.module.mirebalais.smoke.pageobjects.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class VaccinationsSection extends AbstractPageObject {

    public VaccinationsSection(WebDriver driver) {
        super(driver);
    }

    public void addVaccination(Integer row, Integer column) {
        // confirm that we are in "add" mode before proceeding
        wait5seconds.until(visibilityOfElementLocated(By.xpath("//tbody/tr[" + row + "]/td[" + column + "]//a/i[@class='icon-plus']")));
        driver.findElement(By.className("vaccination-table")).findElement(By.xpath("//tbody/tr[" + row + "]/td[" + column + "]")).click();
        clickOn(By.className("vaccination-confirm"));
        wait5seconds.until(visibilityOfElementLocated(By.xpath("//tbody/tr[" + row + "]/td[" + column + "]//a/i[@class='icon-remove']")));
    }

    public void deleteVaccination(Integer row, Integer column) {
        // confirm that we are in "delete" mode before proceeding
        wait5seconds.until(visibilityOfElementLocated(By.xpath("//tbody/tr[" + row + "]/td[" + column + "]//a/i[@class='icon-remove']")));
        driver.findElement(By.className("vaccination-table")).findElement(By.xpath("//tbody/tr[" + row + "]/td[" + column + "]//a")).click();
        clickOn(By.className("vaccination-confirm"));
        wait5seconds.until(visibilityOfElementLocated(By.xpath("//tbody/tr[" + row + "]/td[" + column + "]//a/i[@class='icon-plus']")));
    }

    // TODO currently assumes only one vaccination section on the page
    public void clickOnVaccinationSection() {
        driver.findElement(By.className("vaccinations-section")).click();
    }

}
