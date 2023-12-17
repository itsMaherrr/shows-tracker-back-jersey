/**
 * 
 */
package epd.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import epd.model.Media;


public abstract class ListDAO {
	
	private String url;
	private String username;
	private String password;

	public ListDAO(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public abstract ArrayList<Media> getAll(String username) throws Exception;

	public boolean delete(String id, String username) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = connectDB();
			PreparedStatement statement = connection
					.prepareStatement("DELETE FROM store.lists WHERE id = ? AND owner = ? ;");
			statement.setString(1, id);
			statement.setString(2, username);
			
			return (statement.executeUpdate() > 0);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
	}

	public abstract boolean add(Media object, String username) throws Exception;

	public abstract Optional<Media> get(String id, String username) throws Exception;
	
	protected Optional<Media> map (ResultSet result){
		Media media = null;
		try {
			if (result.next()) {
				media = new Media.Builder()
						.withId(result.getString("id"))
						.withTitle(result.getString("title"))
						.withType(result.getString("type"))
						.withPicture(result.getString("picture"))
						.withCast(result.getString("cast"))
						.withYear(result.getString("year"))
						.build();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(media);
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
