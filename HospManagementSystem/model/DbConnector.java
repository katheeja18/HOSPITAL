package HospManagementSystem.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
	static String url = "jdbc:mysql://localhost:3306/hospital";
	 static String user = "root";
	static  String pass = "kat18";
	 
   public static Connection getConnection() throws SQLException
   {
   	return DriverManager.getConnection(url, user, pass);
   }
}
