package es.urjc.realfood.payments.infrastructure.api.security

import java.time.Duration

const val HEADER_AUTHORIZATION_KEY = "Authorization"
const val TOKEN_BEARER_PREFIX = "Bearer "

const val ISSUER_INFO = "realfood-inc"
val TOKEN_EXPIRATION_TIME = Duration.ofHours(1).toMillis()
