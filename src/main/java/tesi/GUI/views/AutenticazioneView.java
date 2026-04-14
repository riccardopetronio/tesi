package tesi.GUI.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
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

@Component
@Profile("gui")
public class AutenticazioneView {
	
	private VBox layout;
	private Label titolo;
	private HBox rigaInput;
	private ComboBox<String> cbScelta;
	private TextField txtUsername;
	private Button btnInvio;
	private ListView<String> listaLog;
	
	@Autowired
    private AutenticazioneController controller;
	
    public AutenticazioneView() {
        this.layout = new VBox(20);
        this.titolo = new Label("Autenticati per poter interagire con le VM nel resource group!");
        this.rigaInput = new HBox(15);
        this.cbScelta = new ComboBox<>();
        this.txtUsername = new TextField();
        this.btnInvio = new Button("INVIO");
        this.listaLog = new ListView<>();
	}

	public Parent asParent() {
        this.layout.setAlignment(Pos.TOP_CENTER);
        this.titolo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        this.rigaInput.setAlignment(Pos.CENTER);
        
        this.cbScelta.getItems().addAll("LOG-IN", "REGISTRAZIONE");
        this.txtUsername.setPromptText("Inserisci username");
        this.rigaInput.getChildren().addAll(cbScelta, txtUsername);
        
        //this.listaLog.

        this.btnInvio.setOnAction(e -> {
            controller.gestisciAutenticazione(txtUsername.getText(), cbScelta.getValue());
        });

        this.layout.getChildren().addAll(titolo, rigaInput, btnInvio, listaLog);
        return layout;
    }
	
    public void setUsername(String testo) {
        this.txtUsername.setText(testo);
    }

    public void addLog(String s) {
        listaLog.getItems().add(s);
    }
    
    public void setSceltaIniziale() {
        this.cbScelta.getSelectionModel().selectFirst();
    }
}
