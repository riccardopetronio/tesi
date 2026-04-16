package tesi.GUI;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tesi.user.Utente;

@Component @Profile("gui")
public class Dati {
	
	String primaScelta;
	Utente utente;
	String username;
	
	public Dati() {
	}

	public String getPrimaScelta() {
		return primaScelta;
	}
	public void setPrimaScelta(String primaScelta) {
		this.primaScelta = primaScelta;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	

}
