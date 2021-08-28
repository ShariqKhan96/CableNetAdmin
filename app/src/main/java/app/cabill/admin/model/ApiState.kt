package app.cabill.admin.model

sealed class ApiState{
    object Loading : ApiState()
    class Error(val error: Throwable) : ApiState()
    class Success<out T:Any>(val data: T) : ApiState()
    object Idle : ApiState()
}
