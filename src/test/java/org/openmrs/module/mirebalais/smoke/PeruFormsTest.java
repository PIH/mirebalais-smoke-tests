package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PeruLoginPage;

public class PeruFormsTest extends SimpleFormTest {

    @Test
    public void testForms() throws Exception {
        testSectionedFormLoads(PeruLoginPage.MAIN_LOCATION, "Historia Clínica (paciente nuevo)");
    }

    @Override
    protected LoginPage getLoginPage() {
        return new PeruLoginPage(driver);
    }
}
