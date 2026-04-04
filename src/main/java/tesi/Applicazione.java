package tesi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication // Questo "accende" Spring, Hibernate e la scansione dei pacchetti
public class Applicazione {

    public static void main(String[] args) {
    	// Questa riga è il ponte: carica .env e lo rende disponibile a Spring
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
    	
    	// Avvia il contesto di Spring
        SpringApplication.run(Applicazione.class, args);
    }

    @Bean
    
    public CommandLineRunner commandLineRunner(GestoreComandiUtente comandi) {
        return args -> {
            // Questo codice viene eseguito DOPO che Spring ha preparato tutto
            // e ha iniettato correttamente l'UtenteService dentro 'comandi'
            
            System.out.println("--- Sistema Avviato Correttamente ---");
            
            // Se vuoi scommentare la riga di Azure, dovrai iniettare anche VirtualMachineService
            // VirtualMachineService.salvaVirMacDaAzureAlDB("tesi-petronio");
            
            comandi.primaScelta();
        };
    }
}