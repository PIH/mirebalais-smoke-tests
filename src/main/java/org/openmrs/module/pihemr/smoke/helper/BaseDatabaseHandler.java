package org.openmrs.module.pihemr.smoke.helper;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.ITable;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;

import java.math.BigInteger;

import static java.sql.DriverManager.getConnection;
import static org.dbunit.database.DatabaseConfig.PROPERTY_DATATYPE_FACTORY;
import static org.dbunit.database.DatabaseConfig.PROPERTY_METADATA_HANDLER;

public class BaseDatabaseHandler {

    protected static DatabaseConnection connection;

    static {
        SmokeTestProperties properties = new SmokeTestProperties();
        Exception lastException = null;

        // retry a few times: a transient connection blip here permanently breaks every DB-dependent
        // test for the rest of the run, since this only runs once per JVM
        int maxAttempts = 8;
        for (int attempt = 1; attempt <= maxAttempts && connection == null; attempt++) {
            try {
                Class.forName(properties.getDatabaseDriverClass());
                connection = new DatabaseConnection(getConnection(properties.getDatabaseUrl(), properties.getDatabaseUsername(),
                        properties.getDatabasePassword()));

                DatabaseConfig config = connection.getConfig();
                config.setProperty(PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
                config.setProperty(PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
            }
            catch (Exception e) {
                connection = null;
                lastException = e;
                if (attempt < maxAttempts) {
                    try {
                        Thread.sleep(3000);
                    }
                    catch (InterruptedException ignored) {}
                }
            }
        }

        if (connection == null && lastException != null) {
            lastException.printStackTrace();
        }
    }

    protected static BigInteger getNextAutoIncrementFor(String table_name) throws Exception {
        ITable autoIncrement = connection.createQueryTable(table_name,
                "select Auto_increment from information_schema.tables where table_schema = DATABASE() AND table_name = '"
                        + table_name + "'");
        return (BigInteger) autoIncrement.getValue(0, "Auto_increment");
    }

    protected static boolean hasTable(String tableName) throws Exception {
        String q = "select count(*) as num from information_schema.tables where table_schema = DATABASE() AND table_name = '" + tableName + "'";
        ITable queryTable = connection.createQueryTable(tableName, q);
        BigInteger count = (BigInteger) queryTable.getValue(0, "num");
        return count != null && count.intValue() > 0;
    }

}
