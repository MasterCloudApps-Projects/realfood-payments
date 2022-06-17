package es.urjc.realfood.payments.infrastructure.external

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import es.urjc.realfood.payments.application.DeleteClient
import es.urjc.realfood.payments.application.DeleteClientRequest
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class DeletionEventRabbitConsumer(
    private val deleteClient: DeleteClient
) {

    private val logger = LoggerFactory.getLogger(DeletionEventRabbitConsumer::class.java)

    private val objectMapper = ObjectMapper()
        .registerKotlinModule()
    @RabbitListener(queues = ["delete-client-queue"])
    private fun consume(message: String) {
        val deletionEvent = objectMapper.readValue(message, DeletionEvent::class.java)
        try {
            deleteClient(
                DeleteClientRequest(
                    clientId = deletionEvent.clientId
                )
            )

            logger.info(
                "[Consumed] delete client request for client '{}'",
                deletionEvent.clientId
            )
        } catch (exc: Exception) {
            logger.info(
                "[ERROR] client with id '{}' not deleted for payments, reason:\n{}",
                deletionEvent.clientId,
                exc.message
            )
        }
    }
}

data class DeletionEvent(
    val clientId: String
)