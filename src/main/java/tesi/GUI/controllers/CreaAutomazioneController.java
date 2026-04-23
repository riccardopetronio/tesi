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
import tesi.GUI.views.CreaAutomazioneView;
import tesi.automation.AutomazioneService;
import tesi.automation.TipologiaOperazione;
import tesi.vm.VMRecord;
import tesi.vm.VirtualMachineService;

@Component @Profile("gui") @Lazy
public class CreaAutomazioneController {

	
	@Autowired @Lazy
    private CreaAutomazioneView view;
	
	@Autowired @Lazy
	private Dati dati;
	
	@Autowired @Lazy
	private VirtualMachineService vms;
	
	@Autowired @Lazy
	private AutomazioneService as;
	
	
	public void creazione(String nomeVM, String tipoOperazioneIn, String abilitazione) {
		if( nomeVM==null || tipoOperazioneIn==null || abilitazione==null ) {
			this.view.addLog("riempi tutti i campi");
			this.view.pulisciCampi();
			return;
		}
		if( !this.view.getChkUsaOrario().isSelected() ) {
			this.view.addLog("devi selezionare un orario per creare l'automazione");
			this.view.pulisciCampi();
			return;
		}
		if( this.view.getChkUsaData().isSelected() && this.view.getDataPicker().getValue()==null ) {
			this.view.addLog("se selezioni la data, devi anche sceglierla");
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
		if( abilitazione.equals("ABILITATA") ) {
			abilitata = true;
		}
		
		VMRecord vm = this.vms.getVMDalNome(nomeVM);
		if( vm==null ) {
			this.view.addLog("vm non trovata");
			this.view.pulisciCampi();
			return;
		}
		
		this.as.aggiungiAutomazione(this.dati.getUtente(), vm, tipologiaOut, getCronExpression(), abilitata);
		this.view.addLog("automazione creata correttamente");
		this.view.pulisciCampi();
	}
	
	public void indietro() {
		Navigatore.mostraSceltaOperazione();
	}
	
	public List<String> listaVM() {
		List<VMRecord> lista = this.vms.getAllVMDalDB();
		List<String> risultato = new LinkedList<String>();
		for(VMRecord vTemp: lista){
			risultato.add(vTemp.getNome());
		}
		return risultato;
	}
	
	public void inizializzaSchermata() {
        this.view.preparaView("" + this.dati.getUsername() + ", Procedi con la creazione");
	}
	
	public String getCronExpression() {
		// Valori di default: asterisco (significa "sempre/ogni")
	    String s = "00";
	    String m = "*";
	    String h = "*";
	    String giornoMese = "*";
	    String mese = "*";
	    String giornoSett = "?"; // Obbligatorio in Quartz se usi giornoMese
	    String anno = "*";
	    
	    // Se l'orario è abilitato, prendiamo i numeri
	    if (this.view.getChkUsaOrario().isSelected()) {
	        m = String.format("%02d", this.view.getSpMinuto().getValue());
	        h = String.format("%02d", this.view.getSpOra().getValue());
	    }

	    // Se la data è abilitata, prendiamo i valori del calendario
	    if (this.view.getChkUsaData().isSelected() && this.view.getDataPicker().getValue() != null) {
	        LocalDate ld = this.view.getDataPicker().getValue();
	        giornoMese = String.valueOf(ld.getDayOfMonth());
	        mese = String.valueOf(ld.getMonthValue());
	        anno = String.valueOf(ld.getYear());
	    }

	    // Componiamo la stringa finale per Quartz
	    // Formato: secondi minuti ore giornoMese mese giornoSett anno
	    return String.format("%s %s %s %s %s %s %s", s, m, h, giornoMese, mese, giornoSett, anno);
	}
	
}
