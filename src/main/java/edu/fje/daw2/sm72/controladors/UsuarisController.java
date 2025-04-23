package edu.fje.daw2.sm72.controladors;

import edu.fje.daw2.sm72.model.Usuari;
import edu.fje.daw2.sm72.repositoris.UsuariRepositori;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/login")
    public String login(
        @RequestParam String email,
        @RequestParam String password,
        HttpSession session,
        Model model
    ) {
        try {
            Usuari usuari = repositori.findByEmail(email);
            if (usuari != null && usuari.getPassword().equals(password)) {
                session.setAttribute("userId", usuari.getId());
                session.setAttribute("userNom", usuari.getNom());
                session.setAttribute("userEmail", usuari.getEmail());
                if (usuari.getPeliculaIds() == null) {
                    usuari.setPeliculaIds(new ArrayList<>());
                    repositori.save(usuari);
                }
                return "redirect:/home.html";
            } else {
                model.addAttribute("error", "Credenciales inválidas");
                return "auth/login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error en el login: " + e.getMessage());
            return "auth/login";
        }
    }

    @GetMapping("/afegirUsuariForm")
    public String mostrarFormulariAfegirUsuari(Model model) {
        model.addAttribute("activeTab", "usuaris");
        return "usuaris/afegirUsuariForm";
    }

    @GetMapping("/afegirUsuari")
    public String mostrarFormulariAfegirUsuariGet() {
        return "redirect:/afegirUsuariForm";
    }

    @PostMapping("/afegirUsuari")
    public String afegirUsuari(
            @RequestParam String nom,
            @RequestParam String cognom,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {
        Usuari usuari = new Usuari(nom, cognom, email, password);
        repositori.save(usuari);
        model.addAttribute("alumneAfegit", usuari);
        model.addAttribute("llistaUsuaris", repositori.findAll());
        model.addAttribute("success", "Usuari afegit correctament");
        model.addAttribute("activeTab", "usuaris");
        return "redirect:/consultarUsuaris";
    }

    @GetMapping("/consultarUsuaris")
    public String consultarUsuaris(Model model) {
        model.addAttribute("llistaUsuaris", repositori.findAll());
        model.addAttribute("activeTab", "usuaris");
        return "usuaris/consultarUsuaris";
    }

    @GetMapping("/esborrarUsuari")
    public String mostrarFormulariEsborrarUsuari(Model model) {
        model.addAttribute("activeTab", "usuaris");
        return "usuaris/esborrarUsuariForm";
    }

    @PostMapping("/esborrarUsuari")
    public String esborrarUsuari(@RequestParam Long id, Model model) {
        try {
            repositori.deleteById(id);
            model.addAttribute("success", "Usuari eliminat correctament amb ID: " + id);
        } catch (Exception e) {
            model.addAttribute("error", "No s'ha pogut esborrar l'usuari amb ID: " + id);
        }
        model.addAttribute("llistaUsuaris", repositori.findAll());
        model.addAttribute("activeTab", "usuaris");
        return "usuaris/consultarUsuaris";
    }

    @GetMapping("/modificarUsuari")
    public String mostrarFormulariModificarUsuari(Model model) {
        model.addAttribute("activeTab", "usuaris");
        return "usuaris/modificarUsuariForm";
    }

    @PostMapping("/modificarUsuari")
    public String modificarUsuari(
            @RequestParam Long id,
            @RequestParam String nom,
            @RequestParam String cognom,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {
        try {
            Optional<Usuari> optUsuari = repositori.findById(id);
            if (optUsuari.isPresent()) {
                Usuari usuari = optUsuari.get();
                usuari.setNom(nom);
                usuari.setCognom(cognom);
                usuari.setEmail(email);
                usuari.setPassword(password);
                repositori.save(usuari);
                model.addAttribute("success", "Usuari modificat correctament");
            } else {
                model.addAttribute("error", "No s'ha trobat l'usuari amb ID: " + id);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al modificar l'usuari: " + e.getMessage());
        }
        model.addAttribute("llistaUsuaris", repositori.findAll());
        model.addAttribute("activeTab", "usuaris");
        return "usuaris/consultarUsuaris";
    }

    @GetMapping("/misPeliculas")
    public String verMisPeliculas(HttpSession session, Model model) {
        try {
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                return "redirect:/";
            }
            Optional<Usuari> optUsuari = repositori.findById(userId);
            if (!optUsuari.isPresent()) {
                model.addAttribute("error", "Usuario no encontrado");
                return "redirect:/";
            }
            
            Usuari usuari = optUsuari.get();
            List<String> peliculaIds = usuari.getPeliculaIds();

            model.addAttribute("usuario", usuari);
            model.addAttribute("peliculaIds", peliculaIds);
            model.addAttribute("activeTab", "misPeliculas");
            
            return "usuaris/misPeliculas";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar tus películas: " + e.getMessage());
            return "redirect:/";
        }
    }
}