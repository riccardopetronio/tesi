package tesi.GUI.views;

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
import tesi.GUI.controllers.CreaAutomazioneController;

@Component @Profile("gui") @Lazy
public class CreaAutomazioneView {

	private VBox layout;
	private Label titolo;
	private HBox primaRigaInput, rigaBottoni, rigaData, rigaOra;
	private HBox boxSceltaVM, boxSceltaOperazione, boxSceltaAbilitazione;
	private ComboBox<String> cbSceltaVM;
	private Label lblErroreVM, lblErroreOperazione, lblErroreAbilitazione, lblErroreData, lblErroreGiornoSettimana, lblErroreOra, lblEsito;
	
	private DatePicker dataPicker;
	private ComboBox<String> cbGiornoSettimana;
	private Spinner<Integer> spOra, spMinuto;
	private CheckBox chkUsaData, chkUsaGiornoSettimana, chkUsaOrario;
	
	private ComboBox<String> cbSceltaOperazione, cbSceltaAbilitazione;
	private Button btnAvanti, btnIndietro;
	
	@Autowired @Lazy
    private CreaAutomazioneController controller;
	
	public CreaAutomazioneView() {
		this.layout = new VBox(20);
        this.titolo = new Label();
        this.primaRigaInput = new HBox(15);
        this.rigaBottoni = new HBox(15);
        this.rigaData = new HBox(10);
        this.rigaOra = new HBox(10);
        this.boxSceltaVM = new HBox(8);
        this.boxSceltaOperazione = new HBox(8);
        this.boxSceltaAbilitazione = new HBox(8);
        this.cbSceltaVM = new ComboBox<>();
        this.lblErroreVM = new Label();
        this.lblErroreOperazione = new Label();
        this.lblErroreAbilitazione = new Label();
        this.lblErroreData = new Label();
        this.lblErroreGiornoSettimana = new Label();
        this.lblErroreOra = new Label();
        this.lblEsito = new Label();
        
        this.dataPicker = new DatePicker();
        this.cbGiornoSettimana = new ComboBox<>();
	    this.spOra = new Spinner<>(0, 23, 0);
	    this.spMinuto = new Spinner<>(0, 59, 0);

	    this.chkUsaData = new CheckBox("Data specifica");
	    this.chkUsaGiornoSettimana = new CheckBox("Giorno settimana");
	    this.chkUsaOrario = new CheckBox("Orario specifico");
	        
        this.cbSceltaOperazione = new ComboBox<>();
        this.cbSceltaAbilitazione = new ComboBox<>();
        this.btnAvanti = new Button("AVANTI");
        this.btnIndietro = new Button("INDIETRO");
	}
	
