package com.example.helloworld.controller

import com.example.helloworld.dto.HelloDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping
    fun index(): HelloDto = HelloDto(1L, "Hello World!")
}