package org.openmrs.module.mirebalais.smoke.helper;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.openmrs.module.mirebalais.smoke.helper.SmokeTestProperties;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

public class Exporter {

    public static void main(String[] args) throws Exception {

        SmokeTestProperties properties = new SmokeTestProperties();

        // database connection
        Class driverClass = Class.forName(properties.getDatabaseDriverClass());

        Connection jdbcConnection = DriverManager.getConnection(properties.getDatabaseUrl(), properties.getDatabaseUsername(), properties.getDatabasePassword());
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // partial database export
        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("patient_identifier");
        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("partial.xml"));
    }
}