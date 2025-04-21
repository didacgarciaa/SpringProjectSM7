package edu.fje.daw2.sm72.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;
import java.util.Objects;

/**
 * Representa una película en el sistema.
 * Clase que encapsula la información relacionada con una película.
 * Se almacena en la base de datos como una entidad.
 * 
 * @author Spring Project SM7
 * @version 1.0
 */
@Entity
public class Pelicula {
    /** Identificador único autogenerado */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** Número de identificación */
    private int numId;
    
    /** Título de la película */
    private String titulo;
    
    /** Puntuación de la película (0-10) */
    private double puntuacion;
    
    /** Datos de la imagen en formato binario */
    @Lob
    @Column(name = "image_data", length = 1000000)
    private byte[] imageData;
    
    /** Nombre del archivo de la imagen original */
    private String imageFileName;

    /**
     * Constructor por defecto
     */
    public Pelicula() {
    }

    /**
     * Constructor con número de identificación
     * 
     * @param numId Número de identificación
     */
    public Pelicula(int numId) {
        this.numId = numId;
    }

    /**
     * Constructor con todos los campos excepto la imagen
     * 
     * @param numId Número de identificación
     * @param titulo Título de la película
     * @param puntuacion Puntuación de la película (0-10)
     */
    public Pelicula(int numId, String titulo, double puntuacion) {
        this.numId = numId;
        this.titulo = titulo;
        this.puntuacion = puntuacion;
    }
    
    /**
     * Constructor con todos los campos incluida la imagen
     * 
     * @param numId Número de identificación
     * @param titulo Título de la película
     * @param puntuacion Puntuación de la película (0-10)
     * @param imageData Datos binarios de la imagen
     * @param imageFileName Nombre del archivo de imagen original
     */
    public Pelicula(int numId, String titulo, double puntuacion, byte[] imageData, String imageFileName) {
        this.numId = numId;
        this.titulo = titulo;
        this.puntuacion = puntuacion;
        this.imageData = imageData;
        this.imageFileName = imageFileName;
    }

    /**
     * Obtiene el identificador único
     * 
     * @return El identificador único
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único
     * 
     * @param id El identificador único
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el número de identificación
     * 
     * @return El número de identificación
     */
    public int getNumId() {
        return numId;
    }

    /**
     * Establece el número de identificación
     * 
     * @param numId El número de identificación
     */
    public void setNumId(int numId) {
        this.numId = numId;
    }

    /**
     * Obtiene el título de la película
     * 
     * @return El título
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título de la película
     * 
     * @param titulo El título
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene la puntuación de la película
     * Limita la puntuación a un máximo de 10
     * 
     * @return La puntuación
     */
    public double getPuntuacion() {
        if (puntuacion > 10) puntuacion = 10;
        return puntuacion;
    }

    /**
     * Establece la puntuación de la película
     * 
     * @param puntuacion La puntuación
     */
    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }
    
    /**
     * Obtiene los datos binarios de la imagen
     * 
     * @return Los datos de la imagen
     */
    public byte[] getImageData() {
        return imageData;
    }
    
    /**
     * Establece los datos binarios de la imagen
     * 
     * @param imageData Los datos de la imagen
     */
    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
    
    /**
     * Obtiene el nombre del archivo de imagen original
     * 
     * @return El nombre del archivo
     */
    public String getImageFileName() {
        return imageFileName;
    }
    
    /**
     * Establece el nombre del archivo de imagen original
     * 
     * @param imageFileName El nombre del archivo
     */
    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
    
    /**
     * Comprueba si la película tiene una imagen asociada
     * 
     * @return true si tiene imagen, false en caso contrario
     */
    public boolean hasImage() {
        return imageData != null && imageData.length > 0;
    }

    /**
     * Compara si dos películas son iguales basándose en su numId
     * 
     * @param o El objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pelicula pelicula = (Pelicula) o;
        return numId == pelicula.numId;
    }

    /**
     * Genera un código hash basado en el numId
     * 
     * @return El código hash
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(numId);
    }

    /**
     * Genera una representación en texto de la película
     * 
     * @return Representación en texto
     */
    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", numId=" + numId +
                ", titulo='" + titulo + '\'' +
                ", puntuacion=" + puntuacion +
                ", hasImage=" + hasImage() +
                '}';
    }
}
