package OLD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

@Component @Profile("gui") @Lazy
public class InserimentoPasswordView {
	
	private VBox layout;
	private Label titolo;
	private HBox rigaPassword;
	private PasswordField pfPassword;
	private TextField txtPassword;
	private StackPane contenitorePassword;
	private Button btnMostra;
	private Button btnAvanti;
	private Button btnIndietro;
    private HBox rigaBottoni;

	private ListView<String> listaLog;
	
	@Autowired @Lazy
    private InserimentoPasswordController controller; 
	
	public InserimentoPasswordView() {
        this.layout = new VBox(20);
        this.titolo = new Label("Inserimento password");
        this.rigaPassword = new HBox(15);
        this.txtPassword = new TextField();
    	this.pfPassword = new PasswordField();
    	this.btnMostra = new Button("\uD83D\uDC41");
        this.contenitorePassword = new StackPane();
        this.btnAvanti = new Button("AVANTI");
        this.btnIndietro = new Button("INDIETRO");
        this.rigaBottoni = new HBox(15);
        this.listaLog = new ListView<>();
	}


	@PostConstruct  
    public void init() {
		this.layout.setAlignment(Pos.TOP_CENTER);
        this.titolo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        this.rigaPassword.setAlignment(Pos.CENTER);
        this.rigaBottoni.setAlignment(Pos.CENTER);
        
        this.txtPassword.setVisible(false);
        this.pfPassword.setPromptText("Inserisci password");
        this.txtPassword.setPromptText("Inserisci password");

        this.txtPassword.textProperty().bindBidirectional(this.pfPassword.textProperty());

        this.btnMostra.setOnAction(e -> togglePassword());
        
        this.contenitorePassword.getChildren().addAll(this.txtPassword, this.pfPassword);
        this.rigaPassword.getChildren().addAll(this.contenitorePassword, this.btnMostra);
  
        
        this.btnIndietro.setOnAction(e -> {
        	this.controller.indietro();
        });
        
        this.btnAvanti.setOnAction(e -> {
            this.controller.gestisciInserimentoPassword(this.txtPassword.getText());
        });
                
        this.rigaBottoni.getChildren().addAll(this.btnIndietro, this.btnAvanti);
        this.layout.getChildren().addAll(this.titolo,this.rigaPassword, this.rigaBottoni, this.listaLog);
	}
	
	public Parent asParent() {
        return this.layout;
    }
	
	public void reSetPassword() {
		this.pfPassword.clear();
        this.pfPassword.setVisible(true);
        this.txtPassword.setVisible(false);
        this.btnMostra.setText("\uD83D\uDC41");
    }

    public void addLog(String s) {
        this.listaLog.getItems().add(s);
    }
    
    private void togglePassword() {
        if (this.pfPassword.isVisible()) {
            this.pfPassword.setVisible(false);
            this.txtPassword.setVisible(true);
            this.btnMostra.setText("\uD83D\uDE48");
            return;
        }
        this.txtPassword.setVisible(false);
        this.pfPassword.setVisible(true);
        this.btnMostra.setText("\uD83D\uDC41");
    }

}
