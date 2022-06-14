package com.example.helloworld.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWTUtil {

    private val secret = "jwt-secret" // TODO: inject with @Value("${jwt-secret}")

    @Throws(IllegalArgumentException::class, JWTCreationException::class)
    fun generateToken(email: String?): String {
        return JWT.create()
            .withSubject("User Details")
            .withClaim("email", email)
            .withIssuedAt(Date())
            .withIssuer("PPRO")
            .withExpiresAt(null)
            .sign(Algorithm.HMAC256(secret))
    }

    @Throws(JWTVerificationException::class)
    fun validateTokenAndRetrieveSubject(token: String?): String {
        val verifier = JWT.require(Algorithm.HMAC256(secret))
            .withSubject("User Details")
            .withIssuer("PPRO")
            .build()
        val jwt = verifier.verify(token)
        return jwt.getClaim("email").asString()
    }
}