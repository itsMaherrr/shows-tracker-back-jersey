/**
 * 
 */
package epd.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import epd.model.User;

/**
 * @author maher
 *
 */
public class UserDAO extends DAO<User> {

	public UserDAO(String url, String username, String password) {
		super(url, username, password);
	}

	public Optional<User> get(String id) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = connectDB();
		PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM store.users WHERE username = ?");
		statement.setString(1, id);
		ResultSet result = statement.executeQuery();
		
		User user = null;
		try {
			if (result.next()) {
				user = new User();
				user.setUsername(result.getString("username"));
				user.setPassword(result.getString("password"));
			}
		}
		catch (SQLException e) {
			
		}
		return Optional.ofNullable(user);
	}

	public void delete(User object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void add(User object) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public boolean login(String username, String pw) {
		String password = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = connectDB();
			PreparedStatement statement = connection
					.prepareStatement("SELECT PASSWORD(?);");
			statement.setString(1, pw);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				password = result.getString(1);
			}
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Optional<User> user;
		try {
			user = get(username);
			if ((user.isPresent()) && (user.get().getPassword().equals(password))) {
				return true;
			}
			else {
				return false;				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	

}
