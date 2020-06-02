package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.MirebalaisLoginPage;

public class PatientRegistrationMirebalaisFlowTest extends PatientRegistrationHaitiFlowTest {

    @Test
    @Override
    public void registerNewPatient() throws Exception {
        super.registerNewPatient();
    }

    @Test
    @Override
    public void editExistingPatient() throws Exception {
        super.editExistingPatient();
    }

    @Override
    protected LoginPage getLoginPage() {
        return new MirebalaisLoginPage(driver);
    }


    @Override
    protected Integer getPrintIdCardOption() { return 1; }

    @Override
    protected Integer getInsuranceName() {
        return 2;
    }

    @Override
    protected String getInsuranceNumberString() {
        return "076-098765";
    }

    @Override
    protected String getOtherInsuranceNameString() {
        return "non-coded insurance";
    }

}
