package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;

public class AdminPage extends AbstractPageObject {

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public void updateLuceneIndex() throws Exception {
        driver.get(properties.getWebAppUrl() + "/admin/maintenance/searchIndex.htm");
        driver.findElement(By.id("rebuildButton")).click();
        Thread.sleep(2000);
        wait5minutes.until(invisibilityOfElementLocated(By.id("indexing_progress_img")));

    }
}
