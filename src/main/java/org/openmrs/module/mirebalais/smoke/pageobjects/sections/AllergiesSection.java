package org.openmrs.module.mirebalais.smoke.pageobjects.sections;

import org.openmrs.module.mirebalais.smoke.pageobjects.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class AllergiesSection extends AbstractPageObject {


    public AllergiesSection(WebDriver driver) {
        super(driver);
    }

    // TODO currently assumes only one allergy section on the page
    public void expandAllergiesSection() {
        clickOn(By.className("vaccinations-section"));
    }

    public void openAllergiesSection() {
        clickOn(By.className("edit-allergies"));
        // HACK: for some reason the first click does not work, perhaps because angular is not "ready"?
        try {
            wait5seconds.until(visibilityOfElementLocated(By.id("allergies")));
        }
        catch (TimeoutException e) {
            clickOn(By.className("edit-allergies"));
            wait15seconds.until(visibilityOfElementLocated(By.id("allergies")));
        }
    }

    public void addAllergy(Integer allergensIndex, Integer reactionsIndex, Integer severitiesIndex) {
        driver.findElements(By.cssSelector("#allergies ~ button")).get(1).click();
        wait15seconds.until(visibilityOfElementLocated(By.id("allergy")));
        driver.findElement(By.id("allergens")).findElement(By.xpath("//ul/li["+ allergensIndex + "]/input")).click();
        // TODO this doesn't seem to be selectiong a reaction?
        driver.findElement(By.id("reactions")).findElement(By.xpath("//ul/li["+ reactionsIndex + "]/input")).click();
        driver.findElement(By.id("severities")).findElement(By.xpath("//p["+ severitiesIndex + "]/input")).click();
        clickOn(By.id("addAllergyBtn"));
        wait15seconds.until(visibilityOfElementLocated(By.id("allergies")));
    }

    public void removeAllergy(Integer index) {
        driver.findElements(By.className("delete-action")).get(index - 1).click();
        driver.findElement(By.cssSelector("#allergyui-remove-allergy-dialog .confirm")).click();
        wait15seconds.until(visibilityOfElementLocated(By.id("allergies")));
    }

    public void returnFromAllergiesPage() {
        driver.findElements(By.cssSelector("#allergies ~ button")).get(0).click();
    }

    // assuming on the page with Allergies table
    public Integer countOfAllergies() {
        List<WebElement> allergies = driver.findElements(By.cssSelector("#allergies tbody tr"));
        return allergies == null ? 0 : allergies.size();
    }
}

