package app.cabill.admin.model


import com.google.gson.annotations.SerializedName

data class Dashboard(
    @SerializedName("monthlyConnections")
    val monthlyConnections: Int,
    @SerializedName("monthlyEarnings")
    val monthlyEarnings: Int,
    @SerializedName("monthlyReceivables")
    val monthlyReceivables: Int,
    @SerializedName("monthlyReceived")
    val monthlyReceived: Int,
    @SerializedName("totalEarnings")
    val totalEarnings: Int
)