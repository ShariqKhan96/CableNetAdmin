package app.cabill.admin.view.ui.connection

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.Connection
import app.cabill.admin.model.Package
import app.cabill.admin.model.PackageCustomerObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConnectionViewModel : ViewModel() {
    private val loaderLiveData: MutableLiveData<Boolean> = MutableLiveData()
    fun loaderLiveDataObserver(): MutableLiveData<Boolean> {
        return loaderLiveData
    }

    private val connectionListLiveData: MutableLiveData<List<Connection>> = MutableLiveData()
    fun getConnectionListObserver(): MutableLiveData<List<Connection>> {
        return connectionListLiveData
    }

    private val createConnectionLiveData = MutableLiveData<Connection?>()
    fun getCreateObserver(): MutableLiveData<Connection?> {
        return createConnectionLiveData
    }

    private val listPackageLiveData = MutableLiveData<PackageCustomerObject>()
    fun getListPackageObserver(): MutableLiveData<PackageCustomerObject> {
        return listPackageLiveData
    }

    fun create(cid: Int, pid: Int,context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            loaderLiveData.postValue(true)
            val response = ConnectionRepository().create(cid, pid,context)
            createConnectionLiveData.postValue(response.data)
            loaderLiveData.postValue(false)
        }

    }

    fun list(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            loaderLiveData.postValue(true)
            val response = ConnectionRepository().list(context)
            connectionListLiveData.postValue(response.data!!)
            loaderLiveData.postValue(false)
        }

    }

        fun listPackages(context: Context) {
            viewModelScope.launch(Dispatchers.IO) {
                loaderLiveData.postValue(true)
                val response = ConnectionRepository().listPackages(context)
                listPackageLiveData.postValue(response)
                loaderLiveData.postValue(false)
            }

        }


}