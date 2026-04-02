package tesi;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.TreeMap;

public class GestoreComandiUtente {
	
	private Scanner sc;
	
	public GestoreComandiUtente() {
		this.sc = new Scanner(System.in);
	}


	public void primaScelta() throws SQLException {
        System.out.print("Cosa vuoi fare?\n");        

		String menu = "1) Login \n"
					+ "2) Nuova registrazione";
        System.out.println(menu);        

        System.out.print("\nDigita qui --> ");
        String valoreIn = this.sc.nextLine();

        this.gestisciPrimaScelta(valoreIn);
	}
	
	public String credenzialeUsername() {
		System.out.print("\ninserisci qui il tuo username --> ");
		String userIn = this.sc.nextLine();
		return userIn;
	}
	
	public String credenzialePassword() {
		System.out.print("\ninserisci qui la tua password --> ");
		String passIn = this.sc.nextLine();
		return passIn;
	}
	
	
	public void secondaScelta() throws SQLException {
		System.out.print("\nCosa vuoi fare?\n");        

		String menu = "1) stampa tutte le VM presenti nel resource group \n"
					+ "2) aggiorna la lista di VM sul DB \n"
					+ "3) logout \n";

        System.out.println(menu);        

        System.out.print("Digita qui --> ");
        String valoreIn = sc.nextLine();
        
        this.gestisciSecondaScelta(valoreIn);
	}
	
	
	public void gestisciPrimaScelta(String s) throws SQLException {
		
		if( s.equals("1") ) {
			
			String username = this.credenzialeUsername();
			if ( verificaInput(username)==false ){
				this.primaScelta();
				return;
			}
		
			Utente utente = UtenteService.verificaUsername(username);
			if ( utente==null ) {
				System.err.print("\nusername non presente nel DB, riprova\n\n");
				this.primaScelta();
				return;
			}
			System.out.print(" utente trovato\n");

			
			String password = this.credenzialePassword();
			if ( verificaInput(password)==false ){
				this.primaScelta();
				return;
			}
			
			if( UtenteService.logIn(password, utente.getHash(), utente.getSalt())==true ) {
				System.out.print("\nlogin eseguito correttamente\n");
				this.secondaScelta();
			}
			else {
				System.err.print("login fallito riprova\n\n");
				this.primaScelta();
				return;
			}
		}
		
		else if( s.equals("2") ) {
			
			String username = this.credenzialeUsername();
			if ( verificaInput(username)==false ){
				this.primaScelta();
				return;
			}
		
			if ( UtenteService.verificaUsername(username)!=null ) {
				System.err.print("\nusername già presente nel DB, riprova\n\n");
				this.primaScelta();
				return;
			}
			System.out.print(" usename disponibile\n");

			
			String password = this.credenzialePassword();
			if ( verificaInput(password)==false ){
				this.primaScelta();
				return;
			}
			
			if( UtenteService.registrazione(username, password)==true ) {
				System.out.print("Utente registrato correttamente\n\n");
			}
			else {
				System.err.print("\nregistrazione fallita riprova\n\n");
			}
			this.primaScelta();
			return;
		}
			
		else {
			System.err.print("\ncomando non disponibile riprova, puoi sceglie '1' o '2'\n\n");
			primaScelta();
			return;
		}
	}
	
	
	public void gestisciSecondaScelta(String s) throws SQLException {
		
		if( s.equals("1") ) {
			TreeMap<String, VMRecord> risultato = VirtualMachineService.getAllVMDalDB();
			if( risultato!=null ) {
		        System.out.print("\nRISULTATO:");
				for (VMRecord vm : risultato.values()) {
			        System.out.print("\n"+vm);
				}
		        System.out.print("\n");
				this.secondaScelta();
				return;
			}
			else {
				System.err.print("\noperazione fallita riprova\n"); 
				this.secondaScelta();
				return;
			}
		}
		else if( s.equals("2") ) {
			VirtualMachineService.sincronizzaVM("tesi-petronio");
	        System.out.print("\n\n operazione eseguita con successo");
	        this.secondaScelta();
	        return;
		}
		else if( s.equals("3") ) {
			this.primaScelta();
			return;
		}
		else {
			System.err.print("\ncomando non disponibile riprova, puoi scegliere '1', '2' o '3'\n");
			this.secondaScelta();
			return;
		}
	}
	

	public boolean verificaInput(String s) {
		if( s.stripLeading().split(" ").length!=1 ) {
			System.err.print("\nerrore riprova, la stringa non può contenere spazi\n\n");
			return false;
		}
		return true;
	}
}
