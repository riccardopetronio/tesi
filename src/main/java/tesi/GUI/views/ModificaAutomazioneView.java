package tesi.GUI.views;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tesi.GUI.controllers.ModificaAutomazioneController;

@Component @Profile("gui") @Lazy
public class ModificaAutomazioneView {
	
	private VBox layout;
	private Label titolo;
	private HBox rigaInput, secondaRigaInput, rigaData, rigaOra;
	private HBox boxSceltaAutomazione, boxSceltaOperazione, boxSceltaAbilitazione;
	private ComboBox<String> cbSceltaAutomazione, cbSceltaOperazione, cbSceltaAbilitazione;
	private DatePicker dataPicker;
	private ComboBox<String> cbGiornoSettimana;
	private Spinner<Integer> spOra, spMinuto;
	private CheckBox chkUsaData, chkUsaGiornoSettimana, chkUsaOrario;
	private Button btnAvanti, btnIndietro;
	private Label lblErroreAutomazione, lblErroreOperazione, lblErroreAbilitazione, lblErroreData, lblErroreGiornoSettimana, lblErroreOra, lblEsito;
	
	@Autowired @Lazy
    private ModificaAutomazioneController controller;

	public ModificaAutomazioneView() {
        this.layout = new VBox(20);
        this.titolo = new Label();
        this.rigaInput = new HBox(15);
        this.secondaRigaInput = new HBox(15);
        this.rigaData = new HBox(10);
        this.rigaOra = new HBox(10);
        this.boxSceltaAutomazione = new HBox(8);
        this.boxSceltaOperazione = new HBox(8);
        this.boxSceltaAbilitazione = new HBox(8);
        this.cbSceltaAutomazione = new ComboBox<>();
        this.cbSceltaOperazione = new ComboBox<>();
        this.cbSceltaAbilitazione = new ComboBox<>();
        this.dataPicker = new DatePicker();
        this.cbGiornoSettimana = new ComboBox<>();
        this.spOra = new Spinner<>(0, 23, 0);
        this.spMinuto = new Spinner<>(0, 59, 0);
        this.chkUsaData = new CheckBox("Data specifica");
        this.chkUsaGiornoSettimana = new CheckBox("Giorno settimana");
        this.chkUsaOrario = new CheckBox("Orario specifico");
        this.btnAvanti = new Button("AVANTI");
        this.btnIndietro = new Button("INDIETRO");
        this.lblErroreAutomazione = new Label();
        this.lblErroreOperazione = new Label();
        this.lblErroreAbilitazione = new Label();
        this.lblErroreData = new Label();
        this.lblErroreGiornoSettimana = new Label();
        this.lblErroreOra = new Label();
        this.lblEsito = new Label();
	}
	
