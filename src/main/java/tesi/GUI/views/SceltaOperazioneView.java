package tesi.GUI.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tesi.GUI.controllers.SceltaOperazioneController;
import tesi.automation.Automazione;

@Component @Profile("gui") @Lazy
public class SceltaOperazioneView {
	
	private VBox layout;
	private Label titolo;
	private HBox rigaScelta;
	private HBox rigaInput;
	private ComboBox<String> cbScelta;
	private Button btnAvanti;
	private Button btnIndietro;
	private Label lblErroreScelta;
	private TableView<Automazione> tabellaAutomazioni;
	
	@Autowired @Lazy
    private SceltaOperazioneController controller;

	public SceltaOperazioneView() {
        this.layout = new VBox(20);
        this.titolo = new Label();
        this.rigaScelta = new HBox(15);
        this.rigaInput = new HBox(15);
        this.cbScelta = new ComboBox<>();
        this.btnAvanti = new Button("AVANTI");
        this.btnIndietro = new Button("INDIETRO");
        this.lblErroreScelta = new Label();
        this.tabellaAutomazioni = new TableView<>();
	}

	@PostConstruct  
    public void init() {
		this.layout.setAlignment(Pos.TOP_CENTER);
		this.titolo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        this.rigaScelta.setAlignment(Pos.CENTER);
        this.rigaInput.setAlignment(Pos.CENTER);
        this.lblErroreScelta.setStyle("-fx-text-fill: #d93025;");
        
        this.cbScelta.setPromptText("Seleziona un'operazione...");
        this.cbScelta.getItems().addAll("CREA NUOVA AUTOMAZIONE", "ELIMINA AUTOMAZIONE", "MODIFICA AUTOMAZIONE");
        
        this.configuraTabella();
        
        this.btnAvanti.setOnAction(e -> {
        	this.clearFeedback();
            this.controller.gestisciScelta(this.cbScelta.getValue());
        });
        
        this.btnIndietro.setOnAction(e -> {
            this.controller.indietro();
        });
        
        this.rigaScelta.getChildren().addAll(this.cbScelta, this.lblErroreScelta);
        this.rigaInput.getChildren().addAll(this.btnIndietro, this.btnAvanti);
        
        this.layout.getChildren().addAll(this.titolo, this.rigaScelta, this.rigaInput, this.tabellaAutomazioni);
	}

	public Parent asParent() {
        return this.layout;
    }
	
	public void pulisci() {
        this.cbScelta.getSelectionModel().clearSelection();
	}
	
	public void preparaView(String titolo) {
        this.titolo.setText(titolo);
        this.cbScelta.getSelectionModel().clearSelection();
        this.clearFeedback();
    }
	
	public void aggiornaTabella(java.util.List<Automazione> automazioni) {
		this.tabellaAutomazioni.getItems().setAll(automazioni);
	}

	public void showErroreScelta(String messaggio) {
		this.cbScelta.setStyle("-fx-border-color: #d93025; -fx-border-width: 1;");
		this.lblErroreScelta.setText(messaggio);
	}

	public void clearFeedback() {
		this.cbScelta.setStyle("");
		this.lblErroreScelta.setText("");
	}

	private void configuraTabella() {
		TableColumn<Automazione, String> colId = new TableColumn<>("ID");
		colId.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId_automazione())));
		
		TableColumn<Automazione, String> colUtente = new TableColumn<>("Utente");
		colUtente.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getUtente().getUsername()));
		
		TableColumn<Automazione, String> colVm = new TableColumn<>("VM");
		colVm.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getVm().getNome()));
		
		TableColumn<Automazione, String> colOperazione = new TableColumn<>("Operazione");
		colOperazione.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTipologiaOperazione().name()));
		
		TableColumn<Automazione, String> colOrario = new TableColumn<>("Orario");
		colOrario.setCellValueFactory(cell -> new SimpleStringProperty(this.controller.formattaOrario(cell.getValue().getOrario())));
		
		TableColumn<Automazione, String> colAbilitata = new TableColumn<>("Abilitata");
		colAbilitata.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().isAbilitata() ? "SI" : "NO"));
		
		this.tabellaAutomazioni.getColumns().addAll(colId, colUtente, colVm, colOperazione, colOrario, colAbilitata);
		this.tabellaAutomazioni.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.tabellaAutomazioni.setPlaceholder(new Label("Nessuna automazione disponibile."));
		this.tabellaAutomazioni.setPrefHeight(260);
	}
}
