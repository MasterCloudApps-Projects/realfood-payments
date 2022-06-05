package es.urjc.realfood.payments.application

import es.urjc.realfood.payments.domain.ClientObjectProvider.Companion.validClientIdString

abstract class DeleteClientTest {

    protected fun validDeleteClientRequest(): DeleteClientRequest {
        return DeleteClientRequest(
            clientId = validClientIdString()
        )
    }

    protected fun invalidDeleteClientRequest(): DeleteClientRequest {
        return DeleteClientRequest(
            clientId = "INVALID"
        )
    }

    protected fun validDeleteClientResponse(): DeleteClientResponse {
        return DeleteClientResponse(
            clientId = validClientIdString()
        )
    }

}