	@PostConstruct  
    public void init() {
		this.layout.setAlignment(Pos.TOP_CENTER);
		this.titolo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        this.rigaInput.setAlignment(Pos.CENTER);
        this.secondaRigaInput.setAlignment(Pos.CENTER);
        this.rigaData.setAlignment(Pos.CENTER);
        this.rigaOra.setAlignment(Pos.CENTER);
        this.boxSceltaAutomazione.setAlignment(Pos.CENTER_LEFT);
        this.boxSceltaOperazione.setAlignment(Pos.CENTER_LEFT);
        this.boxSceltaAbilitazione.setAlignment(Pos.CENTER_LEFT);
        
        this.impostaStileErrore(this.lblErroreAutomazione);
        this.impostaStileErrore(this.lblErroreOperazione);
        this.impostaStileErrore(this.lblErroreAbilitazione);
        this.impostaStileErrore(this.lblErroreData);
        this.impostaStileErrore(this.lblErroreGiornoSettimana);
        this.impostaStileErrore(this.lblErroreOra);
        this.lblEsito.setStyle("-fx-text-fill: #188038;");
        
        this.cbSceltaAutomazione.setPromptText("Seleziona un'automazione");
        this.cbSceltaAutomazione.setOnAction(e -> {
        	this.clearFeedback();
        	this.controller.caricaAutomazione(this.cbSceltaAutomazione.getValue());
        });
        
        this.cbSceltaOperazione.setPromptText("Seleziona un'operazione...");
        this.cbSceltaOperazione.getItems().addAll("ACCENSIONE", "SPEGNIMENTO");
        
        this.cbSceltaAbilitazione.setPromptText("Seleziona un'abilitazione...");
        this.cbSceltaAbilitazione.getItems().addAll("ABILITATA", "NON ABILITATA");
        
        this.dataPicker.setPromptText("Scegli data...");
        this.dataPicker.setDisable(true);
        this.cbGiornoSettimana.setPromptText("Scegli giorno...");
        this.cbGiornoSettimana.getItems().addAll("LUNEDI", "MARTEDI", "MERCOLEDI", "GIOVEDI", "VENERDI", "SABATO", "DOMENICA");
        this.cbGiornoSettimana.setDisable(true);
        this.spOra.setDisable(true);
        this.spMinuto.setDisable(true);
        this.spOra.setPrefWidth(70);
        this.spMinuto.setPrefWidth(70);
        this.dataPicker.disableProperty().bind(chkUsaData.selectedProperty().not());
        this.cbGiornoSettimana.disableProperty().bind(chkUsaGiornoSettimana.selectedProperty().not());
        this.spOra.disableProperty().bind(chkUsaOrario.selectedProperty().not());
        this.spMinuto.disableProperty().bind(chkUsaOrario.selectedProperty().not());
        this.chkUsaData.setOnAction(e -> {
        	if( this.chkUsaData.isSelected() ) {
        		this.chkUsaGiornoSettimana.setSelected(false);
        	}
        });
        this.chkUsaGiornoSettimana.setOnAction(e -> {
        	if( this.chkUsaGiornoSettimana.isSelected() ) {
        		this.chkUsaData.setSelected(false);
        	}
        });
        
        Label lblData = new Label("Data:");
        Label lblOra = new Label("Ora (HH:mm):");
        this.rigaData.getChildren().addAll(this.chkUsaData, lblData, this.dataPicker, this.lblErroreData, this.chkUsaGiornoSettimana, this.cbGiornoSettimana, this.lblErroreGiornoSettimana);
        this.rigaOra.getChildren().addAll(this.chkUsaOrario, lblOra, this.spOra, new Label(":"), this.spMinuto, this.lblErroreOra);
        
        this.boxSceltaAutomazione.getChildren().addAll(this.cbSceltaAutomazione, this.lblErroreAutomazione);
        this.boxSceltaOperazione.getChildren().addAll(this.cbSceltaOperazione, this.lblErroreOperazione);
        this.boxSceltaAbilitazione.getChildren().addAll(this.cbSceltaAbilitazione, this.lblErroreAbilitazione);
        this.rigaInput.getChildren().addAll(this.boxSceltaAutomazione, this.boxSceltaOperazione, this.boxSceltaAbilitazione);

        this.btnAvanti.setDefaultButton(true);
        this.btnAvanti.setOnAction(e -> {
        	this.clearFeedback();
            this.controller.gestisciModifica(this.cbSceltaAutomazione.getValue(), this.cbSceltaOperazione.getValue(), this.cbSceltaAbilitazione.getValue());
        });
        
        this.btnIndietro.setOnAction(e -> {
            this.controller.indietro();
        });
        
        this.secondaRigaInput.getChildren().addAll(this.btnIndietro, this.btnAvanti, this.lblEsito);
        
        this.layout.getChildren().addAll(this.titolo, this.rigaInput, this.rigaData, this.rigaOra, this.secondaRigaInput);
	}
	
	public CheckBox getChkUsaData() {
		return chkUsaData;
	}

	public CheckBox getChkUsaGiornoSettimana() {
		return chkUsaGiornoSettimana;
	}

	public CheckBox getChkUsaOrario() {
		return chkUsaOrario;
	}

	public DatePicker getDataPicker() {
		return dataPicker;
	}

	public ComboBox<String> getCbGiornoSettimana() {
		return cbGiornoSettimana;
	}

	public Spinner<Integer> getSpOra() {
		return spOra;
	}

	public Spinner<Integer> getSpMinuto() {
		return spMinuto;
	}

	public Parent asParent() {
        return this.layout;
    }
	
	public void pulisciCampi() {
		this.cbSceltaAutomazione.getSelectionModel().clearSelection();
		this.cbSceltaAbilitazione.getSelectionModel().clearSelection();
		this.cbSceltaOperazione.getSelectionModel().clearSelection();
		this.chkUsaData.setSelected(false);
		this.chkUsaGiornoSettimana.setSelected(false);
		this.chkUsaOrario.setSelected(false);
		this.dataPicker.setValue(null);
		this.cbGiornoSettimana.getSelectionModel().clearSelection();
		this.spOra.getValueFactory().setValue(0);
		this.spMinuto.getValueFactory().setValue(0);
	}
	
