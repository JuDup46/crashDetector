package fr.esgi.crashdetector.api

import fr.esgi.crashdetector.models.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL: String = ApiService.BASE_URL

    private val apiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    suspend fun getUsers(): List<User> {
        return this.apiService.getUsers()
    }

    suspend fun getUser(email: String): User {
        return this.apiService.getUserAsync(email)
    }


}