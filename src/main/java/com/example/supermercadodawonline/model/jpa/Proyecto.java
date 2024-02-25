package com.example.supermercadodawonline.model.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Date;

@Entity
public class Proyecto {

    @Id
    private int idproyecto;
    private String nombre;
    private String descripcion;
    private String zona;
    private Date fecha;

    public Proyecto() {
    }

    public Proyecto(int idproyecto, String nombre, String descripcion, String zona, Date fecha) {
        this.idproyecto = idproyecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.zona = zona;
        this.fecha = fecha;
    }

    public int getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(int idproyecto) {
        this.idproyecto = idproyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
