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
    var isActive: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("package_category_id")
    var packageCategoryId: Int,
    @SerializedName("package_type_id")
    var packageTypeId: Int,
    @SerializedName("updated_at")
    var updatedAt: String,
    @SerializedName("package_type")
    var package_type: NameID?,
    @SerializedName("package_category")
    var package_category: NameID?
)