package com.sprint.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.ecommerce.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
