package es.urjc.realfood.payments.application

import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validBalanceDouble
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClientIdString

abstract class GetClientBalanceTest {

    protected fun validGetClientBalanceRequest(): GetClientBalanceRequest {
        return GetClientBalanceRequest(
            clientId = validClientIdString()
        )
    }

    protected fun invalidGetClientBalanceRequest(): GetClientBalanceRequest {
        return GetClientBalanceRequest(
            clientId = "INVALID"
        )
    }

    protected fun validGetClientBalanceResponse(): GetClientBalanceResponse {
        return GetClientBalanceResponse(
            clientId = validClientIdString(),
            balance = validBalanceDouble()
        )
    }

}