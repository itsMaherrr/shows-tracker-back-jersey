/**
 * 
 */
package epd.dao;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author maher
 *
 */
public interface DAO <Type> {
	
	public Optional<Type> get(String id) throws Exception;
	
	public void delete(Type object) throws Exception;
	
	public void add(Type object) throws Exception;
	
}
