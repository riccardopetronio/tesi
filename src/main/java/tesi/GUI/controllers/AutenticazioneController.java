package tesi.GUI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import tesi.GUI.Navigatore;
import tesi.GUI.views.AutenticazioneView;
import tesi.user.Utente;
import tesi.user.UtenteService;

@Component
@Profile("gui")
public class AutenticazioneController {
	
	
	@Autowired
	@Lazy
    private AutenticazioneView view;
	
	@Autowired
	@Lazy
	private UtenteService us;

	public void gestisciAutenticazione(String user, String scelta) {
		Utente u = this.us.verificaUsername(user);
		
		if( "LOG-IN".equals(scelta) ) {
			if( u==null ) {
				this.view.addLog("username non presente, riprova");
				this.view.setUsername("");
				return;
			}
			Navigatore.mostraInserimentoPassword();
		}
		
		if( "REGISTRAZIONE".equals(scelta) ) {
			if( u!=null ) {
				this.view.addLog("username già presente, riprova");
				this.view.setUsername("");
				return;
			}
			Navigatore.mostraInserimentoPassword();
		}
		this.view.addLog("devi selezionare un'opzione");
	}
}
