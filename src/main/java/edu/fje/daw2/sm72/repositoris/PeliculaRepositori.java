package edu.fje.daw2.sm72.repositoris;

import edu.fje.daw2.sm72.model.Pelicula;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PeliculaRepositori extends MongoRepository<Pelicula, String> {

}