package app.cabill.admin.view.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.Dashboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    fun loadingObserver(): MutableLiveData<Boolean> {
        return loading
    }

    private val dashboardData: MutableLiveData<Dashboard> = MutableLiveData()
    fun dashboardDataObserver(): MutableLiveData<Dashboard> {
        return dashboardData
    }

    fun dashboard(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            val response = DashboardRepository().dashboard(date)
            if (response.isSuccessful) {
                if (!response.body()?.error!!) {
                    dashboardData.postValue(response.body()?.data)
                } else dashboardData.postValue(null)
            } else dashboardData.postValue(null)
        }
    }
}