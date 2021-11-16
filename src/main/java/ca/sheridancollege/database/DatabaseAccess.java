package ca.sheridancollege.database;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseAccess {

	@Autowired
	NamedParameterJdbcTemplate jbdc;

	public void addPerson(int id, String name) {// Sql query with user input
		String query_id = "(" + "'" + id + "'" + "," + "'" + name + "'" + ")";
		String query = "INSERT INTO person" + "(person_id, person_name)" + "values" + query_id;
		if (id != 0 || name != null) {// only update when user enter values
			jbdc.update(query, new HashMap());
		}

	}
}