package es.urjc.realfood.payments.infrastructure.api.rest

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.payments.application.GetClientBalanceRequest
import es.urjc.realfood.payments.application.GetClientBalanceResponse
import es.urjc.realfood.payments.application.UpdateBalanceRequest
import es.urjc.realfood.payments.application.UpdateBalanceResponse
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validBalanceDouble
import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClientIdString

abstract class ClientRestControllerTest {

    private val objectMapper = ObjectMapper()

    protected fun validGetClientBalanceRequest(): GetClientBalanceRequest {
        return GetClientBalanceRequest(
            clientId = validClientIdString()
        )
    }

    protected fun validGetClientBalanceResponse(): GetClientBalanceResponse {
        return GetClientBalanceResponse(
            clientId = validClientIdString(),
            balance = validBalanceDouble()
        )
    }

    protected fun validAddBalanceRequestJson(): String {
        return objectMapper.writeValueAsString(
            AddBalanceRequest(
                balance = validBalanceDouble()
            )
        )
    }

    protected fun validAddBalanceRequest(): UpdateBalanceRequest {
        return UpdateBalanceRequest(
            clientId = validClientIdString(),
            quantity = validBalanceDouble(),
            isPayment = false
        )
    }

    protected fun validAddBalanceResponse(): UpdateBalanceResponse {
        return UpdateBalanceResponse(
            clientId = validClientIdString(),
            actualBalance = validBalanceDouble()
        )
    }

}