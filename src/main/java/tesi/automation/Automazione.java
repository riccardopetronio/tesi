package tesi.automation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import tesi.user.Utente;
import tesi.vm.VMRecord;

@Entity
@Table(name = "automazioni")
public class Automazione {

   
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_automazione")
    private int id_automazione;

    @ManyToOne
    @JoinColumn(name = "username_utente", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "id_vm", nullable = false)
    private VMRecord vm;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipologia_operazione")
    private TipologiaOperazione tipologiaOperazione;

    @Column(name = "orario")
    private String orario;
    
    @Column(name = "abilitata")
    private boolean abilitata;
    
    
    public Automazione(Utente utente, VMRecord vm, TipologiaOperazione tipologiaOperazione,
			String orario, boolean abilitata) {
		this.id_automazione = 0;
		this.utente = utente;
		this.vm = vm;
		this.tipologiaOperazione = tipologiaOperazione;
		this.orario = orario;
		this.abilitata = abilitata;
	}
    
    public Automazione() {
    }

	public void setId_automazione(int id_automazione) {
		this.id_automazione = id_automazione;
	}
	public int getId_automazione() {
		return id_automazione;
	}
    
	public Utente getUtente() {
		return this.utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public VMRecord getVm() {
		return vm;
	}
	public void setVm(VMRecord vm) {
		this.vm = vm;
	}

	public String getOrario() {
		return orario;
	}
	public void setOrario(String orario) {
		this.orario = orario;
	}

	public TipologiaOperazione getTipologiaOperazione() {
		return tipologiaOperazione;
	}
	public void setTipologiaOperazione(TipologiaOperazione tipologiaOperazione) {
		this.tipologiaOperazione = tipologiaOperazione;
	}
	
	public boolean isAbilitata() {
		return abilitata;
	}
	public void setAbilitata(boolean abilitata) {
		this.abilitata = abilitata;
	}

	@Override
	public String toString() {
		return "Automazione [id_automazione= " + id_automazione + ", utente= " + utente.getUsername() + ", vm= " + vm.getNome()
				+ ", tipologiaOperazione= " + tipologiaOperazione + ", orario= " + orario + ", abilitata= " + abilitata
				+ "]";
	}

}