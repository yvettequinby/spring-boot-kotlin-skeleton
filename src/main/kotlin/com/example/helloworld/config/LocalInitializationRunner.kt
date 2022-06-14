package com.example.helloworld.config

import com.example.helloworld.entity.HelloUser
import com.example.helloworld.repository.HelloUserRepo
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Profile("local")
class LocalInitializationRunner(
    private val helloUserRepo: HelloUserRepo,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {
    @Throws(Exception::class)
    override fun run(vararg args: String) {
        if (helloUserRepo.findByEmail("user@thing.com")!!.isEmpty) {
            val user = HelloUser()
            user.email = "user@thing.com"
            user.password = passwordEncoder.encode("user1Pass")
            helloUserRepo.save(user)
        }
    }
}