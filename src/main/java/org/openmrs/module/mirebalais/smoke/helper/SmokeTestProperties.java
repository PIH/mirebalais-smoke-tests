package org.openmrs.module.mirebalais.smoke.helper;

public class SmokeTestProperties {

    public static final int IMPLICIT_WAIT_TIME = 5;

    private String webAppUrl = null;

    private String databaseUsername = null;

    private String databasePassword = null;

    public String getWebAppUrl() {
        if (webAppUrl == null) {
            webAppUrl = envOrDefault("WEBAPP_URL", "http://localhost:6040/openmrs");
        }

        return webAppUrl;
    }

    public String getDatabaseUrl() {
        return envOrDefault("DATABASE_URL", "jdbc:mysql://localhost:3308/peru");

    }

    public String getDatabaseUsername() {
        if (databaseUsername == null) {
            databaseUsername = envOrDefault("DATABASE_USERNAME", "root");
        }

        return databaseUsername;
    }

    public String getDatabasePassword() {
        if (databasePassword == null) {
            databasePassword = envOrDefault("DATABASE_PASSWORD", "Admin123");
        }

        return databasePassword;
    }

    public String getWebAppName() {
        return envOrDefault("WEBAPP_NAME", "openmrs");
    }

    public String getChromeDriverExecutable() {
        // if none specified, defaults to logic in SmokeTestDriver, see SmokeTestDriver:37
        return envOrDefault("CHROME_DRIVER_EXECUTABLE", "");
    }

    public String getAdminUserPassword() {
        return envOrDefault("ADMIN_USER_PASSWORD", "Admin123");
    }

    public String getDatabaseDriverClass() {
        return "com.mysql.jdbc.Driver";
    }

    private String envOrDefault(String environmentVariable, String defaultValue) {
        String envVar = System.getenv(environmentVariable);
        return envVar != null ? envVar : defaultValue;
    }
}
