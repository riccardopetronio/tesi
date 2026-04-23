package tesi.GUI.controllers;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tesi.GUI.Dati;
import tesi.GUI.Navigatore;
import tesi.GUI.views.ModificaAutomazioneView;
import tesi.automation.Automazione;
import tesi.automation.AutomazioneService;
import tesi.automation.TipologiaOperazione;

@Component @Profile("gui") @Lazy
public class ModificaAutomazioneController {

	@Autowired @Lazy
    private ModificaAutomazioneView view;
	
	@Autowired @Lazy
	private AutomazioneService as;
	
	@Autowired @Lazy
	private Dati dati;
	
	public void gestisciModifica(String automazioneIn, String tipoOperazioneIn, String abilitazioneIn) {
		if( automazioneIn==null || tipoOperazioneIn==null || abilitazioneIn==null ) {
			this.view.addLog("riempi tutti i campi");
			this.view.pulisciCampi();
			return;
		}
		
		Automazione automazione = this.getAutomazioneSelezionata(automazioneIn);
		if( automazione==null ) {
			this.view.addLog("automazione non trovata");
			this.view.pulisciCampi();
			return;
		}
		
		TipologiaOperazione tipologiaOut;
		if( tipoOperazioneIn.equals("ACCENSIONE") ) {
			tipologiaOut = TipologiaOperazione.ACCENSIONE;
		}
		else {
			tipologiaOut = TipologiaOperazione.SPEGNIMENTO;
		}
		
		boolean abilitata = false;
		if( abilitazioneIn.equals("ABILITATA") ) {
			abilitata = true;
		}
		
		String orarioOut = automazione.getOrario();
		if( this.view.getChkUsaOrario().isSelected() ) {
			if( this.view.getChkUsaData().isSelected() && this.view.getDataPicker().getValue()==null ) {
				this.view.addLog("se selezioni la data, devi anche sceglierla");
				return;
			}
			orarioOut = getCronExpression();
		}
		
		this.as.modificaAutomazione(automazione.getId_automazione(), tipologiaOut, orarioOut, abilitata);
		this.view.preparaView("" + this.dati.getUsername() + ", Quale vuoi modificare?");
		this.view.addLog("automazione modificata correttamente");
	}
	
	public List<String> getAutomazioni() {
		List<Automazione> lista = this.as.getAllAuotomations();
		List<String> risultato = new LinkedList<String>();
		for(Automazione vTemp: lista){
			risultato.add(vTemp.stampa());
		}
		return risultato;
	}
	
	public void caricaAutomazione(String automazioneIn) {
		if( automazioneIn==null ) {
			return;
		}
		Automazione automazione = this.getAutomazioneSelezionata(automazioneIn);
		if( automazione==null ) {
			this.view.addLog("automazione non trovata");
			return;
		}
		
		this.view.impostaTipologia(automazione.getTipologiaOperazione().name());
		this.view.impostaAbilitazione(automazione.isAbilitata());
		this.impostaDataEOrario(automazione.getOrario());
	}
	
	public void inizializzaSchermata() {
		this.view.preparaView("" + this.dati.getUsername() + ", Quale vuoi modificare?");
	}
	
	public void indietro() {
		Navigatore.mostraSceltaOperazione();
	}
	
	private Automazione getAutomazioneSelezionata(String automazioneIn) {
		Integer idAutomazione = this.estraiIdAutomazione(automazioneIn);
		if( idAutomazione==null ) {
			return null;
		}
		return this.as.getAutomazione(idAutomazione);
	}

	private Integer estraiIdAutomazione(String automazioneIn) {
		try {
			return Integer.parseInt(automazioneIn.split(" ")[1]);
		}
		catch (Exception e) {
			return null;
		}
	}
	
	private void impostaDataEOrario(String cronExpression) {
		this.view.impostaData(null);
		this.view.impostaOrario(null, null);
		if( cronExpression==null ) {
			return;
		}
		
		String[] parti = cronExpression.trim().split("\\s+");
		if( parti.length!=7 ) {
			return;
		}
		
		if( !"*".equals(parti[1]) && !"*".equals(parti[2]) ) {
			try {
				Integer minuto = Integer.parseInt(parti[1]);
				Integer ora = Integer.parseInt(parti[2]);
				this.view.impostaOrario(ora, minuto);
			}
			catch (NumberFormatException e) {
			}
		}
		
		if( !"*".equals(parti[3]) && !"*".equals(parti[4]) && !"*".equals(parti[6]) ) {
			try {
				LocalDate data = LocalDate.of(Integer.parseInt(parti[6]), Integer.parseInt(parti[4]), Integer.parseInt(parti[3]));
				this.view.impostaData(data);
			}
			catch (Exception e) {
			}
		}
	}
	
	private String getCronExpression() {
		String s = "00";
	    String m = "*";
	    String h = "*";
	    String giornoMese = "*";
	    String mese = "*";
	    String giornoSett = "?";
	    String anno = "*";
	    
	    if (this.view.getChkUsaOrario().isSelected()) {
	        m = String.format("%02d", this.view.getSpMinuto().getValue());
	        h = String.format("%02d", this.view.getSpOra().getValue());
	    }

	    if (this.view.getChkUsaData().isSelected() && this.view.getDataPicker().getValue() != null) {
	        LocalDate ld = this.view.getDataPicker().getValue();
	        giornoMese = String.valueOf(ld.getDayOfMonth());
	        mese = String.valueOf(ld.getMonthValue());
	        anno = String.valueOf(ld.getYear());
	    }

	    return String.format("%s %s %s %s %s %s %s", s, m, h, giornoMese, mese, giornoSett, anno);
	}
}
