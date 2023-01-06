package api

import api.data.Data
import api.data.Users
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {
    @GET("/rest/v1/login?select=*")
    suspend fun get(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String
    ) : Response<List<Data>>

    @POST("/auth/v1/signup")
    suspend fun signUp(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Body data : Users
    ) : Response<ResponseBody>

    @POST("/auth/v1/token?grant_type=password")
    suspend fun signIn(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Body data : Users
    ) : Response<ResponseBody>

    @POST("/rest/v1/login")
    suspend fun create(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Body data: Data
    )
}