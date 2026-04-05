package tesi;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class TestStampaJob extends QuartzJobBean {

    // Questo è il metodo che Quartz "chiama" quando scatta il timer
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("\n******************************************");
        System.out.println("  Il test di Quartz ha funzionato! ");
        System.out.println("  Sono passati i 5 secondi    ");
        System.out.println("******************************************\n");
    }
}
