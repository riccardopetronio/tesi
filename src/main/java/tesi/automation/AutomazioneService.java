package tesi.automation;

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
		ar.save(vTemp);
	}

}
