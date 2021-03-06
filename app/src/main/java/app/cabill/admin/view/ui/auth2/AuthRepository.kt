package app.cabill.admin.view.ui.auth2

import android.content.Context
import androidx.lifecycle.MutableLiveData
import app.cabill.admin.model.Response
import app.cabill.admin.remote.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthRepository {
    fun login(
        viewModeScope: CoroutineScope,
        loaderLiveData: MutableLiveData<Boolean>,
        loginLiveData: MutableLiveData<Response<Int>>,
        email: String,
        password: String,
        context: Context
    ) {
//        viewModeScope.launch(Dispatchers.IO) {
//            loaderLiveData.postValue(true)
//            val response = RetrofitInstance.client(context).login(email, password)
//            loaderLiveData.postValue(false)
//            loginLiveData.postValue(response)
        //  }
    }
}