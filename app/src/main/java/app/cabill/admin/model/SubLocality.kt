package app.cabill.admin.model

import com.google.gson.annotations.SerializedName

data class SubLocality(
    val id: Int, val name: String, val areaId: String, val description: String,
    @SerializedName("locality_id")
    val localityId: Int
) {

}
