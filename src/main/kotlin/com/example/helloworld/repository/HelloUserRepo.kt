package com.example.helloworld.repository

import com.example.helloworld.entity.HelloUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface HelloUserRepo : JpaRepository<HelloUser?, Long?> {
    fun findByEmail(email: String?): Optional<HelloUser?>?
}