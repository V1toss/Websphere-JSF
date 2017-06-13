package ru.vkaretko.listmanager.data.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ru.vkaretko.listmanager.data.UserDao;
import ru.vkaretko.listmanager.data.util.DbConnector;
import ru.vkaretko.listmanager.data.util.JdbcCloses;
import ru.vkaretko.listmanager.model.User;

public class UserDaoImpl implements UserDao {

	@Override
	public User getUser(String username) {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		User user = null;
		try {
			connection = DbConnector.getConnection();
			stmt = connection.prepareStatement(
					"SELECT U.ID, U.USERNAME, U.DISPLAY_NAME "
					+ "FROM APP_USER U "
					+ "WHERE LOWER(U.USERNAME) = ?");
			stmt.setString(1, username.toLowerCase());
			
			rs = stmt.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getLong("ID"));
				user.setUsername(rs.getString("USERNAME"));
				user.setDisplayName(rs.getString("DISPLAY_NAME"));
			}
			
			rs.close();
			stmt.close();
		} catch (SQLException se) {
			// TODO handle me
		} finally {
			JdbcCloses.closeIgnoreError(rs);
			JdbcCloses.closeIgnoreError(stmt);
			JdbcCloses.closeIgnoreError(connection);
		}
		
		return user;
	}
	
}
