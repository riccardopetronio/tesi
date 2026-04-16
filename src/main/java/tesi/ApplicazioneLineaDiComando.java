package tesi;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class ApplicazioneLineaDiComando {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ApplicazioneLineaDiComando.class);

    public static void main(String[] args) {
    	ConfigurableApplicationContext context = SpringApplication.run(TesiApplication.class, args);
    	log.info("--- Sistema Avviato Correttamente ---");
    	context.getBean(GestoreComandiUtente.class).primaScelta();
    }
}
