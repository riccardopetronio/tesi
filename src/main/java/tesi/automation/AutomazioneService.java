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
	private QuartzService qs;
	@Autowired
	@Lazy
	private AzureService as;
	
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
    
    
	public void aggiungiAutomazione(Utente u, VMRecord vm, TipologiaOperazione to, String orario, boolean abilitata){
		
		Automazione vTemp = new Automazione(u, vm, to, orario, abilitata);
		this.ar.save(vTemp);
		try {
			this.qs.programmaAutomazione(vTemp);
		}
		catch (Exception e) {
        	System.err.print("\n\nerrore nella programmazione della automazione  "+vTemp + e);
		}
	}
	
	public List<Automazione> getAllAuotomations() {
		return this.ar.findAll();
	}

	public List<Automazione> getAutomazioniAbilitate(){
		return this.ar.findByAbilitata(true);
	}
	
	public void eliminaAutomazione(int i) {
		this.ar.deleteById(i);
		this.qs.eliminaAutomazioneDaQuartz(i);
	}
	
	public void modificaOrario(int id, String orario){
		Automazione vTemp = this.ar.findById(id).orElse(null);
		//dò per scontato che esista
		vTemp.setOrario(orario);
		this.ar.save(vTemp);
		try {
			this.qs.programmaAutomazione(vTemp);
		} catch (SchedulerException e) {
        	System.err.print("\n\nerrore nella programmazione della automazione  "+vTemp + e);
		}
	}
	
	public void modificaAbilitazione(int id, boolean abilitazione){
		Automazione vTemp = this.ar.findById(id).orElse(null);
		//dò per scontato che esista
		vTemp.setAbilitata(abilitazione);;
		this.ar.save(vTemp);
		try {
			this.qs.programmaAutomazione(vTemp);
		} catch (SchedulerException e) {
        	System.err.print("\n\nerrore nella programmazione della automazione  "+vTemp + e);
		}
	}
	
	public void modificaOrarioAndAbilitazioe(int id, String orario, boolean abilitazione){
		Automazione vTemp = this.ar.findById(id).orElse(null);
		//dò per scontato che esista
		vTemp.setOrario(orario);
		vTemp.setAbilitata(abilitazione);;
		this.ar.save(vTemp);
		try {
			this.qs.programmaAutomazione(vTemp);
		} catch (SchedulerException e) {
        	System.err.print("\n\nerrore nella programmazione della automazione  "+vTemp + e);
		}
	}
	
}