	public void impostaTipologia(String tipologia) {
		this.cbSceltaOperazione.getSelectionModel().select(tipologia);
	}
	
	public void impostaAbilitazione(boolean abilitata) {
		this.cbSceltaAbilitazione.getSelectionModel().select(abilitata ? "ABILITATA" : "NON ABILITATA");
	}
	
	public void impostaData(LocalDate data) {
		if( data==null ) {
			this.chkUsaData.setSelected(false);
			this.dataPicker.setValue(null);
			return;
		}
		this.chkUsaData.setSelected(true);
		this.chkUsaGiornoSettimana.setSelected(false);
		this.dataPicker.setValue(data);
	}

	public void impostaGiornoSettimana(String giornoSettimana) {
		if( giornoSettimana==null ) {
			this.chkUsaGiornoSettimana.setSelected(false);
			this.cbGiornoSettimana.getSelectionModel().clearSelection();
			return;
		}
		this.chkUsaGiornoSettimana.setSelected(true);
		this.chkUsaData.setSelected(false);
		this.cbGiornoSettimana.getSelectionModel().select(giornoSettimana);
	}
	
	public void impostaOrario(Integer ora, Integer minuto) {
		if( ora==null || minuto==null ) {
			this.chkUsaOrario.setSelected(false);
			this.spOra.getValueFactory().setValue(0);
			this.spMinuto.getValueFactory().setValue(0);
			return;
		}
		this.chkUsaOrario.setSelected(true);
		this.spOra.getValueFactory().setValue(ora);
		this.spMinuto.getValueFactory().setValue(minuto);
	}
	
	public void preparaView(String titolo) {
        this.titolo.setText(titolo);
        this.cbSceltaAutomazione.getItems().clear();
        this.cbSceltaAutomazione.getItems().addAll(this.controller.getAutomazioni());
        this.clearFeedback();
        this.pulisciCampi();
    }

	public void showErroreAutomazione(String messaggio) {
		this.cbSceltaAutomazione.setStyle("-fx-border-color: #d93025; -fx-border-width: 1;");
		this.lblErroreAutomazione.setText(messaggio);
	}

	public void showErroreOperazione(String messaggio) {
		this.cbSceltaOperazione.setStyle("-fx-border-color: #d93025; -fx-border-width: 1;");
		this.lblErroreOperazione.setText(messaggio);
	}

	public void showErroreAbilitazione(String messaggio) {
		this.cbSceltaAbilitazione.setStyle("-fx-border-color: #d93025; -fx-border-width: 1;");
		this.lblErroreAbilitazione.setText(messaggio);
	}

	public void showErroreData(String messaggio) {
		this.dataPicker.setStyle("-fx-border-color: #d93025; -fx-border-width: 1;");
		this.lblErroreData.setText(messaggio);
	}

	public void showErroreGiornoSettimana(String messaggio) {
		this.cbGiornoSettimana.setStyle("-fx-border-color: #d93025; -fx-border-width: 1;");
		this.lblErroreGiornoSettimana.setText(messaggio);
	}

	public void showErroreOra(String messaggio) {
		this.spOra.setStyle("-fx-border-color: #d93025; -fx-border-width: 1;");
		this.spMinuto.setStyle("-fx-border-color: #d93025; -fx-border-width: 1;");
		this.lblErroreOra.setText(messaggio);
	}

	public void showEsito(String messaggio) {
		this.lblEsito.setText(messaggio);
	}

	public void clearFeedback() {
		this.cbSceltaAutomazione.setStyle("");
		this.cbSceltaOperazione.setStyle("");
		this.cbSceltaAbilitazione.setStyle("");
		this.dataPicker.setStyle("");
		this.cbGiornoSettimana.setStyle("");
		this.spOra.setStyle("");
		this.spMinuto.setStyle("");
		this.lblErroreAutomazione.setText("");
		this.lblErroreOperazione.setText("");
		this.lblErroreAbilitazione.setText("");
		this.lblErroreData.setText("");
		this.lblErroreGiornoSettimana.setText("");
		this.lblErroreOra.setText("");
		this.lblEsito.setText("");
	}

	private void impostaStileErrore(Label label) {
		label.setStyle("-fx-text-fill: #d93025;");
	}
}
