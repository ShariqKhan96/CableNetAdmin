package app.cabill.admin.view.ui.surrender

import android.util.Log
import app.cabill.admin.model.Surrender
import app.cabill.admin.remote.API
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class SurrenderRepository constructor(val api: API) {
    suspend fun getSurrenderAgentList(): Flow<List<Surrender>> = flow {
        emit(api.getSurrenderList().body()?.data!!)
    }.flowOn(Dispatchers.IO)

    suspend fun updateSurrender(id: Int): Flow<Response<Any>> = flow {
        emit(api.updateSurrender(id))
    }.flowOn(Dispatchers.IO)
}