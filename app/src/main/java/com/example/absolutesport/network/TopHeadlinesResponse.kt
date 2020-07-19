package com.example.absolutesport.network

import com.fasterxml.jackson.annotation.JsonProperty

data class TopHeadlinesResponse(@JsonProperty("status")val status:String?,
                                @JsonProperty("totalResults")val totalResults:Int?,
                                @JsonProperty("articles")val articles: List<Article>?
                                )