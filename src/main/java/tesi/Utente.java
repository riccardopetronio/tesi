package tesi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "utenti")
public class Utente {

    @Id
    @Column(name = "username", length = 20)
    private String username;

    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "salt", nullable = false)
    private String salt;

    @Column(name = "cittadinanza")
    private String cittadinanza;

    // per Hibernate surve un il costruttore senza argomenti
    public Utente() {
    }

    public Utente(String username, String hash, String salt) {
        this.username = username;
        this.hash = hash;
        this.salt = salt;
        this.cittadinanza = null;
    }

    // Getter e Setter
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
        return ""+this.username+" "+this.hash+" "+this.salt;
    }
}
