package es.urjc.realfood.payments.domain

import javax.persistence.*

@Entity
class Client(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    val id: ClientId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "balance"))
    var balance: Balance
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Client

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Client(id=$id, balance=$balance)"
    }

}