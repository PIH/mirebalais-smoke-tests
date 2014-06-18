package org.openmrs.module.mirebalais.smoke.helper;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.apache.commons.io.IOUtils;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.openmrs.module.mirebalais.smoke.dataModel.User;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.dbunit.operation.DatabaseOperation.DELETE;
import static org.dbunit.operation.DatabaseOperation.INSERT;

public class UserDatabaseHandler extends BaseDatabaseHandler {
	
	protected static Map<User, IDataSet> datasets = new HashMap<User, IDataSet>();

    protected static List<String> usernamesToDelete = new ArrayList<String>();
	
	public static User insertNewClinicalUser() throws Exception {

		return createUserWithApplicationAndProviderRole("clinical", "Clinical Doctor");
	}
	
	public static User insertNewPharmacistUser() throws Exception {
		return createUserWithApplicationAndProviderRole("pharmacist", "Pharmacist");
	}

    public static User insertNewArchivistUser() throws Exception {
        return createUserWithApplicationAndProviderRole("dataArchives", "Archivist/Clerk");
    }
	
	private static User createUserWithApplicationAndProviderRole(String role, String providerRole) throws Exception {
		User user;
		
		try {
			
			BigInteger userId = getNextAutoIncrementFor("users");
			String username = "smoke-test-" + role + "-" + userId;
            Integer providerRoleId = getProviderRoleId(providerRole);
			
			user = new User(getNextAutoIncrementFor("person"), UUID.randomUUID().toString(),
			        getNextAutoIncrementFor("person_name"), userId, username, role, getNextAutoIncrementFor("provider"),
			        UUID.randomUUID().toString(), providerRoleId);
			
			IDataSet dataset = createDataset(user);
			datasets.put(user, dataset);
			
			INSERT.execute(connection, dataset);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("unable to create patient in database", e);
		}
		
		usernamesToDelete.add(user.getUsername());
		return user;
	}
	
	public static void deleteAllTestUsers() throws DatabaseUnitException, SQLException {
        for (String username : usernamesToDelete) {
            deleteUser(username);
        }
	}

    public static void addUserForDelete(String username) {
        usernamesToDelete.add(username);
    }
	
	public static void deleteUser(String username) throws SQLException, DataSetException, DatabaseUnitException {

		ITable userQuery = connection.createQueryTable("users", "select * from users where username = '" + username + "'");
        System.out.println("trying to delete user with username " + username);
		Integer userId = (Integer) userQuery.getValue(0, "user_id");
		Integer personId = (Integer) userQuery.getValue(0, "person_id");
		
		QueryDataSet userDataToDelete = new QueryDataSet(connection);
		userDataToDelete.addTable("person", "select * from person where person_id = " + personId);
		userDataToDelete.addTable("provider", "select * from provider where person_id = " + personId);
		userDataToDelete.addTable("person_name", "select * from person_name where person_id = " + personId);
		userDataToDelete.addTable("name_phonetics",
		    "select * from name_phonetics where person_name_id in (select person_name_id from person_name where person_id = "
		            + personId + ")");
        userDataToDelete.addTable("idgen_log_entry","select * from idgen_log_entry where generated_by = " + userId );
		userDataToDelete.addTable("users", "select * from users where user_id = " + userId);
		userDataToDelete.addTable("user_role", "select * from user_role where user_id = " + userId);
		userDataToDelete.addTable("user_property", "select * from user_property where user_id = " + userId);

        DELETE.execute(connection, userDataToDelete);
	}
	
	private static IDataSet createDataset(User user) throws IOException, DataSetException {
		Handlebars handlebars = new Handlebars();
		Template template = handlebars.compile("datasets/users_dataset.xml");
		
		return new FlatXmlDataSetBuilder().build(new InputStreamReader(IOUtils.toInputStream(template.apply(user))));
	}

    private static Integer getProviderRoleId(String providerRoleName) throws SQLException, DataSetException {
        ITable providerRole = connection.createQueryTable("providermanagement_provider_role",
                "select * from providermanagement_provider_role where name = '" + providerRoleName + "'");
        return  (Integer) providerRole.getValue(0, "provider_role_id");
    }
}
