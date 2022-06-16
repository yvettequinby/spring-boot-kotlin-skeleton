package com.example.helloworld.service

import io.micrometer.core.instrument.MeterRegistry
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class HelloService(private val meterRegistry: MeterRegistry) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun helloWorld() {
        log.debug("I am logging debug!")
        log.info("I am logging info!")
        meterRegistry.counter("hello-custom-counter").increment()
    }
}