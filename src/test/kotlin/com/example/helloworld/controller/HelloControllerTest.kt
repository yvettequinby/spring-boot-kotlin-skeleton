package com.example.helloworld.controller

import com.example.helloworld.service.HelloService
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(MockitoExtension::class)
class HelloControllerTest {

    private var mockMvc: MockMvc? = null

    @Mock
    private val helloService: HelloService? = null

    @InjectMocks
    private val helloController: HelloController? = null


    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(helloController).build()
    }


    @Test
    @Throws(Exception::class)
    fun testIndex() {
        mockMvc!!.perform(
            MockMvcRequestBuilders.get("/")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.text", Matchers.equalTo("Hello World!")))
        Mockito.verify(helloService, Mockito.times(1))?.helloWorld()
        Mockito.verifyNoMoreInteractions(helloService)
    }

}