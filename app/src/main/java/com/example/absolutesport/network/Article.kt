package com.example.absolutesport.network

import com.fasterxml.jackson.annotation.JsonClassDescription
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Article(
    @JsonProperty("author") val author: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("url") val url: String,
    @JsonProperty("urlToImage") val urlToImage: String,
    @JsonProperty("publishedAt") val pulishedDate: String,
    @JsonProperty("content") val content: String,
    @JsonProperty("source") val source: Source

);
