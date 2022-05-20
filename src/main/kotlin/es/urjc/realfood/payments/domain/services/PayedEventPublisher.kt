package es.urjc.realfood.payments.domain.services

interface PayedEventPublisher {

    operator fun invoke(payedEvent: PayedEvent)

}

data class PayedEvent(
    val clientId: String,
    val orderId: String,
    val success: Boolean
)