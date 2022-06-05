package es.urjc.realfood.payments.application

import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.payments.domain.ClientId
import es.urjc.realfood.payments.domain.repository.ClientRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class GetClientBalance(
    private val clientRepository: ClientRepository
) {

    operator fun invoke(request: GetClientBalanceRequest): GetClientBalanceResponse {
        val clientId = ClientId(request.clientId)

        val client = clientRepository.findById(clientId)
            ?: throw EntityNotFoundException("Client not found")

        return GetClientBalanceResponse(
            clientId = request.clientId,
            balance = client.balance.value()
        )
    }
}

data class GetClientBalanceRequest(
    val clientId: String
)

data class GetClientBalanceResponse(
    val clientId: String,
    val balance: Double
)