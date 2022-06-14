package com.example.helloworld.controller

import com.example.helloworld.dto.CredentialsDto
import com.example.helloworld.security.JWTUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class AuthController(private val jwtUtil: JWTUtil, private val authenticationManager: AuthenticationManager) {


    @PostMapping("/auth")
    fun loginHandler(@RequestBody body: CredentialsDto): Map<String?, Any?>? {
        return try {
            val authInputToken = UsernamePasswordAuthenticationToken(body.email, body.password)
            authenticationManager.authenticate(authInputToken)
            val token: String = jwtUtil.generateToken(body.email)
            Collections.singletonMap("jwt-token", token)
        } catch (authExc: AuthenticationException) {
            throw RuntimeException("Invalid Login Credentials")
        }
    }

}