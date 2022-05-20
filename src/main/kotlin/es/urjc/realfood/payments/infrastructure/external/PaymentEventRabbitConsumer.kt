package es.urjc.realfood.payments.infrastructure.external

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.payments.application.UpdateBalance
import es.urjc.realfood.payments.application.UpdateBalanceRequest
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class PaymentEventRabbitConsumer(
    private val updateBalance: UpdateBalance
) {

    private val logger = LoggerFactory.getLogger(PaymentEventRabbitConsumer::class.java)

    private val objectMapper = ObjectMapper()

    @RabbitListener(queues = ["checkout-cart"], ackMode = "AUTO")
    private fun consume(message: String) {
        val paymentEvent = objectMapper.readValue(message, PaymentEvent::class.java)
        try {
            updateBalance(
                UpdateBalanceRequest(
                    clientId = paymentEvent.clientId,
                    quantity = paymentEvent.price,
                    isPayment = true
                )
            )
            logger.info(
                "[Consumed] payment request from client '{}' and order '{}'",
                paymentEvent.clientId,
                paymentEvent.orderId
            )
        } catch (exc: Exception) {
            logger.info(
                "[ERROR] payment not processed for client '{}', reason:\n{}",
                paymentEvent.clientId,
                exc.message
            )
        }
    }

}

data class PaymentEvent(
    val clientId: String,
    val orderId: String,
    val price: Double
)