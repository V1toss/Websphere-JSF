package ru.vkaretko.listmanager.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.h2.jdbcx.JdbcDataSource;

import ru.vkaretko.listmanager.data.util.DbConnector;

public class DataSourceSetupUtil {
	private static boolean hasSetupInitialContext;
	
	public static void setup() throws NamingException, SQLException {
		if (!hasSetupInitialContext) {
			hasSetupInitialContext = true;
			setupInitialContext();
		}
		
		setupDdl();
	}
	
	private static void setupInitialContext() throws NamingException {
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
		System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");            
		InitialContext ic = new InitialContext();
		
		ic.createSubcontext("java:");
		ic.createSubcontext("java:comp");
		ic.createSubcontext("java:comp/env");
		ic.createSubcontext("java:comp/env/jdbc");
		
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
		
		ic.bind("java:comp/env/jdbc/AppDb", dataSource);
	}
	
	private static void setupDdl() throws SQLException {
		Connection connection = DbConnector.getConnection();
		
		Statement stmt = connection.createStatement();
		
		stmt.executeUpdate(
				"CREATE SEQUENCE SEQ_APP_USER_ID");
		stmt.executeUpdate(
				"CREATE TABLE APP_USER ( "
				+ "	    ID              INTEGER         NOT NULL "
				+ "	  , USERNAME        VARCHAR(30)     NOT NULL "
				+ "	  , DISPLAY_NAME    VARCHAR(30)     NOT NULL "
				+ "	  , CONSTRAINT PK_APP_USER PRIMARY KEY (ID) "
				+ ")");
		
		stmt.executeUpdate("INSERT INTO APP_USER (ID, USERNAME, DISPLAY_NAME) VALUES (NEXTVAL('SEQ_APP_USER_ID'), 'test', 'Test User')");
		
		stmt.executeUpdate(
				"CREATE SEQUENCE SEQ_LIST_ITEM_ID");
		stmt.executeUpdate(
				"CREATE TABLE LIST_ITEM ( "
				+ "	    ID              INTEGER         NOT NULL "
				+ "	  , USER_ID         INTEGER         NOT NULL "
				+ "	  , VALUE           VARCHAR(4000)   NOT NULL "
				+ "	  , CONSTRAINT PK_LIST_ITEM PRIMARY KEY (ID) "
				+ "   , CONSTRAINT FK_LIST_ITEM_USER FOREIGN KEY (USER_ID) REFERENCES APP_USER (ID) "
				+ ")");
		
		stmt.close();
		
		connection.close();
	}
	
	public static void tearDown() throws SQLException {
		Connection connection = DbConnector.getConnection();
		
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DROP SEQUENCE SEQ_APP_USER_ID");
		stmt.executeUpdate("DROP SEQUENCE SEQ_LIST_ITEM_ID");
		stmt.executeUpdate("DROP TABLE LIST_ITEM");
		stmt.executeUpdate("DROP TABLE APP_USER");
		stmt.close();
		
		connection.close();
	}
	
	public static void insertListItemForDefaultUser(String value) throws SQLException {
		Connection connection = DbConnector.getConnection();
		
		PreparedStatement stmt = connection.prepareStatement(
				"INSERT INTO LIST_ITEM (ID, USER_ID, VALUE) VALUES (NEXTVAL('SEQ_LIST_ITEM_ID'), 1, ?)");
		stmt.setString(1, value);
		stmt.executeUpdate();
		stmt.close();
		
		connection.close();
	}
	
	public static long getListItemsCount() throws SQLException {
		long count = 0L;
		
		Connection connection = DbConnector.getConnection();
		
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) CNT FROM LIST_ITEM");
		if (rs.next()) {
			count = rs.getLong("CNT");
		}
		rs.close();
		stmt.close();
		
		connection.close();
		
		return count;
	}
}
