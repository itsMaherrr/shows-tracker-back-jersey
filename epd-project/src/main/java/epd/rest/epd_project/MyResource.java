package epd.rest.epd_project;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import epd.database.*;
import epd.model.Media;
import epd.model.User;
import epd.security.JWT;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

	private final static String HOST = "jdbc:mysql://172.20.10.7:3306/store";
	private final static String USERNAME = "mahers";
	private final static String PASSWORD = "mahers123";
	private Gson json = new GsonBuilder().serializeNulls().create();

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Path("watchlist")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getWatchList(@Context HttpHeaders hh) {
		MultivaluedMap<String, String> session = hh.getRequestHeaders();
		HashMap<String, String> response = new HashMap<String, String>();

		String token = session.getFirst("Authorization").split(" ")[1];
		if (JWT.verifyUser(session.getFirst("username"), token)) {
			WatchlistDAO listDao = new WatchlistDAO(HOST, USERNAME, PASSWORD);
			try {
				ArrayList<Media> list = listDao.getAll(session.getFirst("username"));
				response.put("success", "true");
				response.put("message", "data fetched successfully");
				response.put("data", json.toJson(list));
			} catch (Exception e) {
				response.put("success", "false");
				response.put("message", "An error occured while fetching data, please try again later!");
				e.printStackTrace();
			}
		} else {
			response.put("success", "false");
			response.put("message", "Invalid token, re-sign in and try again!");
		}

		return json.toJson(response);
	}
	
	@GET
	@Path("watched-list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getWatchedList(@Context HttpHeaders hh) {
		MultivaluedMap<String, String> session = hh.getRequestHeaders();
		HashMap<String, String> response = new HashMap<String, String>();

		String token = session.getFirst("Authorization").split(" ")[1];
		if (JWT.verifyUser(session.getFirst("username"), token)) {
			WatchedDAO listDao = new WatchedDAO(HOST, USERNAME, PASSWORD);
			try {
				ArrayList<Media> list = listDao.getAll(session.getFirst("username"));
				response.put("success", "true");
				response.put("message", "data fetched successfully");
				response.put("data", json.toJson(list));
			} catch (Exception e) {
				response.put("success", "false");
				response.put("message", "An error occured while fetching data, please try again later!");
				e.printStackTrace();
			}
		} else {
			response.put("success", "false");
			response.put("message", "Invalid token, re-sign in and try again!");
		}

		return json.toJson(response);
	}

	/*
	 * @POST
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.TEXT_PLAIN) public String gotIt() { return "Received";
	 * 
	 * }
	 */

	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String login(User user) {
		UserDAO userDao = new UserDAO(HOST, USERNAME, PASSWORD);

		HashMap<String, String> response = new HashMap<String, String>();
		try {
			boolean login = userDao.login(user.getUsername(), user.getPassword());

			if (login) {
				// response.data
				HashMap<String, String> data = new HashMap<String, String>();
				data.put("username", user.getUsername());
				data.put("token", JWT.getJWTToken(user.getUsername(), user.getPassword()));
				// response
				response.put("success", "true");
				response.put("message", "Logged-in successfully");
				response.put("data", json.toJson(data));

				return json.toJson(response);
			} else {
				response.put("success", "false");
				response.put("message", "Wrong user informations");

				return json.toJson(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("success", "false");
			response.put("message", "Something went wrong, please try again later!");
			return json.toJson(response);
		}

	}

	@POST
	@Path("add-to-watchlist")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addToWatchlist(Media media, @Context HttpHeaders hh) {
		MultivaluedMap<String, String> session = hh.getRequestHeaders();
		HashMap<String, String> response = new HashMap<String, String>();

		String token = session.getFirst("Authorization").split(" ")[1];
		if (JWT.verifyUser(session.getFirst("username"), token)) {
			WatchlistDAO watchlist = new WatchlistDAO(HOST, USERNAME, PASSWORD);
			try {
				watchlist.add(media, session.getFirst("username"));
				response.put("success", "true");
				response.put("message", "Added to watchlist successfully");
			} catch (Exception e) {
				e.printStackTrace();
				response.put("success", "false");
				response.put("message", "An error has occured, please try again later!");
			}
		}
		return json.toJson(response);
	}
	
	@POST
	@Path("add-to-watched-list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addToWatchedlist(Media media, @Context HttpHeaders hh) {
		MultivaluedMap<String, String> session = hh.getRequestHeaders();
		HashMap<String, String> response = new HashMap<String, String>();

		String token = session.getFirst("Authorization").split(" ")[1];
		if (JWT.verifyUser(session.getFirst("username"), token)) {
			WatchedDAO watchlist = new WatchedDAO(HOST, USERNAME, PASSWORD);
			try {
				if (watchlist.add(media, session.getFirst("username"))) {
					response.put("success", "true");
					response.put("message", "Added to watchlist successfully");
				}
				else {
					response.put("success", "false");
					response.put("message", "Couldn't add item, try again please!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.put("success", "false");
				response.put("message", "An error has occured, please try again later!");
			}
		}
		return json.toJson(response);
	}
	
	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteItem(@PathParam("id") String id, @Context HttpHeaders hh) {
		MultivaluedMap<String, String> session = hh.getRequestHeaders();
		HashMap<String, String> response = new HashMap<String, String>();
		
		System.out.println(session.size());

		String token = session.getFirst("Authorization").split(" ")[1];
		if (JWT.verifyUser(session.getFirst("username"), token)) {
			WatchedDAO watchlist = new WatchedDAO(HOST, USERNAME, PASSWORD);
			try {
				if (watchlist.delete(id, session.getFirst("username"))) {
					response.put("success", "true");
					response.put("message", "Item deleted successfully");
				}
				else {
					response.put("success", "false");
					response.put("message", "Couldn't delete item, try again please!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.put("success", "false");
				response.put("message", "An error has occured, please try again later!");
			}
		}
		return json.toJson(response);
	}
	
	@PUT
	@Path("update-to-watched")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateToWatchedlist(Media media, @Context HttpHeaders hh) {
		MultivaluedMap<String, String> session = hh.getRequestHeaders();
		HashMap<String, String> response = new HashMap<String, String>();

		String token = session.getFirst("Authorization").split(" ")[1];
		if (JWT.verifyUser(session.getFirst("username"), token)) {
			WatchlistDAO watchlist = new WatchlistDAO(HOST, USERNAME, PASSWORD);
			try {
				if (watchlist.update(media, session.getFirst("username"))) {
					response.put("success", "true");
					response.put("message", "Added to watched list successfully");
				}
				else {
					response.put("success", "false");
					response.put("message", "Couldn't add item to watched list, try again please!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.put("success", "false");
				response.put("message", "An error has occured, please try again later!");
			}
		}
		return json.toJson(response);
	}
	
	
}
