package app.cabill.admin.view.ui.bill

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.BillData
import app.cabill.admin.model.Response
import app.cabill.admin.util.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BillViewModel : ViewModel() {
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val billListLiveData = MutableLiveData<Response<BillData>?>()
    fun loadingObserver(): MutableLiveData<Boolean> {
        return loading
    }

    fun billListObserver(): MutableLiveData<Response<BillData>?> {
        return billListLiveData
    }

    fun getBills(context: Context) {
        loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val resposne = BillRepository(context).bill()
            if (resposne.isSuccessful) {
                if (!resposne.body()!!.error)
                    billListLiveData.postValue(resposne.body())
                else billListLiveData.postValue(null)
            } else billListLiveData.postValue(null)
            loading.postValue(false)
        }

    }
}