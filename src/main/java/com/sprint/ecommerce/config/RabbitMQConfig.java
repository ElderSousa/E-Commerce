package com.sprint.ecommerce.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_PEDIDO_CRIADO = "notificacoes.pedido-criado";
    public static final String EXCHANGE_PEDIDO = "exchange.pedido";
    public static final String ROUTING_KEY_PEDIDO_CRIADO = "pedido.criado";

    @Bean
    public Queue pedidoCriadoQueue(){

        return new Queue(QUEUE_PEDIDO_CRIADO, true);
    }

    @Bean
    public DirectExchange pedidoExchange(){

        return new DirectExchange(EXCHANGE_PEDIDO);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange){

        return BindingBuilder.bind(queue).to(directExchange).with(ROUTING_KEY_PEDIDO_CRIADO);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){

        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
