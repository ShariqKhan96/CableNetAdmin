package app.cabill.admin.view.ui.messaging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.Message
import app.cabill.admin.model.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageViewModel : ViewModel() {
    val messageListLiveData: MutableLiveData<Response<List<Message>>> = MutableLiveData()
    val messageCreateLiveData: MutableLiveData<Response<Message>> = MutableLiveData()
    val loaderLiveData = MutableLiveData<Boolean>()
    fun messageLiveDataObserver(): MutableLiveData<Response<List<Message>>> {
        return messageListLiveData
    }

    fun loaderObserver(): MutableLiveData<Boolean> {
        return loaderLiveData
    }

    fun messageCreateObserver(): MutableLiveData<Response<Message>> {
        return messageCreateLiveData
    }

    fun create(message: Message) {
        viewModelScope.launch(Dispatchers.IO) {
            loaderLiveData.postValue(true)
            messageCreateLiveData.postValue(MessageRepository().create(message))
            loaderLiveData.postValue(false)
        }
    }

    fun list() {
        viewModelScope.launch(Dispatchers.IO) {
            loaderLiveData.postValue(true)
            val response = MessageRepository().list()
            loaderLiveData.postValue(false)
            messageListLiveData.postValue(response)
        }
    }
    fun templates(){
        viewModelScope.launch(Dispatchers.IO){

        }
    }
}