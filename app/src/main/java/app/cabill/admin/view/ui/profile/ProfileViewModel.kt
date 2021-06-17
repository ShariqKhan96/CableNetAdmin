package app.cabill.admin.view.ui.profile

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.Profile
import app.cabill.admin.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private var profileLiveData: MutableLiveData<Response<Profile>> = MutableLiveData()
    private var profileUpdateLiveData: MutableLiveData<Response<Profile>> = MutableLiveData()
    val loaderLiveData = MutableLiveData<Boolean>()
    fun getProfileObserver(): MutableLiveData<Response<Profile>> {
        return profileLiveData
    }

    fun getProfileUpdateObserver(): MutableLiveData<Response<Profile>> {
        return profileUpdateLiveData
    }

    fun getProfile(context: Context) {
        viewModelScope.launch(Dispatchers.IO)
        {
            loaderLiveData.postValue(true)
            val response = ProfileRepository(context).getProfile()
            if (response.isSuccessful) {
                if (!response.body()?.error!!) {
                    profileLiveData.postValue(response.body())
                } else profileLiveData.postValue(null)
            } else profileLiveData.postValue(null)

            loaderLiveData.postValue(false)
        }
    }

    fun updateProfile(profile: Profile,context: Context) {
        viewModelScope.launch(Dispatchers.IO)
        {
            loaderLiveData.postValue(true)
            val response = ProfileRepository(context).updateProfile(profile)
            if (response.isSuccessful) {
                if (!response.body()?.error!!) {
                    profileUpdateLiveData.postValue(response.body())
                } else profileUpdateLiveData.postValue(null)
            } else profileUpdateLiveData.postValue(null)

            loaderLiveData.postValue(false)
        }
    }
}