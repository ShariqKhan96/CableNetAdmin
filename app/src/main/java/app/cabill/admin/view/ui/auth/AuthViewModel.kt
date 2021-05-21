package app.cabill.admin.view.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.Response

class AuthViewModel : ViewModel() {
    private val loginLiveData: MutableLiveData<Response<Int>> = MutableLiveData()
    private val loaderLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val validatorLiveData: MutableLiveData<String> = MutableLiveData()

    fun getLoginObserver(): MutableLiveData<Response<Int>> {
        return loginLiveData
    }

    fun loaderObserver(): MutableLiveData<Boolean> {
        return loaderLiveData
    }

    fun validationObserver(): MutableLiveData<String> {
        return validatorLiveData
    }

    fun login(email: String, password: String) {
        val validation = AuthValidationFactory().validateEmailAndPassword(email, password)
        if (validation == null) {
            AuthRepository().login(viewModelScope, loaderLiveData, loginLiveData, email, password)
        } else {
            validatorLiveData.postValue(validation)
        }
    }
}