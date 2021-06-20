package app.cabill.admin.remote

import android.provider.ContactsContract
import app.cabill.admin.model.*
import retrofit2.http.*

interface API {
    @GET("auth/verify")
    suspend fun verifyPhone(@Query("phone") mobile: String): Response<VerifyModel>

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(@Field("code") code: String): Response<AuthResponse?>

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

    @FormUrlEncoded
    @POST("connections")
    suspend fun createConnection(
        @Field("customer_id") customerId: Int,
        @Field("package_id") packageId: Int,
        @Field("is_active") is_active: Int
    ):
            Response<Connection>

    @GET("connections")
    suspend fun getConnections(): Response<List<Connection>>


    @GET("connections/create")
    suspend fun connectionPackages(): Response<PackageCustomerObject>


    @GET("sub-localities")
    suspend fun getSubLocalities(): Response<List<SubLocality>>


    @POST("sub-localities")
    suspend fun createSubLocality(@Body subLocality: SubLocality): Response<SubLocality>

    @POST("area")
    suspend fun createArea(@Body area: Area): Response<Area>

    @GET("area")
    suspend fun getAreas(): Response<List<Area>>

    @GET("admin-profile")
    suspend fun getProfile(): retrofit2.Response<Response<Profile>>

    @POST("admin-profile")
    suspend fun updateProfile(@Body profile: Profile): retrofit2.Response<Response<Profile>>

    @GET("customers/create")
    suspend fun getLists(): Response<SulLocalitiesAreaReligions>

    @GET("messages")
    suspend fun getMessages(): Response<List<Message>>

    @POST("messages")
    suspend fun createMessage(@Body message: Message): Response<Any>

    @GET("bills")
    suspend fun bills(): retrofit2.Response<Response<BillData>>

    @GET("dashboard")
    suspend fun dashboard(@Query("date") date: String): retrofit2.Response<Response<Dashboard>>

    @GET("messages/templates")
    suspend fun templates(): Response<List<Template>>

    @GET("localities")
    suspend fun localities(): Response<List<Area>>


    @POST("localities")
    suspend fun createLocality(@Body area: Area): Response<Area>


}