package tesi.quartz;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import tesi.automation.AutomazioneService;

@Component
public class EsecuzioneAutomazioneJob extends QuartzJobBean {

    // uso il service per leggere il mio DB
    @Autowired
    private AutomazioneService as; 

    @Override	// questo è il codice che viene lanciato da quartz quando scocca l'ora X
    protected void executeInternal(JobExecutionContext context) {
    	// 1. RECUPERO DATI: Quartz passa il "bigliettino" (JobDataMap) 
        // che ho salvato nel database al momento della programmazione.
    	int idAutomazione = context.getMergedJobDataMap().getInt("id_automazione");
        
        // 2. Vado nella MIA tabella Automazioni e la eseguo se è abilitata
        as.eseguiAutomazione(idAutomazione);
    }
}
