package com.example.book_store

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class NewUser :Serializable{
    @JsonProperty("username")
    var login: String? = null


    @JsonProperty("Age")
    var userAge: Int ?= null

    @JsonProperty("password")
    var password: String? = null

    constructor() {}

    constructor(username: String, age: Int, password: String, recaptchaToken: String) {
        this.login = username
        this.userAge = age
        this.password = password
    }

    companion object {
        private const val serialVersionUID = -1764970284520387975L
    }
}