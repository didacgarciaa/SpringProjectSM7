package edu.fje.daw2.sm72.controladors;

import edu.fje.daw2.sm72.model.Pelicula;
import edu.fje.daw2.sm72.model.Usuari;
import edu.fje.daw2.sm72.repositoris.PeliculaRepositori;
import edu.fje.daw2.sm72.repositoris.UsuariRepositori;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de películas.
 * Implementa las operaciones CRUD para el modelo Pelicula.
 * Utiliza Spring MVC para manejar las peticiones HTTP.
 * 
 * @author Spring Project SM7
 * @version 1.0
 */
@Controller
public class PeliculesController {

    /**
     * Repositorio de películas para acceso a datos
     */
    @Autowired
    private PeliculaRepositori repositori;

    /**
     * Repositorio de usuarios para acceso a datos
     */
    @Autowired
    private UsuariRepositori usuariRepositori;

    /**
     * Contador para asignar IDs a nuevas películas
     */
    private int numId = 1;

    /**
     * Muestra el formulario para añadir una nueva película
     * 
     * @param model Modelo para pasar datos a la vista
     * @return Vista del formulario
     */
    @GetMapping("/afegirPeliculaForm")
    public String mostrarFormulariAfegirPelicula(Model model) {
        model.addAttribute("activeTab", "pelicules");
        return "pelicules/afegirPeliculaForm";
    }

    /**
     * Redirige al formulario para añadir películas
     * 
     * @return Redirección al formulario
     */
    @GetMapping("/afegirPelicula")
    public String mostrarFormulariAfegirPeliculaGet() {
        return "redirect:/afegirPeliculaForm";
    }

    /**
     * Procesa la adición de una nueva película
     * 
     * @param titulo Título de la película
     * @param puntuacion Puntuación de la película
     * @param image Imagen de la película (opcional)
     * @param model Modelo para pasar datos a la vista
     * @return Vista de confirmación o formulario en caso de error
     */
    @PostMapping("/afegirPelicula")
    public String afegirPelicula(
            @RequestParam(required = true) String titulo,
            @RequestParam(required = true) double puntuacion,
            @RequestParam(value = "image", required = false) MultipartFile image,
            Model model) {
        
        try {
            Pelicula pelicula = new Pelicula(numId++, titulo, puntuacion);
            
            if (image != null && !image.isEmpty()) {
                pelicula.setImageData(image.getBytes());
                pelicula.setImageFileName(image.getOriginalFilename());
            }
            
            repositori.save(pelicula);
            model.addAttribute("peliculaAfegida", pelicula);
            model.addAttribute("llistaPelicules", repositori.findAll());
            model.addAttribute("activeTab", "pelicules");
            return "pelicules/afegirPelicula";
        } catch (IOException e) {
            model.addAttribute("error", "Error al procesar la imagen: " + e.getMessage());
            model.addAttribute("activeTab", "pelicules");
            return "pelicules/afegirPeliculaForm";
        }
    }

    /**
     * Muestra el listado de todas las películas
     * 
     * @param model Modelo para pasar datos a la vista
     * @return Vista con el listado de películas
     */
    @GetMapping("/consultarPelicules")
    public String consultarPelicules(Model model) {
        model.addAttribute("llistaPelicules", repositori.findAll());
        model.addAttribute("activeTab", "pelicules");
        return "pelicules/consultarPelicules";
    }

    /**
     * Muestra el formulario para eliminar una película
     * 
     * @param model Modelo para pasar datos a la vista
     * @return Vista del formulario
     */
    @GetMapping("/esborrarPelicula")
    public String mostrarFormulariEsborrarPelicula(Model model) {
        model.addAttribute("activeTab", "pelicules");
        return "pelicules/esborrarPeliculaForm";
    }

    /**
     * Procesa la eliminación de una película
     * 
     * @param id ID de la película a eliminar
     * @param model Modelo para pasar datos a la vista
     * @return Vista con el resultado de la operación
     */
    @PostMapping("/esborrarPelicula")
    public String esborrarPelicula(
            @RequestParam(required = true) String id, 
            Model model) {
        try {
            repositori.deleteById(id);
            model.addAttribute("success", "Película eliminada correctamente con ID: " + id);
        } catch (Exception e) {
            model.addAttribute("error", "No se ha podido eliminar la película con ID: " + id);
        }
        model.addAttribute("llistaPelicules", repositori.findAll());
        model.addAttribute("activeTab", "pelicules");
        return "pelicules/consultarPelicules";
    }

    /**
     * Muestra el formulario para modificar una película
     * 
     * @param model Modelo para pasar datos a la vista
     * @return Vista del formulario
     */
    @GetMapping("/modificarPelicula")
    public String mostrarFormulariModificarPelicula(Model model) {
        model.addAttribute("activeTab", "pelicules");
        return "pelicules/modificarPeliculaForm";
    }

