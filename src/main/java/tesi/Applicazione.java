package tesi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import io.github.cdimascio.dotenv.Dotenv;
import tesi.vm.VirtualMachineService;


@SpringBootApplication // Questo "accende" Spring, Hibernate e la scansione dei pacchetti
public class Applicazione {
	
	// finale serve a specificare che l'oggetto logger non può essere sostituito o modificato mentre il programma è in eseguzione
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Applicazione.class);

    public static void main(String[] args) {
    	/* carica .env e lo rende disponibile a Spring
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
    	*/
    	
    	// Avvia il contesto di Spring
        SpringApplication.run(Applicazione.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(GestoreComandiUtente comandi) {
        return args -> {
            // Questo codice viene eseguito DOPO che Spring ha preparato tutto
            // e ha iniettato correttamente il gestore dentro 'comandi'
            log.info("--- Sistema Avviato Correttamente ---");

            /*
            VirtualMachineService virtualMachineService = new VirtualMachineService();
			virtualMachineService.salvaVirMacDaAzureAlDB("tesi-petronio");
            */
            
            comandi.primaScelta();
        };
    }
}