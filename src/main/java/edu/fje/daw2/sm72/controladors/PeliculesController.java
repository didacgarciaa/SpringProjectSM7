package edu.fje.daw2.sm72.controladors;

import edu.fje.daw2.sm72.model.Pelicula;
import edu.fje.daw2.sm72.repositoris.PeliculaRepositori;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// Controlador de la gestió de Pel·lícules
@Controller
@SessionAttributes("llistaPelicules")
public class PeliculesController {

    @Autowired
    private PeliculaRepositori repositori;

    @ModelAttribute("llistaPelicules")
    public List<Pelicula> inicializarLlistaPelicules() {
        return new ArrayList<>();
    }

    private int numId = 1;

    @PostMapping("/afegirPelicula")
    public String afegirPelicula(
            @SessionAttribute("llistaPelicules") ArrayList<Pelicula> pelicules,
            @RequestParam(required = true) String titulo,
            @RequestParam(required = true) double puntuacion,
            Model model) {
        Pelicula pelicula = new Pelicula(numId++, titulo, puntuacion);
        pelicules.add(pelicula);
        model.addAttribute("peliculaAfegida", pelicula);
        model.addAttribute("llistaPelicules", pelicules);
        return "pelicules/afegirPelicula";
    }

    @GetMapping("/consultarPelicules")
    public String consultarPelicules(Model model) {
        return "pelicules/consultarPelicules";
    }

    @PostMapping("/esborrarPelicula")
    public String esborrarPelicula(
            @SessionAttribute("llistaPelicules") ArrayList<Pelicula> pelicules,
            @RequestParam(required = true) int id, Model model) {
        pelicules.remove(new Pelicula(id));
        model.addAttribute("llistaPelicules", pelicules);
        return "pelicules/consultarPelicules";
    }

    @PostMapping("/modificarPelicula")
    public String modificarPelicula(
            @SessionAttribute("llistaPelicules") ArrayList<Pelicula> pelicules,
            @RequestParam(required = true) int id,
            @RequestParam(required = true) String titulo,
            @RequestParam(required = true) double puntuacion,
            Model model) {
        Pelicula pelicula = pelicules.get(pelicules.indexOf(new Pelicula(id)));
        pelicula.setTitulo(titulo);
        pelicula.setPuntuacion(puntuacion);
        model.addAttribute("llistaPelicules", pelicules);
        return "pelicules/consultarPelicules";
    }
}
