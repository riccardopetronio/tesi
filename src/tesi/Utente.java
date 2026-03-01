package tesi;

public class Utente {

	private String password;
	private String nome;
	private String cognome;
	private String id;
	
	
	public Utente(String password, String nome, String cognome, String id) {

		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.id = id;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
