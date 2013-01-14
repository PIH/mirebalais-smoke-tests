package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.EmergencyCheckin;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientRegistrationDashboard;

public class EmergencyCheckinTest extends BasicMirebalaisSmokeTest {

    private EmergencyCheckin emergencyCheckinPO;
    private static LoginPage loginPage;
    private PatientRegistrationDashboard patientDashboard;
    
    @Before
    public void setUp() {
        emergencyCheckinPO = new EmergencyCheckin(driver);
        patientDashboard = new PatientRegistrationDashboard(driver);
    }

    @BeforeClass
    public static void setUpEnvironment() {
    	loginPage = new LoginPage(driver);
    	loginPage.logInAsAdmin();
    }
    
    @Test
    public void checkinJohnDoe() {
        emergencyCheckinPO.checkinMaleUnindentifiedPatient();
        
        assertThat(patientDashboard.getIdentifier(), is(notNullValue()));
        assertThat(patientDashboard.getGender(), is("M"));
        assertThat(patientDashboard.getName(), Matchers.stringContainsInOrder(Arrays.asList("UNKNOWN", "UNKNOWN")));
    }

}
