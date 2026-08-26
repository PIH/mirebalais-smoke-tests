package org.openmrs.module.pihemr.smoke.pageobjects.sections;

import org.openmrs.module.pihemr.smoke.pageobjects.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VaccinationsSection extends AbstractPageObject {

    public VaccinationsSection(WebDriver driver) {
        super(driver);
    }

      private WebElement findInVaccinationTable(String xpathSuffix) {
        return driver.findElement(By.className("vaccination-table")).findElement(By.xpath(xpathSuffix));
    }

    public void addVaccination(Integer row, Integer column) {
        String cell = ".//tbody/tr[" + row + "]/td[" + column + "]";
        // confirm that we are in "add" mode before proceeding
        // the vaccination schedule validity per dose is computed asynchronously after the table renders, so give it more than the usual 5s
        wait15seconds.until(d -> findInVaccinationTable(cell + "//a/i[@class='icon-plus']").isDisplayed());
        clickOn(findInVaccinationTable(cell + "//a/i[@class='icon-plus']"));
        confirmVaccinationDialog();
        wait15seconds.until(d -> findInVaccinationTable(cell + "//a/i[@class='icon-remove']").isDisplayed());
    }

    public void deleteVaccination(Integer row, Integer column) {
        String cell = ".//tbody/tr[" + row + "]/td[" + column + "]";
        // confirm that we are in "delete" mode before proceeding
        wait15seconds.until(d -> findInVaccinationTable(cell + "//a/i[@class='icon-remove']").isDisplayed());
        clickOn(findInVaccinationTable(cell + "//a/i[@class='icon-remove']"));
        confirmVaccinationDialog();
        wait15seconds.until(d -> findInVaccinationTable(cell + "//a/i[@class='icon-plus']").isDisplayed());
    }

    // the confirm dialog's click occasionally doesn't register with Angular on the first attempt
    // (the dialog remains open); re-click until it actually closes, rather than assuming success.
    private void confirmVaccinationDialog() {
        for (int attempt = 0; attempt < 3; attempt++) {
            clickOn(By.className("vaccination-confirm"));
            try {
                wait5seconds.until(d -> d.findElements(By.className("vaccination-confirm")).isEmpty());
                return;
            }
            catch (org.openqa.selenium.TimeoutException e) {
                // dialog still open; try clicking again
            }
        }
        throw new org.openqa.selenium.TimeoutException("vaccination-confirm dialog did not close after 3 attempts");
    }

    // TODO currently assumes only one vaccination section on the page
    public void clickOnVaccinationSection() {
        clickOn(By.className("vaccinations-section"));
    }

}
