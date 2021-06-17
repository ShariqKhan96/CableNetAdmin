package app.cabill.agent.view.auth

import android.content.Context
import app.cabill.admin.model.AuthResponse
import app.cabill.admin.model.Response
import app.cabill.admin.model.VerifyModel
import app.cabill.admin.remote.RetrofitInstance


class LoginRepository(val context: Context) {
    suspend fun verify(number: String?): Response<VerifyModel> {
        return RetrofitInstance.client(context).verifyPhone(number!!)
    }

    suspend fun login(code: String): Response<AuthResponse?> {
        return RetrofitInstance.client(context).login(code)
    }

    suspend fun logout() {
        //   return RetrofitInstance.client().login(number!!)
    }
}