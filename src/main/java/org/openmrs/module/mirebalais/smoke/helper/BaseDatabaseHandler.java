package org.openmrs.module.mirebalais.smoke.helper;

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
        try {
            SmokeTestProperties properties = new SmokeTestProperties();

            Class.forName(properties.getDatabaseDriverClass());
            connection = new DatabaseConnection(getConnection(properties.getDatabaseUrl(), properties.getDatabaseUsername(),
                    properties.getDatabasePassword()));

            DatabaseConfig config = connection.getConfig();
            config.setProperty(PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
            config.setProperty(PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());

        }
        catch (Exception e) {
            e.printStackTrace();
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
