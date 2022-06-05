package es.urjc.realfood.payments.infrastructure.api.security.filters

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.payments.infrastructure.api.security.ISSUER_INFO
import es.urjc.realfood.payments.infrastructure.api.security.TOKEN_BEARER_PREFIX
import es.urjc.realfood.payments.infrastructure.api.security.TOKEN_EXPIRATION_TIME
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val tokenSecret: String
) :
    UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {
        return try {
            val credentials: User = ObjectMapper().readValue(
                request.inputStream,
                User::class.java
            )
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    credentials.username, credentials.password, emptyList()
                )
            )
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain,
        auth: Authentication
    ) {
        val token: String = Jwts.builder().setIssuedAt(Date()).setIssuer(ISSUER_INFO)
            .setSubject((auth.principal as User).username)
            .setExpiration(Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, tokenSecret).compact()
        response.addHeader(
            AUTHORIZATION,
            "$TOKEN_BEARER_PREFIX $token"
        )
    }
}