package es.urjc.realfood.payments.domain

import java.io.Serializable
import java.util.*
import javax.persistence.Embeddable

@Embeddable
class PaymentId(private val value: String) : Serializable {

    init {
        UUID.fromString(value)
    }

    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PaymentId

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

}