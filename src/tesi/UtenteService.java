package tesi;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class UtenteService {
	private DAO_Utenti daoU;

	
public UtenteService() {
		this.daoU = new DAO_Utenti();
	}

public boolean logIn(String datiIn) {
		
        String valori[] = datiIn.split(" ");
        String user = valori[0];
        String password = valori[1];
        
        try {
        	Utente utente = this.daoU.getPresenzaUtente(user);
			if( utente==null ) {
				System.err.print("\n\noperazione fallita il tuo username è errato o non presente nel DB\n\n");
				return false;
			}
			System.out.print(" (username trovato)\n");
			
			Boolean risultato = PasswordHasher.verifyPassword(password, utente.getHash(), utente.getSalt());
			if( risultato==false ) {
				System.err.print("\nla tua passuord è errata\n\n");
			}
			return risultato;
		}
        
        catch (SQLException e) {
			System.err.print("\nerrore durante la connessione al DB per verificare la presenza dello username"+ e);
			return false;
		}
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			System.err.print("\nerrore durante l'operazione di hashing"+ e);
			return false;
		}
	}

public boolean registrazione(String datiIn) {
    
    String valori[] = datiIn.split(" ");
    String user = valori[0];
    String password = valori[1];
    
    try {
		if( this.daoU.getPresenzaUtente(user)!=null ) {
			System.err.print("\nil tuo username è già presente nel DB\n");
			return false;
		}		
		System.out.print(" (username non presente)\n");

	}
    catch (SQLException e) {
		System.err.print("\nerrore durante la connessione al DB per verificare la presenza dello username"+ e);
		return false;
	}
    
    try {
		this.daoU.aggiungiUtente(user, password);
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
	
}
