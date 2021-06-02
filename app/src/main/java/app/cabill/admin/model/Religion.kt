package app.cabill.admin.model


import com.google.gson.annotations.SerializedName

data class Religion(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_active")
    val isActive: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String
)