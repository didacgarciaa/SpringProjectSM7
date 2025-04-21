package edu.fje.daw2.sm72.repositoris;

import edu.fje.daw2.sm72.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepositori extends JpaRepository<Pelicula, Long> {
    List<Pelicula> findByNumId(int numId);
}