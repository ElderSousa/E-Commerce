package com.sprint.ecommerce.services.interfaces;

import com.sprint.ecommerce.dtos.request.PedidoRequestDtos.PedidoCreateRequest;
import com.sprint.ecommerce.dtos.responses.PedidoResponse;

public interface PedidoService {

    PedidoResponse savePedido(PedidoCreateRequest pedidoRequest);
}
