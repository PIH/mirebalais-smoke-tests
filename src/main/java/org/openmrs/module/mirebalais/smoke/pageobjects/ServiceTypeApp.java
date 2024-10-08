package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceTypeApp extends AbstractPageObject {
    private static final By NEW_SERVICE_TYPE = By.cssSelector("button.appointment-type-label");
    private static final By SAVE_BUTTON = By.id("save-button");
    private static final By SERVICE_TYPE_TABLE_INFO = By.cssSelector("#appointmentTypesTable_info");

    public ServiceTypeApp(WebDriver driver) {
        super(driver);
    }

    public void openNewServiceType(){
       clickOn(NEW_SERVICE_TYPE);
    }

    public void createServiceType(String name, String duration, String description){
        setTextToField("name-field", name);
        setTextToField("duration-field", duration);
        setTextToField(By.name("description"), description);
        clickOn(SAVE_BUTTON);
    }

    public int getTotalAmountOfServiceTypes() {
        String tableInfo;

        try {
            tableInfo = driver.findElement(SERVICE_TYPE_TABLE_INFO).getText();
        }
        catch (NoSuchElementException e) {
            // if no table, then there are no service types defined
            return 0;
        }

        Pattern pattern = Pattern.compile("(\\d+)(?!.*\\d)");
        Matcher matcher = pattern.matcher(tableInfo);
        matcher.find();
        return Integer.parseInt(matcher.group());
    }
}
