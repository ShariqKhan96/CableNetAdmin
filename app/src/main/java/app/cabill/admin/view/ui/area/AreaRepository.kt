package app.cabill.admin.view.ui.area

import android.content.Context
import androidx.lifecycle.MutableLiveData
import app.cabill.admin.model.Area
import app.cabill.admin.model.Response
import app.cabill.admin.remote.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AreaRepository {
    fun list(coroutineScope: CoroutineScope, liveData: MutableLiveData<Response<List<Area>>>,context: Context) {
        coroutineScope.launch(Dispatchers.IO) {
            liveData.postValue(Response(null, "", false, "loading"))
            val response = RetrofitInstance.client(context).localities()
            response.status = "no_loading"
            liveData.postValue(response)
        }
    }

    fun create(
        coroutineScope: CoroutineScope,
        liveData: MutableLiveData<Response<Area>>,
        area: Area,
        context: Context
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            liveData.postValue(Response(null, "", false, "loading"))
            val response = RetrofitInstance.client(context).createLocality(area)
            response.status = "no_loading"
            liveData.postValue(response)
        }
    }
}