package app.cabill.admin.model

data class Dispatcher(
    val inventory_item_id: Int,
    val user_id: Int,
    val quantity: Int,
    val notes: String?,
    val user: Agent,
    val inventory_item: Inventory
) {
}