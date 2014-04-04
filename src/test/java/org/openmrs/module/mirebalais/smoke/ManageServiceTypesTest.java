package org.openmrs.module.mirebalais.smoke;

import org.junit.After;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.helper.AppointmentTypeDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.helper.NameGenerator;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.ServiceTypeApp;

import static junit.framework.Assert.assertEquals;

public class ManageServiceTypesTest extends DbTest {

    @Test
    public void systemAdminCanAddServiceType() throws Exception {
        AppDashboard appDashboard = new AppDashboard(driver);
        ServiceTypeApp serviceTypeApp = new ServiceTypeApp(driver);

        logInAsAdmin();

        appDashboard.openManageAppointmentTypesApp();
        int expectedAmountOfServiceTypes = serviceTypeApp.getTotalAmountOfServiceTypes() + 1;

        serviceTypeApp.openNewServiceType();
        serviceTypeApp.createServiceType(NameGenerator.getServiceTypeName(), "20", "Description");

        assertEquals(expectedAmountOfServiceTypes, serviceTypeApp.getTotalAmountOfServiceTypes());
    }

    @After
    public void deleteAppointmentTypeTestData() throws Exception {
        try {
            AppointmentTypeDatabaseHandler.deleteAppointmentTypes();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("tear down failed", e);
        }
    }
}
