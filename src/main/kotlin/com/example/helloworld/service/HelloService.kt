package com.example.helloworld.service

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Service

@Service
class HelloService(private val meterRegistry: MeterRegistry) {

    fun helloWorld() {
        meterRegistry.counter("hello-custom-counter").increment()
    }
}