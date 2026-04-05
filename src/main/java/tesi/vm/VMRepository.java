package tesi.vm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VMRepository extends JpaRepository<VMRecord, String> {
    // Qui puoi aggiungere metodi personalizzati se servono, 
    // ma per le operazioni base (salva, cerca, elimina) è già tutto pronto.
}
