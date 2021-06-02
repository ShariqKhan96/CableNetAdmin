package app.cabill.admin.view.ui.sublocality

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.Response
import app.cabill.admin.model.SubLocality
import app.cabill.admin.remote.RetrofitInstance

class SubLocalityViewModel : ViewModel() {

    private val createSubLocalityLiveData: MutableLiveData<Response<SubLocality>> =
        MutableLiveData()
    private val getSubLocalityLiveData: MutableLiveData<Response<List<SubLocality>>> =
        MutableLiveData()

    fun createSubLocalityObserver(): MutableLiveData<Response<SubLocality>> {
        return createSubLocalityLiveData
    }

    fun getSubLocalityLiveDataObserver(): MutableLiveData<Response<List<SubLocality>>> {
        return getSubLocalityLiveData
    }

    fun getSubLocalitiesAPI() {
        SubLocalityRepository().list(viewModelScope, getSubLocalityLiveData)
    }

    fun createSubLocalityAPI(subLocality: SubLocality) {
        SubLocalityRepository().create(viewModelScope, createSubLocalityLiveData,subLocality)
    }

}