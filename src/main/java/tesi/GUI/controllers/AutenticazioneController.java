package tesi.GUI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import tesi.GUI.Dati;
import tesi.GUI.Navigatore;
import tesi.GUI.views.AutenticazioneView;
import tesi.user.Utente;
import tesi.user.UtenteService;

@Component @Profile("gui") @Lazy
public class AutenticazioneController {
	
	@Autowired @Lazy
    private AutenticazioneView view;
	
	@Autowired @Lazy
	private UtenteService us;
	
	@Autowired @Lazy
	private Dati dati;

	public void gestisciAutenticazione(String user, String password, String scelta) {
		Utente u = this.us.verificaUsername(user);
		
		if( "LOG-IN".equals(scelta) ) {
			if( u==null ) {
				this.view.resetCredenziali();
				this.view.showErroreUsername("Username non presente");
				return;
			}
			this.dati.setPrimaScelta(scelta);
			this.dati.setUtente(u);
			this.dati.setUsername(user);
			boolean risultato = this.us.logIn(password, u.getHash(), u.getSalt());
			if( risultato==true ) {
				Navigatore.mostraSceltaOperazione();
				return;
			}
			this.view.resetCredenziali();
			this.view.showErrorePassword("Password errata");
			return;
		}
		
		if( "REGISTRAZIONE".equals(scelta) ) {
			if( u!=null ) {
				this.view.resetCredenziali();
				this.view.showErroreUsername("Username gia presente");
				return;
			}
			if( verificaInput(user)==false ) {
				this.view.resetCredenziali();
				this.view.showErroreUsername("Username non valido");
				return;
			}
			this.dati.setPrimaScelta(scelta);
			this.dati.setUsername(user);
			if( verificaInput(password)==false ) {
				this.view.resetCredenziali();
				this.view.showErrorePassword("Password non valida");
				return;
			}
			boolean risultato = this.us.registrazione(user, password);
			if( risultato==true ) {
				this.dati.setUtente(this.us.verificaUsername(user));
				Navigatore.mostraSceltaOperazione();
				return;
			}
			this.view.resetCredenziali();
			this.view.showErrorePassword("Registrazione non riuscita");
			return;
		}
		this.view.resetCredenziali();
		this.view.showErroreScelta("Seleziona un'opzione");
	}
	
	public boolean verificaInput(String s) {
		if( s.stripLeading().split(" ").length!=1 ) {
			return false;
		}
		return true;
	}
	
}
