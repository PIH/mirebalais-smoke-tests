package org.openmrs.module.mirebalais.smoke.helper;

import static java.sql.DriverManager.getConnection;
import static org.dbunit.database.DatabaseConfig.PROPERTY_DATATYPE_FACTORY;
import static org.dbunit.database.DatabaseConfig.PROPERTY_METADATA_HANDLER;
import static org.dbunit.operation.DatabaseOperation.DELETE;
import static org.dbunit.operation.DatabaseOperation.INSERT;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

public class PatientDatabaseHandler {
	
	private static List<Map<String, String>> patientTablesToDelete = new LinkedList<Map<String, String>>();
	
	private static Map<Patient, IDataSet> datasets = new HashMap<Patient, IDataSet>();
	
	private static DatabaseConnection connection;
	static {
		try {
			SmokeTestProperties properties = new SmokeTestProperties();
			
			Class.forName(properties.getDatabaseDriverClass());
			connection = new DatabaseConnection(getConnection(properties.getDatabaseUrl(), properties.getDatabaseUsername(),
			    properties.getDatabasePassword()));
			
			DatabaseConfig config = connection.getConfig();
			config.setProperty(PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
			config.setProperty(PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
			
			initializePatientTablesToDelete();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addTestPatientForDelete(BigInteger patientId) throws IOException, DataSetException, SQLException {
		Patient patient = new Patient("123", null, patientId, null, -1, getTableId("person_name", patientId), getTableId(
		    "person_address", patientId), new BigInteger("-1"));
		
		datasets.put(patient, createDataset(patient));
	}
	
	public static Patient insertNewTestPatient() throws Exception {
        try {
            Patient patient = new Patient(getNextValidPatientIdentifier(), "Crash Test Dummy",
                    getNextAutoIncrementFor("person"), UUID.randomUUID().toString(), getPatientIdentifierId(),
                    getNextAutoIncrementFor("person_name"), getNextAutoIncrementFor("person_address"),
                    getNextAutoIncrementFor("patient_identifier"));

            lockPatientIdentifier(patient.getIdentifier());

            IDataSet dataset = createDataset(patient);
            datasets.put(patient, dataset);

            INSERT.execute(connection, dataset);
            return patient;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("unable to create patient in database", e);
        }
    }
	
	public static void deleteAllTestPatients() throws Exception {
		for (Patient patient : datasets.keySet()) {
			deleteTestPatient(patient);
		}
	}
	
	private static BigInteger getTableId(String table, BigInteger patientId) throws DataSetException, SQLException {
		ITable queryTable = connection.createQueryTable(table, "select * from " + table + " where person_id = " + patientId);
		return queryTable.getRowCount() > 0 ? new BigInteger(queryTable.getValue(0, table + "_id").toString())
		        : new BigInteger("-1");
	}
	
	private static void deleteTestPatient(Patient patient) throws Exception {
		for (Map<String, String> tables : patientTablesToDelete) {
			QueryDataSet createdData = new QueryDataSet(connection);
			for (String table : tables.keySet()) {
				createdData.addTable(table, String.format(tables.get(table), patient.getId()));
			}
			DELETE.execute(connection, createdData);
		}
		
		DELETE.execute(connection, datasets.get(patient));
		
		unlockPatientIdentifier(patient.getIdentifier());
	}
	
	private static void initializePatientTablesToDelete() {
		Map<String, String> firstToDelete = new LinkedHashMap<String, String>();
		firstToDelete
		        .put(
		            "obs",
		            "select * from obs where encounter_id in (select encounter_id from encounter where patient_id = %d) and obs_group_id is not null");
		patientTablesToDelete.add(firstToDelete);
		
		Map<String, String> secondToDelete = new LinkedHashMap<String, String>();
		secondToDelete
		        .put("name_phonetics",
		            "select * from name_phonetics where person_name_id in (select person_name_id from person_name where person_id = %d)");
		secondToDelete.put("person_attribute", "select * from person_attribute where person_id = %d");
		secondToDelete.put("patient_identifier", "select * from patient_identifier where patient_id = %d");
		secondToDelete.put("emr_paper_record_request", "select * from emr_paper_record_request where patient_id = %d");
		secondToDelete.put("visit", "select * from visit where patient_id = %d");
		secondToDelete.put("encounter", "select * from encounter where patient_id = %d");
		secondToDelete.put("orders", "select * from orders where patient_id = %d");
		secondToDelete.put("test_order",
		    "select * from test_order where order_id in (select order_id from orders where patient_id = %d)");
		secondToDelete.put("emr_radiology_order",
		    "select * from emr_radiology_order where order_id in (select order_id from orders where patient_id = %d)");
		secondToDelete.put("obs",
		    "select * from obs where encounter_id in (select encounter_id from encounter where patient_id = %d)");
		secondToDelete
		        .put("encounter_provider",
		            "select * from encounter_provider where encounter_id in (select encounter_id from encounter where patient_id = %d)");
		patientTablesToDelete.add(secondToDelete);
	}
	
	private static IDataSet createDataset(Patient patient) throws IOException, DataSetException {
		Handlebars handlebars = new Handlebars();
		Template template = handlebars.compile("datasets/patients_dataset.xml");
		
		return new FlatXmlDataSetBuilder().build(new InputStreamReader(IOUtils.toInputStream(template.apply(patient))));
	}
	
	private static String getNextValidPatientIdentifier() throws Exception {
		ITable patientIdentifier = connection.createQueryTable("idgen_pooled_identifier",
		    "select * from idgen_pooled_identifier where date_used is null limit 1");
		
		return (String) patientIdentifier.getValue(0, "identifier");
	}
	
	private static void lockPatientIdentifier(String identifier) throws Exception {
		setDateUsedOfPatientIdentifierTo(identifier, "sysdate()");
	}
	
	private static void unlockPatientIdentifier(String identifier) throws Exception {
		setDateUsedOfPatientIdentifierTo(identifier, "null");
	}
	
	private static Integer getPatientIdentifierId() throws Exception {
		ITable patientIdentifierType = connection.createQueryTable("identifier_source",
		    "select * from patient_identifier_type where name = 'ZL EMR ID'");
		return (Integer) patientIdentifierType.getValue(0, "patient_identifier_type_id");
	}
	
	private static BigInteger getNextAutoIncrementFor(String table_name) throws Exception {
		ITable autoIncrement = connection.createQueryTable(table_name,
		    "select Auto_increment from information_schema.tables where table_schema = DATABASE() AND table_name = '"
		            + table_name + "'");
		return (BigInteger) autoIncrement.getValue(0, "Auto_increment");
	}
	
	private static void setDateUsedOfPatientIdentifierTo(String identifier, String date) throws Exception {
		connection
		        .getConnection()
		        .createStatement()
		        .executeUpdate(
		            "update idgen_pooled_identifier set date_used = " + date + " where identifier = '" + identifier + "'");
	}
}
