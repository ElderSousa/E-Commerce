package com.sprint.ecommerce.messaging;

import com.sprint.ecommerce.config.RabbitMQConfig;
import com.sprint.ecommerce.dtos.responses.PedidoResponse;
import com.sprint.ecommerce.exceptions.dtos.ResourceNotFoundException;
import com.sprint.ecommerce.models.Cliente;
import com.sprint.ecommerce.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class NotificacaoConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificacaoConsumer.class);
    private JavaMailSender emailSender;
    private final ClienteRepository clienteRepository;

    public NotificacaoConsumer(JavaMailSender emailSender, ClienteRepository clienteRepository) {

        this.emailSender = emailSender;
        this.clienteRepository = clienteRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PEDIDO_CRIADO)
    public void receberNotificacao(PedidoResponse pedidoResponse) {
        try{
            log.info("START: Processando notificação do Pedido #{}", pedidoResponse.id());

            var cliente = clienteRepository.findById(pedidoResponse.clienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente", "Id", pedidoResponse.clienteId()));

            String emailDestinatario = cliente.getUsuario().getEmail();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@sprint-ecommerce.com");
            message.setTo(emailDestinatario);
            message.setSubject("Confirmação do Pedido #" + pedidoResponse.id());
            message.setText(montarCorpoDoEmail(pedidoResponse, cliente));

            emailSender.send(message);

            log.info("SUCESSO: E-mail enviado para: {}", emailDestinatario);
            log.info("===================================================");
        }catch (Exception e) {
            log.error(" ERRO: Falha ao enviar e-mail para o pedido {}: {}", pedidoResponse.id(), e.getMessage());
        }

    }

    private String montarCorpoDoEmail(PedidoResponse pedido, Cliente cliente) {
        return String.format("""
                Olá, %s!
                
                Recebemos o seu pedido com sucesso. Estamos preparando tudo para envio.
                
                --------------------------------------
                DADOS DO PEDIDO
                --------------------------------------
                Número: #%d
                Status: %s
                Data: %s
                
                TOTAL: R$ %.2f
                --------------------------------------
                
                Obrigado por comprar no Sprint E-commerce!
                """,
                cliente.getNomeCompleto(),
                pedido.id(),
                pedido.status(),
                pedido.dataPedido(),
                pedido.valorTotal());
    }
}
