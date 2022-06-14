package com.example.helloworld.security

import com.example.helloworld.repository.HelloUserRepo
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class HelloUserDetailsService(private val helloUserRepo: HelloUserRepo) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val oUser = helloUserRepo.findByEmail(email)
        if (oUser!!.isEmpty) throw UsernameNotFoundException("Could not find User with email = $email")
        val user = oUser.get()
        return User(
            email,
            user.password, listOf(SimpleGrantedAuthority("ROLE_USER"))
        )
    }

}