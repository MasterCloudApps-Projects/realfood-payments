package es.urjc.realfood.payments.infrastructure.data

import es.urjc.realfood.payments.domain.Client
import es.urjc.realfood.payments.domain.ClientId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientJpaRepository : JpaRepository<Client, ClientId>