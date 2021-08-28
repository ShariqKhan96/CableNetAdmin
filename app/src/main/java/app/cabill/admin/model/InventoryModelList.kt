package app.cabill.admin.model

import com.google.gson.annotations.SerializedName

data class InventoryModelList(
    @SerializedName("models")
    val models: List<NameID>
) {

}