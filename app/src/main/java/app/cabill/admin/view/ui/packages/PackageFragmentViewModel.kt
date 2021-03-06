package app.cabill.admin.view.ui.packages

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.CategoriesAndTypes
import app.cabill.admin.model.Package
import app.cabill.admin.model.Response

class PackageFragmentViewModel : ViewModel() {
    var loaderLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var packageListMutableLiveData: MutableLiveData<Response<List<Package>>> = MutableLiveData()
    var packageCreateLiveData: MutableLiveData<Response<Package>> = MutableLiveData()
    var packageUpdateLiveData: MutableLiveData<Response<Package>> = MutableLiveData()
    var categoriesTypesLiveData: MutableLiveData<Response<CategoriesAndTypes>> = MutableLiveData()
    fun getPackageListObserver(): MutableLiveData<Response<List<Package>>> {
        return packageListMutableLiveData
    }

    fun catTypesObserver(): MutableLiveData<Response<CategoriesAndTypes>> {
        return categoriesTypesLiveData
    }

    fun getLoaderObserver(): MutableLiveData<Boolean> {
        return loaderLiveData
    }

    fun getPackageCreateLiveDataObserver(): MutableLiveData<Response<Package>> {
        return packageCreateLiveData
    }

    fun getPackageUpdateObserver(): MutableLiveData<Response<Package>> {
        return packageUpdateLiveData
    }

    fun updatePackage(pack: Package, context: Context) {
        PackageRepository(
            viewModelScope,
            packageListMutableLiveData,
            packageCreateLiveData,
            loaderLiveData
        ).updatePackage(pack, packageUpdateLiveData, context)

    }

    fun getPackages(context: Context) {
        PackageRepository(
            viewModelScope,
            packageListMutableLiveData,
            packageCreateLiveData,
            loaderLiveData
        ).getPackageList(context)
    }

    fun createPackage(pack: Package, context: Context) {
        PackageRepository(
            viewModelScope,
            packageListMutableLiveData,
            packageCreateLiveData,
            loaderLiveData
        ).createPackage(pack, context)

    }

    fun categoriesAndTypes(context: Context) {
        PackageRepository(
            viewModelScope,
            packageListMutableLiveData,
            packageCreateLiveData,
            loaderLiveData
        ).getCatAndTypesList(categoriesTypesLiveData, context)
    }
}