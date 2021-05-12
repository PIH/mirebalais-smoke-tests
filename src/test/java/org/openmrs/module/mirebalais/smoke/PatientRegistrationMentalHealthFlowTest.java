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

    @Override
    protected String getPlaceOfBirthString() {
        return "Cange";
    }

    @Override
    protected Integer getReligion() {
        return 2;
    }

    @Override
    protected Integer getMaritalStatus() {
        return 2;
    }

    @Override
    protected LoginPage getLoginPage() {
        return new MentalHealthLoginPage(driver);
    }

    @Override
    protected String getOccupation() {
        return "Chauffeur";
    }

    @Override
    protected Boolean automaticallyEnterIdentifier() { return null; }


}
