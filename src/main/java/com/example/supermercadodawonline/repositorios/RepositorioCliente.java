package com.example.supermercadodawonline.repositorios;

import com.example.supermercadodawonline.model.jpa.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCliente extends JpaRepository<Cliente, String> {

}
