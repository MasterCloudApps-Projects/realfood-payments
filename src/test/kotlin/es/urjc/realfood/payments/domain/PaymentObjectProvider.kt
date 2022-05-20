package es.urjc.realfood.payments.domain

import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClient
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validOrderIdString
import es.urjc.realfood.payments.domain.services.PayedEvent

class PaymentObjectProvider {

    companion object {

        fun validPaymentId(): PaymentId = PaymentId("89a135b8-98dc-4e57-a22f-b5f99c6b1a99")

        fun validPaymentIdString(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a99"

        fun validQuantity(): Quantity {
            return Quantity(validQuantityDouble())
        }

        fun validQuantityDouble(): Double = 50.0

        fun validPayment(): Payment {
            return Payment(
                id = validPaymentId(),
                quantity = validQuantity(),
                client = validClient()
            )
        }

        fun validPayedEvent(): PayedEvent {
            return PayedEvent(
                clientId = validClientIdString(),
                orderId = validOrderIdString(),
                success = true
            )
        }

    }

}