package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.EmergencyCheckin;
import org.openqa.selenium.By;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class EmergencyCheckinTest extends BasicMirebalaisSmokeTest {

    private EmergencyCheckin emergencyCheckinPO;

    @Override
    protected void specificSetUp() {
        emergencyCheckinPO = new EmergencyCheckin(driver);
        emergencyCheckinPO.initialize();
    }

    @Test
    public void checkinJohnDoe() {
        emergencyCheckinPO.checkinMaleUnindentifiedPatient();
        String patientName = driver.findElement(By.xpath("//*[@id='overviewTable']/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText();
        String patientGender = driver.findElement(By.xpath("//*[@id='overviewTable']/tbody/tr/td[1]/table/tbody/tr[4]/td[1]")).getText();
        String zlEmrId = driver.findElement(By.xpath("//*[@id='overviewTable']/tbody/tr/td[2]/table/tbody/tr[2]/td[1]")).getText();

        assertThat(patientName, is("UNKNOWN UNKNOWN"));
        assertThat(patientGender, is("M"));
        assertThat(zlEmrId, notNullValue());
    }
}
