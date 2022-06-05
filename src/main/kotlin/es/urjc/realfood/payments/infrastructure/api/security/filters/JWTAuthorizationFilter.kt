package es.urjc.realfood.payments.infrastructure.api.security.filters

import es.urjc.realfood.payments.domain.services.JWTService.Companion.CLIENT_ROLE
import es.urjc.realfood.payments.infrastructure.api.security.TOKEN_BEARER_PREFIX
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(
    authManager: AuthenticationManager,
    private val tokenSecret: String
) : BasicAuthenticationFilter(authManager) {

    private val log = LoggerFactory.getLogger(JWTAuthorizationFilter::class.java)

    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        val header = req.getHeader(AUTHORIZATION)
        if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX)) {
            chain.doFilter(req, res)
            return
        }
        SecurityContextHolder.getContext().authentication = getAuthentication(req)
        chain.doFilter(req, res)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(AUTHORIZATION)
        if (token != null) {
            val claims = getClaims(token)
            if (claims == null || claims.isEmpty()) {
                log.error("JWT '$token' cannot be decoded")
                return null
            }
            if (CLIENT_ROLE != claims["role"]) {
                log.error("You aren't a client")
                return null
            }
            val valid = LocalDateTime.now()
                .isBefore(LocalDateTime.ofInstant(claims.expiration.toInstant(), ZoneId.systemDefault()))
            if (!valid) {
                log.error("Expired JWT '$token'")
                return null
            }
            val user = claims.subject
            return UsernamePasswordAuthenticationToken(user, null, ArrayList())
        }
        return null
    }

    private fun getClaims(jwt: String): Claims? {
        return try {
            Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(tokenSecret.encodeToByteArray()).toString())
                .parseClaimsJws(jwt.replace(TOKEN_BEARER_PREFIX, ""))
                .body
        } catch (ex: Exception) {
            log.error("Error parsing JWT: ${ex.message}")
            null
        }
    }

}