/**
 * 
 */
package epd.security;

import java.security.Key;
import java.util.HashMap;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author maher
 *
 */
public class JWT {
	
	private final static String SECRET_KEY = "someRandomSignatureForTheAuthentificationIdkXD";
	
	public static String getJWTToken(String username, String password) {
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		
		HashMap<String, String> data = new HashMap();
		data.put("username", username);
		data.put("password", password);
	
		
		String token = Jwts.builder().setHeaderParam("alg", "RS256")
				.setHeaderParam("typ", "JWT")
				.setClaims(data)
				.signWith(signingKey)
				.compact();
		
		return token;
		
	}
	
	public static Jws<Claims> decode (String jwt) {
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		
		Jws<Claims> claims = Jwts.parserBuilder()
				.setSigningKey(signingKey)
				.build()
				.parseClaimsJws(jwt);
	    return claims;		
	}
	
	public static boolean verifyUser (String username, String jwt) {
		return decode(jwt).getBody().get("username").equals(username);
	}

}
