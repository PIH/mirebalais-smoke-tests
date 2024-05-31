package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.HaitiMultiLocationLoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.LoginPage;
import org.openqa.selenium.By;

public class PatientRegistrationHaitiFlowTest extends PatientRegistrationFlowTest {

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
	protected Boolean placeOfBirthAndContactAddressUseHierarchy() { return true; }

	@Override
    protected String getPersonAddressString() {
        return "Cange";
    }

    @Override
    protected Boolean automaticallyEnterIdentifier() { return true; }

    @Override
    protected Boolean getRelationshipsEnabled() {
        return false;
    }

    @Override
    protected Integer getAdditionalIdentifiersCount() { return 4; }

	@Override
	protected String getMothersFirstName() {
		return "Jane";
	}

	@Override
	protected String getContact() {
		return "Dan";
	}

	@Override
	protected String getRelationship() {
		return "cousin";
	}

	@Override
	protected String getContactAddressString() {
		return "Cange";
	}

	protected String getContactPhoneNumber() {
		return "2702 1151";
	}

	@Override
	protected String getPlaceOfBirthString() {
		return "Cange";
	}

	@Override
	protected Integer getMaritalStatus() {
		return 2;
	}

	@Override
	protected Integer getReligion() { return 2; }

	@Override
	protected String getOccupation() {
		return "Mécanicien";
	}

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
    protected By getSuccessElement() {
        return By.id("register-patient-button");    // Haiti redirects to Register Patient search page
    }

    @Override
    protected LoginPage getLoginPage() {
        return new GeneralLoginPage(driver);
    }
}
