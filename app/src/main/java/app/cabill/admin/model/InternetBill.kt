package app.cabill.admin.model


import com.google.gson.annotations.SerializedName

data class InternetBill(
    @SerializedName("agent_id")
    val agentId: Int,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("balance")
    val balance: Int,
    @SerializedName("company_id")
    val companyId: Int,
    @SerializedName("connection")
    val connection: Connection,
    @SerializedName("connection_id")
    val connectionId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_active")
    val isActive: Int,
    @SerializedName("status")
    val status: String?,
    @SerializedName("status_id")
    val statusId: Int,
    @SerializedName("updated_at")
    val updatedAt: String?
)