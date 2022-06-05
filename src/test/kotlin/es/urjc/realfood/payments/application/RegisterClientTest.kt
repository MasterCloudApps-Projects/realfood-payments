package es.urjc.realfood.payments.application

import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClientIdString

abstract class RegisterClientTest {

    protected fun validRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            clientId = validClientIdString()
        )
    }

    protected fun invalidRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            clientId = "INVALID"
        )
    }

    protected fun validRegisterClientResponse(): RegisterClientResponse {
        return RegisterClientResponse(
            clientId = validClientIdString()
        )
    }

}