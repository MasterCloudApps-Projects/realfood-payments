package es.urjc.realfood.payments.infrastructure.data

import es.urjc.realfood.payments.domain.Client
import es.urjc.realfood.payments.domain.ClientId
import es.urjc.realfood.payments.domain.repository.ClientRepository
import org.springframework.stereotype.Component

@Component
class PostgresClientRepository(
    private val jpaRepository: ClientJpaRepository
) : ClientRepository {

    override fun save(client: Client): Client = jpaRepository.save(client)

    override fun findById(id: ClientId): Client? = jpaRepository.findById(id).orElse(null)

    override fun delete(client: Client) = jpaRepository.delete(client)

}