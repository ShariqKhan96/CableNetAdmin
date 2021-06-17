package app.cabill.admin.view.ui.customer

import android.content.Context
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
        customerListLiveData: MutableLiveData<Response<List<Customer>>>,
        context: Context
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            loaderLiveData.postValue(true)
            val response = RetrofitInstance.client(context).getCustomers()
            customerListLiveData.postValue(response)
            loaderLiveData.postValue(false)
        }
    }

    fun createCustomer(
        viewModelScope: CoroutineScope, loaderLiveData: MutableLiveData<Boolean>,
        createCustomerLiveData: MutableLiveData<Response<Customer>>, customer: Customer,context: Context
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            loaderLiveData.postValue(true)
            val response = RetrofitInstance.client(context).createCustomer(customer)
            createCustomerLiveData.postValue(response)
            loaderLiveData.postValue(false)
        }
    }

    fun getNecessaryData(
        coroutineScope: CoroutineScope, loaderLiveData: MutableLiveData<Boolean>,
        liveDta: MutableLiveData<Response<SulLocalitiesAreaReligions>>,context: Context
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            loaderLiveData.postValue(true)
            val response = RetrofitInstance.client(context).getLists()
            liveDta.postValue(response)
            loaderLiveData.postValue(false)
        }
    }


}