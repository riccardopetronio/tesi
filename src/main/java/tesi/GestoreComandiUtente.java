package tesi;

import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tesi.automation.Automazione;
import tesi.automation.AutomazioneService;
import tesi.automation.TipologiaOperazione;
import tesi.user.Utente;
import tesi.user.UtenteService;
import tesi.vm.VMRecord;
import tesi.vm.VirtualMachineService;

@Component
public class GestoreComandiUtente {
	
	private Utente utenteAttuale;
	private final Scanner sc = new Scanner(System.in);;
	
	@Autowired // <--- Spring inietterà automaticamente il Service configurato
	private UtenteService us;
	@Autowired
	private VirtualMachineService vms;
	@Autowired
	private AutomazioneService as;
	
	

	public void primaScelta() {
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
	
	public void secondaScelta() {
		System.out.print("\nCosa vuoi fare?\n");        

		String menu = "1) stampa tutte le VM presenti nel resource group \n"
					+ "2) aggiorna la lista di VM sul DB \n"
					+ "3) gestisci le automazioni \n"
					+ "4) logout \n";

        System.out.println(menu);        

        System.out.print("Digita qui --> ");
        String valoreIn = sc.nextLine();
        
        this.gestisciSecondaScelta(valoreIn);
	}
	
	public void terzaScelta() {
		System.out.print("\nCosa vuoi fare?\n");        

		String menu = "1) crea nuova automazione \n"
				+ "2) mostra tutte le automazioni salvate nel DB \n"
				+ "3) mostra automazioni abilitate \n"
				+ "4) elimina automazione \n"
				+ "5) modifica automazione esistente \n"
				+ "6) vai al menù precedente \n";

        System.out.println(menu);        

        System.out.print("Digita qui --> ");
        String valoreIn = sc.nextLine();
        
        this.gestisciTerzaScelta(valoreIn);
	}
	

	
	public void gestisciPrimaScelta(String s){
		
		if( s.equals("1") ) {
			
			String username = this.credenzialeUsername();
			if ( verificaInput(username)==false ){
				this.primaScelta();
				return;
			}
		
			Utente utente = this.us.verificaUsername(username);
			if ( utente==null ) {
				System.err.print("\nusername non presente nel DB, riprova\n\n");
				this.primaScelta();
				return;
			}
			System.out.print("utente trovato\n");
			
			String password = this.credenzialePassword();
			if ( verificaInput(password)==false ){
				this.primaScelta();
				return;
			}
			
			if( this.us.logIn(password, utente.getHash(), utente.getSalt())==true ) {
				System.out.print("\nlogin eseguito correttamente\n");
				this.setUtenteAttuale(utente);
				this.secondaScelta();
				return;
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
		
			if ( this.us.verificaUsername(username)!=null ) {
				System.err.print("\nusername già presente nel DB, riprova\n\n");
				this.primaScelta();
				return;
			}
			System.out.print("usename disponibile\n");

			
			String password = this.credenzialePassword();
			if ( verificaInput(password)==false ){
				this.primaScelta();
				return;
			}
			
			if( this.us.registrazione(username, password)==true ) {
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
	
	
	public void gestisciSecondaScelta(String s) {
		
		if( s.equals("1") ) {
			List<VMRecord> risultato = vms.getAllVMDalDB();
			
		    System.out.print("\nRISULTATO:");
			for (VMRecord vm : risultato) {
		        System.out.print("\n"+vm);
			}
			System.out.print("\n");
			this.secondaScelta();
			return;
		}
		
		else if( s.equals("2") ) {
			this.vms.sincronizzaVM("tesi-petronio");
	        System.out.print("\noperazione eseguita con successo, il DB è aggiornato\n");
	        this.secondaScelta();
	        return;
		}
		else if( s.equals("3") ) {
			this.terzaScelta();
			return;
		}
		else if( s.equals("4") ) {
			this.primaScelta();
			return;
		}
		else {
			System.err.print("\ncomando non disponibile riprova\n");
			this.secondaScelta();
			return;
		}
	}
	
	
	public void gestisciTerzaScelta(String s) {
		
		if( s.equals("1") ) {
			this.gestisciCreazioneAutomazione();
	        System.out.print("\noperazione eseguita con successo\n");
			this.terzaScelta();
			return;
		}
		
		else if( s.equals("2") ) {
			List<Automazione> listaAutomazioni = this.as.gatAllAuomations();
			for( Automazione vTemp: listaAutomazioni ) {
		        System.out.print("\n"+vTemp);
			}
	        System.out.print("\n");
			this.terzaScelta();
			return;
		}
		
		else if( s.equals("3") ) {
			for( Automazione vTemp: this.as.getAutomazioniAbilitate() ) {
		        System.out.print("\n"+vTemp);
			}
	        System.out.print("\n");
			this.terzaScelta();
			return;
			
		}
		
		else if( s.equals("4") ) {
			System.out.print("\nquale vuoi eliminare (digita l'id) --> ");
	        String n = sc.nextLine();
	        this.as.eliminaAutomazione(Integer.parseInt(n));
	        this.terzaScelta();
	        return;
		}
		
		else if( s.equals("5") ) {
			this.gestisciModificaAutomazione();
			this.terzaScelta();
			return;
		}
		
		else if ( s.equals("6") ) {
			this.secondaScelta();
			return;
		}
		
		else {
			System.err.print("\ncomando non disponibile riprova \n");
			this.terzaScelta();
			return;}
	}
	
	
	public void gestisciCreazioneAutomazione() {
		System.out.print("\nper quale vm? \n");
		
		List<VMRecord> listaVM = this.vms.getAllVMDalDB();
		int n = 1;
		for ( VMRecord vTemp: listaVM ){
			System.out.print("\n"+n+")"+" "+vTemp);
			n++;
		}
		System.out.print("\n\ndigita qui la scelta --> ");
        String numVM = sc.nextLine();
        VMRecord vm = listaVM.get( Integer.parseInt(numVM)-1 );
        
       
        System.out.print("\n(1)accensione (2)spegnimento | quale scegli? --> ");
        String tipologiaIn = sc.nextLine();
        TipologiaOperazione tipologiaOut;
        
        if( tipologiaIn.equals("1") ) {
        	tipologiaOut = TipologiaOperazione.ACCENSIONE;
        }
        else {
        	tipologiaOut = TipologiaOperazione.SPEGNIMENTO;
        }
        
		System.out.print("\na che ora? --> ");
        String orario = sc.nextLine();
		
		System.out.print("\n(1)abilitata (2)disabilitata | quale scegli? --> ");
        String abilitazioneIn = sc.nextLine();
        boolean abilitazioneOut;
        
        if( abilitazioneIn.equals("1") ) {
        	abilitazioneOut = true;
        }
        else {
        	abilitazioneOut = false;
        }

        this.as.aggiungiAutomazione(this.getUtenteAttuale(), vm, tipologiaOut, orario, abilitazioneOut);
	}

	public void gestisciModificaAutomazione() {
		
		for( Automazione vTemp: this.as.gatAllAuomations() ) {
	        System.out.print("\n"+vTemp);
		}
        System.out.print("\n");
		System.out.print("\nper quale automazione? (digita l'id) --> ");
        String id = sc.nextLine();
        
		System.out.print("\ncosa vuoi modificare?  |  (1)orario  (2)abilitazione --> ");
        String n = sc.nextLine();
        
        if( n.equals("1") ) {
        	System.out.print("\nscegli il nuovo orario --> ");
            String ora = sc.nextLine();
            this.as.modificaOrario(Integer.parseInt(id), ora);
            return;
        }
        else if( n.equals("2") ) {
        	System.out.print("\n(0) per disabilitare, (1) per abilitare |  -->  ");
            String scelta = sc.nextLine();
            if( scelta.equals("0") ) {
            	this.as.modificaAbilitazione(Integer.parseInt(id), false);
            	return;
            }
            else {
            	this.as.modificaAbilitazione(Integer.parseInt(id), true);
            	return;
            }
        }
        else {
    		System.err.print("\noperazione non disponibile\n");
    		return;
        }
	}
	
	
	public Utente getUtenteAttuale() {
		return utenteAttuale;
	}
	public void setUtenteAttuale(Utente utenteAttuale) {
		this.utenteAttuale = utenteAttuale;
	}
	public boolean verificaInput(String s) {
		if( s.stripLeading().split(" ").length!=1 ) {
			System.err.print("\nerrore riprova, la stringa non può contenere spazi\n\n");
			return false;
		}
		return true;
	}
}
