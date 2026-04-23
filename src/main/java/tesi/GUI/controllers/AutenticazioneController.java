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

	public void gestisciAutenticazione(String user, String scelta) {
		Utente u = this.us.verificaUsername(user);
		
		if( "LOG-IN".equals(scelta) ) {
			if( u==null ) {
				this.view.addLog("username non presente, riprova");
				this.view.reSetUsername();
				return;
			}
			this.dati.setPrimaScelta(scelta);
			this.dati.setUtente(u);
			this.dati.setUsername(user);
			Navigatore.mostraInserimentoPassword();
			return;
		}
		
		if( "REGISTRAZIONE".equals(scelta) ) {
			if( u!=null ) {
				this.view.addLog("username già presente, riprova");
				this.view.reSetUsername();
				return;
			}
			if( verificaInput(user)==false ) {
				this.view.addLog("lo username non può contenere spazi, riprova");
				this.view.reSetUsername();
				return;
			}
			this.dati.setPrimaScelta(scelta);
			this.dati.setUsername(user);
			Navigatore.mostraInserimentoPassword();
			return;
		}
		this.view.addLog("devi scegliere un'opzione");
		this.view.reSetUsername();
	}
	
	
	
	public boolean verificaInput(String s) {
		if( s.stripLeading().split(" ").length!=1 ) {
			//System.err.print("\nerrore riprova, la stringa non può contenere spazi\n\n");
			return false;
		}
		return true;
	}
	
}
