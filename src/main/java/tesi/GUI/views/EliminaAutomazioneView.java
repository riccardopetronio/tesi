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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tesi.GUI.controllers.EliminaAutomazioneController;


@Component @Profile("gui") @Lazy
public class EliminaAutomazioneView {

	private VBox layout;
	private Label titolo;
	private HBox rigaInput;
	private ComboBox<String> cbScelta;
	private Button btnAvanti;
	private Button btnIndietro;
	private ListView<String> listaLog;
	
	@Autowired @Lazy
    private EliminaAutomazioneController controller;

	public EliminaAutomazioneView() {
        this.layout = new VBox(20);
        this.titolo = new Label();
        this.rigaInput = new HBox(15);
        this.cbScelta = new ComboBox<>();
        this.btnAvanti = new Button("AVANTI");
        this.btnIndietro = new Button("INDIETRO");
        this.listaLog = new ListView<>();
	}
 
     
	@PostConstruct  
    public void init() {
		this.layout.setAlignment(Pos.TOP_CENTER);
		this.titolo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        this.rigaInput.setAlignment(Pos.CENTER);
        
        this.cbScelta.setPromptText("Seleziona un'automazione");
        
        this.btnAvanti.setOnAction(e -> {
            this.controller.gestisciEliminazione(this.cbScelta.getValue());
        });
        
        this.btnIndietro.setOnAction(e -> {
            this.controller.indietro();
        });
        
        this.rigaInput.getChildren().addAll(this.btnIndietro, this.btnAvanti);
        
        this.layout.getChildren().addAll(this.titolo, this.cbScelta, this.rigaInput, this.listaLog);
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
    }
	
	public void addLog(String s) {
        this.listaLog.getItems().add(s);
    }
}
