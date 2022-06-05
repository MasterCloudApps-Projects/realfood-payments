package es.urjc.realfood.payments.infrastructure.api.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JWTValidatorService(
    @Value("\${jwt.secret}") private val tokenSecret: String
) {

    fun getSubjectFromHeaders(headers: Map<String, String>): String {
        val jwt = getJwtFromHeaders(headers)
        val claims = decodeJwt(jwt)
        return getSubjectFromClaims(claims)
    }

    private fun getJwtFromHeaders(headers: Map<String, String>): String {
        val authHeaders = headers[HEADER_AUTHORIZATION_KEY.lowercase()]!!
        return authHeaders.substring(
            TOKEN_BEARER_PREFIX.length,
            authHeaders.length
        )
    }

    private fun decodeJwt(jwt: String): Claims {
        return Jwts.parser()
            .setSigningKey(Base64.getEncoder().encodeToString(tokenSecret.encodeToByteArray()).toString())
            .parseClaimsJws(jwt)
            .body
    }

    private fun getSubjectFromClaims(claims: Claims): String {
        return claims.subject
    }

}