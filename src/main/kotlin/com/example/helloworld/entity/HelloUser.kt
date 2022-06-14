package com.example.helloworld.entity

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
class HelloUser {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="hello_user_id")
    var id: Long? = null

    var email: String? = null

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var password: String? = null
}