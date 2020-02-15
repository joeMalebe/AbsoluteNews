package com.example.absolutesport.network

import com.fasterxml.jackson.annotation.JsonProperty

data class Sport(
    @JsonProperty("id") val id: Int, @JsonProperty("name") val name: String, @JsonProperty(
        "slug"
    ) val slug: String
)
