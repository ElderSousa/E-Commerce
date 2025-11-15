package com.sprint.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.ecommerce.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
