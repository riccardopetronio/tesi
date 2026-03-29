package tesi;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Scanner;
//import java.util.TreeMap;

public class GestoreComandiUtente {
	
	private Scanner sc;
	private DAO_Utenti daoU;
	//private TreeMap<String, Utente> mappaUtenti;
	
	public GestoreComandiUtente() {
		this.sc = new Scanner(System.in);
		this.daoU = new DAO_Utenti();
		//this.mappaUtenti = new TreeMap<String, Utente>();
	}


	
	public void primaScelta() {
		
        System.out.print("Cosa vuoi fare?\n");        

		String menu = "1) Login \n2) Nuova registrazione";
        System.out.println(menu);        

        System.out.print("\nDigita qui --> ");
        String valoreIn = sc.nextLine();

        this.gestisciPrimaScelta(valoreIn);
	}
	
	
	
	public void gestisciPrimaScelta(String s) {
		
		if( s.equals("1") ) {
			if( this.logIn()==true ) {
				System.out.print("\nlogin eseguito correttamente\n");
				this.terminainput();
				
				
				
				
			}
			else {
				System.err.print("riprova\n\n");
				this.primaScelta();
			}
			this.terminainput();
		}
		
		else if( s.equals("2") ) {
			this.registrazione();
			this.primaScelta();
		}
		
		else if( s.equals("3") ) {
			this.terminainput();
		}
			
		
		else {
			System.err.print("\ncomando non disponibile riprova\n");
			this.primaScelta();
		}
	}
	
	
	
	
	public Boolean logIn() {
		
		System.out.print("\ninserisci qui il tuo username e password --> ");
        String datiIn = sc.nextLine();
        
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
				System.err.print("\noperazione fallita la tua passuord è errata\n\n");
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
	
	
	
	public void registrazione() {
        
		System.out.print("\ninserisci qui il tuo username e password --> ");
        String datiIn = sc.nextLine();
        
        String valori[] = datiIn.split(" ");
        String user = valori[0];
        String password = valori[1];
        
        try {
			if( this.daoU.getPresenzaUtente(user)!=null ) {
				System.err.print("\noperazione fallita il tuo username è già presente nel DB\n\n");
				return;
			}		
			System.out.print(" (username non presente)\n");

		}
        catch (SQLException e) {
			System.err.print("\nerrore durante la connessione al DB per verificare la presenza dello username"+ e);
			return;
		}
        
        try {
			this.daoU.aggiungiUtente(user, password);
		} 
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			System.err.print("\nerrore durante l'operazione fi hashing"+ e);
			return;
		}
        catch (SQLException e) {
			System.err.print("\nerrore durante la connessione al DB per l'inserimento dell'utente"+ e);
			return;
		}
        
		System.out.print("Utente registrato correttamente\n\n");
	}


	
	
	public void terminainput() {
		this.sc.close();
	}
	
	
	
	/*  METODI NON USATI MA POTENZIALMENTE UTILI IN FUTURO
	public Utente getUtente(String s) {
		if( this.mappaUtenti.containsKey(s) ) {
			return this.mappaUtenti.get(s);
		}
		else {
			return null;
		}	
    }
	
	public void aggiungiUtente(Utente u) {
		if( getUtente(u.getUsername())==null ) {
			this.mappaUtenti.put(u.getUsername(), u);
		}
		else {
			System.err.print("\n\n non ho potuto aggiungere l'utente alla mappa perche già presente");
		}
	}
	*/
}
