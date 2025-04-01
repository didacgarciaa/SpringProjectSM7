package edu.fje.daw2.sm72.model;

import java.util.Objects;

public class Pelicula {
    private int id;
    private String titulo;
    private double puntuacion;

    public Pelicula(int id) {
        this.id = id;
    }

    public Pelicula(int id, String titulo, double puntuacion) {
        this.id = id;
        this.titulo = titulo;
        this.puntuacion = puntuacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return id == pelicula.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", puntuacion=" + puntuacion +
                '}';
    }
}
