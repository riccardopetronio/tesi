package tesi.GUI;

import javafx.scene.Scene;
import javafx.stage.Stage;
import tesi.GUI.views.AutenticazioneView;
import tesi.GUI.views.InserimentoPasswordView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Navigatore {
    private static Stage stage;
    private static ConfigurableApplicationContext context;

    public static void setStage(Stage stage) { Navigatore.stage = stage; }
    public static void setContext(ConfigurableApplicationContext context) { Navigatore.context = context; }

    public static void mostraLogin() {
        // ottengo la view dal contesto Spring (così può usare @Autowired)
    	AutenticazioneView view = context.getBean(AutenticazioneView.class);
        stage.setScene(new Scene(view.asParent(), 800, 600));
        stage.setTitle("Autenticazione");
        stage.show();
    }
    
    public static void mostraInserimentoPassword() {
        // ottengo la view dal contesto Spring (così può usare @Autowired)
    	InserimentoPasswordView view = context.getBean(InserimentoPasswordView.class);
        stage.setScene(new Scene(view.asParent(), 800, 600));
        stage.setTitle("Inserimento password");
        stage.show();
    }

}
