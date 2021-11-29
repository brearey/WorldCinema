package ru.oktemsec.worldcinema

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/auth/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>
}