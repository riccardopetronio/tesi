package tesi.automation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AutomazioneRepository extends JpaRepository<Automazione, Integer> {
	
	List<Automazione> findByAbilitata(boolean abilitazione);
}
