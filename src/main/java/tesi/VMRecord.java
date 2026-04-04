package tesi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "virtual_machines")
public class VMRecord {

	@Id
    @Column(name = "ID", length = 36)
	private String id;
	
    @Column(name = "nome", nullable = false)
	private String nome;
    
    @Column(name = "sistema_operativo", nullable = false)
	private String os;
    
    @Column(name = "stato", nullable = false)
	private String stato;
	
    
    
	public VMRecord() {
	}

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
	
	public boolean equals(VMRecord vm) {
		return this.getId().equals(vm.getId());
	}
}
