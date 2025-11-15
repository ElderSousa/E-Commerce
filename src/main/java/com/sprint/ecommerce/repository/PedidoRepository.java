package com.sprint.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.ecommerce.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
