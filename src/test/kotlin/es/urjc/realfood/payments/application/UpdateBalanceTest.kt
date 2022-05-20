package es.urjc.realfood.payments.application

import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validBalanceDouble
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.payments.domain.PaymentObjectProvider.Companion.validQuantityDouble

abstract class UpdateBalanceTest {

    protected fun validAddBalanceRequest(): UpdateBalanceRequest {
        return UpdateBalanceRequest(
            clientId = validClientIdString(),
            quantity = validQuantityDouble(),
            isPayment = false
        )
    }

    protected fun validAddBalanceResponse(): UpdateBalanceResponse {
        return UpdateBalanceResponse(
            clientId = validClientIdString(),
            actualBalance = validBalanceDouble() + validQuantityDouble()
        )
    }

    protected fun validRemoveBalanceRequest(): UpdateBalanceRequest {
        return UpdateBalanceRequest(
            clientId = validClientIdString(),
            quantity = validQuantityDouble(),
            isPayment = true
        )
    }

    protected fun validRemoveBalanceResponse(): UpdateBalanceResponse {
        return UpdateBalanceResponse(
            clientId = validClientIdString(),
            actualBalance = validBalanceDouble() - validQuantityDouble()
        )
    }

    protected fun invalidClientIdRequest(): UpdateBalanceRequest {
        return UpdateBalanceRequest(
            clientId = "INVALID",
            quantity = validQuantityDouble(),
            isPayment = false
        )
    }

    protected fun invalidQuantityRequest(): UpdateBalanceRequest {
        return UpdateBalanceRequest(
            clientId = validClientIdString(),
            quantity = -10000.0,
            isPayment = false
        )
    }

    protected fun invalidBalanceRequest(): UpdateBalanceRequest {
        return UpdateBalanceRequest(
            clientId = validClientIdString(),
            quantity = 10000.0,
            isPayment = true
        )
    }

}