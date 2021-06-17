package app.cabill.admin.view.ui.agent

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.Agent
import app.cabill.admin.model.Response
import app.cabill.admin.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AgentListViewModel : ViewModel() {
    var agentListLiveData: MutableLiveData<Response<List<Agent>>> = MutableLiveData()
    var loaderLiveData: MutableLiveData<Boolean> = MutableLiveData()
    fun getAgentListObserver(): MutableLiveData<Response<List<Agent>>> {
        return agentListLiveData
    }

    fun getLoaderObserver(): MutableLiveData<Boolean> {
        return loaderLiveData
    }

    fun callAPI(context: Context) {
        loaderLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.client(context).agents()
            agentListLiveData.postValue(response)
            loaderLiveData.postValue(false)
        }

    }
}