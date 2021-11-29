package ru.oktemsec.worldcinema

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RegisterService {
    @POST("/auth/register")
    fun registerUser(@Body registerRequest:RegisterRequest): Call<RegisterResponse>
}