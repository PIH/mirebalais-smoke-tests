package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.MentalHealthLoginPage;

public class PatientRegistrationMentalHealthFlowTest extends PatientRegistrationHaitiFlowTest {

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
	protected Integer getInsuranceName() {
		return null;
	}

	@Override
	protected String getInsuranceNumberString() {
		return null;
	}

	@Override
	protected String getOtherInsuranceNameString() {
		return null;
	}


	@Override
    protected LoginPage getLoginPage() {
        return new MentalHealthLoginPage(driver);
    }

    @Override
    protected Boolean automaticallyEnterIdentifier() { return null; }


}
