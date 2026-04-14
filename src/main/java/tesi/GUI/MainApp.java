package tesi.GUI;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import javafx.application.Application;
import javafx.stage.Stage;
import tesi.TesiApplication;

public class MainApp extends Application {
    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        // Avvia Spring all'inizializzazione dell'app
        this.context = new SpringApplicationBuilder(TesiApplication.class)
        		.profiles("gui")
        		.run();
    }

    @Override
    public void start(Stage primaryStage) {
        // Inizializza il navigatore e mostra la prima videata
    	Navigatore.setContext(context);
    	Navigatore.setStage(primaryStage);
    	Navigatore.mostraLogin(); 
    }

    @Override
    public void stop() {
        this.context.close();
    }
}
