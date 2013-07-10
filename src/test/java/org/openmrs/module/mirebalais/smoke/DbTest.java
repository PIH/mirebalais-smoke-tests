package org.openmrs.module.mirebalais.smoke;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.apache.commons.io.IOUtils;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
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

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public abstract class DbTest extends BasicMirebalaisSmokeTest {

    private static SmokeTestProperties properties = new SmokeTestProperties();
    private static JdbcDatabaseTester tester;
    private IDataSet dataset;

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
            testPatient = new Patient("Y00001", "Crash Test Dummy", getNextAutoIncrementFor("person"), getPatientIdentifierId(),
                    getNextAutoIncrementFor("person_name"), getNextAutoIncrementFor("person_address"), getNextAutoIncrementFor("patient_identifier"));
            dataset = createDataset();

            DatabaseOperation.INSERT.execute(getConnection(), dataset);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("set up failed", e);
        }
    }

    @After
    public void tearDown() throws Exception {
        try {
            QueryDataSet createdData = new QueryDataSet(getConnection());
            createdData.addTable("obs", "select * from obs where encounter_id in (select encounter_id from encounter where patient_id=" + testPatient.getId() + ") and obs_group_id is not null");
            DatabaseOperation.DELETE.execute(getConnection(), createdData);

            createdData = new QueryDataSet(getConnection());
            createdData.addTable("name_phonetics", "select * from name_phonetics where person_name_id = " + testPatient.getPerson_name_id());
            createdData.addTable("patient_identifier", "select * from patient_identifier where patient_id="+ testPatient.getId());
            createdData.addTable("emr_paper_record_request", "select * from emr_paper_record_request where patient_id="+ testPatient.getId());
            createdData.addTable("visit", "select * from visit where patient_id=" + testPatient.getId());
            createdData.addTable("encounter", "select * from encounter where patient_id="+ testPatient.getId());
            createdData.addTable("obs", "select * from obs where encounter_id in (select encounter_id from encounter where patient_id="+ testPatient.getId() + ")");
            createdData.addTable("encounter_provider", "select * from encounter_provider where encounter_id in (select encounter_id from encounter where patient_id="+ testPatient.getId() + ")");

            DatabaseOperation.DELETE.execute(getConnection(), createdData);

            DatabaseOperation.DELETE.execute(getConnection(), dataset);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("tear down failed", e);
        }
    }

    private IDatabaseConnection getConnection() throws Exception {
        return tester.getConnection();
    }

    private IDataSet createDataset() throws IOException, DataSetException {
        Handlebars handlebars = new Handlebars();
        Template template = handlebars.compile("datasets/patients_dataset.xml");

        return new FlatXmlDataSetBuilder().build(new InputStreamReader(IOUtils.toInputStream(template.apply(testPatient))));
    }

    private Integer getPatientIdentifierId() throws Exception {
        ITable patientIdentifierType = getConnection().createQueryTable("identifier_source",
                "select * from patient_identifier_type where name = 'ZL EMR ID'");
        return (Integer) patientIdentifierType.getValue(0, "patient_identifier_type_id");
    }

    private BigInteger getNextAutoIncrementFor(String table_name) throws Exception {
        ITable autoIncrement = getConnection().createQueryTable(table_name,
                "select Auto_increment from information_schema.tables where table_schema = DATABASE() AND table_name = '" + table_name + "'");
        return (BigInteger) autoIncrement.getValue(0, "Auto_increment");
    }
}