package com.sprint.ecommerce.messaging;

import com.sprint.ecommerce.config.RabbitMQConfig;
import com.sprint.ecommerce.dtos.responses.PedidoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class PedidoProducer {

    private final RabbitTemplate rabbitTemplate;
    private static final Logger log = LoggerFactory.getLogger(PedidoProducer.class);

    public PedidoProducer(RabbitTemplate rabbitTemplate){

        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarPedidoCriado(PedidoResponse pedidoResponse){

        log.info("Mensageria: Enviando evento de Pedido Criado (ID: {}) para a fila...", pedidoResponse.id());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_PEDIDO,
                RabbitMQConfig.ROUTING_KEY_PEDIDO_CRIADO,
                pedidoResponse
        );

        log.info("Mensageria: Evento enviado com sucesso!");
    }
}
