package tesi.GUI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import tesi.GUI.Dati;
import tesi.GUI.Navigatore;
import tesi.GUI.views.InserimentoPasswordView;
import tesi.user.UtenteService;

@Component @Profile("gui") @Lazy
public class InserimentoPasswordController {
	
	@Autowired @Lazy
    private InserimentoPasswordView view;
	
	@Autowired @Lazy
	private UtenteService us;
	
	@Autowired @Lazy
	private Dati dati;
	
	public void gestisciInserimentoPassword(String password){
		
		if( this.dati.getPrimaScelta().equals("LOG-IN") ) {
			boolean risultato = this.us.logIn(password, this.dati.getUtente().getHash(), this.dati.getUtente().getSalt());
			if( risultato==true ) {
				Navigatore.mostraSceltaOperazione();
				return;
			}
			this.view.reSetPassword();
			this.view.addLog("Password errata, riprova");
		}
		
		else if( this.dati.getPrimaScelta().equals("REGISTRAZIONE") ) {
			boolean risultato = this.us.registrazione(this.dati.getUsername(), password);
			this.dati.setUtente( this.us.verificaUsername(this.dati.getUsername()) );
			if( risultato==true && verificaInput(password) ) {
				Navigatore.mostraSceltaOperazione();
				return;
			}
			this.view.reSetPassword();
			this.view.addLog("La password non può contenere spazi, riprova");
		}
	}
	
	public void indietro() {
		Navigatore.mostraAutenticazione();
	}
	
	public boolean verificaInput(String s) {
		if( s.stripLeading().split(" ").length!=1 ) {
			//System.err.print("\nerrore riprova, la stringa non può contenere spazi\n\n");
			return false;
		}
		return true;
	}

}
