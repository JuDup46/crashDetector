package fr.esgi.crashdetector.api

import fr.esgi.crashdetector.models.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    companion object {
        const val BASE_URL: String = "http://localhost:3003/crashDetectorApi/"
        private const val URI: String = "users"
    }

    @GET(URI)
    suspend fun getUsers(): List<User>

    @GET(URI)
    suspend fun getUser(@Path(value = "email") email: String): User

}
