package org.openmrs.module.mirebalais.smoke;

import org.dbunit.DBTestCase;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.openmrs.module.mirebalais.smoke.helper.SmokeTestProperties;

import static org.dbunit.PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL;
import static org.dbunit.PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS;
import static org.dbunit.PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD;
import static org.dbunit.PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME;
import static org.dbunit.operation.DatabaseOperation.DELETE;
import static org.dbunit.operation.DatabaseOperation.REFRESH;

public abstract class DbTest extends DBTestCase {

    private SmokeTestProperties properties = new SmokeTestProperties();

    public DbTest() {
        super();
        System.setProperty(DBUNIT_DRIVER_CLASS, properties.getDatabaseDriverClass());
        System.setProperty(DBUNIT_CONNECTION_URL, properties.getDatabaseUrl());
        System.setProperty(DBUNIT_USERNAME, properties.getDatabaseUsername());
        System.setProperty(DBUNIT_PASSWORD, properties.getDatabasePassword());
    }

    protected abstract IDataSet getDataSet() throws Exception;

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DELETE;
    }

    @Override
    protected void tearDown() throws Exception {
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

            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}