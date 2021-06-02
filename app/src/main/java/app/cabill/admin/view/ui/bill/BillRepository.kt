package app.cabill.admin.view.ui.bill

import app.cabill.admin.model.BillData
import app.cabill.admin.remote.RetrofitInstance
import retrofit2.Response

class BillRepository {
    suspend fun bill(): Response<app.cabill.admin.model.Response<BillData>> {
        return RetrofitInstance.client().bills()
    }
}