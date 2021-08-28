package app.cabill.admin.view.ui.packages

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.cabill.admin.model.CategoriesAndTypes
import app.cabill.admin.model.Package
import app.cabill.admin.model.Response
import app.cabill.admin.remote.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PackageRepository(
    val viewModelScope: CoroutineScope,
    val packageListMutableLiveData: MutableLiveData<Response<List<Package>>>,
    val packageCreateMutableLiveData: MutableLiveData<Response<Package>>,
    val loaderLiveData: MutableLiveData<Boolean>
) {

    fun getPackageList(context: Context) {
        loaderLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.client(context).packages()
            packageListMutableLiveData.postValue(response)
            loaderLiveData.postValue(false)
        }
    }

    fun createPackage(pack: Package,context: Context) {
        loaderLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.client(context).createPackage(
                pack.name,
                pack.amount.toString(),
                pack.discount.toString(),
                pack.package_type_id,
                pack.package_category_id,
                1
            )
            loaderLiveData.postValue(false)
            packageCreateMutableLiveData.postValue(response)
        }
    }

    fun updatePackage(pack: Package, packageUpdateLiveData: MutableLiveData<Response<Package>>,context: Context) {
        loaderLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.client(context).updatePackage(pack, pack.id!!)
            loaderLiveData.postValue(false)
            packageUpdateLiveData.postValue(response)
        }
    }

    fun getCatAndTypesList(typesCatsLiveData: MutableLiveData<Response<CategoriesAndTypes>>,context: Context) {
        loaderLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.client(context).categoriesAndTypesList()
            typesCatsLiveData.postValue(response)
            loaderLiveData.postValue(false)
        }
    }


}