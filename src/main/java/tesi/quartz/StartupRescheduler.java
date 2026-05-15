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
    public void run(String... args) {
        List<Automazione> tutte;
        try {
            tutte = ar.findAll();
        } catch (Exception e) {
            System.err.print("\n\nerrore nel recupero delle automazioni per Quartz  " + e);
            return;
        }

        Set<Integer> idAutomazioni = new HashSet<Integer>();

        try {
            quartzService.svuotaQuartz();
        } catch (SchedulerException e) {
            System.err.print("\n\nerrore nella pulizia delle tabelle Quartz  " + e);
            return;
        }

        // Riconcilio Quartz con lo stato reale delle automazioni salvate nel DB.
        for (Automazione a : tutte) {
            idAutomazioni.add(a.getId_automazione());
            try {
                quartzService.sincronizzaAutomazione(a);
            } catch (SchedulerException e) {
            	System.err.print("\n\nerrore automazione  "+e);
                return;
            }
        }

        try {
            quartzService.eliminaJobOrfani(idAutomazioni);
        } catch (SchedulerException e) {
            System.err.print("\n\nerrore eliminazione job Quartz orfani  " + e);
            return;
        }

        try {
            quartzService.avviaScheduler();
        } catch (SchedulerException e) {
            System.err.print("\n\nerrore avvio scheduler Quartz  " + e);
        }
    }
}
