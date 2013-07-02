package org.openmrs.module.mirebalais.smoke.helper;

import java.io.IOException;
import java.util.Properties;

public class SmokeTestProperties {

    private Properties properties;

    public SmokeTestProperties() {
        properties = new Properties();
        load();
    }

    public String getWebAppUrl() {
        return properties.getProperty("webapp.url", "http://bamboo.pih-emr.org/openmrs");
    }

    public String getDatabaseUrl() {
        return properties.getProperty("database.url", "jdbc:mysql://bamboo.pih-emr.org:3306/openmrs");
    }

    public String getDatabaseUsername() {
        return properties.getProperty("database.username", "");
    }

    public String getDatabasePassword() {
        return properties.getProperty("database.password", "");
    }

    public String getDatabaseDriverClass() {
        return "com.mysql.jdbc.Driver";
    }

    private Properties load() {
        try {
            properties.load(getClass().getResourceAsStream("/org/openmrs/module/mirebalais/smoke/smoketest.properties"));
        }
        catch (IOException e) {
            throw new RuntimeException("smoketest.properties not found. Error: ", e);
        }
        return properties;
    }

}
