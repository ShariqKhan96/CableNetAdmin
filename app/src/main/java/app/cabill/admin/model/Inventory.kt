package app.cabill.admin.model

data class Inventory(
    val name: String,
    val id: Int?,
    val type: String,
    val modelID: Int,
    val stock: Int,
    val price: Int,
    val modelName: String,
    val description: String,
    val is_active: Int = 1
) {
}