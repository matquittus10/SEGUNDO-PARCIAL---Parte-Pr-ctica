package com.example.segundoparcial.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cliente de Retrofit configurado para las peticiones en red.
 */
object RetrofitClient {
    // IMPORTANTE: Recuerda colocar el slash "/" al final de la URL base
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/" 

    private val clienteHttp: OkHttpClient = OkHttpClient.Builder()
        // Muestra en el Logcat las peticiones e intercambios JSON
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
