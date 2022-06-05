package es.urjc.realfood.payments.application

import es.urjc.realfood.payments.domain.Balance
import es.urjc.realfood.payments.domain.Client
import es.urjc.realfood.payments.domain.ClientId
import es.urjc.realfood.payments.domain.repository.ClientRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class RegisterClient(
    private val clientRepository: ClientRepository
) {

    operator fun invoke(request: RegisterClientRequest): RegisterClientResponse {
        val clientId = ClientId(request.clientId)

        if (clientRepository.findById(clientId) != null) {
            throw IllegalArgumentException("User already registered with id ${request.clientId}")
        } else {
            clientRepository.save(
                Client(
                    id = clientId,
                    balance = Balance(0.0)
                )
            )
        }

        return RegisterClientResponse(
            clientId = request.clientId
        )
    }
}

data class RegisterClientRequest(
    val clientId: String
)

data class RegisterClientResponse(
    val clientId: String
)