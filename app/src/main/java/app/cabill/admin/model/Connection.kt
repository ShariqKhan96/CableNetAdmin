package app.cabill.admin.model


import com.google.gson.annotations.SerializedName

data class Connection(
    @SerializedName("company_id")
    val companyId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("customer")
    val customer: Customer,
    @SerializedName("customer_id")
    val customerId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_active")
    val isActive: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    @SerializedName("package_id")
    val packageId: Int,
    @SerializedName("package")
    val packageX: Package,
    @SerializedName("updated_at")
    val updatedAt: Any
)