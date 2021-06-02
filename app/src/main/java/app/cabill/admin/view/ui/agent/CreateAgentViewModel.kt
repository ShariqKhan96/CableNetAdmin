package app.cabill.admin.view.ui.agent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.Agent
import app.cabill.admin.model.Response
import app.cabill.admin.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAgentViewModel : ViewModel() {
    var createLiveData: MutableLiveData<Response<Agent>> = MutableLiveData()
    var updateLiveData: MutableLiveData<Response<Agent>> = MutableLiveData()
    var loader: MutableLiveData<Boolean> = MutableLiveData()
    fun createAgentObserver(): MutableLiveData<Response<Agent>> {
        return createLiveData
    }

    fun updateAgentObserver(): MutableLiveData<Response<Agent>> {
        return updateLiveData
    }

    fun createLoaderObserver(): MutableLiveData<Boolean> {
        return loader
    }

    fun create(agent: Agent) {
        loader.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.client().createAgent(agent)
            if (response.isSuccessful) {
                createLiveData.postValue(response.body())
                loader.postValue(false)
            } else {
                createLiveData.postValue(null)
                loader.postValue(false)
            }

        }
    }

    fun update(agent: Agent?, id: Int) {
        loader.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.client().updateAgent(id, agent)
            updateLiveData.postValue(response)
            loader.postValue(false)
        }
    }


}