package tesi;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class testQarz implements CommandLineRunner {

    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- AVVIO TEST PROGRAMMAZIONE QUARTZ ---");

        try {
            // 1. definisco l'azione (usando la classe Job che abbiamo creato prima)
            JobDetail jobDetail = JobBuilder.newJob(TestStampaJob.class)
                    .withIdentity("testJobRapido", "gruppoTest")
                    .build();

            // 2.definisco il Trigger per scattare tra 5 secondi
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("testTriggerRapido", "gruppoTest")
                    .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.SECOND))
                    .build();

     
            scheduler.scheduleJob(jobDetail, trigger);

            System.out.println("Test pianificato con successo alle ore: " + new Date());
            System.out.println("Controlla la tabella QRTZ_TRIGGERS su DBeaver per conferma.");
            System.out.println("---------------------------------------");

        } catch (ObjectAlreadyExistsException e) {
            System.out.println("Nota: Il job di test esiste già nel database, Quartz lo recupererà all'avvio.");
        }
    }
}
