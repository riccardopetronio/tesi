package tesi.automation;

import java.util.List;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import tesi.quartz.QuartzService;
import tesi.user.Utente;
import tesi.vm.AzureService;
import tesi.vm.VMRecord;
import tesi.vm.VirtualMachineService;

@Service
public class AutomazioneService {
	
	@Autowired
	private AutomazioneRepository ar;
	@Autowired
    private VirtualMachineService vms;
	@Autowired
	private QuartzService qs; // verrà spiegato dopo
	
	@Autowired @Lazy
	private AzureService as;
    
	@Transactional(rollbackOn = SchedulerException.class)
	public void aggiungiAutomazione(Utente u, VMRecord vm, TipologiaOperazione to,
			String orario, boolean abilitata) throws SchedulerException {
		
		Automazione vTemp = new Automazione(u, vm, to, orario, abilitata);
		this.ar.save(vTemp);
		this.qs.sincronizzaAutomazione(vTemp);
	}
	
	public List<Automazione> getAllAuotomations() {
		return this.ar.findAll();
	}

	public Automazione getAutomazione(int id) {
		return this.ar.findById(id).orElse(null);
	}

	public List<Automazione> getAutomazioniAbilitate(){
		return this.ar.findByAbilitata(true);
	}
	
	@Transactional(rollbackOn = SchedulerException.class)
	public void eliminaAutomazione(int i) throws SchedulerException {
		this.ar.deleteById(i);
		this.qs.eliminaAutomazioneDaQuartz(i);
	}
	
	@Transactional(rollbackOn = SchedulerException.class)
	public void modificaOrario(int id, String orario) throws SchedulerException {
		Automazione vTemp = this.ar.findById(id).orElse(null);
		//dò per scontato che esista
		vTemp.setOrario(orario);
		this.ar.save(vTemp);
		this.qs.sincronizzaAutomazione(vTemp);
	}
	
	@Transactional(rollbackOn = SchedulerException.class)
	public void modificaAbilitazione(int id, boolean abilitazione) throws SchedulerException {
		Automazione vTemp = this.ar.findById(id).orElse(null);
		//dò per scontato che esista
		vTemp.setAbilitata(abilitazione);;
		this.ar.save(vTemp);
		this.qs.sincronizzaAutomazione(vTemp);
	}
	
	@Transactional(rollbackOn = SchedulerException.class)
	public void modificaOrarioAndAbilitazioe(int id, String orario, boolean abilitazione) throws SchedulerException {
		Automazione vTemp = this.ar.findById(id).orElse(null);
		//dò per scontato che esista
		vTemp.setOrario(orario);
		vTemp.setAbilitata(abilitazione);;
		this.ar.save(vTemp);
		this.qs.sincronizzaAutomazione(vTemp);
	}

    @Transactional // esegue tutto o niente se avviene qualche errore
    public void eseguiAutomazione(int idAutomazione) {

        Automazione auto = this.ar.findById(idAutomazione).orElse(null);

        if (auto != null && auto.isAbilitata()) {
            VMRecord vm = auto.getVm();

            if ( auto.getTipologiaOperazione() == TipologiaOperazione.ACCENSIONE) {
                vm.setStato("accesa");
                this.as.accendiVirtualMachineSuAzure("tesi-petronio", vm.getNome());
            } else {
                vm.setStato("spenta");
                this.as.spegliVirtualMachineSuAzure("tesi-petronio", vm.getNome());
            }
            this.vms.salvaVirtualMachineSulDB(vm);
        }
    }
	
	@Transactional(rollbackOn = SchedulerException.class)
	public void modificaAutomazione(int id, TipologiaOperazione tipologiaOperazione, String orario, boolean abilitazione) throws SchedulerException {
		Automazione vTemp = this.ar.findById(id).orElse(null);
		//do per scontato che esista
		vTemp.setTipologiaOperazione(tipologiaOperazione);
		vTemp.setOrario(orario);
		vTemp.setAbilitata(abilitazione);
		this.ar.save(vTemp);
		this.qs.sincronizzaAutomazione(vTemp);
	}

}
