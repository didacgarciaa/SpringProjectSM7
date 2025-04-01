package edu.fje.daw2.sm72.controladors;

import edu.fje.daw2.sm72.model.Usuari;
import edu.fje.daw2.sm72.repositoris.UsuariRepositori;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes("llistaUsuaris")
public class UsuarisController {

    @Autowired
    private UsuariRepositori repositori;

    @ModelAttribute("llistaUsuaris")
    public List<Usuari> inicialitzarLlistaUsuaris() {
        List<Usuari> usuaris = new ArrayList<>();
        repositori.findAll().forEach(usuaris::add);
        return usuaris;
    }

    @PostMapping("/afegirUsuari")
    public String afegirUsuari(
            @RequestParam String nom,
            @RequestParam String cognom,
            @RequestParam String email,
            Model model) {
        Usuari usuari = new Usuari(nom, cognom, email);
        repositori.save(usuari);
        model.addAttribute("llistaUsuaris", repositori.findAll());
        return "usuaris/afegirUsuari";
    }

    @GetMapping("/consultarUsuaris")
    public String consultarUsuaris(Model model) {
        model.addAttribute("llistaUsuaris", repositori.findAll());
        return "usuaris/consultarUsuaris";
    }

    @PostMapping("/esborrarUsuari")
    public String esborrarUsuari(@RequestParam String id, Model model) {
        repositori.deleteById(id);
        model.addAttribute("llistaUsuaris", repositori.findAll());
        return "usuaris/consultarUsuaris";
    }

    @PostMapping("/modificarUsuari")
    public String modificarUsuari(
            @RequestParam String id,
            @RequestParam String nom,
            @RequestParam String cognom,
            @RequestParam String email,
            Model model) {
        Optional<Usuari> optUsuari = repositori.findById(id);
        if (optUsuari.isPresent()) {
            Usuari usuari = optUsuari.get();
            usuari.setNom(nom);
            usuari.setCognom(cognom);
            usuari.setEmail(email);
            repositori.save(usuari);
        }
        model.addAttribute("llistaUsuaris", repositori.findAll());
        return "usuaris/consultarUsuaris";
    }
}
