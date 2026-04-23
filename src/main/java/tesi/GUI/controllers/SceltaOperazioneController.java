package tesi.GUI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import tesi.GUI.Dati;
import tesi.GUI.Navigatore;
import tesi.GUI.views.SceltaOperazioneView;
import tesi.automation.Automazione;
import tesi.automation.AutomazioneService;

@Component @Profile("gui") @Lazy
public class SceltaOperazioneController {
	
	@Autowired @Lazy
    private SceltaOperazioneView view;
	
	@Autowired @Lazy
	private Dati dati;
	
	@Autowired @Lazy
	private AutomazioneService as;
	
	public void gestisciScelta(String s){
		if( "MOSTRA LE AUTOMAZIONI ESISTENTI".equals(s) ) {
			for( Automazione vTemp: this.as.getAllAuotomations() ) {
				this.view.addLog(vTemp.toString());
			}
			this.view.addLog("");
			this.view.pulisci();
			return;
		}
		
		else if( "MOSTRA LE AUTOMAZIONI ATTIVE".equals(s) ) {
			for( Automazione vTemp: this.as.getAutomazioniAbilitate() ) {
				this.view.addLog(vTemp.toString());
			}
			this.view.addLog("");
			this.view.pulisci();
			return;
		}
		
		else if( "CREA NUOVA AUTOMAZIONE".equals(s) ) {
			Navigatore.mostraCreazioneAutomazione();
			return;
		}
		
		else if( "ELIMINA AUTOMAZIONE".equals(s) ) {
			Navigatore.mostraEliminazioneAutomazione();
			return;
		}
		
		else if( "MODIFICA AUTOMAZIONE".equals(s) ) {
			Navigatore.mostraModificaAutomazione();
			return;
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
