package app.cabill.admin.view.ui.packages

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

    fun getPackageList() {
        loaderLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.client().packages()
            packageListMutableLiveData.postValue(response)
            loaderLiveData.postValue(false)
        }
    }

    fun createPackage(pack: Package) {
        loaderLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.client().createPackage(pack)
            loaderLiveData.postValue(false)
            packageCreateMutableLiveData.postValue(response)
        }
    }

    fun updatePackage(pack: Package, packageUpdateLiveData: MutableLiveData<Response<Package>>) {
        loaderLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.client().updatePackage(pack, pack.id)
            loaderLiveData.postValue(false)
            packageUpdateLiveData.postValue(response)
        }
    }

    fun getCatAndTypesList(typesCatsLiveData: MutableLiveData<Response<CategoriesAndTypes>>) {
        loaderLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.client().categoriesAndTypesList()
            typesCatsLiveData.postValue(response)
            loaderLiveData.postValue(false)
        }
    }


}