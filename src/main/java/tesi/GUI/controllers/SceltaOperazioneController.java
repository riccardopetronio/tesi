package tesi.GUI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import tesi.GUI.Dati;
import tesi.GUI.Navigatore;
import tesi.GUI.views.SceltaOperazioneView;

@Component @Profile("gui") @Lazy
public class SceltaOperazioneController {
	
	@Autowired @Lazy
    private SceltaOperazioneView view;
	
	@Autowired @Lazy
	private Dati dati;
	
	public void gestisciScelta(String s){
		if( "MOSTRA LE AUTOMAZIONI ESISTENTI".equals(s) ) {
			
		}
		
		else if( "MOSTRA LE AUTOMAZIONI ATTIVE".equals(s) ) {
			
		}
		
		else if( "CREA NUOVA AUTOMAZIONE".equals(s) ) {
			Navigatore.mostraCreazioneAutomazione();
			return;
		}
		
		else if( "ELIMINA AUTOMAZIONE".equals(s) ) {
			
		}
		
		else if( "MODIFICA AUTOMAZIONE".equals(s) ) {
			
		}
		
		this.view.addLog("Devi selezionare un'operazione per poter andare avanti");
	}
	
	
	public void inizializzaSchermata() {
    	this.view.preparaView("Ciao " + this.dati.getUsername() + "! Quale operazione vuoi fare?");
	}

	public void indietro() {
		Navigatore.mostraAutenticazione();
	}
}
