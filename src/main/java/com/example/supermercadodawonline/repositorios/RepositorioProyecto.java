package com.example.supermercadodawonline.repositorios;

import com.example.supermercadodawonline.model.jpa.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioProyecto extends JpaRepository<Proyecto, Integer> {
    @Query("SELECT MAX(p.idproyecto) FROM Proyecto p")
    Integer findMaxId();
}