	@PostConstruct  
    public void init() {
        this.layout.setAlignment(Pos.TOP_CENTER);
        this.titolo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        this.primaRigaInput.setAlignment(Pos.CENTER);
        this.rigaBottoni.setAlignment(Pos.CENTER);
        this.rigaData.setAlignment(Pos.CENTER);
        this.rigaOra.setAlignment(Pos.CENTER);
        this.boxSceltaVM.setAlignment(Pos.CENTER_LEFT);
        this.boxSceltaOperazione.setAlignment(Pos.CENTER_LEFT);
        this.boxSceltaAbilitazione.setAlignment(Pos.CENTER_LEFT);

        this.impostaStileErrore(this.lblErroreVM);
        this.impostaStileErrore(this.lblErroreOperazione);
        this.impostaStileErrore(this.lblErroreAbilitazione);
        this.impostaStileErrore(this.lblErroreData);
        this.impostaStileErrore(this.lblErroreGiornoSettimana);
        this.impostaStileErrore(this.lblErroreOra);
        this.lblEsito.setStyle("-fx-text-fill: #188038;");
        
        this.cbSceltaVM.setPromptText("Seleziona una VM");
        
        this.dataPicker.setPromptText("Scegli data...");
        this.dataPicker.setDisable(true);
        this.cbGiornoSettimana.setPromptText("Scegli giorno...");
        this.cbGiornoSettimana.getItems().addAll("LUNEDI", "MARTEDI", "MERCOLEDI", "GIOVEDI", "VENERDI", "SABATO", "DOMENICA");
        this.cbGiornoSettimana.setDisable(true);
        
        this.spOra.setDisable(true);
        this.spMinuto.setDisable(true);
        this.spOra.setPrefWidth(70);
        this.spMinuto.setPrefWidth(70);
        Label lblData = new Label("Data:");
        Label lblOra = new Label("Ora (HH:mm):");
        
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
        
        this.rigaData.getChildren().addAll(chkUsaData, lblData, dataPicker, this.lblErroreData, chkUsaGiornoSettimana, this.cbGiornoSettimana, this.lblErroreGiornoSettimana);
        this.rigaOra.getChildren().addAll(chkUsaOrario, lblOra, spOra, new Label(":"), spMinuto, this.lblErroreOra);
                
        this.cbSceltaOperazione.setPromptText("Seleziona un'operazione...");
        this.cbSceltaOperazione.getItems().addAll("ACCENSIONE", "SPEGNIMENTO");
        
        this.cbSceltaAbilitazione.setPromptText("Seleziona un'abilitazione...");
        this.cbSceltaAbilitazione.getItems().addAll("ABILITATA", "NON ABILITATA");
        
        this.boxSceltaVM.getChildren().addAll(this.cbSceltaVM, this.lblErroreVM);
        this.boxSceltaOperazione.getChildren().addAll(this.cbSceltaOperazione, this.lblErroreOperazione);
        this.boxSceltaAbilitazione.getChildren().addAll(this.cbSceltaAbilitazione, this.lblErroreAbilitazione);
        this.primaRigaInput.getChildren().addAll(this.boxSceltaVM, this.boxSceltaOperazione, this.boxSceltaAbilitazione);
        
        this.btnAvanti.setDefaultButton(true);
        this.btnAvanti.setOnAction(e -> {
        	this.clearFeedback();
        	this.controller.creazione(this.cbSceltaVM.getValue(), this.cbSceltaOperazione.getValue(), this.cbSceltaAbilitazione.getValue());
        });
        this.btnIndietro.setOnAction(e -> {
            this.controller.indietro();
        });
        this.rigaBottoni.getChildren().addAll(this.btnIndietro, this.btnAvanti, this.lblEsito);

        this.layout.getChildren().addAll(this.titolo, this.primaRigaInput, this.rigaData, this.rigaOra, this.rigaBottoni);

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
		this.cbSceltaAbilitazione.getSelectionModel().clearSelection();
		this.cbSceltaOperazione.getSelectionModel().clearSelection();
		this.cbSceltaVM.getSelectionModel().clearSelection();
		this.chkUsaData.setSelected(false);
		this.chkUsaGiornoSettimana.setSelected(false);
		this.chkUsaOrario.setSelected(false);
		this.dataPicker.setValue(null);
		this.cbGiornoSettimana.getSelectionModel().clearSelection();
		this.spOra.getValueFactory().setValue(0);
		this.spMinuto.getValueFactory().setValue(0);
	}
	
	public void preparaView(String titolo) {
        this.titolo.setText(titolo);
        this.cbSceltaVM.getItems().clear();
        this.cbSceltaVM.getItems().addAll(this.controller.listaVM());
        this.clearFeedback();
        this.pulisciCampi();
	}

	public void showErroreVM(String messaggio) {
		this.cbSceltaVM.setStyle("-fx-border-color: #d93025; -fx-border-width: 1;");
		this.lblErroreVM.setText(messaggio);
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
		this.cbSceltaVM.setStyle("");
		this.cbSceltaOperazione.setStyle("");
		this.cbSceltaAbilitazione.setStyle("");
		this.dataPicker.setStyle("");
		this.cbGiornoSettimana.setStyle("");
		this.spOra.setStyle("");
		this.spMinuto.setStyle("");
		this.lblErroreVM.setText("");
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
