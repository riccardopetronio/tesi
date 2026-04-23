package tesi.quartz;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tesi.automation.Automazione;
import tesi.automation.AutomazioneRepository;

@Component
public class StartupRescheduler implements CommandLineRunner {

    @Autowired
    private AutomazioneRepository ar;
    @Autowired
    private QuartzService quartzService;

    @Override
    public void run(String... args) throws SchedulerException {
        List<Automazione> tutte = ar.findAll(); 
        Set<Integer> idAutomazioni = new HashSet<Integer>();
        
        // Riconcilio Quartz con lo stato reale delle automazioni salvate nel DB.
        for (Automazione a : tutte) {
            idAutomazioni.add(a.getId_automazione());
            try {
                quartzService.sincronizzaAutomazione(a);
            } catch (Exception e) {
            	System.err.print("\n\nerrore automazione  "+e);
            }
        }

        quartzService.eliminaJobOrfani(idAutomazioni);
    }
}
