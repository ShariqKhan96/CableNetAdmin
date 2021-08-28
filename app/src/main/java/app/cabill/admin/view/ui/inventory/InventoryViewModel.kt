package app.cabill.admin.view.ui.inventory

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.Dispatcher
import app.cabill.admin.model.Inventory
import app.cabill.admin.model.InventoryModelList
import app.cabill.agent.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InventoryViewModel : BaseViewModel() {
    private val create: MutableLiveData<Inventory> = MutableLiveData()
    val update: MutableLiveData<Inventory> = MutableLiveData()
    private val list: MutableLiveData<List<Inventory>> = MutableLiveData()
    val modelList: MutableLiveData<InventoryModelList> = MutableLiveData()

    fun getCreateObserver(): MutableLiveData<Inventory> {
        return create
    }

    fun getListObserver(): MutableLiveData<List<Inventory>> {
        return list
    }

    fun create(inventory: Inventory, context: Context) {
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            loading.postValue(true)
            val response = InventoryRepository().create(inventory, context)
            loading.postValue(false)
            create.postValue(response.data)
        }
    }

    fun update(inventory: Inventory, context: Context) {
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            loading.postValue(true)
            val response = InventoryRepository().update(inventory, context)
            loading.postValue(false)
            update.postValue(response.data)
        }
    }

    val dispatch = MutableLiveData<List<Dispatcher>>()
    fun dispatcherList(context: Context, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            val response = InventoryRepository().list_disp(context, id)
            loading.postValue(false)
            dispatch.postValue(response.data!!)
        }
    }

    val dispatchItem = MutableLiveData<Any?>()
    fun dispatchItem(context: Context, user_id: Int, qty: Int, notes: String, itemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            val response = InventoryRepository().disp_item(context, user_id, qty, notes, itemId)
            loading.postValue(false)
            dispatchItem.postValue(response.data)
        }
    }

    fun list(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            val response = InventoryRepository().list(context)
            loading.postValue(false)
            list.postValue(response.data)
        }
    }

    fun dataForList(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            val response = InventoryRepository().getModelList(context)
            loading.postValue(false)
            modelList.postValue(response.data)
        }
    }
}