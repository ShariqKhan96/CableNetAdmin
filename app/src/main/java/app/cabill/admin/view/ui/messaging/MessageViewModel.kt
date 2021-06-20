package app.cabill.admin.view.ui.messaging

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.Message
import app.cabill.admin.model.Response
import app.cabill.admin.model.Template
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageViewModel : ViewModel() {
    val messageListLiveData: MutableLiveData<Response<List<Message>>> = MutableLiveData()
    val messageCreateLiveData: MutableLiveData<Response<Any>> = MutableLiveData()
    val loaderLiveData = MutableLiveData<Boolean>()
    fun messageLiveDataObserver(): MutableLiveData<Response<List<Message>>> {
        return messageListLiveData
    }

    fun loaderObserver(): MutableLiveData<Boolean> {
        return loaderLiveData
    }

    fun messageCreateObserver(): MutableLiveData<Response<Any>> {
        return messageCreateLiveData
    }

    val templateData: MutableLiveData<List<Template>> = MutableLiveData()
    fun getTemplatesObserver(): MutableLiveData<List<Template>> {
        return templateData
    }

    fun getTemplates(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            loaderLiveData.postValue(true)
            val resposne = MessageRepository().getTemplates(context)
            templateData.postValue(resposne.data)
            loaderLiveData.postValue(false)
        }
    }

    fun create(message: Message, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            loaderLiveData.postValue(true)
            messageCreateLiveData.postValue(MessageRepository().create(message, context))
            loaderLiveData.postValue(false)
        }
    }

    fun list(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            loaderLiveData.postValue(true)
            val response = MessageRepository().list(context)
            loaderLiveData.postValue(false)
            messageListLiveData.postValue(response)
        }
    }

    fun templates() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}