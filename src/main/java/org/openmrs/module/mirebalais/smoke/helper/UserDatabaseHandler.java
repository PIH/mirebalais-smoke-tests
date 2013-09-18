package org.openmrs.module.mirebalais.smoke.helper;

import static org.dbunit.operation.DatabaseOperation.DELETE;
import static org.dbunit.operation.DatabaseOperation.INSERT;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.openmrs.module.mirebalais.smoke.dataModel.User;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

public class UserDatabaseHandler extends BaseDatabaseHandler {
	
	protected static Map<User, IDataSet> datasets = new HashMap<User, IDataSet>();
	
	private static QueryDataSet userDataToDelete;
	
	public static User insertNewClinicalUser() throws Exception {
		return createUserWithApplicationRole("clinical");
	}
	
	public static User insertNewPharmacistUser() throws Exception {
		return createUserWithApplicationRole("pharmacist");
	}
	
	private static User createUserWithApplicationRole(String role) throws Exception {
		User user;
		
		try {
			
			BigInteger userId = getNextAutoIncrementFor("users");
			String username = "smoke-test-" + role + "-" + userId;
			
			user = new User(getNextAutoIncrementFor("person"), UUID.randomUUID().toString(),
			        getNextAutoIncrementFor("person_name"), userId, username, role, getNextAutoIncrementFor("provider"),
			        UUID.randomUUID().toString());
			
			IDataSet dataset = createDataset(user);
			datasets.put(user, dataset);
			
			INSERT.execute(connection, dataset);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("unable to create patient in database", e);
		}
		
		addUserForDelete(user.getUsername());
		return user;
	}
	
	public static void deleteAllTestUsers() throws DatabaseUnitException, SQLException {
		if (userDataToDelete != null) {
			DELETE.execute(connection, userDataToDelete);
		}
	}
	
	public static void addUserForDelete(String username) throws SQLException, DataSetException {
		ITable userQuery = connection.createQueryTable("users", "select * from users where username = '" + username + "'");
		
		Integer userId = (Integer) userQuery.getValue(0, "user_id");
		Integer personId = (Integer) userQuery.getValue(0, "person_id");
		
		userDataToDelete = new QueryDataSet(connection);
		userDataToDelete.addTable("person", "select * from person where person_id = " + personId);
		userDataToDelete.addTable("provider", "select * from provider where person_id = " + personId);
		userDataToDelete.addTable("person_name", "select * from person_name where person_id = " + personId);
		userDataToDelete.addTable("name_phonetics",
		    "select * from name_phonetics where person_name_id in (select person_name_id from person_name where person_id = "
		            + personId + ")");
		userDataToDelete.addTable("users", "select * from users where user_id = " + userId);
		userDataToDelete.addTable("user_role", "select * from user_role where user_id = " + userId);
		userDataToDelete.addTable("user_property", "select * from user_property where user_id = " + userId);
	}
	
	private static IDataSet createDataset(User user) throws IOException, DataSetException {
		Handlebars handlebars = new Handlebars();
		Template template = handlebars.compile("datasets/users_dataset.xml");
		
		return new FlatXmlDataSetBuilder().build(new InputStreamReader(IOUtils.toInputStream(template.apply(user))));
	}
}
