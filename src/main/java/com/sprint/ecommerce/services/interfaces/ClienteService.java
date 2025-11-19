package com.sprint.ecommerce.services.interfaces;

import com.sprint.ecommerce.dtos.request.ClienteRequestDtos.ClienteCreateRequest;
import com.sprint.ecommerce.dtos.responses.ClienteResponse;

public interface ClienteService {

    ClienteResponse saveClient(ClienteCreateRequest clienteRequest);
}
