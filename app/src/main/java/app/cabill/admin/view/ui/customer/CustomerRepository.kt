package app.cabill.admin.view.ui.customer

import androidx.lifecycle.MutableLiveData
import app.cabill.admin.model.Customer
import app.cabill.admin.model.Response
import app.cabill.admin.model.SulLocalitiesAreaReligions
import app.cabill.admin.remote.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerRepository {
    fun getList(
        viewModelScope: CoroutineScope, loaderLiveData: MutableLiveData<Boolean>,
        customerListLiveData: MutableLiveData<Response<List<Customer>>>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            loaderLiveData.postValue(true)
            val response = RetrofitInstance.client().getCustomers()
            customerListLiveData.postValue(response)
            loaderLiveData.postValue(false)
        }
    }

    fun createCustomer(
        viewModelScope: CoroutineScope, loaderLiveData: MutableLiveData<Boolean>,
        createCustomerLiveData: MutableLiveData<Response<Customer>>, customer: Customer
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            loaderLiveData.postValue(true)
            val response = RetrofitInstance.client().createCustomer(customer)
            createCustomerLiveData.postValue(response)
            loaderLiveData.postValue(false)
        }
    }

    fun getNecessaryData(
        coroutineScope: CoroutineScope, loaderLiveData: MutableLiveData<Boolean>,
        liveDta: MutableLiveData<Response<SulLocalitiesAreaReligions>>
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            loaderLiveData.postValue(true)
            val response = RetrofitInstance.client().getLists()
            liveDta.postValue(response)
            loaderLiveData.postValue(false)
        }
    }


}