package app.cabill.admin.remote

import android.provider.ContactsContract
import app.cabill.admin.model.Agent
import app.cabill.admin.model.CategoriesAndTypes
import app.cabill.admin.model.Package
import app.cabill.admin.model.Response
import retrofit2.http.*

interface API {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(@Field("email") email: String, @Field("password") password: String):Response<Int>

    @POST("agents")
    suspend fun createAgent(@Body agent: Agent): Response<Agent>

    @GET("agents")
    suspend fun agents(): Response<List<Agent>>

    @POST("agents/{id}")
    suspend fun updateAgent(@Path("id") id: Int, @Body agent: Agent?): Response<Agent>

    @GET("packages")
    suspend fun packages(): Response<List<Package>>

    @POST("packages")
    suspend fun createPackage(@Body pack: Package): Response<Package>

    @PUT("packages/{id}")
    suspend fun updatePackage(@Body pack: Package, @Path("id") id: Int): Response<Package>

    @GET("packages/create")
    suspend fun categoriesAndTypesList(): Response<CategoriesAndTypes>
}