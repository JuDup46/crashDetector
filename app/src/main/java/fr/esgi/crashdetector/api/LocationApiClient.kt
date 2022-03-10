package fr.esgi.crashdetector.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LocationApiClient {

    private const val BASE_URL: String = LocationApiService.BASE_URL

    private val apiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LocationApiClient::class.java)

    suspend fun sendCall(email: String, coordinate: String) {
        return this.apiService.sendCall(email, coordinate)
    }
}