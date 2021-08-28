package app.cabill.admin.view.ui.sublocality

import android.content.Context
import androidx.lifecycle.MutableLiveData
import app.cabill.admin.model.Response
import app.cabill.admin.model.SubLocality
import app.cabill.admin.remote.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubLocalityRepository {
    fun create(
        coroutineScope: CoroutineScope,
        createSubLocalityViewModel: MutableLiveData<Response<SubLocality>>,
        subLocality: SubLocality,
        context: Context,
        loading: MutableLiveData<Boolean>
    ) {

        coroutineScope.launch(Dispatchers.IO) {
//            createSubLocalityViewModel.postValue(
//                Response(
//                    null,
//                    "",
//                    false,
//                    "loading"
//                )
//            )
         //   loading.postValue(true)
            val response = RetrofitInstance.client(context).createSubLocality(subLocality)
            response.status = "no_loading"
            createSubLocalityViewModel.postValue(response)
          //  loading.postValue(false)
        }
    }

    fun list(
        coroutineScope: CoroutineScope,
        getSubLocalityLiveData: MutableLiveData<Response<List<SubLocality>>>,
        context: Context,
        loading: MutableLiveData<Boolean>
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            //  getSubLocalityLiveData.postValue(Response(null, "", false, "loading"))
            //loading.postValue(true)
            val response = RetrofitInstance.client(context).getSubLocalities()
            response.status = "no_loading"
            getSubLocalityLiveData.postValue(response)
          // loading.postValue(false)
        }
    }
}