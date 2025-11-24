package com.sprint.ecommerce.services;

import com.sprint.ecommerce.dtos.request.PedidoRequestDtos.ItemDoPedidoRequest;
import com.sprint.ecommerce.dtos.request.PedidoRequestDtos.PedidoCreateRequest;
import com.sprint.ecommerce.dtos.responses.PedidoResponse;
import com.sprint.ecommerce.mappers.PedidoMapper;
import com.sprint.ecommerce.messaging.PedidoProducer;
import com.sprint.ecommerce.models.ItemPedido;
import com.sprint.ecommerce.models.Pedido;
import com.sprint.ecommerce.models.enums.StatusPedido;
import com.sprint.ecommerce.repository.ClienteRepository;
import com.sprint.ecommerce.repository.PedidoRepository;
import com.sprint.ecommerce.repository.ProdutoRepository;
import com.sprint.ecommerce.services.interfaces.PedidoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PedidoServiceImp  implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoMapper pedidoMapper;
    private final ProdutoRepository produtoRepository;
    private final PedidoProducer pedidoProducer;
    private static final Logger log = LoggerFactory.getLogger(PedidoServiceImp.class);

    public PedidoServiceImp(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
                            PedidoMapper pedidoMapper, ProdutoRepository produtoRepository, PedidoProducer pedidoProducer){

        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.pedidoMapper = pedidoMapper;
        this.produtoRepository = produtoRepository;
        this.pedidoProducer = pedidoProducer;
    }

    @Transactional
    @Override
    public PedidoResponse savePedido(PedidoCreateRequest pedidoRequest){

        log.info("Iniciando tentativa de salvar pedido...");

        var cliente = clienteRepository.findById(pedidoRequest.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente com o ID: " + pedidoRequest.clienteId() + "não encontrado."));

        var pedido = Pedido.builder()
                .cliente(cliente)
                . status(StatusPedido.PENDENTE)
                .valorTotal(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (ItemDoPedidoRequest itemRequest : pedidoRequest.itens()) {

            var produto = produtoRepository.findById(itemRequest.produtoId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto como o ID: " + itemRequest.produtoId() + "não encontrado."));

            BigDecimal precoAtual = produto.getPreco();

            var itemPedido = ItemPedido.builder()
                    .pedido(pedido)
                    .produto(produto)
                    .quantidade(itemRequest.quantidade())
                    .precoUnitario(precoAtual)
                    .build();

            pedido.getItens().add(itemPedido);
            total = total.add(precoAtual.multiply(BigDecimal.valueOf(itemRequest.quantidade())));
        }
        pedido.setValorTotal(total);

        var pedidoSalvo = pedidoRepository.save(pedido);

        var pedidoResponse =  pedidoMapper.pedidoToResponse(pedidoSalvo);

        pedidoProducer.enviarPedidoCriado(pedidoResponse);

        return pedidoResponse;
    }
}
