package tesi.vm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VMRepository extends JpaRepository<VMRecord, String> {
    
	VMRecord findByNome(String nome);
}
