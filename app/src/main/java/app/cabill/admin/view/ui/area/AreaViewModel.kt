package app.cabill.admin.view.ui.area

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.Area
import app.cabill.admin.model.Response
import app.cabill.agent.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AreaViewModel : BaseViewModel() {

    private val areaList = MutableLiveData<Response<List<Area>>>()
    fun areaListObserver(): MutableLiveData<Response<List<Area>>> {
        return areaList
    }

    fun list(context: Context) {
     //   loading.postValue(true)
        AreaRepository().list(viewModelScope, areaList, context,loading)
      //  loading.postValue(false)
    }

    private val createArea = MutableLiveData<Response<Area>>()

    fun createAreaObserver(): MutableLiveData<Response<Area>> {
        return createArea
    }

    fun create(context: Context, area: Area) {
        //loading.postValue(true)
        AreaRepository().create(viewModelScope, createArea, area, context,loading)
       // loading.postValue(false)
    }
}