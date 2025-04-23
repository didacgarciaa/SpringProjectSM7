package edu.fje.daw2.sm72.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe entitat que representa un usuari.
 * Es tracta d'una classe del model que es farà persistent.
 * @author
 * @version 1.0
 */
@Entity
public class Usuari {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String cognom;
    private String email;
    private String password;
    
    @Lob
    @Column(name = "pelicula_ids")
    private String peliculaIdsString = "";
    
    public Usuari() {}

    public Usuari(String nom, String cognom, String email, String password) {
        this.nom = nom;
        this.cognom = cognom;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {  
        this.password = password;
    }

    // Convierte el String a lista al obtener
    public List<String> getPeliculaIds() {
        if (peliculaIdsString == null || peliculaIdsString.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(peliculaIdsString.split(",")));
    }

    // Convierte la lista a String al guardar
    public void setPeliculaIds(List<String> peliculaIds) {
        if (peliculaIds == null || peliculaIds.isEmpty()) {
            this.peliculaIdsString = "";
        } else {
            this.peliculaIdsString = String.join(",", peliculaIds);
        }
    }
    
    // Añade una película a la lista
    public void addPeliculaId(String peliculaId) {
        List<String> ids = getPeliculaIds();
        if (!ids.contains(peliculaId)) {
            ids.add(peliculaId);
            setPeliculaIds(ids);
        }
    }
    
    // Elimina una película de la lista
    public void removePeliculaId(String peliculaId) {
        List<String> ids = getPeliculaIds();
        ids.remove(peliculaId);
        setPeliculaIds(ids);
    }

    @Override
    public String toString() {
        return "Usuari{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", cognom='" + cognom + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}