package app.cabill.admin.model


import com.google.gson.annotations.SerializedName

data class BillData(
    @SerializedName("internetBills")
    val internetBills: List<InternetBill>,
    @SerializedName("cableBills")
    val cableBills: List<CableBill>
)