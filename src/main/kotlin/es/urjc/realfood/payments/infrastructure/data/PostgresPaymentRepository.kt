package es.urjc.realfood.payments.infrastructure.data

import es.urjc.realfood.payments.domain.Payment
import es.urjc.realfood.payments.domain.repository.PaymentRepository
import org.springframework.stereotype.Component

@Component
class PostgresPaymentRepository(
    private val jpaRepository: PaymentJpaRepository
) : PaymentRepository {

    override fun save(payment: Payment): Payment = jpaRepository.save(payment)

}