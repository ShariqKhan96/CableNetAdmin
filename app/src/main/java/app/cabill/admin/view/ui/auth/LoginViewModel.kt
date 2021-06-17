package app.cabill.agent.view.auth

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.Response
import app.cabill.admin.model.VerifyModel
import app.cabill.agent.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel() : BaseViewModel() {
    private val otpValue = MutableLiveData<String>()
    val mobileNumberValue = MutableLiveData<String>()

    fun getMobileObserver(): MutableLiveData<String> {
        return mobileNumberValue
    }

    fun setOtpValue(text: String) {
        otpValue.value = text
    }

    fun getOtpValue(): MutableLiveData<String> {

        return otpValue
    }

    fun setMobileValue(text: String) {
        mobileNumberValue.value = text
    }

    val auth = MutableLiveData<Response<VerifyModel>>()
    fun authObserver(): MutableLiveData<Response<VerifyModel>> {
        return auth
    }

    fun verify(it: String?,context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            val response = LoginRepository(context).verify(it)
            loading.postValue(false)
            auth.postValue(response)
        }
    }

    private val tokenLiveData = MutableLiveData<String?>()
    fun getTokenObserver(): MutableLiveData<String?> {
        return tokenLiveData
    }

    fun login(it: String?,context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            val response = LoginRepository(context).login(it!!)
            loading.postValue(false)
            tokenLiveData.postValue(response.data?.token)
        }
    }
}