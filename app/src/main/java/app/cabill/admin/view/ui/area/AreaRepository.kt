package app.cabill.admin.view.ui.area

import androidx.lifecycle.MutableLiveData
import app.cabill.admin.model.Area
import app.cabill.admin.model.Response
import app.cabill.admin.remote.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AreaRepository {
    fun list(coroutineScope: CoroutineScope, liveData: MutableLiveData<Response<List<Area>>>) {
        coroutineScope.launch(Dispatchers.IO) {
            liveData.postValue(Response(null, "", false, "loading"))
            val response = RetrofitInstance.client().getAreas()
            response.status = "no_loading"
            liveData.postValue(response)
        }
    }

    fun create(
        coroutineScope: CoroutineScope,
        liveData: MutableLiveData<Response<Area>>,
        area: Area
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            liveData.postValue(Response(null, "", false, "loading"))
            val response = RetrofitInstance.client().createArea(area)
            response.status = "no_loading"
            liveData.postValue(response)
        }
    }
}