package app.cabill.admin.view.ui.dashboard

import app.cabill.admin.model.Dashboard
import app.cabill.admin.remote.RetrofitInstance
import retrofit2.Response

class DashboardRepository {
    suspend fun dashboard(date: String): Response<app.cabill.admin.model.Response<Dashboard>> {
        return RetrofitInstance.client().dashboard(date)
    }
}