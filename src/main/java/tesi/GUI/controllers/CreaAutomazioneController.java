package tesi.GUI.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tesi.GUI.Dati;
import tesi.GUI.Navigatore;
import tesi.GUI.views.CreaAutomazioneView;
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
	
	
	public void creazione(String nomeVM, String tipoOperazione, String abilitazione) {
		this.view.addLog("non ancora disponibile");
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
	
}
