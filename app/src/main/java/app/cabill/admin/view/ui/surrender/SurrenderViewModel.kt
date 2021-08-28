package app.cabill.admin.view.ui.surrender

import android.util.Log
import androidx.lifecycle.viewModelScope
import app.cabill.admin.model.ApiState
import app.cabill.admin.model.Surrender
import app.cabill.agent.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response

class SurrenderViewModel(val repository: SurrenderRepository) : BaseViewModel() {
    private val surrenderListPostState: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Idle)
    val _surrenderListPostState: StateFlow<ApiState> = surrenderListPostState

    fun getSurrenderList() {
        viewModelScope.launch {
            Log.d("COROUTINE", "${Thread.currentThread().name}")
            surrenderListPostState.value = ApiState.Loading
            val response = repository.getSurrenderAgentList()
            response.catch { e ->
                surrenderListPostState.value = ApiState.Error(e)
            }.collect {
                Log.d("COROUTINE", "${Thread.currentThread().name}")
                surrenderListPostState.value = ApiState.Success(it)
            }
        }

    }

    val surrenderPostState: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Idle)
    fun updateSurrender(id: Int) {
        viewModelScope.launch {
            surrenderPostState.value = ApiState.Loading
            val response = repository.updateSurrender(id)
            response.catch { e ->
                surrenderPostState.value = ApiState.Error(e)
            }.collect {
                surrenderPostState.value = ApiState.Success(it)
            }
        }
    }

}