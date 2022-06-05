package es.urjc.realfood.payments.infrastructure.api.rest

import es.urjc.realfood.payments.application.*
import es.urjc.realfood.payments.infrastructure.api.security.JWTValidatorService
import org.springframework.web.bind.annotation.RestController

@RestController
class ClientRestController(
    private val getClientBalance: GetClientBalance,
    private val updateBalance: UpdateBalance,
    private val jwtValidatorService: JWTValidatorService
) : ClientRestApi {

    override fun getBalance(headers: Map<String, String>): GetClientBalanceResponse {
        val subject = jwtValidatorService.getSubjectFromHeaders(headers)
        return getClientBalance(
            GetClientBalanceRequest(
                clientId = subject
            )
        )
    }

    override fun addBalance(headers: Map<String, String>, addBalanceRequest: AddBalanceRequest): UpdateBalanceResponse {
        val subject = jwtValidatorService.getSubjectFromHeaders(headers)
        return updateBalance(
            UpdateBalanceRequest(
                clientId = subject,
                quantity = addBalanceRequest.balance,
                isPayment = false
            )
        )
    }

}