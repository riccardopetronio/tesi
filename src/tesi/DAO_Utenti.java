package tesi;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Utenti {
	
	public static Utente getPresenzaUtente(String user) throws SQLException {
	    
		System.out.println("Inizio operazione di SELECT (verifico presenza username)...");
	    Connection conn = DatabaseConnector.creaConnessione();
	    
	    String query = "SELECT * FROM utenti WHERE username = ?";
	    PreparedStatement pstmt = conn.prepareStatement(query);
	    pstmt.setString(1, user);
	    
	    ResultSet rs = pstmt.executeQuery();
    	Utente u = null;
	    
	    // Spostiamo il puntatore sulla riga trovata
	    if( rs.next() ) {
	    	u = new Utente(rs.getNString(1), rs.getNString(2), rs.getNString(3));
	    }

	    // Chiudiamo tutto esplicitamente
	    rs.close();
	    pstmt.close();
	    conn.close();

	    System.out.print("Operazione di SELECT conclusa, connessione chiusa");

	    return u;
	}
	
	public static void aggiungiUtente(String user, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
		
		String risultato[] = PasswordHasher.gerenaSaltAndHash(password);
		System.out.println("\nHashing eseguito correttamente (ho generato salt e hash)");

		String hash = risultato[0];
		String salt = risultato[1];
        
		System.out.println("Inizio operazione di INSERT utente...");

		Connection conn = DatabaseConnector.creaConnessione();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO utenti(username, hash, salt) VALUES(?, ?, ?)");
		
		ps.setString(1, user);
	    ps.setString(2, hash);
	    ps.setString(3, salt);
		
	    ps.executeUpdate();
	    ps.clearParameters();
	    
	    ps.close();
	    conn.close();
	    System.out.println("Operazione di INSERT riuscira, chiudo la connessione\n");
	}

	
}
