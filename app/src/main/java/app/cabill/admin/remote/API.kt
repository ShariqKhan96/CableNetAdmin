package app.cabill.admin.remote

import app.cabill.admin.model.Agent
import app.cabill.admin.model.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface API {
    @POST("agents")
    suspend fun createAgent(@Body agent: Agent): Response<Agent>

    @GET("agents")
    suspend fun agents(): Response<List<Agent>>

    @POST("agents/{id}")
    suspend fun updateAgent(@Path("id") id: Int, @Body agent: Agent?): Response<Agent>
}