package es.urjc.realfood.payments

import es.urjc.realfood.payments.domain.Balance
import es.urjc.realfood.payments.domain.Client
import es.urjc.realfood.payments.domain.ClientId
import es.urjc.realfood.payments.domain.repository.ClientRepository
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

@Component
class DatabaseInitializer(
    private val clientRepository: ClientRepository
) {

    @PostConstruct
    fun setUp() {

        val cristofer = Client(
            id = ClientId(UUID.nameUUIDFromBytes("cristofer@cristofer.es".toByteArray()).toString()),
            balance = Balance(1000000.0)
        )

        clientRepository.save(cristofer)

        val juan = Client(
            id = ClientId(UUID.nameUUIDFromBytes("juan@juan.es".toByteArray()).toString()),
            balance = Balance(1000000.0)
        )

        clientRepository.save(juan)

    }

}