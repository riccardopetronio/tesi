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
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tesi.GUI.controllers.ModificaAutomazioneController;

@Component @Profile("gui") @Lazy
public class ModificaAutomazioneView {
	
	private VBox layout;
	private Label titolo;
	private HBox rigaInput, secondaRigaInput, rigaData, rigaOra;
	private ComboBox<String> cbSceltaAutomazione, cbSceltaOperazione, cbSceltaAbilitazione;
	private DatePicker dataPicker;
	private Spinner<Integer> spOra, spMinuto;
	private CheckBox chkUsaData, chkUsaOrario;
	private Button btnAvanti, btnIndietro;
	private ListView<String> listaLog;
	
	@Autowired @Lazy
    private ModificaAutomazioneController controller;

	public ModificaAutomazioneView() {
        this.layout = new VBox(20);
        this.titolo = new Label();
        this.rigaInput = new HBox(15);
        this.secondaRigaInput = new HBox(15);
        this.rigaData = new HBox(10);
        this.rigaOra = new HBox(10);
        this.cbSceltaAutomazione = new ComboBox<>();
        this.cbSceltaOperazione = new ComboBox<>();
        this.cbSceltaAbilitazione = new ComboBox<>();
        this.dataPicker = new DatePicker();
        this.spOra = new Spinner<>(0, 23, 0);
        this.spMinuto = new Spinner<>(0, 59, 0);
        this.chkUsaData = new CheckBox("Data specifica");
        this.chkUsaOrario = new CheckBox("Orario specifico");
        this.btnAvanti = new Button("AVANTI");
        this.btnIndietro = new Button("INDIETRO");
        this.listaLog = new ListView<>();
	}
	
	@PostConstruct  
    public void init() {
		this.layout.setAlignment(Pos.TOP_CENTER);
		this.titolo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        this.rigaInput.setAlignment(Pos.CENTER);
        this.secondaRigaInput.setAlignment(Pos.CENTER);
        this.rigaData.setAlignment(Pos.CENTER);
        this.rigaOra.setAlignment(Pos.CENTER);
        
        this.cbSceltaAutomazione.setPromptText("Seleziona un'automazione");
        this.cbSceltaAutomazione.setOnAction(e -> {
        	this.controller.caricaAutomazione(this.cbSceltaAutomazione.getValue());
        });
        
        this.cbSceltaOperazione.setPromptText("Seleziona un'operazione...");
        this.cbSceltaOperazione.getItems().addAll("ACCENSIONE", "SPEGNIMENTO");
        
        this.cbSceltaAbilitazione.setPromptText("Seleziona un'abilitazione...");
        this.cbSceltaAbilitazione.getItems().addAll("ABILITATA", "NON ABILITATA");
        
        this.dataPicker.setPromptText("Scegli data...");
        this.dataPicker.setDisable(true);
        this.spOra.setDisable(true);
        this.spMinuto.setDisable(true);
        this.spOra.setPrefWidth(70);
        this.spMinuto.setPrefWidth(70);
	        this.dataPicker.disableProperty().bind(chkUsaData.selectedProperty().not());
	        this.spOra.disableProperty().bind(chkUsaOrario.selectedProperty().not());
	        this.spMinuto.disableProperty().bind(chkUsaOrario.selectedProperty().not());
        
        Label lblData = new Label("Data:");
        Label lblOra = new Label("Ora (HH:mm):");
        this.rigaData.getChildren().addAll(this.chkUsaData, lblData, this.dataPicker);
        this.rigaOra.getChildren().addAll(this.chkUsaOrario, lblOra, this.spOra, new Label(":"), this.spMinuto);
        
        this.rigaInput.getChildren().addAll(this.cbSceltaAutomazione, this.cbSceltaOperazione, this.cbSceltaAbilitazione);

        this.btnAvanti.setDefaultButton(true);
        this.btnAvanti.setOnAction(e -> {
            this.controller.gestisciModifica(this.cbSceltaAutomazione.getValue(), this.cbSceltaOperazione.getValue(), this.cbSceltaAbilitazione.getValue());
        });
        
        this.btnIndietro.setOnAction(e -> {
            this.controller.indietro();
        });
        
        this.secondaRigaInput.getChildren().addAll(this.btnIndietro, this.btnAvanti);
        
        this.layout.getChildren().addAll(this.titolo, this.rigaInput, this.rigaData, this.rigaOra, this.secondaRigaInput, this.listaLog);
	}
	
	public CheckBox getChkUsaData() {
		return chkUsaData;
	}

	public CheckBox getChkUsaOrario() {
		return chkUsaOrario;
	}

	public DatePicker getDataPicker() {
		return dataPicker;
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
		this.chkUsaOrario.setSelected(false);
		this.dataPicker.setValue(null);
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
		this.dataPicker.setValue(data);
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
        this.listaLog.getItems().clear();
        this.pulisciCampi();
    }
	
	public void addLog(String s) {
        this.listaLog.getItems().add(s);
    }
}
