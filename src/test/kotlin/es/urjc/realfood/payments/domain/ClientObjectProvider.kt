package es.urjc.realfood.payments.domain

class ClientObjectProvider {

    companion object {

        fun validClientId(): ClientId = ClientId("89a135b8-98dc-4e57-a22f-b5f99c6b1a00")

        fun validClientIdString(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a00"

        fun validOrderIdString(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a77"

        fun validBalance(): Balance {
            return Balance(validBalanceDouble())
        }

        fun validBalanceDouble(): Double = 100.0

        fun validClient(): Client {
            return Client(
                id = validClientId(),
                balance = validBalance()
            )
        }


    }

}