package org.openmrs.module.mirebalais.smoke;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.apache.commons.io.IOUtils;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.SmokeTestProperties;

import java.io.InputStreamReader;

public abstract class DbTest extends BasicMirebalaisSmokeTest {

    private static SmokeTestProperties properties = new SmokeTestProperties();
    private static JdbcDatabaseTester tester;

    @BeforeClass
    public static void setDatabaseConnection() throws ClassNotFoundException {
        tester = new JdbcDatabaseTester(properties.getDatabaseDriverClass(), properties.getDatabaseUrl(),
                properties.getDatabaseUsername(), properties.getDatabasePassword());
    }

    @AfterClass
    public static void closeDatabaseConnection() throws Exception {
        tester.closeConnection(tester.getConnection());
    }

    @Before
    public void setUp() throws Exception {
        try {
            DatabaseOperation.REFRESH.execute(getConnection(), getDataSet());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("set up failed", e);
        }
    }

    @After
    public void tearDown() throws Exception {
        try {
            QueryDataSet createdData = new QueryDataSet(getConnection());
            createdData.addTable("obs", "select * from obs where encounter_id in (select encounter_id from encounter where patient_id=9999999) and obs_group_id is not null");
            DatabaseOperation.DELETE.execute(getConnection(), createdData);

            createdData = new QueryDataSet(getConnection());
            createdData.addTable("patient_identifier", "select * from patient_identifier where patient_id=9999999");
            createdData.addTable("emr_paper_record_request", "select * from emr_paper_record_request where patient_id=9999999");
            createdData.addTable("visit", "select * from visit where patient_id=9999999");
            createdData.addTable("encounter", "select * from encounter where patient_id=9999999");
            createdData.addTable("obs", "select * from obs where encounter_id in (select encounter_id from encounter where patient_id=9999999)");
            createdData.addTable("encounter_provider", "select * from encounter_provider where encounter_id in (select encounter_id from encounter where patient_id=9999999)");

            DatabaseOperation.DELETE.execute(getConnection(), createdData);

            DatabaseOperation.DELETE.execute(getConnection(), getDataSet());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("tear down failed", e);
        }
    }

    private IDatabaseConnection getConnection() throws Exception {
        return tester.getConnection();
    }

    private IDataSet getDataSet() throws Exception {
        ITable patient_identifier_type = getConnection().createQueryTable("identifier_source", "select * from patient_identifier_type where name = 'ZL EMR ID'");
        Integer patient_identifier_type_id = (Integer) patient_identifier_type.getValue(0, "patient_identifier_type_id");

        testPatient = new Patient("TESTIDTEST", "Crash Test Dummy", 9999999, patient_identifier_type_id);
        Handlebars handlebars = new Handlebars();
        Template template = handlebars.compile("datasets/patients_dataset.xml");

        return new FlatXmlDataSetBuilder().build(new InputStreamReader(IOUtils.toInputStream(template.apply(testPatient))));
    }

}