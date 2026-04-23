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
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tesi.GUI.controllers.CreaAutomazioneController;

@Component @Profile("gui") @Lazy
public class CreaAutomazioneView {

	private VBox layout;
	private Label titolo;
	private HBox primaRigaInput, rigaBottoni, rigaData, rigaOra;
	private ComboBox<String> cbSceltaVM;
	
	private DatePicker dataPicker;
	private Spinner<Integer> spOra, spMinuto;
	private CheckBox chkUsaData, chkUsaOrario;
	
	private ComboBox<String> cbSceltaOperazione, cbSceltaAbilitazione;
	private Button btnAvanti, btnIndietro;
	private ListView<String> listaLog;
	
	@Autowired @Lazy
    private CreaAutomazioneController controller;
	
	public CreaAutomazioneView() {
		this.layout = new VBox(20);
        this.titolo = new Label();
        this.primaRigaInput = new HBox(15);
        this.rigaBottoni = new HBox(15);
        this.cbSceltaVM = new ComboBox<>();
        
        this.dataPicker = new DatePicker();
	    // Spinner per le ore (0-23) e minuti (0-59)
	    this.spOra = new Spinner<>(0, 23, 0);
	    this.spMinuto = new Spinner<>(0, 59, 0);
	    this.rigaData = new HBox(10);
	    this.rigaOra = new HBox(10);

	    
	    this.chkUsaData = new CheckBox("Data specifica");
	    this.chkUsaOrario = new CheckBox("Orario specifico");
	        
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
        this.rigaBottoni.setAlignment(Pos.CENTER);
        this.rigaData.setAlignment(Pos.CENTER);
        this.rigaOra.setAlignment(Pos.CENTER);

        
        this.cbSceltaVM.setPromptText("Seleziona una VM");
        
        this.dataPicker.setPromptText("Scegli data...");
        this.dataPicker.setDisable(true); // Spento di default
        
        this.spOra.setDisable(true);
        this.spMinuto.setDisable(true);
        this.spOra.setPrefWidth(70);
        this.spMinuto.setPrefWidth(70);
        Label lblData = new Label("Data:");
        Label lblOra = new Label("Ora (HH:mm):");
        
        // 2. Logica di attivazione (Binding)
        // Se il checkbox è selezionato, il campo si abilita
        this.dataPicker.disableProperty().bind(chkUsaData.selectedProperty().not());
        this.spOra.disableProperty().bind(chkUsaOrario.selectedProperty().not());
        this.spMinuto.disableProperty().bind(chkUsaOrario.selectedProperty().not());
        
        this.rigaData.getChildren().addAll(chkUsaData, lblData, dataPicker);
        this.rigaOra.getChildren().addAll(chkUsaOrario, lblOra, spOra, new Label(":"), spMinuto);
                
        this.cbSceltaOperazione.setPromptText("Seleziona un'operazione...");
        this.cbSceltaOperazione.getItems().addAll("ACCENSIONE", "SPEGNIMENTO");
        
        this.cbSceltaAbilitazione.setPromptText("Seleziona un'abilitazione...");
        this.cbSceltaAbilitazione.getItems().addAll("ABILITATA", "NON ABILITATA");
        
        this.primaRigaInput.getChildren().addAll(this.cbSceltaVM, this.cbSceltaOperazione, this.cbSceltaAbilitazione);
        
        this.btnAvanti.setDefaultButton(true);
        this.btnAvanti.setOnAction(e -> {
        	this.controller.creazione(this.cbSceltaVM.getValue(), this.cbSceltaOperazione.getValue(), this.cbSceltaAbilitazione.getValue());
        });
        this.btnIndietro.setOnAction(e -> {
            this.controller.indietro();
        });
        this.rigaBottoni.getChildren().addAll(this.btnIndietro, this.btnAvanti);

        this.layout.getChildren().addAll(this.titolo, this.primaRigaInput, this.rigaData, this.rigaOra, this.rigaBottoni, this.listaLog);

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
		this.cbSceltaAbilitazione.getSelectionModel().clearSelection();
		this.cbSceltaOperazione.getSelectionModel().clearSelection();
		this.cbSceltaVM.getSelectionModel().clearSelection();
		this.chkUsaData.setSelected(false);
		this.chkUsaOrario.setSelected(false);
		this.dataPicker.setValue(null);
		this.spOra.getValueFactory().setValue(0);
		this.spMinuto.getValueFactory().setValue(0);
	}
	
	public void addLog(String s) {
        this.listaLog.getItems().add(s);
    }
	
	public void preparaView(String titolo) {
        this.titolo.setText(titolo);
        this.cbSceltaVM.getItems().clear();
        this.cbSceltaVM.getItems().addAll(this.controller.listaVM());
        this.listaLog.getItems().clear();
        this.pulisciCampi();
	}
}
