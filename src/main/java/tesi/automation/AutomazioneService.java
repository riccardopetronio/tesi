package tesi.automation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tesi.user.Utente;
import tesi.vm.VMRecord;

@Service
public class AutomazioneService {
	
	@Autowired
	private AutomazioneRepository ar;
	
	public void aggiungiAutomazione(Utente u, VMRecord vm, TipologiaOperazione to,
			String orario, boolean abilitata) {
		Automazione vTemp = new Automazione(u, vm, to, orario, abilitata);
		this.ar.save(vTemp);
	}
	
	public List<Automazione> gatAllAuomations() {
		return this.ar.findAll();
	}

	public List<Automazione> getAutomazioniAbilitate(){
		return this.ar.findByAbilitata(true);
	}
	
	public void eliminaAutomazione(int i) {
		this.ar.deleteById(i);
	}
	
	public void modificaOrario(int id, String orario){
		Automazione vTemp = this.ar.findById(id).orElse(null);
		//dò per scontato che esista
		vTemp.setOrario(orario);
		this.ar.save(vTemp);
	}
	
	public void modificaAbilitazione(int id, boolean abilitazione){
		Automazione vTemp = this.ar.findById(id).orElse(null);
		//dò per scontato che esista
		vTemp.setAbilitata(abilitazione);;
		this.ar.save(vTemp);
	}
	
	public void modificaOrarioAndAbilitazioe(int id, String orario, boolean abilitazione){
		Automazione vTemp = this.ar.findById(id).orElse(null);
		//dò per scontato che esista
		vTemp.setOrario(orario);
		vTemp.setAbilitata(abilitazione);;
		this.ar.save(vTemp);
	}

}
