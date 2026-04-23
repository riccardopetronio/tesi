package tesi.GUI.controllers;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tesi.GUI.Dati;
import tesi.GUI.Navigatore;
import tesi.GUI.views.EliminaAutomazioneView;
import tesi.automation.Automazione;
import tesi.automation.AutomazioneService;


@Component @Profile("gui") @Lazy
public class EliminaAutomazioneController {
	
	@Autowired @Lazy
    private EliminaAutomazioneView view;
	
	@Autowired @Lazy
	private AutomazioneService as;
	
	@Autowired @Lazy
	private Dati dati;

	public void gestisciEliminazione(String automazione) {
		if( automazione==null ) {
			this.view.addLog("devi selezionarne una");
			return;
		}
		String id = automazione.split(" ")[1];
		this.as.eliminaAutomazione(Integer.parseInt(id));
		this.view.addLog("eliminazione eseguita");
		this.inizializzaSchermata();
	}
	
	public List<String> getAutomazioni() {
		List<Automazione> lista = this.as.getAllAuotomations();
		List<String> risultato = new LinkedList<String>();
		for(Automazione vTemp: lista){
			risultato.add(vTemp.stampa());
		}
		return risultato;
	}
	
	public void inizializzaSchermata() {
		this.view.preparaView(""+ this.dati.getUsername() + ", Quale vuoi eliminare?");
	}
	
	
	public void indietro() {
		Navigatore.mostraSceltaOperazione();
	}
}
