package com.example.absolutesport.network

import retrofit2.http.GET

interface Api {

    @GET("5e4811c73000007400294a73")
    fun getSports():List<Sport>
}
