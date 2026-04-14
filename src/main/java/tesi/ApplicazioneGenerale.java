package tesi;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class ApplicazioneGenerale {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ApplicazioneGenerale.class);

    public static void main(String[] args) {
    	ConfigurableApplicationContext context = SpringApplication.run(TesiApplication.class, args);
    	log.info("--- Sistema Avviato Correttamente ---");
    	context.getBean(GestoreComandiUtente.class).primaScelta();
    }
}
