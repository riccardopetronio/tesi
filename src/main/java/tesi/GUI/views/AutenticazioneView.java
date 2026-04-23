package tesi.GUI.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tesi.GUI.controllers.AutenticazioneController;

@Component @Profile("gui") @Lazy
public class AutenticazioneView {
	
	private VBox layout;
	private Label titolo;
	private HBox rigaInput;
	private ComboBox<String> cbScelta;
	private TextField txtUsername;
	private Button btnAvanti;
	private ListView<String> listaLog;
	
	@Autowired @Lazy
    private AutenticazioneController controller;
	
	
    public AutenticazioneView() {
        this.layout = new VBox(20);
        this.titolo = new Label("Autenticati per poter interagire con le VM nel resource group!");
        this.rigaInput = new HBox(15);
        this.cbScelta = new ComboBox<>();
        this.txtUsername = new TextField();
        this.btnAvanti = new Button("AVANTI");
        this.listaLog = new ListView<>();  
    }
    
// l'interfaccia viene "montata" una volta sola. Quando navigo, il computer deve solo spostare un oggetto già pronto	       
    @PostConstruct  
    public void init() {
        this.layout.setAlignment(Pos.TOP_CENTER);
        this.titolo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        this.rigaInput.setAlignment(Pos.CENTER);
        
        this.cbScelta.setPromptText("Seleziona un'operazione...");
        this.cbScelta.getItems().addAll("LOG-IN", "REGISTRAZIONE");
        this.txtUsername.setPromptText("Inserisci username");
        this.rigaInput.getChildren().addAll(cbScelta, txtUsername);
        
        this.btnAvanti.setOnAction(e -> {
            this.controller.gestisciAutenticazione(this.txtUsername.getText(), this.cbScelta.getValue());
        });

        this.layout.getChildren().addAll(this.titolo, this.rigaInput, this.btnAvanti, this.listaLog);
    }
    
    public Parent asParent() {
        return this.layout;
    }
	
    public void addLog(String s) {
        this.listaLog.getItems().add(s);
    }
    
	public void reSetUsername() {
        this.txtUsername.clear();;
    }

    public void setSceltaIniziale() {
        this.cbScelta.getSelectionModel().clearSelection();
    }
    
    public void resetCampi() {
        this.reSetUsername();
        this.setSceltaIniziale();
    }
}
