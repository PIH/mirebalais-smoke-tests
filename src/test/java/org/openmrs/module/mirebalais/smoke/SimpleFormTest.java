package org.openmrs.module.mirebalais.smoke;

import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public abstract class SimpleFormTest extends DbTest {

    public abstract void testForms() throws Exception;

    /**
     * Checks that every section of a multi-section form loads without an error. Does not input data or submit.
     *
     * Precondition: must have the PIH Visit page open with no encounters
     *
     * @param formLabel The text of the action button that opens the form
     * @throws Exception
     */
    public void testSectionedFormLoads(String formLabel) throws Exception {
        visitNote.openForm(By.linkText(formLabel));
        int numSections = driver.findElements(By.className("visit-element")).size();
        for (int i = 0; i < numSections; i++) {
            WebElement sectionHeader = driver.findElements(By.className("visit-element")).get(i);
            WebElement editButton = getVisible(sectionHeader.findElements(By.className("edit-encounter")));
            if (editButton != null) {
                editButton.click();
                assertThat(driver.findElement(By.id("htmlform")), Matchers.notNullValue());
                driver.navigate().back();
            } else {
                fail("No edit button was visible for form section " + sectionHeader.getText());
            }
        }
        deleteTheEncounter();
    }

    public void testSimpleFormLoads(String formLabel) throws Exception {
        visitNote.openForm(By.linkText(formLabel));
        assertThat(driver.findElement(By.id("htmlform")), Matchers.notNullValue());
        driver.navigate().back();
    }

    /**
     * Deletes the visible encounter. There should only be one.
     */
    private void deleteTheEncounter() throws Exception {
        // We get the encounter header by looking for an element which has *only* class `visit-element`.
        // Section headers have class `visit-element indent`, so they are not matched using this xpath.
        WebElement encounterHeader = driver.findElement(By.xpath("//*[@class='visit-element']"));
        WebElement deleteButton = getVisible(encounterHeader.findElements(By.className("delete-encounter")));
        deleteButton.click();
        WebElement confirmButton = driver.findElement(By.className("delete-encounter-button"));
        confirmButton.click();
        (new WebDriverWait(driver, 5)).until(ExpectedConditions.invisibilityOfElementLocated(By.className("visit-element")));
    }

    /**
     * Returns a visible/interactable element from a list of candidates. If there are
     * no visible elements, returns null.
     *
     * @param candidates elements, some of which may not be visible/interactable
     */
    private WebElement getVisible(List<WebElement> candidates) throws Exception {
        for (WebElement candidate : candidates) {
            if (candidate.isDisplayed()) {
                return candidate;
            }
        }
        throw new Exception("None of the available elements are visible");
    }

}
