package tesi.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tesi.automation.Automazione;

@Service
public class QuartzService {

    @Autowired
    private Scheduler scheduler; // L'oggetto principale di Quartz

    public void programmaAutomazione(Automazione automazione) throws SchedulerException {
        
    	// Dico a Quartz quale classe deve eseguire e gli passo l'ID dell'automazione da memorizzare nelle sue tabelle.
    	JobDetail jobDetail = JobBuilder.newJob(EsecuzioneAutomazioneJob.class)
                .withIdentity("Automazione_" + automazione.getId_automazione(), "GruppoVM")  // crea una chiave primaria per il Job all'interno di Quartz, associandoli una categoria "GruppoVM" per avere un raggruppamento
                .usingJobData("id_automazione", automazione.getId_automazione()) // IL COLLEGAMENTO: trasferisce in quartz l'automazione
                .build();

        // Definisco IL QUANDO farlo (Uso l'orario salvato nella tua tabella) (DEFINIZIONE DEL TRIGGER)
        CronTrigger trigger = TriggerBuilder.newTrigger()
             .withIdentity("Trigger_" + automazione.getId_automazione(), "GruppoVM") // Anche l'orario di esecuzione ha un suo identificativo per la specifica automazione
             .withSchedule( CronScheduleBuilder.cronSchedule(automazione.getOrario()) ) // trasferisce in quartz l'orario di eseguzione
             .build();
        
        // Se esiste già un trigger con lo stesso nome lo sostituiamo, altrimenti ne creiamo uno nuovo.
        if ( scheduler.checkExists(jobDetail.getKey()) ) {
            scheduler.deleteJob(jobDetail.getKey());
        }
        // Consegno tutto a Quartz
        scheduler.scheduleJob(jobDetail, trigger);
    }
    
    public void eliminaAutomazioneDaQuartz(Integer idAutomazione) {
        try {
            // Ricostruisco la "Targa" esatta del Job
            JobKey chiaveJob = JobKey.jobKey("Job_" + idAutomazione, "GruppoVM");
            
            // Se esiste in Quartz, lo elimino
            if (scheduler.checkExists(chiaveJob)) {
                scheduler.deleteJob(chiaveJob);
                System.out.println("[QUARTZ] Job dell'automazione " + idAutomazione + " eliminato con successo.");
            }
        } catch (SchedulerException e) {
            System.err.println("[ERRORE QUARTZ] Impossibile eliminare il job " + idAutomazione + e);
        }
    }
}
