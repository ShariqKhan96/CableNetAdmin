package app.cabill.admin.view.ui.customer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.Customer
import app.cabill.admin.model.Response
import app.cabill.admin.model.SulLocalitiesAreaReligions
import app.cabill.admin.util.Utils

class CustomerViewModel : ViewModel() {
    private val customerListLiveData: MutableLiveData<Response<List<Customer>>> = MutableLiveData()
    private val customerCreateLiveData: MutableLiveData<Response<Customer>> = MutableLiveData()
    private val loaderLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val listsLiveData: MutableLiveData<Response<SulLocalitiesAreaReligions>> =
        MutableLiveData()

    fun customerListLiveDataObserver(): MutableLiveData<Response<List<Customer>>> {
        return customerListLiveData
    }

    fun customerCreateLiveDataObserver(): MutableLiveData<Response<Customer>> {
        return customerCreateLiveData
    }

    fun loaderLiveDataObserver(): MutableLiveData<Boolean> {
        return loaderLiveData
    }

    fun getNecessaryDataObserver(): MutableLiveData<Response<SulLocalitiesAreaReligions>> {
        return listsLiveData
    }

     fun getList() {
        CustomerRepository().getList(viewModelScope, loaderLiveData, customerListLiveData)
    }

     fun createCustomer(customer: Customer) {
        CustomerRepository().createCustomer(
            viewModelScope,
            loaderLiveData,
            customerCreateLiveData,
            customer
        )
    }

     fun getRelSubLoc() {
        CustomerRepository().getNecessaryData(viewModelScope, loaderLiveData, listsLiveData)
    }
}