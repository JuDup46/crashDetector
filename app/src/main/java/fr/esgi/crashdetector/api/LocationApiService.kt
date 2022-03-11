package fr.esgi.crashdetector.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface LocationApiService {

    companion object {
        const val BASE_URL: String = "http://iagain.fr:8085/crashDetectorApi/"
        private const val URI: String = "twillio"
    }

    @GET("$URI/{email}/{coord}")
    suspend fun sendCall(@Path("email") email: String, @Path("coord") coordinate: String): Response<ResponseBody>

}
