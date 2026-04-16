package tesi.GUI;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tesi.GUI.controllers.CreaAutomazioneController;
import tesi.GUI.controllers.SceltaOperazioneController;
import tesi.GUI.views.AutenticazioneView;
import tesi.GUI.views.CreaAutomazioneView;
import tesi.GUI.views.SceltaOperazioneView;
import tesi.GUI.views.InserimentoPasswordView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Navigatore {
    private static Stage stage;
    private static ConfigurableApplicationContext context;
    public static void setStage(Stage stage) { Navigatore.stage = stage; }
    public static void setContext(ConfigurableApplicationContext context) { Navigatore.context = context; }

    public static void mostraAutenticazione() {
        // ottengo la view dal contesto Spring (così può usare @Autowired)
    	AutenticazioneView view = context.getBean(AutenticazioneView.class);
    	view.resetCampi();
    	
        Parent root = view.asParent();

        if (stage.getScene() == null) {
            // Se è la primissima volta che apriamo l'app, creiamo la scena
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
        } else {
            // Se la scena esiste già (es. stiamo tornando indietro), 
            // cambiamo solo la radice invece di ricreare tutta la scena
            stage.getScene().setRoot(root);
        }
        
        stage.setTitle("Autenticazione");
        stage.show();
    }

    
    public static void mostraInserimentoPassword() {
    	InserimentoPasswordView view = context.getBean(InserimentoPasswordView.class);
    	view.reSetPassword();
    	Parent root = view.asParent();
    	
        if (stage.getScene() == null) {
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
        } else {

            stage.getScene().setRoot(root);
        }
        stage.setTitle("Inserimento password");
        stage.show();
    }
    
    public static void mostraSceltaOperazione() {
    	SceltaOperazioneController controller = context.getBean(SceltaOperazioneController.class);
    	SceltaOperazioneView view = context.getBean(SceltaOperazioneView.class);
    	Parent root = view.asParent();
    	
    	controller.inizializzaSchermata();
    	
    	if (stage.getScene() == null) {
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
        } else {

            stage.getScene().setRoot(root);
        }
    	stage.setTitle("Gestione automazioni");
        stage.show();
    }
    
    public static void mostraCreazioneAutomazione() {
    	CreaAutomazioneController controller = context.getBean(CreaAutomazioneController.class);
    	CreaAutomazioneView view = context.getBean(CreaAutomazioneView.class);
    	Parent root = view.asParent();
    	
    	controller.inizializzaSchermata();
    
    	if (stage.getScene() == null) {
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
        } else {

            stage.getScene().setRoot(root);
        }
    	stage.setTitle("Creazione automazione");
        stage.show();
    }

}
