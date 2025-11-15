package com.sprint.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.ecommerce.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
