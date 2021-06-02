package app.cabill.admin.model


import com.google.gson.annotations.SerializedName

data class SulLocalitiesAreaReligions(
    @SerializedName("localities")
    val localities: List<Locality>,
    @SerializedName("religions")
    val religions: List<Religion>,
    @SerializedName("subLocalities")
    val subLocalities: List<SubLocality>
)