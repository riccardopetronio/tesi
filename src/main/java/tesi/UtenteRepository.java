package tesi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, String> {
    // Qui si possono aggiungere metodi personalizzati se servono, 
    // ma per le operazioni base (salva, cerca, elimina) è già tutto presente
}