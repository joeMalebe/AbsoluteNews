package com.example.absolutesport.network

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Source(@JsonProperty("id")val id:String?,@JsonProperty("name")val name:String?)