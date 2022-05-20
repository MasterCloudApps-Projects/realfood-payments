package es.urjc.realfood.payments.domain

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
class Balance(private val value: Double) : Serializable {

    init {
        if (value < 0)
            throw IllegalArgumentException("Balance cannot be negative")
    }

    fun value() = value

    override fun toString(): String {
        return value.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Balance

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}