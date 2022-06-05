package es.urjc.realfood.payments.domain

import es.urjc.realfood.payments.domain.services.JWTService.Companion.CLIENT_ROLE
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.time.Instant
import java.util.*

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

        fun validNewClient(): Client {
            return Client(
                id = validClientId(),
                balance = Balance(0.0)
            )
        }

        fun validJwt(): String {
            return Jwts.builder()
                .setIssuedAt(Date.from(Instant.now()))
                .setSubject(validClientIdString())
                .setIssuer("realfood-auth")
                .setExpiration(Date.from(Instant.now().plusSeconds(1800)))
                .addClaims(mapOf("role" to CLIENT_ROLE))
                .signWith(
                    SignatureAlgorithm.HS256,
                    Base64.getEncoder().encodeToString("1234".encodeToByteArray()).toString()
                )
                .compact()
        }

    }

}