package es.urjc.realfood.payments.application

import es.urjc.realfood.payments.domain.services.PayedEvent
import es.urjc.realfood.payments.domain.services.PayedEventPublisher
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class SendPayment(
    private val payedEventPublisher: PayedEventPublisher
) {

    operator fun invoke(request: SendEventRequest) {
        payedEventPublisher(
            PayedEvent(
                clientId = request.clientId,
                orderId = request.orderId,
                success = request.success
            )
        )
    }

}

data class SendEventRequest(
    val clientId: String,
    val orderId: String,
    val success: Boolean
)