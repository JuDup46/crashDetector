package fr.esgi.crashdetector.api

import fr.esgi.crashdetector.models.User
import retrofit2.http.*

interface ApiService {

    companion object {
        const val BASE_URL: String = "http://46.105.14.4:8085/crashDetectorApi/"
        private const val URI: String = "user"
    }

    @GET(URI)
    suspend fun getUsers(): List<User>

    @GET("$URI/{email}")
    suspend fun getUserAsync(@Path("email") email: String): User

}
