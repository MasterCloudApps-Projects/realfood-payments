package es.urjc.realfood.payments.infrastructure.external

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.payments.application.RegisterClient
import es.urjc.realfood.payments.application.RegisterClientRequest
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RegisterEventRabbitConsumer(
    private val registerClient: RegisterClient
) {

    private val logger = LoggerFactory.getLogger(RegisterEventRabbitConsumer::class.java)

    private val objectMapper = ObjectMapper()

    @RabbitListener(queues = ["register-queue"])
    private fun consume(message: String) {
        val registerEvent = objectMapper.readValue(message, RegisterEvent::class.java)
        try {
            registerClient(
                RegisterClientRequest(
                    clientId = registerEvent.clientId
                )
            )

            logger.info(
                "[Consumed] register request for client '{}'",
                registerEvent.clientId
            )
        } catch (exc: Exception) {
            logger.info(
                "[ERROR] client with id '{}' not registered for payments, reason:\n{}",
                registerEvent.clientId,
                exc.message
            )
        }
    }
}

data class RegisterEvent(
    val clientId: String
)