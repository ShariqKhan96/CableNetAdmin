package app.cabill.admin.model


import com.google.gson.annotations.SerializedName

data class Customer(
    @SerializedName("address")
    val address: String,
    @SerializedName("cnic")
    val cnic: String,
    @SerializedName("company_id")
    val companyId: Int?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String,
    @SerializedName("is_active")
    val isActive: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("locality_id")
    val localityId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("religion_id")
    val religionId: String,
    @SerializedName("sub_locality_id")
    val subLocalityId: String,
    @SerializedName("updated_at")
    val updatedAt: String,
)