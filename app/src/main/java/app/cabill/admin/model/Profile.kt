package app.cabill.admin.model


import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("address")
    val address: Any,
    @SerializedName("cnic")
    val cnic: Any,
    @SerializedName("company_id")
    val companyId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: Any,
    @SerializedName("is_active")
    val isActive: Int,
    @SerializedName("latitude")
    val latitude: Any,
    @SerializedName("longitude")
    val longitude: Any,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: Any,
    @SerializedName("religion_id")
    val religionId: Int,
    @SerializedName("role_id")
    val roleId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("verified_at")
    val verifiedAt: Any
)