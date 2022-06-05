package es.urjc.realfood.payments.domain.repository

import es.urjc.realfood.payments.domain.Client
import es.urjc.realfood.payments.domain.ClientId

interface ClientRepository {

    fun save(client: Client): Client

    fun findById(id: ClientId): Client?

    fun delete(client: Client)

}