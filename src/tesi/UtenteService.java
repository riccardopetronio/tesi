package tesi;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class UtenteService {

	public static boolean logIn(String password, String hash, String salt) {
			
	    try {
	    	Boolean risultato = PasswordHasher.verifyPassword(password, hash, salt);
			if( risultato==false ) {
				System.err.print("\nla password è errata\n\n");
				return false;
			}
			return risultato;
		}
	    catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			System.err.print("\nerrore durante l'operazione di hashing"+ e);
			return false;
		}
	}
	
	public static boolean registrazione(String user, String password) {
	
	    try {
	    	DAO_Utenti.aggiungiUtente(user, password);
		} 
	    catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			System.err.print("\nerrore durante l'operazione fi hashing"+ e);
			return false;
		}
	    catch (SQLException e) {
			System.err.print("\nerrore durante la connessione al DB per l'inserimento dell'utente"+ e);
			return false;
		}
	    return true;
	}
	
	public static Utente verificaUsername(String u) throws SQLException {
			return DAO_Utenti.getPresenzaUtente(u);
	}
}
