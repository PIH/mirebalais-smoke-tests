package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.PeruLoginPage;

public class PeruFormsTest extends SimpleFormTest {

    @Test
    public void testForms() throws Exception {
        logInAsPhysicianUser(PeruLoginPage.MAIN_LOCATION);
        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());
        clinicianDashboard.startVisit();
        testSectionedFormLoads("Historia Clínica (paciente nuevo)");
        testSimpleFormLoads("Triaje");
        testSimpleFormLoads("Consulta Ambulatoria");
        testSimpleFormLoads("Nota de Enfermeria");
        testSimpleFormLoads("Ficha de VIH");
        testSimpleFormLoads("Ficha de tuberculosis");
    }

    @Override
    protected LoginPage getLoginPage() {
        return new PeruLoginPage(driver);
    }
}
