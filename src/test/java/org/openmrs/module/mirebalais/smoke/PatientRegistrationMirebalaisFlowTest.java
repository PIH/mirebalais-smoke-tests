package org.openmrs.module.mirebalais.smoke;

import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.MirebalaisLoginPage;

public class PatientRegistrationMirebalaisFlowTest extends PatientRegistrationHaitiFlowTest {

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

    @Override
    protected Boolean getRelationshipsEnabled() {
        return false;
    }

}
