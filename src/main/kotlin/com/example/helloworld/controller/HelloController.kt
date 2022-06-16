package com.example.helloworld.controller

import com.example.helloworld.dto.HelloDto
import com.example.helloworld.service.HelloService
import io.micrometer.core.annotation.Timed
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(private val helloService: HelloService) {

    @GetMapping
    @Timed("hello-world-endpoint")
    fun index(): HelloDto {
        helloService.helloWorld()
        return HelloDto(1L, "Hello World!")
    }
}