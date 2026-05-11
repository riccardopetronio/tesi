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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tesi.GUI.controllers.AutenticazioneController;

@Component @Profile("gui") @Lazy
public class AutenticazioneView {
	
	private VBox layout;
	private Label titolo;
	private VBox formAutenticazione;
	private HBox rigaScelta;
	private HBox rigaUsername;
	private HBox rigaPassword;
	private ComboBox<String> cbScelta;
	private TextField txtUsername;
	private PasswordField pfPassword;
	private TextField txtPassword;
	private StackPane contenitorePassword;
	private Button btnMostraPassword;
	private Button btnAvanti;
	private Label lblErroreScelta;
	private Label lblErroreUsername;
	private Label lblErrorePassword;
	
	@Autowired @Lazy
    private AutenticazioneController controller;
	
	
    public AutenticazioneView() {
        this.layout = new VBox(20);
        this.titolo = new Label("Autenticati per poter interagire con le VM nel resource group!");
        this.formAutenticazione = new VBox(15);
        this.rigaScelta = new HBox(15);
        this.rigaUsername = new HBox(15);
        this.rigaPassword = new HBox(15);
        this.cbScelta = new ComboBox<>();
        this.txtUsername = new TextField();
        this.pfPassword = new PasswordField();
        this.txtPassword = new TextField();
        this.contenitorePassword = new StackPane();
        this.btnMostraPassword = new Button("\uD83D\uDC41");
        this.btnAvanti = new Button("AVANTI");
        this.lblErroreScelta = new Label();
        this.lblErroreUsername = new Label();
        this.lblErrorePassword = new Label();
    }
    
    @PostConstruct  
    public void init() {
        this.layout.setAlignment(Pos.TOP_CENTER);
        this.titolo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        this.formAutenticazione.setAlignment(Pos.CENTER);
        this.rigaScelta.setAlignment(Pos.CENTER);
        this.rigaUsername.setAlignment(Pos.CENTER);
        this.rigaPassword.setAlignment(Pos.CENTER);
        
        this.cbScelta.setPromptText("Seleziona un'operazione...");
        this.cbScelta.getItems().addAll("LOG-IN", "REGISTRAZIONE");
        this.txtUsername.setPromptText("Inserisci username");
        this.pfPassword.setPromptText("Inserisci password");
        this.txtPassword.setPromptText("Inserisci password");
        this.txtPassword.setVisible(false);
        this.txtPassword.textProperty().bindBidirectional(this.pfPassword.textProperty());

        this.impostaStileErrore(this.lblErroreScelta);
        this.impostaStileErrore(this.lblErroreUsername);
        this.impostaStileErrore(this.lblErrorePassword);

        this.btnMostraPassword.setOnAction(e -> togglePassword());

        this.rigaScelta.getChildren().addAll(this.cbScelta, this.lblErroreScelta);
        this.rigaUsername.getChildren().addAll(this.txtUsername, this.lblErroreUsername);
        this.contenitorePassword.getChildren().addAll(this.txtPassword, this.pfPassword);
        this.rigaPassword.getChildren().addAll(this.contenitorePassword, this.btnMostraPassword, this.lblErrorePassword);
        this.formAutenticazione.getChildren().addAll(this.rigaScelta, this.rigaUsername, this.rigaPassword);
        
        this.btnAvanti.setOnAction(e -> {
        	this.clearErrors();
            this.controller.gestisciAutenticazione(
            	this.txtUsername.getText(),
            	this.txtPassword.getText(),
            	this.cbScelta.getValue()
            );
        });

        this.layout.getChildren().addAll(this.titolo, this.formAutenticazione, this.btnAvanti);
    }
    
    public Parent asParent() {
        return this.layout;
    }
	
	public void reSetUsername() {
        this.txtUsername.clear();
    }

	public void reSetPassword() {
		this.pfPassword.clear();
		this.pfPassword.setVisible(true);
		this.txtPassword.setVisible(false);
		this.btnMostraPassword.setText("\uD83D\uDC41");
	}

    public void setSceltaIniziale() {
        this.cbScelta.getSelectionModel().clearSelection();
    }
    
    public void resetCampi() {
        this.clearErrors();
        this.reSetUsername();
        this.reSetPassword();
        this.setSceltaIniziale();
    }

    public void resetCredenziali() {
        this.reSetUsername();
        this.reSetPassword();
    }

    public void showErroreScelta(String messaggio) {
    	this.cbScelta.setStyle("-fx-border-color: #d93025; -fx-border-width: 1;");
    	this.lblErroreScelta.setText(messaggio);
    }

    public void showErroreUsername(String messaggio) {
    	this.txtUsername.setStyle("-fx-border-color: #d93025; -fx-border-width: 1;");
    	this.lblErroreUsername.setText(messaggio);
    }

    public void showErrorePassword(String messaggio) {
    	this.pfPassword.setStyle("-fx-border-color: #d93025; -fx-border-width: 1;");
    	this.txtPassword.setStyle("-fx-border-color: #d93025; -fx-border-width: 1;");
    	this.lblErrorePassword.setText(messaggio);
    }

    public void clearErrors() {
    	this.cbScelta.setStyle("");
    	this.txtUsername.setStyle("");
    	this.pfPassword.setStyle("");
    	this.txtPassword.setStyle("");
    	this.lblErroreScelta.setText("");
    	this.lblErroreUsername.setText("");
    	this.lblErrorePassword.setText("");
    }

    private void togglePassword() {
    	if (this.pfPassword.isVisible()) {
    		this.pfPassword.setVisible(false);
    		this.txtPassword.setVisible(true);
    		this.btnMostraPassword.setText("\uD83D\uDE48");
    		return;
    	}
    	this.txtPassword.setVisible(false);
    	this.pfPassword.setVisible(true);
    	this.btnMostraPassword.setText("\uD83D\uDC41");
    }

    private void impostaStileErrore(Label label) {
    	label.setStyle("-fx-text-fill: #d93025;");
    }
}
