package com.sprint.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.ecommerce.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
