package app.cabill.admin.model


import com.google.gson.annotations.SerializedName

data class Package(
    @SerializedName("amount")
    var amount: Int,
    @SerializedName("company_id")
    var companyId: Int,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("discount")
    var discount: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("is_active")
    var is_active: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("package_category_id")
    var package_category_id: Int,
    @SerializedName("package_type_id")
    var package_type_id: Int,
    @SerializedName("updated_at")
    var updatedAt: String,
    @SerializedName("package_type")
    var package_type: NameID?,
    @SerializedName("package_category")
    var package_category: NameID?
)