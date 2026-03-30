package tesi;

import java.util.Scanner;

public class GestoreComandiUtente {
	
	private Scanner sc;
	private UtenteService us;
	
	public GestoreComandiUtente() {
		this.sc = new Scanner(System.in);
		this.us = new UtenteService();
	}


	
	public void primaScelta() {
        System.out.print("Cosa vuoi fare?\n");        

		String menu = "1) Login \n"
					+ "2) Nuova registrazione";
        System.out.println(menu);        

        System.out.print("\nDigita qui --> ");
        String valoreIn = sc.nextLine();

        this.gestisciPrimaScelta(valoreIn);
	}
	
	
	public void secondaScelta() {
		System.out.print("\nCosa vuoi fare?\n");        

		String menu = "1) stampa tutte le VM presenti nel resource group \n"
					+ "2) stampa tutte le VM presenti nella mia lista \n"
					+ "3) modifica la mia lista di VM \n";

        System.out.println(menu);        

        System.out.print("Digita qui --> ");
        String valoreIn = sc.nextLine();
        
        this.gestisciSecondaScelta(valoreIn);
	}
	
	
	public void terzaScelta() {
		System.out.print("\nCosa vuoi fare?\n");        

		String menu = "1) rimuovi VM dalla mia lista \n"
					+ "2) aggiungi VM alla mia lista \n";

        System.out.println(menu);        

        System.out.print("Digita qui --> ");
        String valoreIn = sc.nextLine();
        
        this.gestisciTerzaScelta(valoreIn);
	}
	
	
	public void gestisciPrimaScelta(String s) {
		
		if( s.equals("1") ) {
			
			System.out.print("\ninserisci qui il tuo username e password --> ");
			String datiIn = sc.nextLine();
		
			if( this.us.logIn(datiIn)==true ) {
				System.out.print("\nlogin eseguito correttamente\n");
				this.secondaScelta();
			}
			else {
				System.err.print("login fallito riprova\n\n");
				this.primaScelta();
			}
		}
		
		else if( s.equals("2") ) {
			
			System.out.print("\ninserisci qui il tuo username e password --> ");
			String datiIn = sc.nextLine();
			
			if( this.us.registrazione(datiIn)==true ) {
				System.out.print("Utente registrato correttamente\n\n");
			}
			else {
				System.err.print("\nregistrazione fallita riprova\n\n");
			}
			this.primaScelta();
		}
			
		else {
			System.err.print("\ncomando non disponibile riprova\n\n");
			primaScelta();
		}
	}
	
	
	public void gestisciSecondaScelta(String s) {
		
		if( s.equals("1") ) {
			this.terminainput();
		}
		
		else if( s.equals("2") ) {
			this.terminainput();
		}
		
		else if( s.equals("3") ) {
			this.terzaScelta();
		}
		
		else {
			System.err.print("\ncomando non disponibile riprova\n");
			this.secondaScelta();
		}
	}
	
	
	public void gestisciTerzaScelta(String s) {
		
		this.terminainput();		
	}
	
	

	
	public void terminainput() {
		this.sc.close();
	}

}
