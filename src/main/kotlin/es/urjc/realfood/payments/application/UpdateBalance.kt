package es.urjc.realfood.payments.application

import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.payments.domain.*
import es.urjc.realfood.payments.domain.repository.ClientRepository
import es.urjc.realfood.payments.domain.repository.PaymentRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class UpdateBalance(
    private val clientRepository: ClientRepository,
    private val paymentRepository: PaymentRepository
) {

    operator fun invoke(request: UpdateBalanceRequest): UpdateBalanceResponse {
        val clientId = ClientId(request.clientId)

        val client = clientRepository.findById(clientId)
            ?: throw EntityNotFoundException("Client not found")

        val quantity = Quantity(request.quantity)

        val balanceToAddOrRemove: Double =
            if (request.isPayment) request.quantity * -1
            else request.quantity

        client.balance = Balance(client.balance.value() + balanceToAddOrRemove)

        clientRepository.save(client)

        if (request.isPayment) {
            val payment = Payment(
                id = PaymentId(UUID.randomUUID().toString()),
                quantity = quantity,
                client = client
            )
            paymentRepository.save(payment)
        }

        return UpdateBalanceResponse(
            clientId = request.clientId,
            actualBalance = client.balance.value()
        )
    }

}

data class UpdateBalanceRequest(
    val clientId: String,
    val quantity: Double,
    val isPayment: Boolean
)

data class UpdateBalanceResponse(
    val clientId: String,
    val actualBalance: Double
)