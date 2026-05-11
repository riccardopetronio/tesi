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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tesi.GUI.controllers.EliminaAutomazioneController;

@Component @Profile("gui") @Lazy
public class EliminaAutomazioneView {

	private VBox layout;
	private Label titolo;
	private HBox rigaScelta;
	private HBox rigaInput;
	private ComboBox<String> cbScelta;
	private Button btnAvanti;
	private Button btnIndietro;
	private Label lblErroreScelta;
	private Label lblEsito;
	
	@Autowired @Lazy
    private EliminaAutomazioneController controller;

	public EliminaAutomazioneView() {
        this.layout = new VBox(20);
        this.titolo = new Label();
        this.rigaScelta = new HBox(15);
        this.rigaInput = new HBox(15);
        this.cbScelta = new ComboBox<>();
        this.btnAvanti = new Button("AVANTI");
        this.btnIndietro = new Button("INDIETRO");
        this.lblErroreScelta = new Label();
        this.lblEsito = new Label();
	}
 
	@PostConstruct  
    public void init() {
		this.layout.setAlignment(Pos.TOP_CENTER);
		this.titolo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        this.rigaScelta.setAlignment(Pos.CENTER);
        this.rigaInput.setAlignment(Pos.CENTER);
        this.lblErroreScelta.setStyle("-fx-text-fill: #d93025;");
        this.lblEsito.setStyle("-fx-text-fill: #188038;");
        
        this.cbScelta.setPromptText("Seleziona un'automazione");
        
        this.btnAvanti.setOnAction(e -> {
        	this.clearFeedback();
            this.controller.gestisciEliminazione(this.cbScelta.getValue());
        });
        
        this.btnIndietro.setOnAction(e -> {
            this.controller.indietro();
        });
        
        this.rigaScelta.getChildren().addAll(this.cbScelta, this.lblErroreScelta);
        this.rigaInput.getChildren().addAll(this.btnIndietro, this.btnAvanti, this.lblEsito);
        
        this.layout.getChildren().addAll(this.titolo, this.rigaScelta, this.rigaInput);
	}
	
	public Parent asParent() {
        return this.layout;
    }
	
	public ComboBox<String> getCbScelta() {
		return cbScelta;
	}
	
	public void preparaView(String titolo) {
        this.titolo.setText(titolo);
        this.cbScelta.getItems().clear();
        this.cbScelta.getItems().addAll(this.controller.getAutomazioni());
        this.cbScelta.getSelectionModel().clearSelection();
        this.clearFeedback();
    }
	
	public void showErroreScelta(String messaggio) {
		this.cbScelta.setStyle("-fx-border-color: #d93025; -fx-border-width: 1;");
		this.lblErroreScelta.setText(messaggio);
	}

	public void showEsito(String messaggio) {
		this.lblEsito.setText(messaggio);
	}

	public void clearFeedback() {
		this.cbScelta.setStyle("");
		this.lblErroreScelta.setText("");
		this.lblEsito.setText("");
	}
}
