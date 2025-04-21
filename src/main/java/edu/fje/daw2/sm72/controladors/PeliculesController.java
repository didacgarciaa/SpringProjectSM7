package edu.fje.daw2.sm72.controladors;

import edu.fje.daw2.sm72.model.Pelicula;
import edu.fje.daw2.sm72.repositoris.PeliculaRepositori;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Controlador de la gestió de Pel·lícules
@Controller
public class PeliculesController {

    @Autowired
    private PeliculaRepositori repositori;

    private int numId = 1;

    @GetMapping("/afegirPeliculaForm")
    public String mostrarFormulariAfegirPelicula() {
        return "pelicules/afegirPeliculaForm";
    }

    @GetMapping("/afegirPelicula")
    public String mostrarFormulariAfegirPeliculaGet() {
        return "redirect:/afegirPeliculaForm";
    }

    @PostMapping("/afegirPelicula")
    public String afegirPelicula(
            @RequestParam(required = true) String titulo,
            @RequestParam(required = true) double puntuacion,
            Model model) {
        Pelicula pelicula = new Pelicula(numId++, titulo, puntuacion);
        repositori.save(pelicula);
        model.addAttribute("peliculaAfegida", pelicula);
        model.addAttribute("llistaPelicules", repositori.findAll());
        return "pelicules/afegirPelicula";
    }

    @GetMapping("/consultarPelicules")
    public String consultarPelicules(Model model) {
        model.addAttribute("llistaPelicules", repositori.findAll());
        return "pelicules/consultarPelicules";
    }

    @GetMapping("/esborrarPelicula")
    public String mostrarFormulariEsborrarPelicula() {
        return "redirect:/esborrarPelicula.html";
    }

    @PostMapping("/esborrarPelicula")
    public String esborrarPelicula(
            @RequestParam(required = true) String id, 
            Model model) {
        try {
            repositori.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("error", "No se ha podido eliminar la película con ID: " + id);
        }
        model.addAttribute("llistaPelicules", repositori.findAll());
        return "pelicules/consultarPelicules";
    }

    @GetMapping("/modificarPelicula")
    public String mostrarFormulariModificarPelicula() {
        return "redirect:/modificarPelicula.html";
    }

    @PostMapping("/modificarPelicula")
    public String modificarPelicula(
            @RequestParam(required = true) String id,
            @RequestParam(required = true) int numId,
            @RequestParam(required = true) String titulo,
            @RequestParam(required = true) double puntuacion,
            Model model) {
        try {
            Optional<Pelicula> optPelicula = repositori.findById(id);
            if (optPelicula.isPresent()) {
                Pelicula pelicula = optPelicula.get();
                pelicula.setNumId(numId);
                pelicula.setTitulo(titulo);
                pelicula.setPuntuacion(puntuacion);
                repositori.save(pelicula);
            } else {
                model.addAttribute("error", "No se ha encontrado la película con ID: " + id);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al modificar la película: " + e.getMessage());
        }
        model.addAttribute("llistaPelicules", repositori.findAll());
        return "pelicules/consultarPelicules";
    }
}