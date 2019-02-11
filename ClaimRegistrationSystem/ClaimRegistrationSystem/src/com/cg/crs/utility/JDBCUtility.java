package com.cg.crs.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.cg.crs.exception.CRSException;



public class JDBCUtility {

	private static Connection connection = null;

	public static Connection getConnection() throws CRSException {

		File file = null;
		FileInputStream inputStream = null;

		file = new File("resources/jdbc.properties");
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			throw new CRSException("file not found");
		}

		Properties properties = new Properties();
		try {
			properties.load(inputStream);

			String driver = properties.getProperty("db.driver");
			String url = properties.getProperty("db.url");
			String username = properties.getProperty("db.name");
			String password = properties.getProperty("db.pass");

			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);

		} catch (IOException e1) {
			throw new CRSException("unable to load the file");
		} catch (ClassNotFoundException e) {
			throw new CRSException("class not loaded..");
		} catch (SQLException e) {
			throw new CRSException("not connected..");
		}

		return connection;
	}
}
