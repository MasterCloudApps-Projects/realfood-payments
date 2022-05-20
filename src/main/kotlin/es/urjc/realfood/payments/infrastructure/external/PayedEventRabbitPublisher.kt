package es.urjc.realfood.payments.infrastructure.external

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.payments.domain.services.PayedEvent
import es.urjc.realfood.payments.domain.services.PayedEventPublisher
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class PayedEventRabbitPublisher(
    private val rabbitTemplate: RabbitTemplate
) : PayedEventPublisher {

    private val logger = LoggerFactory.getLogger(PayedEventRabbitPublisher::class.java)

    private val objectMapper = ObjectMapper()

    private val queueName: String = "payments"

    override fun invoke(payedEvent: PayedEvent) {
        val msg: String = objectMapper.writeValueAsString(payedEvent)
        rabbitTemplate.convertAndSend(queueName, msg)
        logger.info(
            "[Published] payment response sent for client: '{}' and order '{}'",
            payedEvent.clientId,
            payedEvent.orderId
        )
    }
}