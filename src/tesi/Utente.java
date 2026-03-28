package tesi;

public class Utente {

	private String username;
	private String hash;
	private String salt;
	private String cittadinanza;
	
	
	public Utente(String username, String hash, String salt) {
		super();
		this.username = username;
		this.hash = hash;
		this.salt = salt;
		this.cittadinanza = null;
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}


	@Override
	public String toString() {
		return ""+this.username+"    "+this.hash+"    "+this.salt;
	}
	
	
	
	
}
