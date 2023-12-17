/**
 * 
 */
package epd.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;

import epd.model.Media;

/**
 * @author maher
 *
 */
public class WatchlistDAO extends ListDAO {

	public WatchlistDAO(String url, String username, String password) {
		super(url, username, password);
	}

	@Override
	public ArrayList<Media> getAll(String username) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection connection = connectDB();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM store.lists where owner = ? AND list='watchlist';");
		statement.setString(1, username);
		ResultSet result = statement.executeQuery();

		ArrayList<Media> list = new ArrayList<Media>();
		Optional<Media> container;

		while ((container = map(result)).isPresent()) {
			list.add(container.get());
		}

		close(result, statement, connection);

		return list;
	}

	@Override
	public boolean add(Media media, String username) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection connection = connectDB();
		PreparedStatement statement = connection.prepareStatement("INSERT INTO store.lists VALUES ( ? , ? , ? , ? , ? , ? , ? , 'watchlist');");
		statement.setString(1, media.getId());
		statement.setString(2, media.getTitle());
		statement.setString(3, media.getType());
		statement.setString(4, media.getPicture());
		statement.setString(5, media.getCast());
		statement.setString(6, media.getYear());
		statement.setString(7, username);
		
		return (statement.executeUpdate() > 0);		
	}
	
	public boolean update(Media media, String username) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection connection = connectDB();
		PreparedStatement statement = connection
				.prepareStatement("UPDATE store.lists SET list = 'watched' WHERE id = ? AND owner = ? ;");
		statement.setString(1, media.getId());
		statement.setString(2, username);
		
		return (statement.executeUpdate() > 0);
		
	}
	

	@Override
	public Optional<Media> get(String id, String username) throws Exception {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
