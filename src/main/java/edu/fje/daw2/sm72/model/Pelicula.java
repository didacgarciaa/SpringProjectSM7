package edu.fje.daw2.sm72.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Objects;

@Document(collection = "peliculas")
public class Pelicula {
    @Id
    private String id;
    private int numId;
    private String titulo;
    private double puntuacion;

    public Pelicula() {
    }

    public Pelicula(int numId) {
        this.numId = numId;
    }

    public Pelicula(int numId, String titulo, double puntuacion) {
        this.numId = numId;
        this.titulo = titulo;
        this.puntuacion = puntuacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumId() {
        return numId;
    }

    public void setNumId(int numId) {
        this.numId = numId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getPuntuacion() {
        if (puntuacion > 10) puntuacion = 10;
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pelicula pelicula = (Pelicula) o;
        return numId == pelicula.numId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numId);
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", numId=" + numId +
                ", titulo='" + titulo + '\'' +
                ", puntuacion=" + puntuacion +
                '}';
    }
}
