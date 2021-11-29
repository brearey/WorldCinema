package ru.oktemsec.worldcinema

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    fun getRetrofit():Retrofit {
        val httpLoggingInterceptor:HttpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient:OkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val gson:Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit:Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://cinema.areas.su")
            .build()

        return retrofit
    }

    fun getRegister(): RegisterService {
        return getRetrofit().create(RegisterService::class.java)
    }

//    fun getLogin(): LoginService {
//        return getRetrofit().create(LoginService::class.java)
//    }
}