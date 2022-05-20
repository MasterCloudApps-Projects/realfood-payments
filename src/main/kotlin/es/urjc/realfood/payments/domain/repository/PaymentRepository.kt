package es.urjc.realfood.payments.domain.repository

import es.urjc.realfood.payments.domain.Payment

interface PaymentRepository {

    fun save(payment: Payment): Payment

}