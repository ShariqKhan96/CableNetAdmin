package app.cabill.admin.view.ui.messaging

import android.content.Context
import androidx.lifecycle.MutableLiveData
import app.cabill.admin.model.Message
import app.cabill.admin.model.Response
import app.cabill.admin.remote.RetrofitInstance
import kotlinx.coroutines.CoroutineScope

class MessageRepository {
    suspend fun list(context: Context): Response<List<Message>> {
        return RetrofitInstance.client(context).getMessages()
    }
    suspend fun create(message: Message,context: Context):Response<Message>{
        return RetrofitInstance.client(context).createMessage(message)
    }
}