    /**
     * Procesa la modificación de una película
     * 
     * @param id ID de la película a modificar
     * @param numId Número ID de la película
     * @param titulo Nuevo título de la película
     * @param puntuacion Nueva puntuación de la película
     * @param image Nueva imagen (opcional)
     * @param keepExistingImage Indica si mantener la imagen existente
     * @param model Modelo para pasar datos a la vista
     * @return Vista con el resultado de la operación
     */
    @PostMapping("/modificarPelicula")
    public String modificarPelicula(
            @RequestParam(required = true) String id,
            @RequestParam(required = true) int numId,
            @RequestParam(required = true) String titulo,
            @RequestParam(required = true) double puntuacion,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "keepExistingImage", required = false, defaultValue = "false") boolean keepExistingImage,
            Model model) {
        try {
            Optional<Pelicula> optPelicula = repositori.findById(id);
            if (optPelicula.isPresent()) {
                Pelicula pelicula = optPelicula.get();
                pelicula.setNumId(numId);
                pelicula.setTitulo(titulo);
                pelicula.setPuntuacion(puntuacion);
                
                // Handle image update
                if (image != null && !image.isEmpty()) {
                    pelicula.setImageData(image.getBytes());
                    pelicula.setImageFileName(image.getOriginalFilename());
                } else if (!keepExistingImage) {
                    // Clear image data if not keeping the existing image and no new image provided
                    pelicula.setImageData(null);
                    pelicula.setImageFileName(null);
                }
                
                repositori.save(pelicula);
                model.addAttribute("success", "Película modificada correctamente");
            } else {
                model.addAttribute("error", "No se ha encontrado la película con ID: " + id);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al modificar la película: " + e.getMessage());
        }
        model.addAttribute("llistaPelicules", repositori.findAll());
        model.addAttribute("activeTab", "pelicules");
        return "pelicules/consultarPelicules";
    }

    /**
     * Guarda una película en la lista de favoritos de un usuario
     * 
     * @param peliculaId ID de la película a guardar
     * @param session Sesión actual del usuario
     * @param model Modelo para pasar datos a la vista
     * @return Vista con el resultado de la operación
     */
    @PostMapping("/guardarPeliculaUsuari")
    public String guardarPeliculaUsuari(
            @RequestParam String peliculaId,
            HttpSession session,
            Model model) {
        try {
            // Obtener el ID del usuario de la sesión
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                model.addAttribute("error", "Necesitas iniciar sesión para guardar películas");
                return "redirect:/consultarPelicules";
            }
            
            // Buscar el usuario
            Optional<Usuari> optUsuari = usuariRepositori.findById(userId);
            if (!optUsuari.isPresent()) {
                model.addAttribute("error", "Usuario no encontrado");
                return "redirect:/consultarPelicules";
            }
            
            // Buscar la película
            Optional<Pelicula> optPelicula = repositori.findById(peliculaId);
            if (!optPelicula.isPresent()) {
                model.addAttribute("error", "Película no encontrada");
                return "redirect:/consultarPelicules";
            }
            
            // Guardar la película en la lista del usuario
            Usuari usuari = optUsuari.get();
            usuari.addPeliculaId(peliculaId);
            usuariRepositori.save(usuari);
            
            model.addAttribute("success", "Película guardada en tu lista");
            return "redirect:/consultarPelicules";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar la película: " + e.getMessage());
            return "redirect:/consultarPelicules";
        }
    }
    
    /**
     * Elimina una película de la lista de favoritos de un usuario
     * 
     * @param peliculaId ID de la película a eliminar
     * @param session Sesión actual del usuario
     * @param model Modelo para pasar datos a la vista
     * @return Vista con el resultado de la operación
     */
    @PostMapping("/eliminarPeliculaUsuari")
    public String eliminarPeliculaUsuari(
            @RequestParam String peliculaId,
            HttpSession session,
            Model model) {
        try {
            // Obtener el ID del usuario de la sesión
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                model.addAttribute("error", "Necesitas iniciar sesión para eliminar películas");
                return "redirect:/consultarPelicules";
            }
            
            // Buscar el usuario
            Optional<Usuari> optUsuari = usuariRepositori.findById(userId);
            if (!optUsuari.isPresent()) {
                model.addAttribute("error", "Usuario no encontrado");
                return "redirect:/consultarPelicules";
            }
            
            // Eliminar la película de la lista del usuario
            Usuari usuari = optUsuari.get();
            usuari.removePeliculaId(peliculaId);
            usuariRepositori.save(usuari);
            
            model.addAttribute("success", "Película eliminada de tu lista");
            return "redirect:/consultarPelicules";
        } catch (Exception e) {
            model.addAttribute("error", "Error al eliminar la película: " + e.getMessage());
            return "redirect:/consultarPelicules";
        }
    }
}