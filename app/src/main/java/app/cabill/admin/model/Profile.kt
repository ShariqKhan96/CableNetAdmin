package app.cabill.admin.model


import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("address")
    var address: Any,
    @SerializedName("cnic")
    var cnic: Any,
    @SerializedName("company_id")
    var companyId: Int,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("image")
    var image: Any,
    @SerializedName("is_active")
    var isActive: Int,
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("longitude")
    var longitude: Double,
    @SerializedName("name")
    var name: String,
    @SerializedName("phone")
    var phone: Any,
    @SerializedName("religion_id")
    var religionId: Int,
    @SerializedName("role_id")
    var roleId: Int,
    @SerializedName("updated_at")
    var updatedAt: String,
    @SerializedName("verified_at")
    var verifiedAt: Any,
    var _method:String
)