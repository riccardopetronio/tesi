package tesi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Dice a Spring che questa è una classe di logica
public class UtenteService {

    @Autowired // Spring "inserisce" qui il repository automaticamente
    private UtenteRepository utenteRepository;

    // Metodo per verificare se l'utente esiste
    public Utente verificaUsername(String u) {
        // findById ritorna un Utente presente nel DB. Se non trova nulla, restituisce null.
        return utenteRepository.findById(u).orElse(null);
    }

    public boolean registrazione(String user, String password) {
    	try {
            // Generiamo hash e salt
            String[] risultato = PasswordHasher.gerenaSaltAndHash(password);
            
            // Creo l'oggetto Utente
            Utente nuovoUtente = new Utente(user, risultato[0], risultato[1]);
            
            // Hibernate scrive la INSERT SQL da solo
            utenteRepository.save(nuovoUtente);
            return true;
        } catch (Exception e) {
            System.err.println("Errore durante la registrazione: " + e.getMessage());
            return false;
        }
    }

    public boolean logIn(String password, String hash, String salt) {
        try {
            return PasswordHasher.verifyPassword(password, hash, salt);
        } catch (Exception e) {
            System.err.println("Errore durante l'operazione di hashing: " + e);
            return false;
        }
    }
}