package com.vaadin.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

/**
 * This init listener is run once whenever the Vaadin context starts. As such,
 * it is a great place to create dummy data.
 * <p>
 * See the <code>application.properties</code> file for database connection
 * properties.
 */
@Service
public class ApplicationServiceInitListener implements VaadinServiceInitListener {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void serviceInit(ServiceInitEvent serviceInitEvent) {
		System.out.println("_________DB initiation has started____________");

		// Initializing tables in the database

		// First, remove if already exist
	//	initDBStructure();
		// insert data
	//	populateData();

		System.out.println("_________DB initiation has finished____________");
	}

	private void initDBStructure() {
		jdbcTemplate.execute("DROP TABLE kunde IF EXISTS ");
		jdbcTemplate.execute("DROP TABLE account IF EXISTS ");



		jdbcTemplate.execute("CREATE TABLE kunde (id IDENTITY NOT NULL PRIMARY KEY, name VARCHAR(255),vorname VARCHAR(255) , email VARCHAR(255), uid VARCHAR(255))");
		jdbcTemplate.execute("CREATE TABLE account (id IDENTITY NOT NULL PRIMARY KEY, name VARCHAR(255),passwort VARCHAR(255) , rolle VARCHAR(255))");

	}


	private void populateData() {


		jdbcTemplate.update(
				"INSERT INTO kunde VALUES (DEFAULT, 'Niederhauser','Jamie', 'jamie.niederhauser@fdas.ch','2ad195a4-be9c-4761-bf67-ddbd3df8f088')");

		jdbcTemplate.update(
				"INSERT INTO kunde VALUES (DEFAULT, 'Niederhauadsfasdfser','Jamadsfie', 'jamie.niedfaser@fdas.ch','4ad195a4-be9c-4761-bf67-ddbd3df8f007')");
		jdbcTemplate.update(
				"INSERT INTO kunde VALUES (DEFAULT, 'Niederhaudasfaser','Jamiadsfe', 'jamie.hauser@fdas.ch','2ad195a4-be9c-4761-bf67-ddbd3df8f444')");

		jdbcTemplate.update(
				"INSERT INTO kunde VALUES (DEFAULT, 'User','User', 'User.user@gmail.com','2ad195a4-be9c-4761-bf67-ddbd3df8f733')");
		jdbcTemplate.update(
				"INSERT INTO kunde VALUES (DEFAULT, 'Admin','Admin', 'Admin.admin@gmail.com','2ad195a4-be9c-4761-bf67-ddbd3df8f092')");
		jdbcTemplate.update(
				"INSERT INTO account VALUES (DEFAULT, 'user','$2a$12$DfQ4CYYo7bDai5hC0B0pzOQ0fyLpGSjbUTPlL.UqgpNmMXP08cqO.', 'user')");
		jdbcTemplate.update(
				"INSERT INTO account VALUES (DEFAULT, 'admin','$2a$12$1OH4xyqC4XInHS7quDaGEemm2NpOwOAuL8fu0kRCLs.MY0zn6UMee', 'admin')");

	}

}
