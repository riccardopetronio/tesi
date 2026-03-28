package tesi;

import java.sql.Connection;
import java.sql.DriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnector {
	
	public static Connection creaConnessione() {
		
	    System.out.println("Inizio la connessione al DB...");
		try {
			Dotenv dotenv = Dotenv.configure()
	                .directory(System.getProperty("user.dir")) // root del progetto
	                .load();
			
	     	// sto prendento un database di tipo mysql, salvato in un servere locale (il mio calcolatore), nominato db_tesi e con la mia password
			String passwordDatabase = dotenv.get("DATABASE_PASSWORD");	
			String url = "jdbc:mysql://localhost/db_tesi?user=root&password="+passwordDatabase;		
	
			Connection conn = DriverManager.getConnection(url); // questo oggetto rappresenta la connessione con il database
		    System.out.println("Connessione al DB riuscira...");
			return conn;
		}
		
		catch (Exception e) {
		    throw new RuntimeException("Errore durante la connessione al DB ", e);
		}
		
	}
}
