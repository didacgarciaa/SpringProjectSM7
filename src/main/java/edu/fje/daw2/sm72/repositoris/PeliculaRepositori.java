package edu.fje.daw2.sm72.repositoris;

import edu.fje.daw2.sm72.model.Pelicula;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepositori extends MongoRepository<Pelicula, String> {
    List<Pelicula> findByNumId(int numId);
}