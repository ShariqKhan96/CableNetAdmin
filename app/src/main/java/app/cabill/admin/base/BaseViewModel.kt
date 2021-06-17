package app.cabill.agent.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
     val loading: MutableLiveData<Boolean> = MutableLiveData()
    public fun loadingObserver(): MutableLiveData<Boolean> {
        return loading
    }
}