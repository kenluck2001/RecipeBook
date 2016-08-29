package client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DB {
	private static Statement stmt;
	private static Connection conn;
	
	public static void connect() {
        // Settings
        String dbServer = "crofle.ath.cx";
        String dbUser   = "vaadin";
        String dbPass   = "vaadin";
        String dbName   = "vaadin";
        
        try {
	        Class.forName("com.mysql.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://"+dbServer+"/"+dbName+"?user="+dbUser+"&password="+dbPass);
	        setStmt(conn.createStatement());
        } catch (Exception ex) {
        	// Error
        	System.out.println("Connection error: " + ex);
        }
	}
	
	// Close connection
	public static void disconnect(){
    	try {
    		getStmt().close();
        	conn.close();
		} catch (Exception e) {
		} 
    }

	public static void setStmt(Statement stmt) {
		DB.stmt = stmt;
	}

	public static Statement getStmt() {
		return stmt;
	}
}
