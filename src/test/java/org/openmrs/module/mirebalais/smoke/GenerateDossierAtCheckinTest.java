package org.openmrs.module.mirebalais.smoke;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.apache.commons.io.IOUtils;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.flows.CheckInPatientFlow;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.ArchivesRoomApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;

import java.io.InputStreamReader;

import static junit.framework.Assert.assertTrue;

public class GenerateDossierAtCheckinTest extends DbTest {

    @Test
    public void shouldCreateDossierLocallyAtCheckinWhenDossierIsMissing() throws Exception {
        new LoginPage(driver).logIn("admin", "Admin123", 10);

        AppDashboard dashboard = new AppDashboard(driver);
        dashboard.openCheckinApp();

        PatientDashboard patientDashboard = new PatientDashboard(driver);

        CheckInPatientFlow checkInPatientFlow = new CheckInPatientFlow(driver);
        checkInPatientFlow.checkInAndCreateLocalDossierFor("TESTIDTEST");
        checkInPatientFlow.confirmPatient("TESTIDTEST");

        String dossieNumber = patientDashboard.getDossieNumber();
        assertTrue(dossieNumber.matches("A\\d{6}"));

        checkInPatientFlow.checkIn();

        dashboard.openArchivesRoomApp();
        ArchivesRoomApp archives = new ArchivesRoomApp(driver);
        archives.goToPullTab();
        assertTrue(archives.isPatientInList(dossieNumber, "pull_requests_table"));
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        ITable patient_identifier_type = getConnection().createQueryTable("identifier_source", "select * from patient_identifier_type where name = 'ZL EMR ID'");
        Integer patient_identifier_type_id = (Integer) patient_identifier_type.getValue(0, "patient_identifier_type_id");

        Patient patient = new Patient(patient_identifier_type_id.toString(), "", 9999999);
        Handlebars handlebars = new Handlebars();
        Template template = handlebars.compile("datasets/patients_dataset.xml");

        return new FlatXmlDataSetBuilder().build(new InputStreamReader(IOUtils.toInputStream(template.apply(patient))));
    }
}
