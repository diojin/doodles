package outerhaven.cip.listing.three;

import java.sql.Connection;
import java.sql.DriverManager;

import outerhaven.cip.common.ThreadSafe;

/**
 * Listing 3.10. Using ThreadLocal to Ensure thread Confinement
 * @author threepwood
 *
 */
@ThreadSafe
public class ConnectionHolder {
	private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
		public Connection initialValue(){
			try{
				return DriverManager.getConnection(DB_URL);
			}finally{
				return null;
			}
			
		}
	};

	public static Connection getConnection() {
		return connectionHolder.get();
	}
	
	private static final String DB_URL="";
}
