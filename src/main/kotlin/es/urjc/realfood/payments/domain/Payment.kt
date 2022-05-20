package es.urjc.realfood.payments.domain

import javax.persistence.*

@Entity
class Payment(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    val id: PaymentId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "quantity"))
    val quantity: Quantity,

    @ManyToOne
    val client: Client
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Payment

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Payment(id=$id, quantity=$quantity, client=$client)"
    }

}