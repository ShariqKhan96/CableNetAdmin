package app.cabill.admin.view.ui.messaging

import androidx.lifecycle.MutableLiveData
import app.cabill.admin.model.Message
import app.cabill.admin.model.Response
import app.cabill.admin.remote.RetrofitInstance
import kotlinx.coroutines.CoroutineScope

class MessageRepository {
    suspend fun list(): Response<List<Message>> {
        return RetrofitInstance.client().getMessages()
    }
    suspend fun create(message: Message):Response<Message>{
        return RetrofitInstance.client().createMessage(message)
    }
}