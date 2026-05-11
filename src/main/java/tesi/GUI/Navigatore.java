package tesi.GUI;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tesi.GUI.controllers.CreaAutomazioneController;
import tesi.GUI.controllers.EliminaAutomazioneController;
import tesi.GUI.controllers.ModificaAutomazioneController;
import tesi.GUI.controllers.SceltaOperazioneController;
import tesi.GUI.views.AutenticazioneView;
import tesi.GUI.views.CreaAutomazioneView;
import tesi.GUI.views.EliminaAutomazioneView;
import tesi.GUI.views.SceltaOperazioneView;
import tesi.GUI.views.ModificaAutomazioneView;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Navigatore {
    // Riferimento unico alla finestra principale dell'applicazione (Stage)
    private static Stage stage;
    // Contenitore di Spring per recuperare dinamicamente le View e i Controller (Bean) 
    private static ConfigurableApplicationContext context;
    // Inizializza la finestra su cui il navigatore effettuerà i cambi di scena
    public static void setStage(Stage stage) { Navigatore.stage = stage; }
    // Collega il contesto di Spring per permettere l'iniezione delle dipendenze nelle viste
    public static void setContext(ConfigurableApplicationContext context) { Navigatore.context = context; }

    
    private static void renderizza(Parent root, double width, double height, String titolo) {
        if (stage.getScene() == null) {
            // Prima creazione
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
        } else {
            // Cambio contenuto
            stage.getScene().setRoot(root);
            // AGGIORNAMENTO DIMENSIONI FORZATO
            stage.setWidth(width);
            stage.setHeight(height);
        }
        
        stage.setTitle(titolo); 
        stage.show();
    }
    
    public static void mostraAutenticazione() {
        AutenticazioneView view = context.getBean(AutenticazioneView.class);
        view.resetCampi();
        renderizza(view.asParent(), 800, 300, "Autenticazione"); // qui definisco anche il nome della schermata
    }

    public static void mostraSceltaOperazione() {
        SceltaOperazioneController controller = context.getBean(SceltaOperazioneController.class);
        SceltaOperazioneView view = context.getBean(SceltaOperazioneView.class);
        controller.inizializzaSchermata();
        
        renderizza(view.asParent(), 920, 460, "Gestione Automazioni");
    }
    
    public static void mostraCreazioneAutomazione() {
    	CreaAutomazioneController controller = context.getBean(CreaAutomazioneController.class);
    	CreaAutomazioneView view = context.getBean(CreaAutomazioneView.class);
    	
    	controller.inizializzaSchermata();
    
        renderizza(view.asParent(), 850, 380, "Creazione automazione");

    }

    
    public static void mostraEliminazioneAutomazione() {
    	EliminaAutomazioneController controller = context.getBean(EliminaAutomazioneController.class);
    	EliminaAutomazioneView view = context.getBean(EliminaAutomazioneView.class);
    	
    	controller.inizializzaSchermata();
    
        renderizza(view.asParent(), 800, 280, "Eliminazione automazione");

    }
    
    public static void mostraModificaAutomazione() {
    	ModificaAutomazioneController controller = context.getBean(ModificaAutomazioneController.class);
    	ModificaAutomazioneView view = context.getBean(ModificaAutomazioneView.class);
    	
    	controller.inizializzaSchermata();
    
        renderizza(view.asParent(), 1100, 420, "Modifica automazione");

    }
}
