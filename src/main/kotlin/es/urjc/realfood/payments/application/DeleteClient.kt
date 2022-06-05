package es.urjc.realfood.payments.application

import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.payments.domain.ClientId
import es.urjc.realfood.payments.domain.repository.ClientRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class DeleteClient(
    private val clientRepository: ClientRepository
) {

    operator fun invoke(request: DeleteClientRequest): DeleteClientResponse {
        val clientId = ClientId(request.clientId)

        val client = clientRepository.findById(clientId)
            ?: throw EntityNotFoundException("Client not found")

        clientRepository.delete(client)

        return DeleteClientResponse(
            clientId = request.clientId
        )
    }
}

data class DeleteClientRequest(
    val clientId: String
)

data class DeleteClientResponse(
    val clientId: String
)