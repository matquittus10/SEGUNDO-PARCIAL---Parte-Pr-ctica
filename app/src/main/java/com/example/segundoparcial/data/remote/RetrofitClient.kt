package com.example.segundoparcial.data.remote

import com.example.segundoparcial.data.remote.api.ProductoApiService
import com.example.segundoparcial.data.remote.api.PublicacionApiService
import com.example.segundoparcial.data.remote.api.TipoCambioApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cliente único de Retrofit que provee las instancias de ApiService para las tres APIs.
 */
object RetrofitClient {

    private const val DUMMY_JSON_BASE_URL = "https://dummyjson.com/"
    private const val FRANKFURTER_BASE_URL = "https://api.frankfurter.dev/"
    private const val JSON_PLACEHOLDER_BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val clienteHttp: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private fun crearRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val productoApi: ProductoApiService by lazy {
        crearRetrofit(DUMMY_JSON_BASE_URL).create(ProductoApiService::class.java)
    }

    val tipoCambioApi: TipoCambioApiService by lazy {
        crearRetrofit(FRANKFURTER_BASE_URL).create(TipoCambioApiService::class.java)
    }

    val publicacionApi: PublicacionApiService by lazy {
        crearRetrofit(JSON_PLACEHOLDER_BASE_URL).create(PublicacionApiService::class.java)
    }
}
