package app.cabill.admin.view.ui.inventory

import android.content.Context
import app.cabill.admin.model.Dispatcher
import app.cabill.admin.model.Inventory
import app.cabill.admin.model.InventoryModelList
import app.cabill.admin.model.Response
import app.cabill.admin.remote.RetrofitInstance

class InventoryRepository {

    suspend fun create(inventory: Inventory, context: Context): Response<Inventory> {
        return RetrofitInstance.client(context).addProduct(inventory)
    }

    suspend fun update(inventory: Inventory, context: Context): Response<Inventory> {
        return RetrofitInstance.client(context).updateProduct(inventory.id!!, inventory)
    }

    suspend fun list(context: Context): Response<List<Inventory>> {
        return RetrofitInstance.client(context).products()
    }

    suspend fun list_disp(context: Context, id: Int): Response<List<Dispatcher>> {
        return RetrofitInstance.client(context).products_disp()
    }

    suspend fun getModelList(context: Context): Response<InventoryModelList> {
        return RetrofitInstance.client(context = context).getInventoryList()
    }

    suspend fun disp_item(
        context: Context,
        user_id: Int,
        qty: Int,
        notes: String,
        itemId: Int
    ): Response<Any> {
        return RetrofitInstance.client(context).disp_item(itemId, notes, qty, user_id)
    }

}