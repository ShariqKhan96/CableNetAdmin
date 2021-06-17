package app.cabill.admin.view.ui.dashboard

import android.content.Context
import app.cabill.admin.model.Dashboard
import app.cabill.admin.remote.RetrofitInstance
import retrofit2.Response

class DashboardRepository {
    suspend fun dashboard(date: String,context: Context): Response<app.cabill.admin.model.Response<Dashboard>> {
        return RetrofitInstance.client(context).dashboard(date)
    }
}