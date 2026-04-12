package tesi.GUI;

import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class View extends JFrame {
	
	private JComboBox primaScelta;
	private JTextField txtInUsername;
	private JLabel usernameLabel;

	private GestoreEventi controller;
	
	
	public void primaFinestra() {
		
		// INIZIALIZAZIONE
		this.setTitle("AUTENTICAZIONE");
		this.setSize(600, 250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		
		// WIDGET / CONTROLLI
		String[] tipologia = { "LOG-IN", "REGISTRAZIONE" };
		this.primaScelta = new JComboBox(tipologia);
		
		this.usernameLabel = new JLabel("Inserisci il tuo username:");
		this.txtInUsername = new JTextField(20); 
		
		// IMPOSTO IL LAYOUT
		//FlowLayout layout = new FlowLayout();
		FlowLayout layout = new FlowLayout();
		this.setLayout( layout );
		
		
		// AGGIUNGO I WIDGET ALLA FINESTRA
		add(primaScelta);
        add(usernameLabel);
        add(txtInUsername);
		
		// LA MOSTRO
		this.setVisible(true);
	}
}
	/*
	public void setController( GestoreEventi controller) {
		this.controller=controller;
	
	// IMPOSTO GLI ASCOLTATORI DI EVENTI
	pulsante.addActionListener(controller);
	pulsante2.addActionListener(controller);
	}
	
	public void aggiornaEtichetta() {
		etichetta.setText(""+model.contatore);
	}
	*/