package tesi.GUI.views;

import java.util.List;
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
import tesi.GUI.controllers.CreaAutomazioneController;
import tesi.vm.VMRecord;

@Component @Profile("gui") @Lazy
public class CreaAutomazioneView {

	private VBox layout;
	private Label titolo;
	private HBox primaRigaInput;
	private HBox secondaRigaInput;
	private ComboBox<String> cbSceltaVM;
	private ComboBox<String> cbSceltaOperazione;
	private ComboBox<String> cbSceltaAbilitazione;
	private Button btnAvanti;
	private Button btnIndietro;
	private ListView<String> listaLog;
	
	@Autowired @Lazy
    private CreaAutomazioneController controller;
	
	public CreaAutomazioneView() {
		this.layout = new VBox(20);
        this.titolo = new Label();
        this.primaRigaInput = new HBox(15);
        this.secondaRigaInput = new HBox(15);
        this.cbSceltaVM = new ComboBox<>();
        this.cbSceltaOperazione = new ComboBox<>();
        this.cbSceltaAbilitazione = new ComboBox<>();
        this.btnAvanti = new Button("AVANTI");
        this.btnIndietro = new Button("INDIETRO");
        this.listaLog = new ListView<>();
	}
	
	@PostConstruct  
    public void init() {
        this.layout.setAlignment(Pos.TOP_CENTER);
        this.titolo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        this.primaRigaInput.setAlignment(Pos.CENTER);
        this.secondaRigaInput.setAlignment(Pos.CENTER);
        
        this.cbSceltaVM.getItems().addAll(this.controller.listaVM());
        this.cbSceltaOperazione.getItems().addAll("ACCENSIONE", "SPEGNIMENTO");
        this.cbSceltaAbilitazione.getItems().addAll("ABILITATA", "NON ABILITATA");
        
        this.primaRigaInput.getChildren().addAll(this.cbSceltaVM, this.cbSceltaOperazione, this.cbSceltaAbilitazione);
        
        this.btnAvanti.setOnAction(e -> {
        	this.controller.creazione(this.cbSceltaVM.getValue(), this.cbSceltaOperazione.getValue(), this.cbSceltaAbilitazione.getValue());
        });
        this.btnIndietro.setOnAction(e -> {
            this.controller.indietro();
        });
        this.secondaRigaInput.getChildren().addAll(this.btnIndietro, this.btnAvanti);

        this.layout.getChildren().addAll(this.titolo, this.primaRigaInput, this.secondaRigaInput);

	}
	
	public Parent asParent() {
        return this.layout;
    }
	
	public void pulisciCampi() {
		this.cbSceltaAbilitazione.getSelectionModel().clearSelection();
		this.cbSceltaOperazione.getSelectionModel().clearSelection();
		this.cbSceltaVM.getSelectionModel().clearSelection();
	}
	
	public void addLog(String s) {
        this.listaLog.getItems().add(s);
    }
	
	public void preparaView(String titolo) {
        this.titolo.setText(titolo);
        this.pulisciCampi();
	}
}
