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
    fun list(
        coroutineScope: CoroutineScope,
        liveData: MutableLiveData<Response<List<Area>>>,
        context: Context,
        loading: MutableLiveData<Boolean>
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            //  liveData.postValue(Response(null, "", false, "loading"))
        //   loading.postValue(true)
            val response = RetrofitInstance.client(context).localities()
            response.status = "no_loading"
            liveData.postValue(response)
         // loading.postValue(false)
        }
    }

    fun create(
        coroutineScope: CoroutineScope,
        liveData: MutableLiveData<Response<Area>>,
        area: Area,
        context: Context,
        loading: MutableLiveData<Boolean>
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            // liveData.postValue(Response(null, "", false, "loading"))
          //  loading.postValue(true)
            val response = RetrofitInstance.client(context).createLocality(area)
            response.status = "no_loading"
            liveData.postValue(response)
         //  loading.postValue(false)
        }
    }
}