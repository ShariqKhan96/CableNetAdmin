package app.cabill.admin.remote

import android.provider.ContactsContract
import app.cabill.admin.model.*
import retrofit2.http.*

interface API {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<Int>

    @POST("agents")
    suspend fun createAgent(@Body agent: Agent): retrofit2.Response<Response<Agent>>

    @GET("agents")
    suspend fun agents(): Response<List<Agent>>

    @POST("agents/{id}")
    suspend fun updateAgent(@Path("id") id: Int, @Body agent: Agent?): Response<Agent>

    @GET("packages")
    suspend fun packages(): Response<List<Package>>

    @FormUrlEncoded
    @POST("packages")
    suspend fun createPackage(
        @Field("name") name: String,
        @Field("amount") amount: String,
        @Field("discount") discount: String,
        @Field("package_type_id") package_type: Int,
        @Field("package_category_id") package_cat: Int,
        @Field("is_active") boolean: Int
    ): Response<Package>

    @PUT("packages/{id}")
    suspend fun updatePackage(@Body pack: Package, @Path("id") id: Int): Response<Package>

    @GET("packages/create")
    suspend fun categoriesAndTypesList(): Response<CategoriesAndTypes>

    @GET("customers")
    suspend fun getCustomers(): Response<List<Customer>>

    @POST("customers")
    suspend fun createCustomer(@Body customer: Customer): Response<Customer>


    @GET("sub_localities")
    suspend fun getSubLocalities(): Response<List<SubLocality>>


    @POST("sub_localities")
    suspend fun createSubLocality(@Body subLocality: SubLocality): Response<SubLocality>

    @POST("area")
    suspend fun createArea(@Body area: Area): Response<Area>

    @GET("area")
    suspend fun getAreas(): Response<List<Area>>

    @GET("admin-profile")
    suspend fun getProfile(): retrofit2.Response<Response<Profile>>

    @GET("update")
    suspend fun updateProfile(): Response<Profile>

    @GET("customers/create")
    suspend fun getLists(): Response<SulLocalitiesAreaReligions>

    @GET("messages")
    suspend fun getMessages(): Response<List<Message>>

    @POST("messages")
    suspend fun createMessage(@Body message: Message): Response<Message>

    @GET("bills")
    suspend fun bills(): retrofit2.Response<Response<BillData>>

    @GET("dashboard")
    suspend fun dashboard(@Query("date") date: String): retrofit2.Response<Response<Dashboard>>

}