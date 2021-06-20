package app.cabill.admin.view.ui.sublocality

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.Response
import app.cabill.admin.model.SubLocality
import app.cabill.admin.remote.RetrofitInstance
import app.cabill.agent.base.BaseViewModel

class SubLocalityViewModel : BaseViewModel() {

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

    fun getSubLocalitiesAPI(context: Context) {
        SubLocalityRepository().list(viewModelScope, getSubLocalityLiveData,context)
    }

    fun createSubLocalityAPI(subLocality: SubLocality,context: Context) {
        SubLocalityRepository().create(viewModelScope, createSubLocalityLiveData,subLocality,context)
    }

}