package edu.fje.daw2.sm72.repositoris;

import edu.fje.daw2.sm72.model.Usuari;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UsuariRepositori extends CrudRepository<Usuari, String> {
    List<Usuari> findByNom(String nom);
}
