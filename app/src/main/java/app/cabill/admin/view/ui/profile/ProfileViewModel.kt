package app.cabill.admin.view.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.Profile
import app.cabill.admin.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private var profileLiveData: MutableLiveData<Response<Profile>> = MutableLiveData()
    val loaderLiveData = MutableLiveData<Boolean>()
    fun getProfileObserver(): MutableLiveData<Response<Profile>> {
        return profileLiveData
    }

    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO)
        {
            loaderLiveData.postValue(true)
            val response = ProfileRepository().getProfile()
            if (response.isSuccessful) {
                if (!response.body()?.error!!) {
                    profileLiveData.postValue(response.body())
                } else profileLiveData.postValue(null)
            } else profileLiveData.postValue(null)

            loaderLiveData.postValue(false)
        }
    }
}