package org.openmrs.module.mirebalais.smoke;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.EmergencyCheckin;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openqa.selenium.By;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class EmergencyCheckinTest extends BasicMirebalaisSmokeTest {

    private EmergencyCheckin emergencyCheckinPO;

    @Override
    protected void specificSetUp() {
        emergencyCheckinPO = new EmergencyCheckin(driver, wait);
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

    @Test
    public void checkInByName() {
        String lastName = "Emergency";
        String firstName = "ByName";
        emergencyCheckinPO.registerCreateByName(lastName, firstName);

        PatientDashboard patientDashboard = new PatientDashboard(driver, wait);
        assertThat(patientDashboard.getIdentifier(), is(notNullValue()));
        assertThat(patientDashboard.getName(), Matchers.stringContainsInOrder(Arrays.asList(firstName, lastName)));
    }

}
