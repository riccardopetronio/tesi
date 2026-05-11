package tesi.GUI.controllers;

import java.util.List;

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
		if( "CREA NUOVA AUTOMAZIONE".equals(s) ) {
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
		
		this.view.showErroreScelta("Seleziona un'operazione");
	}
	
	public void inizializzaSchermata() {
		this.view.preparaView("Ciao " + this.dati.getUsername() + "! Quale operazione vuoi fare?");
		this.view.aggiornaTabella(this.getAutomazioniDaMostrare());
	}

	public void indietro() {
		Navigatore.mostraAutenticazione();
	}

	public String formattaOrario(String cronExpression) {
		if( cronExpression==null || cronExpression.isBlank() ) {
			return "";
		}
		String[] parti = cronExpression.trim().split("\\s+");
		if( parti.length!=7 ) {
			return cronExpression;
		}
		
		String orario = cronExpression;
		if( !"*".equals(parti[1]) && !"*".equals(parti[2]) ) {
			orario = "alle " + parti[2] + ":" + parti[1];
		}
		
		if( !"*".equals(parti[3]) && !"*".equals(parti[4]) && !"*".equals(parti[6]) ) {
			try {
				return "il " + String.format("%02d", Integer.parseInt(parti[3])) + "/" 
						+ String.format("%02d", Integer.parseInt(parti[4])) + "/" + parti[6] + " " + orario;
			}
			catch (NumberFormatException e) {
				return cronExpression;
			}
		}
		
		if( !"?".equals(parti[5]) && !"*".equals(parti[5]) ) {
			return "ogni " + this.traduciGiornoSettimana(parti[5]) + " " + orario;
		}
		
		return "ogni giorno " + orario;
	}

	private List<Automazione> getAutomazioniDaMostrare() {
		return this.as.getAllAuotomations();
	}

	private String traduciGiornoSettimana(String giorno) {
		return switch (giorno.toUpperCase()) {
			case "MON" -> "lunedi";
			case "TUE" -> "martedi";
			case "WED" -> "mercoledi";
			case "THU" -> "giovedi";
			case "FRI" -> "venerdi";
			case "SAT" -> "sabato";
			case "SUN" -> "domenica";
			default -> giorno.toLowerCase();
		};
	}
}
