package com.example.supermercadodawonline.model.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.sql.Date;

@Entity
public class Tarea {

    @Id
    private int idtarea;
    @ManyToOne
    @JoinColumn(name="idproyecto")
    private Proyecto proyecto;
    private String titulo;
    private String descripcion;
    private Date inicioprevisto;
    private Date finprevisto;
    private Date inicioreal;
    private Date finreal;
    private String estado;
    @ManyToOne
    @JoinColumn(name="DNI")
    private Cliente cliente;

    public Tarea() {
    }

    public Tarea(int idtarea, Proyecto proyecto, String titulo, String descripcion, Date inicioPrevisto, Date finPrevisto, Date inicioReal, Date finReal, String estado, Cliente cliente) {
        this.idtarea = idtarea;
        this.proyecto = proyecto;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.inicioprevisto = inicioPrevisto;
        this.finprevisto = finPrevisto;
        this.inicioreal = inicioReal;
        this.finreal = finReal;
        this.estado = estado;
        this.cliente = cliente;
    }

    public int getIdtarea() {
        return idtarea;
    }

    public void setIdtarea(int idtarea) {
        this.idtarea = idtarea;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getInicioPrevisto() {
        return inicioprevisto;
    }

    public void setInicioPrevisto(Date inicioPrevisto) {
        this.inicioprevisto = inicioPrevisto;
    }

    public Date getFinPrevisto() {
        return finprevisto;
    }

    public void setFinPrevisto(Date finPrevisto) {
        this.finprevisto = finPrevisto;
    }

    public Date getInicioReal() {
        return inicioreal;
    }

    public void setInicioReal(Date inicioReal) {
        this.inicioreal = inicioReal;
    }

    public Date getFinReal() {
        return finreal;
    }

    public void setFinReal(Date finReal) {
        this.finreal = finReal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
