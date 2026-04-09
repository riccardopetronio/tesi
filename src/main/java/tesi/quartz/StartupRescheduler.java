package tesi.quartz;

import java.util.List;

import org.quartz.Scheduler;
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
    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(String... args) throws SchedulerException {
    	scheduler.clear();
        List<Automazione> tutte = ar.findAll(); 
        
        // metto tutte le automazioni in Quartz, sarà poi il Job a decidere se eseguirle o meno in base al campo 'abilitata'.
        for (Automazione a : tutte) {
        	
            try {
                quartzService.programmaAutomazione(a);
            } catch (Exception e) {
            	System.err.print("\n\nerrore automazione  "+e);
            }
        }
    }
}
