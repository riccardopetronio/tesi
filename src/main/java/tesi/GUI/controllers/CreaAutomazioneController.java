package tesi.GUI.controllers;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import org.quartz.SchedulerException;
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
		if( nomeVM==null ) {
			this.view.pulisciCampi();
			this.view.showErroreVM("Seleziona una VM");
			return;
		}
		if( tipoOperazioneIn==null ) {
			this.view.pulisciCampi();
			this.view.showErroreOperazione("Seleziona un'operazione");
			return;
		}
		if( abilitazione==null ) {
			this.view.pulisciCampi();
			this.view.showErroreAbilitazione("Seleziona un'abilitazione");
			return;
		}
		if( !this.view.getChkUsaOrario().isSelected() ) {
			this.view.pulisciCampi();
			this.view.showErroreOra("Seleziona un orario");
			return;
		}
		if( this.view.getChkUsaData().isSelected() && this.view.getDataPicker().getValue()==null ) {
			this.view.pulisciCampi();
			this.view.showErroreData("Scegli la data");
			return;
		}
		if( this.view.getChkUsaGiornoSettimana().isSelected() && this.view.getCbGiornoSettimana().getValue()==null ) {
			this.view.pulisciCampi();
			this.view.showErroreGiornoSettimana("Scegli un giorno");
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
			this.view.pulisciCampi();
			this.view.showErroreVM("VM non trovata");
			return;
		}
		
		try {
			this.as.aggiungiAutomazione(this.dati.getUtente(), vm, tipologiaOut, getCronExpression(), abilitata);
		} catch (SchedulerException e) {
			this.view.showErroreOra("Errore nella programmazione dell'automazione");
			return;
		}
		this.view.pulisciCampi();
		this.view.showEsito("Automazione creata");
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
	        return String.format("%s %s %s %s %s %s %s", s, m, h, giornoMese, mese, giornoSett, anno);
	    }
	    
	    if( this.view.getChkUsaGiornoSettimana().isSelected() && this.view.getCbGiornoSettimana().getValue()!=null ) {
	    	giornoMese = "?";
	    	giornoSett = this.toQuartzDayOfWeek(this.view.getCbGiornoSettimana().getValue());
	    }

	    return String.format("%s %s %s %s %s %s %s", s, m, h, giornoMese, mese, giornoSett, anno);
	}

	private String toQuartzDayOfWeek(String giorno) {
		if( giorno==null ) {
			return "?";
		}
		return switch (giorno) {
			case "LUNEDI" -> "MON";
			case "MARTEDI" -> "TUE";
			case "MERCOLEDI" -> "WED";
			case "GIOVEDI" -> "THU";
			case "VENERDI" -> "FRI";
			case "SABATO" -> "SAT";
			case "DOMENICA" -> "SUN";
			default -> "?";
		};
	}
	
}
