package edu.fje.daw2.sm72.repositoris;

import edu.fje.daw2.sm72.model.Usuari;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuariRepositori extends JpaRepository<Usuari, Long> {
    List<Usuari> findByNom(String nom);
}
