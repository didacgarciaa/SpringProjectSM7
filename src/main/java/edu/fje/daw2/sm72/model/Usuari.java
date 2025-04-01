package edu.fje.daw2.sm72.model;

import org.springframework.data.annotation.Id;

/**
 * Classe entitat que representa un usuari.
 * Es tracta d'una classe del model que es farà persistent.
 * @author
 * @version 1.0
 */
public class Usuari {
    @Id
    public String id;
    public String nom;
    public String cognom;
    public String email;

    public Usuari() {}

    public Usuari(String nom, String cognom, String email) {
        this.nom = nom;
        this.cognom = cognom;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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