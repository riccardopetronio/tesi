package tesi;

public class VMRecord {
	
	private String id;
	private String nome;
	private String os;
	private String stato;
	
	
	public VMRecord(String id, String nome, String os, String stato) {
		this.id = id;
		this.nome = nome;
		this.os = os;
		this.stato = stato;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
	@Override
	public String toString() {
		return "VM: [id=" + id + ", nome=" + nome + ", os=" + os + ", stato=" + stato + "]";
	}
}
