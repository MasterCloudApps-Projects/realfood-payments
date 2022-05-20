package es.urjc.realfood.payments.infrastructure.data

import es.urjc.realfood.payments.domain.Payment
import es.urjc.realfood.payments.domain.PaymentId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentJpaRepository : JpaRepository<Payment, PaymentId>