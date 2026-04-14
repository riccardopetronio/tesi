package tesi.GUI.views;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;

@Component
@Profile("gui")
public class InserimentoPasswordView {
	
	private VBox layout;

	

	public InserimentoPasswordView() {
        this.layout = new VBox(20);
	}



	public Parent asParent() {
		return this.layout;
	}

}
