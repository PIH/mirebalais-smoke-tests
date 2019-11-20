package org.openmrs.module.mirebalais.smoke.helper;

public class SmokeTestProperties {

    public static final int IMPLICIT_WAIT_TIME = 20;

    private String webAppUrl = null;

    private String databaseUrl = null;

    private String databaseUsername = null;

    private String databasePassword = null;

    public String getWebAppUrl() {
        if (webAppUrl == null) {
            webAppUrl = envOrDefault("WEBAPP_URL", "http://localhost:8080/openmrs");
        }

        return webAppUrl;
    }

    public String getDatabaseUrl() {
        return envOrDefault("DATABASE_URL", "jdbc:mysql://localhost:3306/openmrs");

    }

    public String getDatabaseUsername() {
        if (databaseUsername == null) {
            databaseUsername = envOrDefault("DATABASE_USERNAME", "openmrs");
        }

        return databaseUsername;
    }

    public String getDatabasePassword() {
        if (databasePassword == null) {
            databasePassword = envOrDefault("DATABASE_PASSWORD", "openmrs");
        }

        return databasePassword;
    }

    public String getWebAppName() {
        return envOrDefault("WEBAPP_NAME", "openmrs");
    }

    public String getDatabaseDriverClass() {
        return "com.mysql.jdbc.Driver";
    }

    private String envOrDefault(String environmentVariable, String defaultValue) {
        String envVar = System.getenv(environmentVariable);
        return envVar != null ? envVar : defaultValue;
    }
}
