package edu.fje.daw2.sm72.controladors;

import edu.fje.daw2.sm72.model.Pelicula;
import edu.fje.daw2.sm72.repositoris.PeliculaRepositori;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

/**
 * Controlador para gestionar la visualización de imágenes.
 * Se encarga de servir las imágenes almacenadas en la base de datos.
 * 
 * @author Spring Project SM7
 * @version 1.0
 */
@Controller
public class ImageController {

    /**
     * Repositorio de películas para acceso a datos
     */
    @Autowired
    private PeliculaRepositori peliculaRepositori;

    /**
     * Recupera y sirve la imagen de una película específica.
     * Detecta el tipo de contenido basado en la extensión del archivo.
     * 
     * @param id Identificador de la película
     * @return ResponseEntity con los datos de la imagen o NOT_FOUND si no existe
     */
    @GetMapping("/image/pelicula/{id}")
    public ResponseEntity<byte[]> getPeliculaImage(@PathVariable("id") Long id) {
        Optional<Pelicula> optionalPelicula = peliculaRepositori.findById(id);
        
        if (optionalPelicula.isPresent() && optionalPelicula.get().hasImage()) {
            Pelicula pelicula = optionalPelicula.get();
            HttpHeaders headers = new HttpHeaders();
            
            // Try to determine content type based on file name
            String fileName = pelicula.getImageFileName().toLowerCase();
            if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if (fileName.endsWith(".png")) {
                headers.setContentType(MediaType.IMAGE_PNG);
            } else if (fileName.endsWith(".gif")) {
                headers.setContentType(MediaType.IMAGE_GIF);
            } else {
                // Default to octet-stream if type can't be determined
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }
            
            return new ResponseEntity<>(pelicula.getImageData(), headers, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
} 