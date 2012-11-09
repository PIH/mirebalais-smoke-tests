package org.openmrs.module.mirebalais.smoke.pageobjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CleanUpTests {

    private Connection connection;

    private SmokeTestProperties properties = new SmokeTestProperties();

    public CleanUpTests() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = properties.getDatabaseUrl();
            connection = DriverManager.getConnection(url, "openmrs_editor", "dr0pper");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePatientsWithNameAs(String name, String lastName) throws SQLException {
        String query = "select person_id,person_name_id from openmrs.person_name where given_name = '"+ name +"' and family_name = '"+ lastName +"'";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (!resultSet.isClosed() && resultSet.next()){
            String personId = resultSet.getString("person_id");
            String personNameId = resultSet.getString("person_name_id");

            Statement deleteStatement = connection.createStatement();

            String delete = "delete from openmrs.name_phonetics where person_name_id = " + personNameId;
            deleteStatement.executeUpdate(delete);

            delete = "delete from openmrs.person_name where person_id = " + personId;
            deleteStatement.executeUpdate(delete);

            delete = "delete from openmrs.person_address where person_id = "  + personId;
            deleteStatement.executeUpdate(delete);

            delete = "delete from openmrs.person_attribute where person_id = "  + personId;
            deleteStatement.executeUpdate(delete);

            delete = "delete from openmrs.patient_identifier where patient_id = "  + personId;
            deleteStatement.executeUpdate(delete);

            deleteEncounter(personId,connection);

            delete = "delete from openmrs.patient where patient_id= "  + personId;
            deleteStatement.executeUpdate(delete);

            delete = "delete from openmrs.person where person_id = "  + personId;
            deleteStatement.executeUpdate(delete);
        }
        resultSet.close();
        statement.close();
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    private void deleteEncounter(String patientId, Connection connection) throws SQLException {
        String query = "select encounter_id from openmrs.encounter where patient_id = " + patientId;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (!resultSet.isClosed() && resultSet.next()){
            String encounterId = resultSet.getString("encounter_id");

            String delete = "delete from openmrs.encounter_provider where encounter_id = " + encounterId;
            statement.executeUpdate(delete);

            delete = "delete from openmrs.encounter where encounter_id = " + encounterId;
            statement.executeUpdate(delete);
        }
    }

    public static void main(String[] args) throws SQLException {
        CleanUpTests cleanUpTests = new CleanUpTests();
        cleanUpTests.deletePatientsWithNameAs("Jayne", "Marconi");
    }

}
