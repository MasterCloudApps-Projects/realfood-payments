package es.urjc.realfood.payments.application

import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validOrderIdString

abstract class SendPaymentTest {

    protected fun validSendPaymentRequest(): SendEventRequest {
        return SendEventRequest(
            clientId = validClientIdString(),
            orderId = validOrderIdString(),
            success = true
        )
    }

}