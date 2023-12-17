/**
 * 
 */
package epd.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author maher
 *
 */
public abstract class DAO<Type> implements epd.dao.DAO<Type> {
	
	private String url;
	private String username;
	private String password;
	
	public DAO (String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public void close (ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close (Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close (Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close (Statement statement, Connection connection) {
		close(statement);
		close(connection);
	}
	
	public void close (ResultSet resultSet, Statement statement, Connection connection) {
		close(resultSet);
		close(statement);
		close(connection);
	}
	
	protected Connection connectDB() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
}
