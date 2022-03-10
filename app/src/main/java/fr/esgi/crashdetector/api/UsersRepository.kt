package fr.esgi.crashdetector.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UsersRepository {

    private var apiService: ApiService? = null

    init {
        this.apiService = Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    suspend fun getUsers() = this.apiService?.getUsers()

    suspend fun getUser(email: String) = this.apiService?.getUser(email)

}